package if1001.watchtime.Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import if1001.watchtime.DAO.ConfigFirebase;

public class Users {

    private String id;
    private String email;
    private String password;
    private String name;
    private String lastname;
    private String birthday;
    private String gender;

    public Users() {
    }

    public void save() {
        DatabaseReference firebaseReference = ConfigFirebase.getFirebase();
        firebaseReference.child("user").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude

    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapUser = new HashMap<>();

        hashMapUser.put("id", getId());
        hashMapUser.put("email", getEmail());
        hashMapUser.put("password", getPassword());
        hashMapUser.put("name", getName());
        hashMapUser.put("lastname", getLastname());
        hashMapUser.put("birthday", getBirthday());
        hashMapUser.put("gender", getGender());

        return hashMapUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
