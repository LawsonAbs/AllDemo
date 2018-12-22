package mapReduce.three;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<Text,IntWritable,ReceiveTable,NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        for(IntWritable intW : values){
            sum += intW.get();
        }
        ReceiveTable receiveTable = new ReceiveTable(key.toString(),sum);
        context.write(receiveTable,null);
    }
}
