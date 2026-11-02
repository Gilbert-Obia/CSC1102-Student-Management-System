
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StudentManager {

    // Array for storing Student objects
    private Student[] students;

    // Number of registered students
    private int studentCount;

    // File used for permanent storage
    private static final String FILE_NAME = "students.txt";

    // Constructor
    public StudentManager() {

        students = new Student[100];
        studentCount = 0;

        // Load previously saved students
        loadStudents();
    }

    // Add a student
    public boolean addStudent(Student student) {

        if (student == null) {
            return false;
        }

        if (studentCount >= students.length) {
            return false;
        }

        // Check for duplicate student IDs
        for (int i = 0; i < studentCount; i++) {

            if (students[i].getStudentId().equalsIgnoreCase(
                    student.getStudentId())) {

                return false;
            }
        }

        students[studentCount] = student;
        studentCount++;

        // Save records after registration
        saveStudents();

        return true;
    }

    // Search for a student
    public Student searchStudent(String studentId) {

        for (int i = 0; i < studentCount; i++) {

            if (students[i].getStudentId().equalsIgnoreCase(studentId)) {
                return students[i];
            }
        }

        return null;
    }

    // Display all registered students
    public String displayAllStudents() {

        if (studentCount == 0) {
            return "No students have been registered.";
        }

        String result = "";

        for (int i = 0; i < studentCount; i++) {

            result += students[i].toString()
                    + "\n--------------------------\n";
        }

        return result;
    }

    // Delete a student
    public boolean deleteStudent(String studentId) {

        for (int i = 0; i < studentCount; i++) {

            if (students[i].getStudentId().equalsIgnoreCase(studentId)) {

                // Shift remaining students to the left
                for (int j = i; j < studentCount - 1; j++) {
                    students[j] = students[j + 1];
                }

                students[studentCount - 1] = null;
                studentCount--;

                // Save the updated records
                saveStudents();

                return true;
            }
        }

        return false;
    }

    // Save all students into the text file
    private void saveStudents() {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (int i = 0; i < studentCount; i++) {

                Student student = students[i];

                writer.write(
                        student.getStudentId() + "|"
                        + student.getFullName() + "|"
                        + student.getProgramme() + "|"
                        + student.getYearOfStudy()
                );

                writer.newLine();
            }

            System.out.println("Student records saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving student records: "
                    + e.getMessage());
        }
    }

    // Load students from the text file
    private void loadStudents() {

        File file = new File(FILE_NAME);

        // Check whether the file exists
        if (!file.exists()) {
            System.out.println("No previous student records found.");
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            // Read the file one line at a time
            while ((line = reader.readLine()) != null
                    && studentCount < students.length) {

                String[] details = line.split("\\|", -1);

                // Check whether the record has four fields
                if (details.length == 4) {

                    try {

                        int year = Integer.parseInt(details[3]);

                        if (year >= 1 && year <= 7
                                && searchStudent(details[0]) == null) {

                            Student student = new Student(
                                    details[0],
                                    details[1],
                                    details[2],
                                    year
                            );

                            students[studentCount] = student;
                            studentCount++;
                        }

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "An invalid student record was skipped."
                        );
                    }
                }
            }

            System.out.println(studentCount
                    + " student record(s) loaded successfully.");

        } catch (IOException e) {

            System.out.println("Error loading student records: "
                    + e.getMessage());
        }
    }

    // Get the number of registered students
    public int getStudentCount() {
        return studentCount;
    }
}
