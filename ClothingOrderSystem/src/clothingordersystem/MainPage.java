package clothingordersystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.DecimalFormat;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

class MainPage extends JFrame {
    private String username;
    private JPanel mainPanel;
    private JPanel productPanel;
    private JLabel subtotalLabel;
    private double subtotal = 0.0;
    private ArrayList<Product> cart = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("0.00");
    
    // Product catalog
    private HashMap<String, ArrayList<Product>> productCatalog;
    
    public MainPage(String username) {
        this.username = username;
        
        // Initialize product catalog
        initializeProducts();
        
        // Setup frame
        setTitle("Clothing Order System - Main Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main layout
        mainPanel = new JPanel(new BorderLayout());
        
        // Add header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Add category panel on the left
        JPanel categoryPanel = createCategoryPanel();
        mainPanel.add(categoryPanel, BorderLayout.WEST);
        
        // Create product panel for the center
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(productPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create welcome message in product panel
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2>Welcome to the Clothing Order System!</h2>"
                + "<p>Select a category from the left to view products.</p></div></html>");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        productPanel.add(Box.createVerticalGlue());
        productPanel.add(welcomeLabel);
        productPanel.add(Box.createVerticalGlue());
        
        // Add cart panel at the bottom
        JPanel cartPanel = createCartPanel();
        mainPanel.add(cartPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Display the frame
        setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("CLOTHING ORDERING SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create a panel for the right side with username and cart button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // Add cart button with icon
        JButton cartButton = new JButton("View Cart");
        cartButton.setIcon(createCartIcon());
        cartButton.addActionListener(e -> openCart());
        
        JLabel userLabel = new JLabel("Welcome, " + username);
        userLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        
        rightPanel.add(userLabel);
        rightPanel.add(cartButton);
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private ImageIcon createCartIcon() {
        // Create a simple cart icon
        int size = 16;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setColor(Color.BLACK);
        // Draw cart body
        g2d.drawRect(0, 8, 12, 6);
        // Draw cart wheels
        g2d.fillOval(2, 14, 3, 3);
        g2d.fillOval(8, 14, 3, 3);
        // Draw cart handle
        g2d.drawLine(12, 10, 15, 5);
        
        g2d.dispose();
        
        return new ImageIcon(image);
    }
    
    private void openCart() {
        // Create a User object to pass to Cart
        User currentUser = new User(username);
        currentUser.setCart(cart);
        
        // Open the cart window
        new Cart(currentUser);
    }
    
    private void initializeProducts() {
        productCatalog = new HashMap<>();
        
        // Tops
        ArrayList<Product> tops = new ArrayList<>();
        tops.add(new Product("T-Shirt", 19.99));
        tops.add(new Product("Dress Shirt", 39.99));
        tops.add(new Product("Sweater", 29.99));
        tops.add(new Product("Hoodie", 34.99));
        tops.add(new Product("Polo Shirt", 24.99));
        productCatalog.put("Tops", tops);
        
        // Bottoms
        ArrayList<Product> bottoms = new ArrayList<>();
        bottoms.add(new Product("Jeans", 45.99));
        bottoms.add(new Product("Shorts", 25.99));
        bottoms.add(new Product("Khakis", 35.99));
        bottoms.add(new Product("Sweatpants", 29.99));
        bottoms.add(new Product("Skirt", 32.99));
        productCatalog.put("Bottom", bottoms);
        
        // Shoes
        ArrayList<Product> shoes = new ArrayList<>();
        shoes.add(new Product("Sneakers", 59.99));
        shoes.add(new Product("Dress Shoes", 79.99));
        shoes.add(new Product("Sandals", 24.99));
        shoes.add(new Product("Boots", 89.99));
        shoes.add(new Product("Slippers", 19.99));
        productCatalog.put("Shoes", shoes);
    }
    
    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder("Categories")
        ));
        categoryPanel.setPreferredSize(new Dimension(150, 0));
        
        String[] categories = {"Tops", "Bottom", "Shoes"};
        for (String category : categories) {
            JButton categoryButton = new JButton(category);
            categoryButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, categoryButton.getPreferredSize().height));
            categoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            categoryButton.addActionListener(e -> displayProductsForCategory(category));
            
            categoryPanel.add(categoryButton);
            categoryPanel.add(Box.createVerticalStrut(10));
        }
        
        return categoryPanel;
    }
    
    private JPanel createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        subtotalLabel = new JLabel("Subtotal: $" + df.format(subtotal));
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(e -> openCart());
        
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> checkout());
        
        buttonPanel.add(viewCartButton);
        buttonPanel.add(checkoutButton);
        
        cartPanel.add(subtotalLabel, BorderLayout.WEST);
        cartPanel.add(buttonPanel, BorderLayout.EAST);
        
        return cartPanel;
    }
    
    private void displayProductsForCategory(String category) {
        // Clear existing products
        productPanel.removeAll();
        
        // Add title for the category
        JLabel categoryTitle = new JLabel(category);
        categoryTitle.setFont(new Font("Arial", Font.BOLD, 20));
        categoryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        productPanel.add(categoryTitle);
        
        // Get products for this category
        ArrayList<Product> products = productCatalog.get(category);
        
        // Add each product
        for (Product product : products) {
            JPanel productItemPanel = new JPanel();
            productItemPanel.setLayout(new BoxLayout(productItemPanel, BoxLayout.X_AXIS));
            productItemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            productItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            
            JLabel productName = new JLabel(product.getName());
            productName.setFont(new Font("Arial", Font.PLAIN, 14));
            
            JLabel productPrice = new JLabel("$" + df.format(product.getPrice()));
            productPrice.setFont(new Font("Arial", Font.PLAIN, 14));
            
            JButton addButton = new JButton("Add");
            addButton.addActionListener(e -> addToCart(product, category));
            
            productItemPanel.add(productName);
            productItemPanel.add(Box.createHorizontalGlue());
            productItemPanel.add(productPrice);
            productItemPanel.add(Box.createHorizontalStrut(20));
            productItemPanel.add(addButton);
            
            productPanel.add(productItemPanel);
        }
        
        // Add some space at the bottom
        productPanel.add(Box.createVerticalGlue());
        
        // Refresh the panel
        productPanel.revalidate();
        productPanel.repaint();
    }
    
    private void addToCart(Product product, String category) {
        // Create a new product object to avoid reference issues
        Product cartProduct = new Product(product.getName(), product.getPrice());
        
        cart.add(cartProduct);
        subtotal += cartProduct.getPrice();
        updateSubtotal();
        
        JOptionPane.showMessageDialog(this, 
            product.getName() + " added to your cart!", 
            "Product Added", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateSubtotal() {
        subtotalLabel.setText("Subtotal: $" + df.format(subtotal));
    }
    
    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Your cart is empty. Please add items before checkout.", 
                "Empty Cart", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Create a User object to pass to Cart
        User currentUser = new User(username);
        currentUser.setCart(cart);
        
        // Open the cart window for checkout
        new Cart(currentUser);
    }
}