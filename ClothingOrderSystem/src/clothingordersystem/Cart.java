package clothingordersystem;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cart extends JFrame {
    private User currentUser;
    private ArrayList<Product> cartItems;
    private JPanel cartItemsPanel;
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;
    private double subtotal = 0.0;
    private double tax = 0.0;
    private double total = 0.0;
    private DecimalFormat df = new DecimalFormat("0.00");
    private static final double TAX_RATE = 0.07;
    
    public Cart(User user) {
        this.currentUser = user;
        this.cartItems = user.getCart();
        
        // Configure frame
        setTitle("Shopping Cart - Clothing Order System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Your Shopping Cart");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back to Shopping");
        backButton.addActionListener(e -> {
            dispose(); // Close the cart window
        });
        headerPanel.add(backButton, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Cart items section (scrollable)
        cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(cartItemsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Items in Cart"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Summary panel (right side)
        JPanel summaryPanel = createSummaryPanel();
        mainPanel.add(summaryPanel, BorderLayout.EAST);
        
        // Load items and calculate totals
        loadCartItems();
        calculateTotals();
        
        // Add main panel to frame
        add(mainPanel);
        
        // Display the frame
        setVisible(true);
    }
    
    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Order Summary"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        summaryPanel.setPreferredSize(new Dimension(220, 0));
        
        // Order summary labels
        subtotalLabel = new JLabel("Subtotal: $0.00");
        subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtotalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        taxLabel = new JLabel("Tax (7%): $0.00");
        taxLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        taxLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JSeparator separator = new JSeparator();
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        separator.setMaximumSize(new Dimension(200, 1));
        
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Checkout button
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkoutButton.setMaximumSize(new Dimension(200, checkoutButton.getPreferredSize().height));
        checkoutButton.addActionListener(e -> checkout());
        
        // Clear cart button
        JButton clearCartButton = new JButton("Clear Cart");
        clearCartButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        clearCartButton.setMaximumSize(new Dimension(200, clearCartButton.getPreferredSize().height));
        clearCartButton.addActionListener(e -> clearCart());
        
        // Add components to panel
        summaryPanel.add(subtotalLabel);
        summaryPanel.add(Box.createVerticalStrut(5));
        summaryPanel.add(taxLabel);
        summaryPanel.add(Box.createVerticalStrut(10));
        summaryPanel.add(separator);
        summaryPanel.add(Box.createVerticalStrut(10));
        summaryPanel.add(totalLabel);
        summaryPanel.add(Box.createVerticalStrut(30));
        summaryPanel.add(checkoutButton);
        summaryPanel.add(Box.createVerticalStrut(10));
        summaryPanel.add(clearCartButton);
        summaryPanel.add(Box.createVerticalGlue());
        
        return summaryPanel;
    }
    
    private void loadCartItems() {
        cartItemsPanel.removeAll();
        
        if (cartItems == null || cartItems.isEmpty()) {
            JLabel emptyCartLabel = new JLabel("Your cart is empty.");
            emptyCartLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyCartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cartItemsPanel.add(Box.createVerticalGlue());
            cartItemsPanel.add(emptyCartLabel);
            cartItemsPanel.add(Box.createVerticalGlue());
        } else {
            // Create a panel for headers and items with GridBagLayout for better control
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // Add header row
            JPanel headerRow = new JPanel(new GridLayout(1, 3, 10, 0));
            headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            
            JLabel productHeader = new JLabel("Product");
            productHeader.setFont(new Font("Arial", Font.BOLD, 14));
            
            JLabel priceHeader = new JLabel("Price");
            priceHeader.setFont(new Font("Arial", Font.BOLD, 14));
            
            JLabel actionHeader = new JLabel("Action");
            actionHeader.setFont(new Font("Arial", Font.BOLD, 14));
            
            headerRow.add(productHeader);
            headerRow.add(priceHeader);
            headerRow.add(actionHeader);
            
            contentPanel.add(headerRow);
            contentPanel.add(new JSeparator());
            
            // Add each cart item
            for (int i = 0; i < cartItems.size(); i++) {
                Product product = cartItems.get(i);
                
                JPanel itemRow = new JPanel(new GridLayout(1, 3, 10, 0));
                itemRow.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                itemRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                
                JLabel nameLabel = new JLabel(product.getName());
                nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                
                JLabel priceLabel = new JLabel("$" + df.format(product.getPrice()));
                priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                
                JButton removeButton = new JButton("Remove");
                final int index = i;
                removeButton.addActionListener(e -> removeFromCart(index));
                
                itemRow.add(nameLabel);
                itemRow.add(priceLabel);
                itemRow.add(removeButton);
                
                contentPanel.add(itemRow);
                
                // Add separator except after the last item
                if (i < cartItems.size() - 1) {
                    contentPanel.add(new JSeparator());
                }
            }
            
            cartItemsPanel.add(contentPanel);
        }
        
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }
    
    private void calculateTotals() {
        subtotal = 0.0;
        
        if (cartItems != null) {
            for (Product product : cartItems) {
                if (product != null && product.getPrice() >= 0) {
                    subtotal += product.getPrice();
                }
            }
        }
        
        // Calculate tax (7%)
        tax = subtotal * TAX_RATE;
        
        // Calculate total
        total = subtotal + tax;
        
        // Update labels
        subtotalLabel.setText("Subtotal: $" + df.format(subtotal));
        taxLabel.setText("Tax (" + (int)(TAX_RATE * 100) + "%): $" + df.format(tax));
        totalLabel.setText("Total: $" + df.format(total));
    }
    
    private void removeFromCart(int index) {
        try {
            if (cartItems != null && index >= 0 && index < cartItems.size()) {
                Product removedProduct = cartItems.get(index);
                cartItems.remove(index);
                
                // Update user's cart
                currentUser.setCart(cartItems);
                
                // Reload cart items
                loadCartItems();
                calculateTotals();
                
                // Optional: Show feedback message
                JOptionPane.showMessageDialog(this,
                    "Removed " + removedProduct.getName() + " from cart.",
                    "Item Removed",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "An error occurred: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearCart() {
        if (cartItems == null || cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Cart is already empty.",
                "Clear Cart",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to clear your cart?",
            "Clear Cart",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                cartItems.clear();
                
                // Update user's cart
                currentUser.setCart(cartItems);
                
                // Reload cart items
                loadCartItems();
                calculateTotals();
                
                JOptionPane.showMessageDialog(this,
                    "Cart has been cleared.",
                    "Clear Cart",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "An error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void checkout() {
        if (cartItems == null || cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Your cart is empty. Please add items before checkout.",
                "Empty Cart",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            StringBuilder orderSummary = new StringBuilder();
            orderSummary.append("Order Summary:\n\n");
            
            for (Product product : cartItems) {
                orderSummary.append(product.getName())
                        .append(" - $")
                        .append(df.format(product.getPrice()))
                        .append("\n");
            }
            
            orderSummary.append("\nSubtotal: $").append(df.format(subtotal));
            orderSummary.append("\nTax (").append((int)(TAX_RATE * 100)).append("%): $").append(df.format(tax));
            orderSummary.append("\n-------------------");
            orderSummary.append("\nTotal: $").append(df.format(total));
            
            // Create receipt dialog
            JTextArea receiptArea = new JTextArea(orderSummary.toString());
            receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            receiptArea.setEditable(false);
            receiptArea.setLineWrap(true);
            receiptArea.setWrapStyleWord(true);
            
            JScrollPane scrollPane = new JScrollPane(receiptArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            
            int confirm = JOptionPane.showConfirmDialog(this,
                scrollPane,
                "Confirm Purchase",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
                
            if (confirm == JOptionPane.OK_OPTION) {
                // Clear cart after checkout
                cartItems.clear();
                
                // Update user's cart
                currentUser.setCart(cartItems);
                
                // Reload cart items
                loadCartItems();
                calculateTotals();
                
                JOptionPane.showMessageDialog(this,
                    "Thank you for your purchase!\nYour order has been successfully placed.",
                    "Checkout Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "An error occurred during checkout: " + e.getMessage(),
                "Checkout Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}