package bookstore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerScreen extends JPanel {
    private BookStoreApp app;
    private Customer currentCustomer; // currently logged in customer
    private CardLayout cardLayout; // layout manager to switch subscreens
    private JPanel cardsPanel; // container panel that holds customer subscreens
    private JLabel welcomeLabel;
    private DefaultTableModel booksTableModel; 
    
    // cost screen components
    private JLabel costLabel;
    private JLabel pointsStatusLabel;

    public CustomerScreen(BookStoreApp app) {
        this.app = app;
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        setLayout(new BorderLayout());
        add(cardsPanel, BorderLayout.CENTER);
        // subpanels with keys for each
        cardsPanel.add(createStartPanel(), "START");
        cardsPanel.add(createCostPanel(), "COST");
    }

    public void showStart(Customer c) {
        this.currentCustomer = c; // store logged in customer
        // welcome label with current customers data
        welcomeLabel.setText("Welcome " + c.getUsername() + ". You have " + c.getPoints() + " points. Your status is " + c.getStatus());
        refreshBooksTable(); // refresh books table to show available books
        cardLayout.show(cardsPanel, "START"); // flip to start panel
    }

    public void showCost(double cost, Customer c) {
        costLabel.setText("Total Cost: " + cost + " CAD"); // show final cost
        pointsStatusLabel.setText("Points: " + c.getPoints() + ", Status: " + c.getStatus()); // display updated points and status after purchase
        cardLayout.show(cardsPanel, "COST"); // flip to cost panel
    }

    private JPanel createStartPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // welcome message at top of panel
        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // table model with 3 columns and 0  initial rows
        booksTableModel = new DefaultTableModel(new String[]{"Book Name", "Book Price", "Select"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) { // override to specific data type of each column
                if (columnIndex == 2) return Boolean.class; // force checkboxes in column 2
                return super.getColumnClass(columnIndex); // return default type for all other columns
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // only allow editing the checkbox column
            }
        };
        JTable table = new JTable(booksTableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER); // wrap book table in scroll pane and center

        // bottom panel buttons
        JPanel bottomPanel = new JPanel();
        JButton buyBtn = new JButton("Buy");
        JButton redeemBtn = new JButton("Redeem points and Buy");
        JButton logoutBtn = new JButton("Logout");

        // listeners to call functions when clicked
        buyBtn.addActionListener(e -> processPurchase(false));
        redeemBtn.addActionListener(e -> processPurchase(true));
        logoutBtn.addActionListener(e -> app.showLoginScreen());
        
        // add buttons to bottom panel
        bottomPanel.add(buyBtn);
        bottomPanel.add(redeemBtn);
        bottomPanel.add(logoutBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel; // return complete start panel
    }

    private JPanel createCostPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1)); // 3 row 1 column panel
        panel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150)); // padding around 

        costLabel = new JLabel("", SwingConstants.CENTER); // empty centered cost label (filled with showCost)
        pointsStatusLabel = new JLabel("", SwingConstants.CENTER); // points label ^
        
        JButton logoutBtn = new JButton("Logout"); // logout button
        logoutBtn.addActionListener(e -> app.showLoginScreen()); // logout screen when clicked
        
        // panel for logout button
        JPanel btnPanel = new JPanel(); 
        btnPanel.add(logoutBtn);
        
        // add cost, points, status, logout to cost panel
        panel.add(costLabel);
        panel.add(pointsStatusLabel);
        panel.add(btnPanel);

        return panel; // return complete cost panel
    }

    private void refreshBooksTable() { // same as function in ownerscreen
        booksTableModel.setRowCount(0);
        for (Book b : app.getDataManager().getBooksList()) {
            booksTableModel.addRow(new Object[]{b.getName(), b.getPrice(), false});
        }
    }

    private void processPurchase(boolean usePoints) { 
        java.util.List<Book> selectedBooks = new ArrayList<>(); // list to store selected books

        for (int i = 0; i < booksTableModel.getRowCount(); i++) { // loop through each row in table
            Boolean isSelected = (Boolean) booksTableModel.getValueAt(i, 2); // check if checkbox in col 2 is selected in current row
            if (isSelected != null && isSelected) { // if selected
                Book b = app.getDataManager().getBooksList().get(i); // get book from same index of data manager
                selectedBooks.add(b); // add selected book to list
            }
        }

        if (selectedBooks.isEmpty()) { // if no selected books
            JOptionPane.showMessageDialog(this, "Please select at least one book."); // show error message 
            return;
        }

        double finalCost; 
        if (usePoints) { // check if redeem and buy was clicked 
            finalCost = currentCustomer.redeemAndBuy(selectedBooks); // process purchase with points
        } else { // regular buy button clicked
            finalCost = currentCustomer.buy(selectedBooks); // process purchase without point redeem
        }

        showCost(finalCost, currentCustomer); // transition to cost screen for current customer
    }
}