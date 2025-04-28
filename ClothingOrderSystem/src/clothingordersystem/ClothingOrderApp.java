package clothingordersystem;
/**
 * File: ClothingOrderApp.java
 * Main application class that launches the Clothing Order System
 */
import javax.swing.*;
import java.awt.*;

public class ClothingOrderApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Set look and feel to system default
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Start with the login screen
            new LoginScreen();
        });
    }
}

