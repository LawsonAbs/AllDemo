package mapReduce.three;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceiveTable implements Writable,DBWritable{
    //column1:keyword  column2:number
    private String keyWord;
    private int number;

    public ReceiveTable(){

    }
    public ReceiveTable(String keyWord,int number){
        this.keyWord = keyWord;
        this.number = number;
    }
    /**Writable  only serializable and deseiralizable
     *
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.number);
        /*1.将this.keyWord以UTF8的编码方式写入到out中[Write a UTF8 encoded string to out]
        2.其实这个效果和out.writeInt(this.number)是一样的，只不过是DataOutput类型没有writeString()这个方法，
        所以借用了Text.writeString(...)这个方法
         */
        Text.writeString(out, this.keyWord);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.number = in.readInt();
        this.keyWord = in.readUTF();
    }


    /**DBWritable
     * write data to mysql
     * @param statement
     * @throws SQLException
     */
    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1,this.keyWord);
        statement.setInt(2,this.number);
    }

    /**DBWritable
     * get data from resultset.And set in your fields
     * @param resultSet
     * @throws SQLException
     */
    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.keyWord = resultSet.getString(1);
        this.number = resultSet.getInt(2);
    }
}
