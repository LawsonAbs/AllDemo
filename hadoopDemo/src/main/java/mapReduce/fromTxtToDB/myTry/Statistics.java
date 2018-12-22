package mapReduce.fromTxtToDB.myTry;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Statistics implements DBWritable , Writable {
    private String name;
    private int id;

    public Statistics(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //DBWrite
    /**接口DBWrite中的方法
     * 这里的参数是SQL查询的结果集类型（ResultSet）
     *
     * @param resultSet
     * @throws SQLException
     */
    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt(1);
        this.name = resultSet.getString(2);
    }

    /**
     * 这里的功能有点像从Mysql执行语句
     * @param statement
     * @throws SQLException
     */
    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setInt(1, this.getId());
        statement.setString(2, this.getName());
    }

    //Writable
    @Override
    public void write(DataOutput out) throws IOException {
        out.write(this.id);
        Text.writeString(out, this.name);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.name = Text.readString(in);
    }
}
