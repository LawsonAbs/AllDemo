package mapReduce.fromHBToMys.one;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Statistics implements Writable,DBWritable {
    /**
     * id: primary key and auto_increment
     * callDuration: total call's duration
     */
    public int id = 0;
    public String teleNumber;
    public String yearMonth;
    public int callDuration;

    public Statistics(){
    }

    public Statistics(
            String teleNumber,
            String yearMonth,
            int callDuration){
        this.teleNumber = teleNumber;
        this.yearMonth = yearMonth;
        this.callDuration = callDuration;
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        Text.writeString(out,this.teleNumber);
        Text.writeString(out, yearMonth);
        out.writeInt(this.callDuration);
    }

    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.teleNumber = Text.readString(in);
        this.yearMonth = Text.readString(in);
        this.callDuration = in.readInt();
    }


    //-----------------------------------DBWritable------------------------------------------
    public void write(PreparedStatement statement) throws SQLException {
        statement.setInt(1, this.id);
        statement.setString(2,this.teleNumber);
        statement.setString(3,this.yearMonth);
        statement.setInt(4,this.callDuration);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt(1);
        this.teleNumber = resultSet.getString(2);
        this.yearMonth = resultSet.getString(3);
        this.callDuration = resultSet.getInt(4);
    }
}
