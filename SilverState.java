package bookstore;

public class SilverState implements CustomerState { // just the opposite of goldstate
    @Override
    public double handlePurchase(double totalCost, Customer customer) {
        return totalCost;
    }
    
    @Override
    public void checkStateUpdate(Customer customer) {
        if(customer.getPoints() >= 1000) {
            customer.setState(new GoldState());
        }
    }
    
    @Override
    public String getStatusName() {
        return "Silver";
    }
}