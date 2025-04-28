package clothingordersystem;

/**
 * File: LoginScreen.java
 * Handles the user login functionality
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class LoginScreen extends JFrame {
    private JTextField usernameField;
    
    public LoginScreen() {
        // Setup frame
        setTitle("Clothing Order System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title label
        JLabel titleLabel = new JLabel("CLOTHING ORDERING SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 30, 10);
        panel.add(titleLabel, gbc);
        
        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 10, 5, 5);
        panel.add(usernameLabel, gbc);
        
        // Username field
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 10);
        panel.add(usernameField, gbc);
        
        // Sign in button
        JButton signInButton = new JButton("Sign In");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(signInButton, gbc);
        
        // Add action to sign in button
        signInButton.addActionListener(e -> signIn());
        
        // Add enter key functionality to username field
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    signIn();
                }
            }
        });
        
        // Add panel to frame
        add(panel);
        
        // Display the frame
        setVisible(true);
    }
    
    private void signIn() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a username", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Close login screen
        dispose();
        
        // Open main page
        new MainPage(username);
    }
}