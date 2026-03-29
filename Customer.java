package bookstore;

import java.util.List;

public class Customer extends User {
    private int points;
    private CustomerState state;
    
    public Customer(String username, String password, int initialPoints){
        super(username, password); // pass username and password to user
        this.points = initialPoints; 
        if (this.points >= 1000) {
            this.state = new GoldState();
        } else {
            this.state = new SilverState();
        }
    }
    
    public double buy(List<Book> books) {
        double totalCost = 0; // initialize totalcost to 0
        for (Book b : books) { // loop through books 
            totalCost += b.getPrice(); // add each price to total
        }
        
        points += (int)(totalCost * 10); // earn 10 points for every dollar spent and cast to int
        
        state.checkStateUpdate(this); // update state if points cross gold threshold
        return totalCost;
    }
    
    public double redeemAndBuy(List<Book> books) { 
        double totalCost = 0;
        for (Book b : books) {
            totalCost += b.getPrice();
        }
        
        double maxDiscount = points / 100.0; // calculate max discount , 100 points = $1 off
        
        if (maxDiscount >= totalCost) { // check if points cover total cost
            points -= (int)(totalCost * 100); // deduct only points needed for total
            totalCost = 0; // cost covered so set it to 0
        } else { // if points don't cover total cost 
            totalCost -= maxDiscount; // subtract max possible discount
            points = 0; // all points redeemed set to zero
            points += (int)(totalCost * 10); // earn 10 points per dollar spent on remaining total
        }
        
        state.checkStateUpdate(this); // check if points crossed threshold
        return totalCost;
    }
    
    public int getPoints() {
        return points; // returns current points 
    }
    
    public void updatePoints(int pointsToAdd) {
        this.points += pointsToAdd; // add points to current balance 
    }
    
    public void setState(CustomerState state) {
        this.state = state; // update state to current state
    }
    
    public String getStatus() {
        return state.getStatusName(); 
    }
}