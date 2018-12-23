package mapReduce.one;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;


//分析网络服务器上的Apache日志，统计每个IP访问资源的次数，并将结果写入到mysql数据库中。
public class IPCountMR {
    public static class IPCountMap extends Mapper<LongWritable, Text, Text, IntWritable> {
        private IntWritable one = new IntWritable(1);
        private Text k = new Text();
        @Override
        public void map(LongWritable key, Text value, Context context)  throws IOException, InterruptedException {
            String line = value.toString();
            //判断该行数据是否合法
            TextLine textLine = new TextLine(line);
            if (textLine.isRight()) {
                k.set(textLine.getIp());
                context.write(k, one);
            }
        }
    }

    /**1.因为reduce的keyOut,valueOut的类型决定输出的类型。所以说，如果你想将结果输出到mysql。必须这样实现
     *
     */
    public static class IPCountReducer extends Reducer<Text, IntWritable, TblsWritable, TblsWritable> {
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            /*1.输出的键类型为TblsWritable，值类型是null！！！
            2.因为最后传递的是一个TblsWritable类型的对象，所以需要new 一下。*/
            TblsWritable tbl = new TblsWritable(key.toString(),sum);
            context.write(tbl, null);
        }
    }
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.10.1:3306/hadoop", "root", "root");

        // 新建一个任务
        Job job = Job.getInstance(conf, "ip-count");
        // 设置主类
        job.setJarByClass(IPCountMR.class);

        // 输入路径
        FileInputFormat.addInputPath(job, new Path(args[0]));

        // MyMapper
        job.setMapperClass(IPCountMap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // Reducer
        job.setReducerClass(IPCountReducer.class);

        // 输出的key,value类型     输出格式（DB类型）
        job.setOutputKeyClass(TblsWritable.class);
        job.setOutputValueClass(TblsWritable.class);
        job.setOutputFormatClass(DBOutputFormat.class);

        // 输出到哪些表、字段
        DBOutputFormat.setOutput(job, "log", "ip", "count");

        // 添加mysql数据库jar
        // job.addArchiveToClassPath(new Path("/lib/mysql/mysql-connector-java-5.1.18-bin.jar"));

        //提交任务
        job.waitForCompletion(true);
    }
}