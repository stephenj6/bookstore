package bookstore;

public class Owner extends User {
    private static Owner instance = new Owner();
        
    private Owner() {
        super("admin", "admin"); // hardcoded admin login credentials
    }
    
    public static Owner getInstance() { // singleton design only one owner
        return instance;
    }
        
}