package Projects.StudentsMarksManagementSystem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

public class StudentsMarksManagementSystem {
    // Static variables for storing data
    static ArrayList<String[]> studentList = new ArrayList<>();
    static DefaultTableModel tableModel;

    public static void main(String[] args) {
        // Create main frame
        JFrame frame = new JFrame("Student Marks Management System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create input fields and labels
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 20, 80, 25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(120, 20, 150, 25);
        frame.add(nameField);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(30, 60, 80, 25);
        frame.add(rollLabel);

        JTextField rollField = new JTextField();
        rollField.setBounds(120, 60, 150, 25);
        frame.add(rollField);

        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setBounds(30, 100, 80, 25);
        frame.add(marksLabel);

        JTextField marksField = new JTextField();
        marksField.setBounds(120, 100, 150, 25);
        frame.add(marksField);

        // Create buttons
        JButton addButton = new JButton("Add Student");
        addButton.setBounds(30, 140, 120, 30);
        frame.add(addButton);

        JButton saveButton = new JButton("Save to File");
        saveButton.setBounds(160, 140, 120, 30);
        frame.add(saveButton);

        JButton loadButton = new JButton("Load from File");
        loadButton.setBounds(30, 180, 120, 30);
        frame.add(loadButton);

        JButton deleteButton = new JButton("Delete Student");
        deleteButton.setBounds(160, 180, 120, 30);
        frame.add(deleteButton);

        // Create table to display students
        tableModel = new DefaultTableModel(new String[]{"Name", "Roll No", "Marks"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 230, 500, 200);
        frame.add(scrollPane);

        // Button Actions
        addButton.addActionListener(e -> addStudent(nameField, rollField, marksField));
        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());
        deleteButton.addActionListener(e -> deleteStudent(table));

        // Load data from file on startup
        loadFromFile();

        frame.setVisible(true);
    }

    // Method to add a student
    static void addStudent(JTextField nameField, JTextField rollField, JTextField marksField) {
        String name = nameField.getText();
        String roll = rollField.getText();
        String marks = marksField.getText();

        if (name.isEmpty() || roll.isEmpty() || marks.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields!");
            return;
        }

        String[] student = {name, roll, marks};
        studentList.add(student);
        tableModel.addRow(student);

        nameField.setText("");
        rollField.setText("");
        marksField.setText("");
    }

    // Method to save data to file
    static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (String[] student : studentList) {
                writer.write(String.join(",", student));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }

    // Method to load data from file
    static void loadFromFile() {
        studentList.clear();
        tableModel.setRowCount(0);

        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] student = line.split(",");
                studentList.add(student);
                tableModel.addRow(student);
            }
        } catch (FileNotFoundException e) {
            // File might not exist initially, that's okay
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    // Method to delete a student
    static void deleteStudent(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            studentList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a student to delete!");
        }
    }
}