package bjtu.monitor.pojo.table;

import java.util.Date;

public class Administrator {
    private Integer id;

    private String adminname;

    private String adminacc;

    private String adminpassword;

    private Date starttime;

    private Integer rights;

    private String adminemail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname == null ? null : adminname.trim();
    }

    public String getAdminacc() {
        return adminacc;
    }

    public void setAdminacc(String adminacc) {
        this.adminacc = adminacc == null ? null : adminacc.trim();
    }

    public String getAdminpassword() {
        return adminpassword;
    }

    public void setAdminpassword(String adminpassword) {
        this.adminpassword = adminpassword == null ? null : adminpassword.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getRights() {
        return rights;
    }

    public void setRights(Integer rights) {
        this.rights = rights;
    }

    public String getAdminemail() {
        return adminemail;
    }

    public void setAdminemail(String adminemail) {
        this.adminemail = adminemail == null ? null : adminemail.trim();
    }
}