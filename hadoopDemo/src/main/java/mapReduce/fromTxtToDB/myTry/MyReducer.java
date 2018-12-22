package mapReduce.fromTxtToDB.myTry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
    /*
    1.同一批次的key，映射到相同的分区，根据他们的value做累加统计
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int count = 0;
        for(IntWritable val : values){
            /*but you should remember:
            1.because the type mismatch,so you couldn't use "count += val"
            2.so, you should get the 'real' value of val.The right method is get();
             */
            count +=  val.get();//计算总值
        }
        context.write(key,new IntWritable(count));
    }
}

