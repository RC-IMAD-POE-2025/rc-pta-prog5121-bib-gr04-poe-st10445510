package com.mycompany.registrationandlogin;

import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class RegistrationAndLoginApp {
    public static HashMap<String, String> userDatabase = new HashMap<>(); // Stores registered users

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }

    // Registration Form
    public static class RegistrationForm extends JFrame {
        public JTextField usernameField, phoneField;
        public JPasswordField passwordField;
        public JComboBox<String> countryCodeComboBox;
        public JLabel messageLabel;
        private final JCheckBox showPasswordCheckBox;

        public RegistrationForm() {
            // Set up the JFrame
            setTitle("Registration Form");
            setSize(800, 850); // Increased form size
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            getContentPane().setBackground(new Color(255, 222, 173)); // Light orange background

            // Add maximize, minimize, and close buttons
            JButton maximizeButton = new JButton("⬜");
            maximizeButton.setBounds(700, 10, 50, 30);
            add(maximizeButton);
            maximizeButton.addActionListener(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

            JButton minimizeButton = new JButton("_");
            minimizeButton.setBounds(650, 10, 50, 30);
            add(minimizeButton);
            minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));

            JButton closeButton = new JButton("X");
            closeButton.setBounds(750, 10, 50, 30);
            add(closeButton);
            closeButton.addActionListener(e -> System.exit(0));

            // Add components
            JLabel titleLabel = new JLabel("Registration Form");
            titleLabel.setBounds(250, 50, 300, 40); // Adjusted position
            titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24)); // Italicized font
            titleLabel.setForeground(new Color(0, 102, 204)); // Blue text
            add(titleLabel);

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setBounds(50, 120, 150, 30);
            add(usernameLabel);

            usernameField = new JTextField();
            usernameField.setBounds(200, 120, 400, 30); // Slightly wider text field
            add(usernameField);

            JLabel phoneLabel = new JLabel("Cellphone Number:");
            phoneLabel.setBounds(50, 180, 150, 30);
            add(phoneLabel);

            phoneField = new JTextField();
            phoneField.setBounds(200, 180, 300, 30);
            add(phoneField);

            JLabel countryCodeLabel = new JLabel("Country Code:");
            countryCodeLabel.setBounds(50, 240, 150, 30);
            add(countryCodeLabel);

            // Populate country code dropdown
            String[] countryCodes = {"+27 (South Africa)"};
            countryCodeComboBox = new JComboBox<>(countryCodes);
            countryCodeComboBox.setBounds(200, 240, 400, 30);
            add(countryCodeComboBox);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(50, 300, 150, 30);
            add(passwordLabel);

            passwordField = new JPasswordField();
            passwordField.setBounds(200, 300, 400, 30); // Wider text field
            add(passwordField);

            // Add checkbox to show password
            showPasswordCheckBox = new JCheckBox("Show Password");
            showPasswordCheckBox.setBounds(200, 340, 150, 30);
            showPasswordCheckBox.addActionListener(e -> togglePasswordVisibility());
            add(showPasswordCheckBox);

            JButton registerButton = new JButton("Register");
            registerButton.setBounds(250, 380, 150, 40); // Increased button size
            registerButton.setBackground(new Color(0, 204, 255)); // Light cyan button
            add(registerButton);

            JButton goToLoginButton = new JButton("Go to Login");
            goToLoginButton.setBounds(250, 440, 150, 40); // Increased button size
            goToLoginButton.setBackground(new Color(0, 204, 255)); // Light cyan button
            add(goToLoginButton);

            messageLabel = new JLabel("");
            messageLabel.setBounds(50, 500, 700, 200); // Adjusted position and size
            messageLabel.setForeground(Color.RED);
            messageLabel.setVerticalAlignment(SwingConstants.TOP);
            add(messageLabel);

            // Add ActionListener for the Register button
            registerButton.addActionListener(e -> registerUser());

            // Add ActionListener for the Go to Login button
            goToLoginButton.addActionListener(e -> {
                dispose(); // Close the registration form
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            });
        }

        // Toggle password visibility
        private void togglePasswordVisibility() {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        }

        public void registerUser() {
            String username = usernameField.getText();
            String phone = phoneField.getText();
            String countryCode = (String) countryCodeComboBox.getSelectedItem(); // Get selected country code
            String password = new String(passwordField.getPassword());

            // Boolean validations
            boolean isUsernameValid = username.matches("^[a-zA-Z0-9_]{1,5}$") && username.contains("_"); // Username must contain an underscore and be no more than 5 characters
            boolean isPhoneValid = phone.matches("0\\d{9}"); // South African phone number must start with 0 and be 10 digits
            boolean isPasswordValid = password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$"); // Password must meet the criteria
            boolean isUsernameUnique = !userDatabase.containsKey(username); // Check if username is unique

            // Build success or error messages
            String usernameMessage = isUsernameValid
                ? (isUsernameUnique ? "Username added successfully." : "Username already exists. Please choose a different username.")
                : "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
            String phoneMessage = isPhoneValid ? "Cellphone number added successfully." : "Cellphone number has been incorrectly added.";
            String passwordMessage = isPasswordValid ? "Password added successfully." : "Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";

            // Display messages
            if (isUsernameValid && isPhoneValid && isPasswordValid && isUsernameUnique) {
                userDatabase.put(username, password); // Store username and password in the database
                messageLabel.setText("<html>" + usernameMessage + "<br>" + phoneMessage + "<br>" + passwordMessage + "</html>");
                messageLabel.setForeground(new Color(0, 128, 0)); // Dark green
            } else {
                messageLabel.setText("<html>" + usernameMessage + "<br>" + phoneMessage + "<br>" + passwordMessage + "</html>");
                messageLabel.setForeground(Color.RED);
            }
        }
    }

    // Login Form
    public static class LoginForm extends JFrame {
        public JTextField usernameField;
        public JPasswordField passwordField;
        public JLabel messageLabel;
        private final JCheckBox showPasswordCheckBox;

        public LoginForm() {
            // Set up the JFrame
            setTitle("Login Form");
            setSize(700, 600); // Increased form size
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            getContentPane().setBackground(new Color(255, 222, 173)); // Light orange background

            // Add maximize, minimize, and close buttons
            JButton maximizeButton = new JButton("⬜");
            maximizeButton.setBounds(600, 10, 50, 30);
            add(maximizeButton);
            maximizeButton.addActionListener(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

            JButton minimizeButton = new JButton("_");
            minimizeButton.setBounds(550, 10, 50, 30);
            add(minimizeButton);
            minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));

            JButton closeButton = new JButton("X");
            closeButton.setBounds(650, 10, 50, 30);
            add(closeButton);
            closeButton.addActionListener(e -> System.exit(0));

            // Add components
            JLabel titleLabel = new JLabel("Login Form");
            titleLabel.setBounds(250, 50, 300, 40); // Adjusted position
            titleLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24)); // Italicized font
            titleLabel.setForeground(new Color(0, 102, 204)); // Blue text
            add(titleLabel);

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setBounds(50, 120, 150, 30);
            add(usernameLabel);

            usernameField = new JTextField();
            usernameField.setBounds(200, 120, 400, 30); // Wider text field
            add(usernameField);

            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(50, 180, 150, 30);
            add(passwordLabel);

            passwordField = new JPasswordField();
            passwordField.setBounds(200, 180, 400, 30); // Wider text field
            add(passwordField);

            // Add checkbox to show password
            showPasswordCheckBox = new JCheckBox("Show Password");
            showPasswordCheckBox.setBounds(200, 220, 150, 30);
            showPasswordCheckBox.addActionListener(e -> togglePasswordVisibility());
            add(showPasswordCheckBox);

            JButton loginButton = new JButton("Login");
            loginButton.setBounds(250, 270, 200, 40); // Increased button size
            loginButton.setBackground(new Color(0, 204, 255)); // Light cyan button
            add(loginButton);

            JButton createAccountButton = new JButton("Click here if you don't have an account");
            createAccountButton.setBounds(200, 330, 300, 40); // Adjusted text and size
            createAccountButton.setBackground(new Color(0, 204, 255)); // Light cyan button
            add(createAccountButton);

            messageLabel = new JLabel("");
            messageLabel.setBounds(50, 380, 600, 50);
            messageLabel.setForeground(Color.RED);
            messageLabel.setVerticalAlignment(SwingConstants.TOP);
            add(messageLabel);

            // Add ActionListener for the Login button
            loginButton.addActionListener(e -> loginUser());

            // Add ActionListener for the Create Account button
            createAccountButton.addActionListener(e -> {
                dispose(); // Close the login form
                RegistrationForm registrationForm = new RegistrationForm();
                registrationForm.setVisible(true);
            });
        }

        // Toggle password visibility
        private void togglePasswordVisibility() {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show password
            } else {
                passwordField.setEchoChar('*'); // Hide password
            }
        }

        public void loginUser() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Boolean validations
            boolean isLoginValid = userDatabase.containsKey(username) && userDatabase.get(username).equals(password);

            // Display messages
            String loginMessage = isLoginValid ? "Hello " + username + " it's wonderful to see you once again." : "Invalid username or password.";
            messageLabel.setText(loginMessage);
            messageLabel.setForeground(isLoginValid ? new Color(0, 128, 0) : Color.RED); // Dark green for success, red for failure
        }
    }
}
