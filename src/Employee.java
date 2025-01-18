import java.util.UUID; // For generating unique identifiers
import java.util.Base64; // For encoding the unique identifier to create a compact ID
import java.time.LocalDate; // For handling date-related operations

/**
 * The Employee class represents an employee with various attributes like ID, name, age, gender,
 * date of birth, position, and department. It provides getter and setter methods for all attributes
 * and a method to generate a unique ID for each employee.
 */
public class Employee {
    // Attributes of the Employee class
    private String id; // Unique identifier for the employee
    private String name; // Name of the employee
    private int age; // Age of the employee
    private String gender; // Gender of the employee
    private String dateOfBirth; // Date of birth of the employee in YYYY-MM-DD format

    private String position; // Position of the employee in the organization
    private String department; // Department of the employee

    /**
     * Gets the unique ID of the employee.
     *
     * @return The employee's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Generates a unique ID for the employee using UUID and Base64 encoding.
     * The generated ID is prefixed with the year "2024/".
     */
    public void generateId() {
        // Generate a random UUID, encode it in Base64, and take the first 4 characters
        String uuid = String.valueOf(UUID.randomUUID());
        this.id = "2024/" + Base64.getUrlEncoder().withoutPadding().encodeToString(uuid.getBytes()).substring(0, 4);
    }

    /**
     * Sets the unique ID for the employee.
     *
     * @param id The ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the employee.
     *
     * @return The employee's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the employee.
     *
     * @return The employee's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the employee.
     *
     * @param age The age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the gender of the employee.
     *
     * @return The employee's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the employee.
     *
     * @param gender The gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the date of birth of the employee.
     *
     * @return The employee's date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the employee.
     *
     * @param dateOfBirth The date of birth to set in YYYY-MM-DD format.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the position of the employee in the organization.
     *
     * @return The employee's position.
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the employee in the organization.
     *
     * @param position The position to set.
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets the department of the employee in the organization.
     *
     * @return The employee's department.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department of the employee in the organization.
     *
     * @param department The department to set.
     */
    public void setDepartment(String department) {
        this.department = department;
    }
}
