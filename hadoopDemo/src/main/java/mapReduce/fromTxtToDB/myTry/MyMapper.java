package mapReduce.fromTxtToDB.myTry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//wordCount in MapReduce
public class MyMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
    //KEYIN:txt文件中每行文本的起始偏移量作为输入键
    //VALUEIN：txt文件中的每行的文本值作为输入值
    //KEYOUT：txt文件中的每行值作为输出键【作为reducer阶段的输入键】
    //VALUEOUT：txt文件中的每行值的个数作为输出值【作为reducer阶段的输入值】
    //需要根据相应的mapper<>中的泛型，实现map方法
    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String line = value.toString();//将每行文本内容转换成String类型的line
        String [] word = line.split(" ");//因为文本中的每行中的单词是以空格分割【根据不同的文件设置】

        for(int i = 0;i < word.length;i++){
            //context.write(word[i],1) ;//将每行的结果输出  error:parameters mismatch
            context.write(new Text(word[i]),new IntWritable(1));//
        }
    }
}
