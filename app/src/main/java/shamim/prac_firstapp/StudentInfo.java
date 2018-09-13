package shamim.prac_firstapp;

public class StudentInfo {
    String sname, ssid, slevel, searnedcredit;

    public StudentInfo() {

    }

   public StudentInfo(String sname,String ssid,String slevel,String searnedcredit){
        this.sname=sname;
        this.ssid=ssid;
        this.slevel=slevel;
        this.searnedcredit=searnedcredit;
   }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSlevel() {
        return slevel;
    }

    public void setSlevel(String slevel) {
        this.slevel = slevel;
    }

    public String getSearnedcredit() {
        return searnedcredit;
    }

    public void setSearnedcredit(String searnedcredit) {
        this.searnedcredit = searnedcredit;
    }
}