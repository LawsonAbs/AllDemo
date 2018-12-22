package mapReduce.one;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//自定义输出类
//向mysql中写入数据
public class TblsWritable implements Writable, DBWritable{
    String ip;
    int count;
    public TblsWritable() {
    }
    public TblsWritable(String ip, int count) {
        this.ip = ip;
        this.count = count;
    }
    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1, this.ip);
        statement.setInt(2, this.count);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.ip = resultSet.getString(1);
        this.count = resultSet.getInt(2);
    }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(ip);
        out.writeInt(count);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        ip = in.readUTF();
        count = in.readInt();
    }
}