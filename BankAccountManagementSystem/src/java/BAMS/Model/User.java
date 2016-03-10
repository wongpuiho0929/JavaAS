package BAMS.Model;

import BAMS.Enum.UserType;
import BAMS.DAO.DAO;
import BAMS.DAO.UserDAO;

public class User extends Model {

    private String username, password;
    private UserType type;
    private String customerId;
    private static UserDAO db = DAO.userDB;

    public User() {

    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean checkPassword(String pwd) {
        return pwd.equals(this.getPassword());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return DAO.customerDB.findById(this.customerId);
    }

    public void setCustomer(Customer c) {
        setCustomerId(c.id);
    }

}
