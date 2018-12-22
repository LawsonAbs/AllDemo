package mapReduce.three;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        //获取每一个输入行
        String line = value.toString();
        //get every separated word
        String [] word = line.split(" ");
        for(int i = 0;i< word.length;i++){
            context.write(new Text(word[i]),new IntWritable(1));
        }
    }
}
