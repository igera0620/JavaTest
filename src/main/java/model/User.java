package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;  // ハッシュ化前提
    private Timestamp createdAt;
    private LocalDateTime deletedAt;
    private int delFlg;

    public User() {}

    public User(int id, String firstName, String lastName, String email, String password,
                Timestamp createdAt, LocalDateTime deletedAt, int delFlg) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.delFlg = delFlg;
    }

    // ===== Getter / Setter =====
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getDelFlg() {
        return delFlg;
    }
    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }
}
