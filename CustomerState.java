package bookstore;

public interface CustomerState {
    double handlePurchase(double totalCost, Customer customer);
    void checkStateUpdate(Customer customer);
    String getStatusName();
}