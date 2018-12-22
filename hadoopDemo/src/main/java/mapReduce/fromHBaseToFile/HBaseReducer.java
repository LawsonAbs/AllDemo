package mapReduce.fromHBaseToFile;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class HBaseReducer extends
        Reducer<Text, IntWritable,Text,IntWritable> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values,Context context)
            throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable i : values) {
                sum += i.get();
            }
            context.write(key,new IntWritable(sum));
        }
}

/*
1.如下代码：
byte[] keyBytes = Bytes.toBytes(key.toString());
        if(keyBytes.length>0){
        // 列族为content，列为count，列值为数目
        context.write(key, new IntWritable(sum));
}
 */