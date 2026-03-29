package bookstore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JPanel {
    private BookStoreApp app;   // reference to main app for navigation
    private JTextField usernameField; // input field for username
    private JPasswordField passwordField; // password field to hide characters

    public LoginScreen(BookStoreApp app) {
        this.app = app;
        setLayout(new GridLayout(4, 1, 10, 10)); // divide panel into 4 rows, 1 column, 10 px gaps
        setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150)); // adds padding 50px to top/bottom and 150px on sides

        JLabel welcomeLabel = new JLabel("Welcome to the BookStore App", SwingConstants.CENTER); // centered welcome label
        
        JPanel userPanel = new JPanel(new FlowLayout()); // create panel with flowlayout (lext to right row) for username 
        userPanel.add(new JLabel("Username: ")); // adds text label to row
        usernameField = new JTextField(15); // adds text field 15 char wide
        userPanel.add(usernameField); // adds username field to row

        JPanel passPanel = new JPanel(new FlowLayout()); // does the same as above but for password
        passPanel.add(new JLabel("Password: "));
        passwordField = new JPasswordField(15);
        passPanel.add(passwordField);

        JButton loginButton = new JButton("Login"); // login button
        loginButton.addActionListener(new ActionListener() { // listens for click
            @Override
            public void actionPerformed(ActionEvent e) { // when clicked 
                handleLogin(); // calls login handler
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout()); // creates panel for buttons with flowlayout
        btnPanel.add(loginButton); // add login button to button panel

        // adds below panels in each row
        add(welcomeLabel); 
        add(userPanel);
        add(passPanel);
        add(btnPanel);
    }

    public void handleLogin() {
        String username = usernameField.getText().trim(); // get user input and trim whitespace
        String password = new String(passwordField.getPassword()); // get password input and convert to string

        // check if Owner
        if (username.equals("admin") && password.equals("admin")) {
            app.showOwnerScreen(); // show owner screen if owner
            return;
        }

        // check if Customer
        for (Customer c : app.getDataManager().getCustomersList()) { // loop through customers
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) { // check if username and password match
                app.showCustomerScreen(c); // navigate to customers screen
                return;
            }
        }

        // error if user not in system
        JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
    }
}