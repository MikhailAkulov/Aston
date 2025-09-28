package task2.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private Detail userDetail;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "user_learning_direction",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "direction_id")
    )
    private List<Direction> directions;

    public User() {
    }

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User [id = " + id + ", name = " + name + ", email = " + email + ", created_at = " + createdAt + "]";
    }

    public void addDirectionToUser(Direction direction) {
        if (directions == null) {
            directions = new ArrayList<>();
        }
        directions.add(direction);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean checkName(String name) {
//        String pattern1 = "[a-zA-Z0-9\\._\\-]{3,}";
        String pattern2 = "^[A-Za-z]+$";
        if (!name.isBlank() && name.length() >= 2 && name.matches(pattern2)) {
            return true;
        } else {
            System.out.println("Имя должно содержать только латинские буквы, не менее 2-х.");
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static boolean checkEmail(String email) {
        String pattern1 = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";
//        String pattern2 = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.isBlank() && email.matches(pattern1)) {
            return true;
        } else {
            System.out.println("Неправильно введён email, воспользуйтесь шаблоном: hello@example.com");
            return false;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static boolean checkAge(int age) {
        if (age > 0 && age <= 100) {
            return true;
        } else {
            System.out.println("Значение возраста должно быть числом в диапазоне от 1 до 100");
            return false;
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Detail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(Detail userDetail) {
        this.userDetail = userDetail;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }
}
