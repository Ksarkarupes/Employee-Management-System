import java.sql.*;
import java.util.*;

public class Main {

    // Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/employee_database";
    private static final String username = "root";
    private static final String password = "GbnpMPRW@1";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("----------------------- Application Started -----------------------\n");
        Connection connection = databaseConnection(); // Establish database connection
        if (connection != null) {
            menu(connection); // Display menu options
        } else {
            System.out.println("Failed to connect to the database. Exiting...");
        }
    }

    // Method to establish a connection to the database
    public static Connection databaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established successfully.\n");
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }

        return connection;
    }

    // Display menu options for user interaction
    public static void menu(Connection connection) {
        while (true) {
            System.out.println("\n--- Menu Options ---");
            System.out.println("Press 0 to Add a New Employee");
            System.out.println("Press 1 for Existing Employee Operations");
            System.out.println("Press 2 to View Employee Details");
            System.out.println("Press 3 to Exit\n");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            int[] status;
            switch (choice) {
                case 0:
                    Employee employee = createEmployee(); // Create a new employee object
                    status = pushToDatabase(employee, connection); // Add employee to database
                    if (status[0] == 0) {
                        System.out.println("Could not update the database.\n");
                    } else {
                        System.out.println("Data updated in the database successfully.\n");
                    }
                    break;

                case 1:
                    System.out.println("Press 0 to Update Employee");
                    System.out.println("Press 1 to Remove Employee");
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();

                    if (choice == 0) {
                        status = updateEmployee(connection); // Update employee details
                        if (status[0] == 0) {
                            System.out.println("Could not update the database.\n");
                        } else {
                            System.out.println("Data updated in the database successfully.\n");
                        }
                    } else if (choice == 1) {
                        status = removeEmployee(connection); // Remove employee from database
                        if (status[0] == 0) {
                            System.out.println("Could not update the database.\n");
                        } else {
                            System.out.println("Data updated in the database successfully.\n");
                        }
                    } else {
                        System.out.println("Invalid input.\n");
                    }
                    break;

                case 2:
                    System.out.println("Press 0 to View All Employees");
                    System.out.println("Press 1 to View Specific Employee");
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    fetchData(connection, choice); // Fetch and display employee data
                    break;

                case 3:
                    System.out.println("Exiting Application.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    // Create a new Employee object with user inputs
    public static Employee createEmployee() {
        Employee employee = new Employee();
        employee.generateId(); // Generate a unique ID for the employee

        System.out.print("Enter Name: ");
        employee.setName(sc.nextLine());

        System.out.print("Enter Age: ");
        employee.setAge(sc.nextInt());
        sc.nextLine(); // Clear buffer

        System.out.print("Enter Gender: ");
        employee.setGender(sc.nextLine());

        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        employee.setDateOfBirth(sc.nextLine());

        System.out.print("Enter Position: ");
        employee.setPosition(sc.nextLine());

        System.out.print("Enter Department: ");
        employee.setDepartment(sc.nextLine());

        return employee;
    }

    // Push a new employee's data to the database
    public static int[] pushToDatabase(Employee employee, Connection connection) {
        int[] status = new int[1];
        try {
            String query = "INSERT INTO employee (id, name, age, gender, dateOfBirth, position, department) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getId());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getGender());
            preparedStatement.setString(5, employee.getDateOfBirth());
            preparedStatement.setString(6, employee.getPosition());
            preparedStatement.setString(7, employee.getDepartment());

            preparedStatement.addBatch();
            status = preparedStatement.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
        return status;
    }

    // Update an existing employee's data
    public static int[] updateEmployee(Connection connection) {
        System.out.print("Enter the ID of the employee to update: ");
        String id = sc.nextLine();
        System.out.print("Enter the column to update: ");
        String columnName = sc.nextLine();
        System.out.print("Enter the new value: ");
        String columnValue = sc.nextLine();

        int[] status = new int[1];
        try {
            String query = "UPDATE employee SET " + columnName + " = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            if (columnName.equals("age")) {
                preparedStatement.setInt(1, Integer.parseInt(columnValue));
            } else {
                preparedStatement.setString(1, columnValue);
            }
            preparedStatement.setString(2, id);

            preparedStatement.addBatch();
            status = preparedStatement.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
        return status;
    }

    // Remove an employee's data from the database
    public static int[] removeEmployee(Connection connection) {
        System.out.print("Enter the ID of the employee to remove: ");
        String id = sc.nextLine();

        int[] status = new int[1];
        try {
            String query = "DELETE FROM employee WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);

            preparedStatement.addBatch();
            status = preparedStatement.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error deleting data: " + e.getMessage());
        }
        return status;
    }

    // Fetch and display employee data
    public static void fetchData(Connection connection, int choice) {
        Employee employee = new Employee();
        try {
            String query;
            PreparedStatement preparedStatement;
            if (choice == 0) {
                query = "SELECT * FROM employee";
                preparedStatement = connection.prepareStatement(query);
            } else {
                query = "SELECT * FROM employee WHERE id = ?";
                System.out.print("Enter the ID of the employee: ");
                String id = sc.nextLine();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, id);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 1;
            while (resultSet.next()) {
                employee.setId(resultSet.getString("id"));
                employee.setName(resultSet.getString("name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setGender(resultSet.getString("gender"));
                employee.setDateOfBirth(resultSet.getString("dateOfBirth"));
                employee.setPosition(resultSet.getString("position"));
                employee.setDepartment(resultSet.getString("department"));
                printDetails(employee, count++);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }

    // Print employee details
    public static void printDetails(Employee employee, int count) {
        System.out.println("\n--- Employee " + count + " Details ---");
        System.out.println("ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Age: " + employee.getAge());
        System.out.println("Gender: " + employee.getGender());
        System.out.println("Date of Birth: " + employee.getDateOfBirth());
        System.out.println("Position: " + employee.getPosition());
        System.out.println("Department: " + employee.getDepartment());
    }
}
