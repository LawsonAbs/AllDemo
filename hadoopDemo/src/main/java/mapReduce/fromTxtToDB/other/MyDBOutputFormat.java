package mapReduce.fromTxtToDB.other;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MyDBOutputFormat {
    // 定义输出路径
    private static final String INPUT_PATH = "hdfs://192.168.211.3:9000/input/test.txt";

    public static void main(String[] args) {
        try {
            // 创建配置信息
            Configuration conf = new Configuration();
            // 通过conf创建数据库配置信息
            DBConfiguration.configureDB(
                    conf,
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://192.168.211.3:3306/mydatabase",
                    "root",
                    "root");

            /*// 创建文件系统
            FileSystem fileSystem = FileSystem.get(new URI(OUT_PATH), conf);

            // 如果输出目录存在就删除
            if (fileSystem.exists(new Path(OUT_PATH))) {
                fileSystem.delete(new Path(OUT_PATH), true);
            }*/

            // 创建任务
            Job job = new Job(conf, MyDBOutputFormat.class.getName());

            //1.1 设置输入数据格式化的类
            job.setInputFormatClass(TextInputFormat.class);

            //设置数据来源
            FileInputFormat.setInputPaths(job, INPUT_PATH);

            //1.2 设置自定义的Mapper类和Mapper输出的key和value的类型
            job.setMapperClass(MyDBOutputFormatMapper.class);
            job.setMapOutputKeyClass(LongWritable.class);
            job.setMapOutputValueClass(User.class);

            // 1.3 设置分区和reduce数量(reduce的数量和分区的数量对应，因为分区只有一个，所以reduce的个数也设置为一个)
            job.setPartitionerClass(HashPartitioner.class);
            job.setNumReduceTasks(1);

            // 1.4 排序、分组
            // 1.5 归约
            // 2.1 Shuffle把数据从Map端拷贝到Reduce端

            // 2.2 指定Reducer类和输出key和value的类型
            job.setReducerClass(MyDBOutputFormatReducer.class);

            // 2.3 设置输出的格式化类
            job.setOutputFormatClass(DBOutputFormat.class);

            //设置将reduce端输出的key值对应user表
            DBOutputFormat.setOutput(job, "user", new String[] { "id", "name" });

            // 提交作业 然后关闭虚拟机正常退出
            System.exit(job.waitForCompletion(true) ? 0 : 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  自定义Mapper类
     *  注意观察：
     *  KEYIN:LongWritable
     *  KEYOUT:Text
     *  VALUEIN:LongWritable
     *  VALUEOUT:User【注意这里是自定义的User类型，而不是普通类型】
     */
    public static class MyDBOutputFormatMapper extends
            Mapper<LongWritable, Text, LongWritable, User>{

        //创建写出去的value类型
        private User user = new User();

        @Override
        protected void map(LongWritable key,
                           Text value,
                           Mapper<LongWritable, Text, LongWritable, User>.Context context)
                throws IOException,InterruptedException {
            //对value进行切分  这里的value是读文件时读出的每一行
            String[] splits = value.toString().split("\t");
            //封装user对象  --> 因为文件结构是熟悉的，所以直接赋值
            user.setId(Integer.parseInt(splits[0]));
            user.setName(splits[1]);

            //把user对象作为value写出去        //key是没有变化的LongWritable类型
            context.write(key, user);
        }
    }

    /**
     * 关键是写出去的key要为User对象
     * 写出去的value值无所谓，为NullWritable都可以[非常重要！！]
     * @version
     */
    public static class MyDBOutputFormatReducer extends
            Reducer<LongWritable, User, User, NullWritable> {

        @Override
        protected void reduce(LongWritable key,
                              Iterable<User> values,
                              /*如下的形式1也是可以的，只是为了尽可能的全面的显示信息
                              1. Reducer<LongWritable, User, User, NullWritable>.Context context*/
                              Context context)
                throws IOException,InterruptedException {
            for (User user : values){
                /*这里的两层new Text()作用不相同  如下的形式1与正常语句等价
                1. context.write(user, new Text(new Text(user.getName())));  = context.write(user,null)
                =  context.write(user, new Text(new Text(user.getName())))

                2.因为最后是要写出user 对象【之所以选择将写出结果放到key，而不是value中。是因为键值对，键不能为空】
                */
                context.write(user,null);
            }
        }
    }
}

/**
 * 因为使用了DBOutputFormat，所以要实现DBWritable接口
 * 因为是VALUE，必须要序列化传输，所以实现了Writable接口
 * 自定义输出对象
 */
class User implements Writable,DBWritable {
    private int id;
    private String name;
    // 无参构造函数
    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    // 实现DBWritable接口要实现的方法
    public void readFields(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt(1);
        this.name = resultSet.getString(2);
    }

    @Override
    // 实现DBWritable接口要实现的接口
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, this.id);
        preparedStatement.setString(2, this.name);
    }



    @Override
    // 实现Writable接口要实现的接口
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        Text.writeString(dataOutput, this.name);
    }

    @Override
    // 实现Writable接口要实现的方法
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.name = Text.readString(dataInput);
    }



    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }
}