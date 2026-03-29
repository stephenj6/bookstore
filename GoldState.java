package bookstore;

public class GoldState implements CustomerState {
    @Override
    public double handlePurchase(double totalCost, Customer customer) {
        return totalCost; // gold customers have no discount so just return totalcost
    }
    
    @Override
    public void checkStateUpdate(Customer customer) { // check if customer is changing state to silver when less than 1000 points
        if (customer.getPoints() < 1000) { 
            customer.setState(new SilverState());
        } // stays in gold if over 1000
    }
    
    @Override
    public String getStatusName(){
        return "Gold";
    }
}