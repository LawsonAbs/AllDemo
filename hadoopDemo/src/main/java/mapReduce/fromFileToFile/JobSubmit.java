package mapReduce.fromFileToFile;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


public class JobSubmit {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        //System.setProperty("HADOOP_USER_NAME","root");//ensure
        Job wordCountJob = Job.getInstance(conf);

        //重要：指定本job所在的jar包
        wordCountJob.setJarByClass(JobSubmit.class);

        //设置wordCountJob所用的mapper逻辑类为哪个类
        wordCountJob.setMapperClass(WordCountMapper.class);
        //设置wordCountJob所用的reducer逻辑类为哪个类
        wordCountJob.setReducerClass(WordCountReducer.class);

        //设置map阶段输出的kv数据类型
        wordCountJob.setMapOutputKeyClass(Text.class);
        wordCountJob.setMapOutputValueClass(IntWritable.class);

        //设置最终输出的kv数据类型
        wordCountJob.setOutputKeyClass(Text.class);
        wordCountJob.setOutputValueClass(IntWritable.class);

        //设置要处理的文本数据所存放的路径
        //FileInputFormat is a abstract class,but how it's call?
        FileInputFormat.setInputPaths(wordCountJob,
                "hdfs://192.168.211.4:9000/input/data.txt");

        /*
        01.这个FileOutputFormat可以替换成任何一个实现了OutPutFormat的子类，比如说你想输出到Mysql中，
        你就可以实现DBOutPutFormat类，然后在这里写成如下形式：
        DBOutPutFormat.setOutput(...)

        02.下面的这个new Path(...)过程是否可以再简化/修复一下，如果有这个路径，则不会创建了，直接使用该文件路径。
            现在的情况是：如果这个路径已经存在，就会报错。
         */
        FileOutputFormat.setOutputPath(wordCountJob,
                new Path("hdfs://192.168.211.4:9000/output/wordCount"));

        //提交job给hadoop集群
        wordCountJob.waitForCompletion(true);
    }
}