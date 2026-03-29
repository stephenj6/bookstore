package bookstore;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class BookStoreApp extends JFrame {
    private DataManager dataManager; // manages all book and customer data
    
    private JPanel mainPanel; // container panel that holds all screens 
    private CardLayout mainLayout; // layout manager switches between screens
    private LoginScreen loginScreen; // login screen panel 
    private OwnerScreen ownerScreen; // owner screen panel
    private CustomerScreen customerScreen; // customer screen panel
    
    public BookStoreApp() {
        dataManager = new DataManager(); // instance of datamanager
        dataManager.loadBooksData(); // loads books from books.txt (datamanager)
        dataManager.loadCustomersData(); // loads customers from customers.txt 
        
        setTitle("Bookstore App"); // sets window title
        setSize(800,600); // sets window size 
        setLocationRelativeTo(null); // centers window on screen
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // allows us to handle saving before closing
        
        mainLayout = new CardLayout(); // card layout to switch between screens 
        mainPanel = new JPanel(mainLayout); // creates main panel using card layout
        
        //create 3 types of screen
        loginScreen = new LoginScreen(this);
        ownerScreen = new OwnerScreen(this);
        customerScreen = new CustomerScreen(this);
        
        //registers screen with a key for each
        mainPanel.add(loginScreen, "LOGIN");
        mainPanel.add(ownerScreen, "OWNER");
        mainPanel.add(customerScreen, "CUSTOMER");
        
        add(mainPanel);
        
        //listener to stop window close event
        addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) { // when x button clicked
                dataManager.saveBooksData(); // save books to books.txt
                dataManager.saveCustomersData(); // save customers to customers.txt
                System.exit(0); // exit application
            }
        });
    }
    
    public DataManager getDataManager() {
        return dataManager;
    }
    
    public void launch() {
        showLoginScreen(); // shows loginscreen as first screen
        setVisible(true); // make window visible
    }
    
    public void showLoginScreen() { 
        mainLayout.show(mainPanel, "LOGIN"); // show login screen when called 
    }
    
    public void showCustomerScreen(Customer c){
        customerScreen.showStart(c);
        mainLayout.show(mainPanel, "CUSTOMER"); // show customer panel when called
    }
    
    public void showOwnerScreen(){
        ownerScreen.showStart();
        mainLayout.show(mainPanel, "OWNER"); // show owner screen when called
    }   
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> { 
            BookStoreApp app = new BookStoreApp(); // create application instance
            app.launch(); // launch app
        });
    }
}