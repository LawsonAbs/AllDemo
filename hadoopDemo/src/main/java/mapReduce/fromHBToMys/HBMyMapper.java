package mapReduce.fromHBToMys;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashMap;

public class HBMyMapper extends TableMapper<Text,IntWritable> {
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context)
            throws IOException, InterruptedException {
        String cellValue = "";
        HashMap<String, String> cfValueMap = new HashMap<String, String>();

        //the value is a scan of every row of HBase table's
        for(Cell cell :value.rawCells()){
            /*1.这里的每个cell是什么意思？
            如果一个列族family里面有很多qualifier，那么这个该怎么表示？===>每个qualifier将得到顺序的表示*/
            //String qualifier =  new String(cell.getQualifierArray());
            //cellValue = new String(cell.getValueArray());
            String qualifier = new String(CellUtil.cloneQualifier(cell));
            cellValue = new String(CellUtil.cloneValue(cell));

            //put into map
            cfValueMap.put(qualifier,cellValue);
            System.out.println("qualifier:"+qualifier+"cellValue : "+cellValue);
        }
        //将caller+year+month作为输出的key
        String realKey = cfValueMap.get("caller") + cfValueMap.get("date").substring(0,6);
        String tempValue = cfValueMap.get("duration");
        Integer realValue = Integer.valueOf(tempValue);
        System.out.println("realKey:"+realKey+"\ntempValue:"+tempValue);
        context.write(new Text(realKey),new IntWritable(realValue));
    }
}
/*
1.for(Cell cell:values )这里的value是HBAse表中scan操作得到的一行
 */
