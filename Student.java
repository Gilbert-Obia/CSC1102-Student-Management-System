public class Student {

    // Student attributes
    private String studentId;
    private String fullName;
    private String programme;
    private int yearOfStudy;

    // Constructor
    public Student(String studentId, String fullName,
                   String programme, int yearOfStudy) {

        this.studentId = studentId;
        this.fullName = fullName;
        this.programme = programme;
        this.yearOfStudy = yearOfStudy;
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getProgramme() {
        return programme;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    // Setters
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    // Display student information
    @Override
    public String toString() {
        return "Student ID: " + studentId
                + "\nFull Name: " + fullName
                + "\nProgramme: " + programme
                + "\nYear of Study: " + yearOfStudy;
    }
}