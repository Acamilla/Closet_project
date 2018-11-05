package project.lellon.closet;

public class closet_item {
    private  String name,date,RFID_type,memo;
    boolean exist;

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRFID_type(String RFID_type) {
        this.RFID_type = RFID_type;
    }

    public void  setMemo(String memo){
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getRFID_type() {
        return RFID_type;
    }

    public String getmemo() {
        return memo;
    }

    public boolean getexist() {
        return exist;
    }
}
