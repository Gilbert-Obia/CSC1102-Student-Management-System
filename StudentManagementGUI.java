
/*
====================================================
CSC 1102: STRUCTURED AND OBJECT-ORIENTED PROGRAMMING

PROJECT TITLE: STUDENT MANAGEMENT SYSTEM

GROUP DETAILS:
Group Number: Group 5
Group Leader: Nakirima Joan

GROUP MEMBERS:
1. Taneta Catherine
2. Muwanga Abdul Malik
3. Ewuonzi Gilbert Obia
4. Olanya Collins

SESSION: AFTERNOON

PROJECT DESCRIPTION:
A simple Java Swing application designed to
register, search, display and delete student records.

====================================================
*/

import javax.swing.*;
import java.awt.*;

public class StudentManagementGUI extends JFrame {

    // Connect the GUI to our StudentManager class
    private StudentManager manager;

    // Text fields for entering student information
    private JTextField idField;
    private JTextField nameField;
    private JTextField programmeField;
    private JTextField yearField;

    // Area for displaying student records
    private JTextArea outputArea;

    // Constructor
    public StudentManagementGUI() {

        manager = new StudentManager();

        // Set up the application window
        setTitle("Student Management System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));

        idField = new JTextField();
        nameField = new JTextField();
        programmeField = new JTextField();
        yearField = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Programme:"));
        formPanel.add(programmeField);

        formPanel.add(new JLabel("Year of Study:"));
        formPanel.add(yearField);

        // Create buttons
        JButton addButton = new JButton("Register Student");
        JButton searchButton = new JButton("Search Student");
        JButton displayButton = new JButton("Display All");
        JButton deleteButton = new JButton("Delete Student");

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(deleteButton);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Arrange components
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Register button action
        addButton.addActionListener(e -> addStudent());

        // Search button action
        searchButton.addActionListener(e -> searchStudent());

        // Display button action
        displayButton.addActionListener(e -> displayStudents());

        // Delete button action
        deleteButton.addActionListener(e -> deleteStudent());

    }

    // Register a new student
    private void addStudent() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String programme = programmeField.getText().trim();
        String yearText = yearField.getText().trim();

        // Check for empty fields
        if (id.isEmpty() || name.isEmpty()
                || programme.isEmpty() || yearText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all student details."
            );

            return;
        }

        try {

            int year = Integer.parseInt(yearText);

            if (year < 1 || year > 7) {
                JOptionPane.showMessageDialog(
                        this,
                        "Year of study must be between 1 and 7."
                );
                return;
            }

            // Create a Student object
            Student student = new Student(
                    id, name, programme, year
            );

            // Add the student using StudentManager
            if (manager.addStudent(student)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student registered successfully!"
                );

                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Student ID already exists or storage is full."
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Year of study must be a valid number."
            );
        }
    }

    // Search for a student
    private void searchStudent() {

        String id = idField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a Student ID."
            );
            return;
        }

        Student student = manager.searchStudent(id);

        if (student != null) {

            outputArea.setText(student.toString());

        } else {

            outputArea.setText("Student not found.");
        }
    }

    // Display all students
    private void displayStudents() {

        outputArea.setText(manager.displayAllStudents());
    }

    // Delete a student
    private void deleteStudent() {

        String id = idField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the Student ID to delete."
            );
            return;
        }

        boolean deleted = manager.deleteStudent(id);

        if (deleted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student deleted successfully!"
            );

            outputArea.setText(manager.displayAllStudents());

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found."
            );
        }
    }

    // Clear the input fields
    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        programmeField.setText("");
        yearField.setText("");
    }

    // Main method: starts the application
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            StudentManagementGUI gui =
                    new StudentManagementGUI();

            gui.setVisible(true);

        });
    }
}
