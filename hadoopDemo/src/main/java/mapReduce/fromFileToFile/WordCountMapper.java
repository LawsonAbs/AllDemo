package mapReduce.fromFileToFile;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
 * KEYIN：输入kv数据对中key的数据类型
 * VALUEIN：输入kv数据对中value的数据类型

 * KEYOUT：输出kv数据对中key的数据类型
 * VALUEOUT：输出kv数据对中value的数据类型
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    /*
     * map方法是提供给map task进程来调用的，map task进程是每读取一行文本来调用一次我们自定义的map方法
     * map task在调用map方法时，传递的参数：
     * 一行的起始偏移量(这到底是什么意思？)LongWritable作为key
     * 一行的文本内容Text作为value
     */
    @Override
    protected void map(LongWritable key, Text value,Context context)
            throws IOException, InterruptedException {
        //step 01.拿到一行文本内容，转换成String类型
        //step 02.将这行文本切分成单词
        String words[] = value.toString().split(" ");

        //输出<单词，1>
        for(String word:words){
            //1.write():Generate an output key/value pair.=>(KeyOut[Type is Text],ValueOut[Type is IntWritable])
            context.write(new Text(word), new IntWritable(1));
        }
    }
}
