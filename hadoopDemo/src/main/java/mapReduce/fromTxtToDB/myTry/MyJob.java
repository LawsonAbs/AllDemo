package mapReduce.fromTxtToDB.myTry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.IOException;

//Note
//1.if your class name is same as Apache's class name,Reference bag could use full name
public class MyJob{
    public static void main(String[] args) {
        Configuration conf = new Configuration();//care!the configuration is in hadoop

        DBConfiguration.configureDB(
                conf,
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.211.3:3306/mydatabase",
                "root",
                "root");
        try {
            Job job = Job.getInstance(conf);//get a instance of job
            job.setJarByClass(MyJob.class);
            job.setJobName("My wordCount");

            //job.setMapOutputKeyClass(MyMapper.class);//just as its name implies
            job.setMapperClass(MyMapper.class);
            job.setReducerClass(MyReducer.class);

            //set key,value class
            //if you don't this,you could meet an error!--->Type mismatch
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);


            //input and output
            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(DBOutputFormat.class);

            //The correct usage is shown below
            FileInputFormat.setInputPaths(job,new Path(
                    "hdfs://192.168.211.3:9000/input/word.txt"));
            //FileOutputFormat.setOutputPath(job,new Path("hdfs://192.168.211.3:9000/result.txt"));
            String tableName = "statistics";

            DBOutputFormat.setOutput(job,tableName,new String[] { "id", "name" });
            try {
                job.waitForCompletion(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
