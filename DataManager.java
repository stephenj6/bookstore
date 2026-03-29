package bookstore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<Book> booksList; // list to store books
    private List<Customer> customersList; // list for customers 
    private final String BOOKS_FILE = "books.txt"; // book file name 
    private final String CUSTOMERS_FILE = "customers.txt"; // customer file name
    
    public DataManager() {
        booksList = new ArrayList<>(); // initialize lists
        customersList = new ArrayList<>();
    }
    
    public void loadBooksData() {
        try {
            File file = new File(BOOKS_FILE); // create file object for books
            if (!file.exists()) { // check if file already exists 
                return; 
            }
            
            Scanner scanner = new Scanner(file); // create scanner to read file
            while (scanner.hasNextLine()) { // continue while file still has lines
                String line = scanner.nextLine(); // read line
                String[] parts = line.split(","); // split line by comma into array "parts"
                if (parts.length == 2) { // check if line has exactly 2 parts 
                    String name = parts[0]; // first part is name
                    double price = Double.parseDouble(parts[1]); // second part is price converted to doubl e
                    booksList.add(new Book(name, price)); // create book object and add to list 
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) { // catch errors
            System.out.println("Error loading books" + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing price" + e.getMessage());
        }
    }
    
    public void loadCustomersData() { // same as loadBooksdata
        try {
            File file = new File(CUSTOMERS_FILE);
            if (!file.exists()) {
                return;
            }
            
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    int points = Integer.parseInt(parts[2]); // third part is points converted to int
                    customersList.add(new Customer(username, password, points)); // customer added to list
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading customer data" + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing points" + e.getMessage());
        }
    }
    
    public void saveBooksData() {
        try {
            FileWriter writer = new FileWriter(BOOKS_FILE, false); // open file for writing , false to overwrite existing content
            for (Book b : booksList) { // loop through book objects in list
                writer.write(b.getName() + "," + b.getPrice() + "\n"); // writes book name, comma, price, newline
            }
            writer.close();
        } catch (IOException e) { // catch errors
            System.out.println("Error saving book data" + e.getMessage());
        }
    }
    
    public void saveCustomersData() { // same as savebooksdata
        try {
            FileWriter writer = new FileWriter(CUSTOMERS_FILE, false);
            for (Customer c : customersList) {
                writer.write(c.getUsername() + "," + c.getPassword() + "," + c.getPoints() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving customer data" + e.getMessage());

        }
    }
    
    //getters
    public List<Book> getBooksList() {
        return booksList;
    }
    
    public List<Customer> getCustomersList() {
        return customersList;
    }
}