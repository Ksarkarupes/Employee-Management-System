import java.util.UUID;
import java.util.Base64;
import java.time.LocalDate;
public class Employee {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String dateOfBirth;

    private String position;
    private String department;


    public String getId() {
        return id;
    }
    public void generateId(){
        String id = String.valueOf(UUID.randomUUID());
        this.id = "2024/"+Base64.getUrlEncoder().withoutPadding().encodeToString(id.getBytes()).substring(0,4);
    }
    public void setId(String id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}

