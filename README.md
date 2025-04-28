Clothing Order System (Java GUI)

Welcome to the Clothing Order System, a desktop shopping app built using Java Swing!
This project simulates an online clothing store experience — users can log in, browse products by category, add items to a cart, and proceed to checkout.
No database is needed; all data is managed locally using .dat files for persistence.

✨ Features Implemented
Sign In Page
Username field for user input

"Sign In" button to proceed to the main page

Error handling for empty username (prevents login)

Main Page after Login
Displays a welcome message with the entered username

Three category buttons:

Tops

Bottoms

Shoes

Product Display
Clicking a category shows all products under it

Each product card displays:

Product name

Price

"Add" button to add the product to the cart

Shopping Cart Functionality
Dedicated Cart page (Cart.java) accessible via "View Cart" button

View all selected items with names, prices, and quantities

Subtotal is automatically calculated and displayed

Checkout functionality:

Shows order summary

Final total includes tax (7%)

Cart is cleared after successful checkout

Ability to remove individual items or clear the entire cart

Local Storage (No Database Needed)
All products, users, and orders are stored in .dat files

Application automatically loads data on startup and saves changes on actions

Persistence across sessions

📂 File Structure
pgsql
Copy
Edit
ClothingOrderSystem/
│
├── src/
│   ├── ClothingOrderApp.java    (Main application class - launches login)
│   ├── LoginScreen.java         (Handles sign-in functionality)
│   ├── MainPage.java            (Displays main shopping page after login)
│   ├── Product.java             (Represents clothing items)
│   ├── User.java                (Handles user info and their cart)
│   ├── Order.java               (Stores completed order details)
│   ├── Cart.java                (Separate window for cart management)
│   └── DataStore.java           (Manages saving/loading data to/from .dat files)
│
├── products.dat                 (Local storage for products)
├── users.dat                    (Local storage for users and carts)
├── orders.dat                   (Local storage for completed orders)
└── README.md                    (This file)
🖥️ How to Run the Application
Ensure you have Java SE 8 or higher installed.

Place all .java files inside the src/ directory.

Compile all Java files together:

bash
Copy
Edit
javac src/*.java
Run the program from the src/ directory:

bash
Copy
Edit
java ClothingOrderApp
The Login Screen will appear first.

After entering a username and clicking "Sign In," you will be redirected to the Main Page.

Start browsing, adding items to your cart, and checking out!

🛠 Technologies Used
Java SE 8+

Java Swing (GUI components: JFrame, JPanel, JButton, JLabel, etc.)

Java Serialization (ObjectOutputStream, ObjectInputStream)

📈 Future Improvements
Add product images alongside product names

Implement sorting and filtering options (e.g., by price, brand)

Enhance the design using custom icons and styles

Build an admin panel for managing products

Add a login authentication system (currently any username is accepted)

📄 License
This project is licensed for educational purposes.
Feel free to fork and modify it for your learning!

