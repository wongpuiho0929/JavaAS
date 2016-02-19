package BAMS.Model;

import java.io.Serializable;
import java.util.Date;

public abstract class Model implements Serializable{

    protected String id;
    protected Date createdAt, updatedAt, deletedAt;

    public Model() {
        updatedAt = createdAt = new Date();
        deletedAt = null;
    }

    public String getId() {
        return this.id;
    }

    ;
    public void setId(String id) {
        this.id = id;
    }

    ;
    public static boolean checkTel(String tel) {
        return tel.matches("[1-9][0-9]{7}");
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}
