package mapReduce.fromHBaseToFile;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


public class HBaseWCJob {
    public static final String tableName = "mytable";
    public static final String outputFilePath ="hdfs://192.168.211.4:9000/output/mytable";

    public static Configuration conf = HBaseConfiguration.create();
    static{
        //this configuration is very important
        conf.set("hbase.master", "192.168.211.4:60000");
        conf.set("hbase.zookeeper.quorum", "192.168.211.4");
        conf.set("hbase.zookeeper.property.clientPort","2181");
    }
    /**
     *
     * @param tableName table name you want to create
     * @param columnFamily column name you want to add
     * @throws IOException
     */
    public static void createHBaseTable(String tableName,String ...columnFamily) throws IOException {
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));

        HBaseAdmin admin = new HBaseAdmin(conf);
        if (admin.tableExists(tableName)) {
            System.out.println("table exists, trying to recreate table......");
            //admin.disableTable(tableName);
            //admin.deleteTable(tableName);
            return ;
        }
        System.out.println("create new table:" + tableName);
        for(String string:columnFamily){
            //Adds a column family.
            hTableDescriptor.addFamily(new HColumnDescriptor(string));
        }
        admin.createTable(hTableDescriptor);
    }

    public static void main(String[] args)
            throws IOException, InterruptedException, ClassNotFoundException {
        //create a Hbase table with a column named cf
        createHBaseTable(tableName,"cf");
        putToTable(tableName, "cf");
        Scan scan = new Scan();

        //Get all columns from the specified family
        //scan.addFamily(Byte.getBytes("cf"));与下面这句作用相同
        scan.addFamily("cf".getBytes());

        Job job = Job.getInstance(conf, "hbase_word_count");
        job.setJarByClass(HBaseWCJob.class);

        //输入的类是TableInputFormat
        job.setInputFormatClass(TableInputFormat.class);

        //配置作业对象
        //HBaseMapper.class :The mapper class to use.
        //HBaseReducer.class :The reducer class to use.
        TableMapReduceUtil.initTableMapperJob(
                "mytable",// The table name to read from
                scan,//The scan instance with the columns, time range etc.
                HBaseMapper.class, //The mapper class to use.
                Text.class, //The class of the output key.
                IntWritable.class, //The class of the output value.
                job);//The current job to adjust.  Make sure the passed job is carrying all necessary HBase configuration.

        //set the ReducerClass
        job.setReducerClass(HBaseReducer.class);

        //set output file
        //care!The class of FileOutputFormat is in package of mapreduce
        //judge the output file catalog have exists
        FileOutputFormat.setOutputPath(job, new Path(outputFilePath));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static void putToTable(String tableName,String columnName){
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            Admin admin = connection.getAdmin();
            //Table:Used to communicate with a single HBase table.
            Table table = connection.getTable(TableName.valueOf(tableName));
            String []rowName = {"first","second","third","fourth"};
            String[] value = {"hello spark", "hi hadoop", "hello hbase", "hello kafka"};
            for(int i = 0;i< rowName.length;i++){
                Put put = new Put(Bytes.toBytes(rowName[i]));
                put.addColumn(
                        Bytes.toBytes("cf"),
                        Bytes.toBytes("keyWord"),
                        Bytes.toBytes(value[i]));
                table.put(put);
            }
            System.out.println("put data to "+tableName+" successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
