package BAMS.Model;

public class User extends Model {

    private String username, password;
    private UserType type;
    private Customer customer;

    public User() {

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
        return customer;
    }

    public void setCustomer(Customer c) {
        this.customer = c;
    }

}
