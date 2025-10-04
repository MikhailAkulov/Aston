package task2.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "directions")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "learning_direction")
    private String learningDirection;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
            , fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_learning_direction",
            joinColumns = @JoinColumn(name = "direction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Direction() {
    }

    public Direction(String learningDirection) {
        this.learningDirection = learningDirection;
    }

    @Override
    public String toString() {
        return "id: " + id + ", learningDirection: " + learningDirection;
    }

    public void addUserToDirection(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLearningDirection() {
        return learningDirection;
    }

    public void setLearningDirection(String learningDirection) {
        this.learningDirection = learningDirection;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
