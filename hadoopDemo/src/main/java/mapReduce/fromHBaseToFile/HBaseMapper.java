package mapReduce.fromHBaseToFile;


import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public class HBaseMapper extends TableMapper<Text, IntWritable> {

    /**
     *
     * @param key:KEYIN，这个是HBase API操作得到的rowkey
     * @param value:VALUEINT，这个是HBase API操作读取到的内容
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context)
            throws IOException, InterruptedException {
        //return array of cells
        for (Cell cell : value.rawCells())
        {
            String family = new String((CellUtil.cloneFamily(cell)));
            String row = new String ((CellUtil.cloneRow(cell)));
            String qualifier = new String((CellUtil.cloneQualifier(cell)));

            String line = new String ((CellUtil.cloneValue(cell)));
            System.out.println("qualifier = "+qualifier+"\nline = "+line+"\nfamily = "+family+"\nrow = "+row);
            String [] word = line.split(" ");
            for(int i = 0;i< word.length;i++){
                System.out.println("x"+word[i]+"x");
                context.write(new Text(word[i]),new IntWritable(1));
            }
        }
    }
}
/*
1.cloneQualifier(cell)这个方法是将cell中的内容赋值到一个byte[]数组中
紧接着，将byte[]数组构造成一个String,再由String构造成一个Text对象【说明最后传递出去的keyOut是Text类型】
2.ValueOut:IntWritable;


3.如下代码分析：
if(new String(CellUtil.cloneQualifier(cell)).equals("GroupID")){
   context.write(new Text(new String(CellUtil.cloneValue(cell))), new IntWritable(1));
}

context.write(new Text(new String(CellUtil.cloneValue(cell))), new IntWritable(1));这行代码只是将每行中的值拿出来，
然后作为Mapper阶段的KeyOut输出。

4.Object a;
String.valueOf(a)与new String(a)有时候的功能是不一样的

5.上面那个for循环读出来的cell值是按照rowkey,qualifierName排序之后得到的顺序值。所以说这个顺序是固定的。
 */