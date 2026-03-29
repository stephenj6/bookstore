package bookstore;

public class Book {
    private String name;
    private double price;
    
    public Book (String name, double price) {
        this.name = name; // set name
        this.price = price; // set price
    }
    
    public String getName() { // return book name
        return name; 
    }
    public double getPrice() { // return book price
        return price;
    }
    
    public boolean repOk() { // check if object is valid
        if (name == null) return false; // name cannot be null
        if (price < 0) return false; // price cannot be negative
        return true;
    }

    @Override
    public String toString() { // string 
        return name + " - $" + price; // return name and price
    }
}