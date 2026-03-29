package bookstore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OwnerScreen extends JPanel {
    private BookStoreApp app;
    private CardLayout cardLayout; // layout manager between owner subscreens 
    private JPanel cardsPanel; // container for owner subscreens

    // table models hold book and customer data
    private DefaultTableModel booksTableModel;
    private DefaultTableModel customersTableModel;

    public OwnerScreen(BookStoreApp app) {
        this.app = app;
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        setLayout(new BorderLayout()); // set border layout for this panel 
        add(cardsPanel, BorderLayout.CENTER); // add card panel to fill center area

        // initialize sub screens with keys for each
        cardsPanel.add(createStartPanel(), "START");
        cardsPanel.add(createBooksPanel(), "BOOKS");
        cardsPanel.add(createCustomersPanel(), "CUSTOMERS");
    }

    // flip to show each panel when called
    public void showStart() { 
        cardLayout.show(cardsPanel, "START"); 
    }
    
    public void showBooks() {
        cardLayout.show(cardsPanel, "BOOKS"); 
        refreshBooksTable(); 
    }
    
    public void showCustomers() { 
        cardLayout.show(cardsPanel, "CUSTOMERS"); 
        refreshCustomersTable(); 
    }

    private JPanel createStartPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10)); // create panel 3 rows 1 column 10 px gaps
        panel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200)); // add padding 50top/bot 200px l/r

        // buttons to bring you to each subscreen
        JButton booksBtn = new JButton("Books"); 
        JButton customersBtn = new JButton("Customers"); 
        JButton logoutBtn = new JButton("Logout"); 

        // listens for when each button is clicked and brings you to respective screen  
        booksBtn.addActionListener(e -> showBooks());
        customersBtn.addActionListener(e -> showCustomers());
        logoutBtn.addActionListener(e -> app.showLoginScreen());

        // add buttons to panel and return
        panel.add(booksBtn);
        panel.add(customersBtn);
        panel.add(logoutBtn);
        return panel;
    }

    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout()); // create panel with borderlayout 
        
      
        booksTableModel = new DefaultTableModel(new String[]{"Book Name", "Book Price"}, 0); // create table model with 2 columns and 0 initial rows
        JTable table = new JTable(booksTableModel); // create table using tabel model
        panel.add(new JScrollPane(table), BorderLayout.NORTH); // wrap table in scroll pane and pin to top

        
        JPanel middlePanel = new JPanel(); // panel for input fields 
        JTextField nameField = new JTextField(10); // field for book name 10 char wide
        JTextField priceField = new JTextField(10); // field for book price 10 char wide
        JButton addBtn = new JButton("Add"); // create add button
        
        addBtn.addActionListener(e -> {  // listens for click
            try {
                String name = nameField.getText(); // book name from input field
                double price = Double.parseDouble(priceField.getText()); // price from input field convert to double
                app.getDataManager().getBooksList().add(new Book(name, price)); // create  new book with params and add to list
                refreshBooksTable(); // refresh table to show new book 
                nameField.setText(""); // clear name field after adding
                priceField.setText(""); // clear price field after adding
            } catch (NumberFormatException ex) { // show error if price is not a valid number
                JOptionPane.showMessageDialog(this, "Invalid price format.");
            }
        });
        // add add labels and buttons to input row
        middlePanel.add(new JLabel("Name:")); 
        middlePanel.add(nameField);
        middlePanel.add(new JLabel("Price:"));
        middlePanel.add(priceField);
        middlePanel.add(addBtn);
        panel.add(middlePanel, BorderLayout.CENTER); // add input row to center section

        // panel for delete/back buttons at bottom
        JPanel bottomPanel = new JPanel();
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");

        deleteBtn.addActionListener(e -> { // check if delete button clicked
            int row = table.getSelectedRow(); // gets index of selected row
            if (row >= 0) { // check if row is actualyl selected
                app.getDataManager().getBooksList().remove(row); // remove book at selected index from data list
                refreshBooksTable(); // refresh to show deletion
            }
        });
        backBtn.addActionListener(e -> showStart()); // go back to start panel when back button clicked

        // add buttons to bottom
        bottomPanel.add(deleteBtn); 
        bottomPanel.add(backBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel; // returns completed books panel
    }

    private JPanel createCustomersPanel() { // pretty much the same as books panel
        JPanel panel = new JPanel(new BorderLayout());
        
        customersTableModel = new DefaultTableModel(new String[]{"Username", "Password", "Points"}, 0);
        JTable table = new JTable(customersTableModel);
        panel.add(new JScrollPane(table), BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        JTextField userField = new JTextField(10);
        JTextField passField = new JTextField(10);
        JButton addBtn = new JButton("Add");
        
        addBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = passField.getText();
            app.getDataManager().getCustomersList().add(new Customer(user, pass, 0));
            refreshCustomersTable();
            userField.setText("");
            passField.setText("");
        });

        middlePanel.add(new JLabel("Username:"));
        middlePanel.add(userField);
        middlePanel.add(new JLabel("Password:"));
        middlePanel.add(passField);
        middlePanel.add(addBtn);
        panel.add(middlePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                app.getDataManager().getCustomersList().remove(row);
                refreshCustomersTable();
            }
        });
        backBtn.addActionListener(e -> showStart());

        bottomPanel.add(deleteBtn);
        bottomPanel.add(backBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshBooksTable() {
        booksTableModel.setRowCount(0); //  clear all rows from existing table
        for (Book b : app.getDataManager().getBooksList()) { // loop through books in data manager
            booksTableModel.addRow(new Object[]{b.getName(), b.getPrice()}); // add each book as a new row 
        }
    }

    private void refreshCustomersTable() { // do the same as above but for customers
        customersTableModel.setRowCount(0);
        for (Customer c : app.getDataManager().getCustomersList()) {
            customersTableModel.addRow(new Object[]{c.getUsername(), c.getPassword(), c.getPoints()});
        }
    }
}