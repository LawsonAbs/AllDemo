package mapReduce.fromTxtToDB.other;/*
package MapReduce.WriteToDB;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.StringTokenizer;

public class WriteDB {

    // Map处理过程
    public static class Map extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(Object key, Text value,
                        OutputCollector<Text, IntWritable> output, Reporter reporter)
                throws IOException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                word.set(tokenizer.nextToken());
                output.collect(word, one);
            }
        }
    }


    // Combine处理过程
    public static class Combine extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        public void reduce(Text key, Iterator<IntWritable> values,
                           OutputCollector<Text, IntWritable> output, Reporter reporter)
                throws IOException {
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next().get();
            }

            output.collect(key, new IntWritable(sum));
        }
    }

    // Reduce处理过程
    public static class Reduce extends Reducer<Text, IntWritable, WordRecord, Text> {
        @Override
        public void reduce(Text key, Iterator<IntWritable> values,
                           OutputCollector<WordRecord, Text> collector, Reporter reporter)
                throws IOException {
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next().get();
            }
            WordRecord wordcount = new WordRecord();
            wordcount.word = key.toString();
            wordcount.number = sum;
            collector.collect(wordcount, new Text());
        }
    }

    public static class WordRecord implements Writable, DBWritable {
        public String word;
        public int number;
        @Override
        public void readFields(DataInput in) throws IOException {
            this.word = Text.readString(in);
            this.number = in.readInt();
        }
        @Override
        public void write(DataOutput out) throws IOException {
            Text.writeString(out, this.word);
            out.writeInt(this.number);
        }
        @Override
        public void readFields(ResultSet result) throws SQLException {
            this.word = result.getString(1);
            this.number = result.getInt(2);
        }
        @Override
        public void write(PreparedStatement stmt) throws SQLException {
            stmt.setString(1, this.word);
            stmt.setInt(2, this.number);
        }
    }


    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(WriteDB.class);
        // 这句话很关键
        conf.set("mapred.job.tracker", "192.168.1.2:9001");
        DistributedCache.addFileToClassPath(new Path(
                "/lib/mysql-connector-java-5.1.18-bin.jar"), conf);

        // 设置输入输出类型
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(DBOutputFormat.class);
        // 不加这两句，通不过，但是网上给的例子没有这两句。
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        // 设置Map和Reduce类
        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Combine.class);
        conf.setReducerClass(Reduce.class);
        // 设置输如目录
        FileInputFormat.setInputPaths(conf, new Path("wdb_in"));

        // 建立数据库连接

        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.1.24:3306/school", "root", "hadoop");
        // 写入"wordcount"表中的数据
        String[] fields = { "word", "number" };
        DBOutputFormat.setOutput(conf, "wordcount", fields);
        JobClient.runJob(conf);
    }
}*/
