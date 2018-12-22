package mapReduce.fromFileToFile;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
 * KEYIN：对应mapper阶段输出的key类型
 * VALUEIN：对应mapper阶段输出的value类型
 * KEYOUT：reduce处理完之后输出的结果kv对中key的类型
 * VALUEOUT：reduce处理完之后输出的结果kv对中value的类型
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        /*
         * reduce方法提供给reduce task进程来调用
         *
         * reduce task会将shuffle阶段分发过来的大量kv数据对进行聚合，聚合的机制是相同key的kv对聚合为一组
         * 然后reduce task对每一组聚合kv调用一次我们自定义的reduce方法
         * 比如：<hello,1><hello,1><hello,1><tom,1><tom,1><tom,1>
         *hello组会调用一次reduce方法进行处理，tom组也会调用一次reduce方法进行处理
         *调用时传递的参数：
         *key：一组kv中的key
         *values：一组kv中所有value的迭代器
         */
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values,Context context)
                throws IOException, InterruptedException {
        //定义一个计数器，用来记录key的次数
        int count = 0;
        //通过value这个迭代器，遍历这一组kv中所有的value，进行累加
        //注意这里的value.get() 得到的是1，而不是别的值。【因为传递过来的<key,value>的形式是<word,1>】
        for(IntWritable value:values){
            count+=value.get();
        }
        //输出这个单词的统计结果
        context.write(key, new IntWritable(count));
    }
}