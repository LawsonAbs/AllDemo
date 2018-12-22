package mapReduce.three;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

public class WordCountJob {
    public static String driverClass = "com.mysql.jdbc.Driver";
    public static String dbUrl = "jdbc:mysql://192.168.211.3:3306/mydatabase";
    public static String userName = "root";
    public static String passwd = "root";
    public static String inputFilePath = "hdfs://192.168.211.3:9000/input/word.txt";
    public static String tableName = "keyWord";
    public static String [] fields = {"word","total"};

    public static void main(String[] args) {
        Configuration conf = new Configuration();
        DBConfiguration.configureDB(conf,driverClass,dbUrl,userName,passwd);
        try {
            Job job = Job.getInstance(conf);

            job.setJarByClass(WordCountJob.class);
            job.setMapOutputValueClass(IntWritable.class);
            job.setMapOutputKeyClass(Text.class);

            job.setMapperClass(WordCountMapper.class);
            job.setReducerClass(WordCountReducer.class);

            job.setJobName("MyWordCountDB");

            FileInputFormat.setInputPaths(job,new Path(inputFilePath));
            DBOutputFormat.setOutput(job,tableName,fields);

            job.waitForCompletion(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
