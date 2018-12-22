package mapReduce.one;

public class TextLine {
    private String ip;
    //标识数据是否合法
    private boolean flag = true;
    public TextLine(String line){
    //检验一行日志数据是否符合要求，如不符合，将其标识为不可用
        if(line == null || "".equals(line)){
            this.flag = false;
            return;
        }
        String[] strs = line.split(" ");
        if(strs.length < 2){
            this.flag = false;
            return;
        }
        ip = strs[0];
    }
    public boolean isRight(){
        return flag;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
}