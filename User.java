package bookstore;

public abstract class User { // abstract so you can not instantiate user directly
    protected String username; 
    protected String password;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword() {
        return password;
    }
}