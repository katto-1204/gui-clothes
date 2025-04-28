package clothingordersystem;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList<Product> cart;
    
    public User(String username) {
        this.username = username;
        this.cart = new ArrayList<>();
    }
    
    public String getUsername() {
        return username;
    }
    
    public ArrayList<Product> getCart() {
        return cart;
    }
    
    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }
}