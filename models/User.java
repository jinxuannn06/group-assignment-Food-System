package models;

public class User {
    private String userId;
    private String name;
    private String contactInfo;

    // Constructor
    public User(String userId, String name, String contactInfo) {
        this.userId = userId;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Structural toString for Member 1's displayAll() function
    @Override
    public String toString() {
        return "User [ID=" + userId + ", Name=" + name + ", Contact=" + contactInfo + "]";
    }
}