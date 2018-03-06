package model;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

public class Contact {
    private String firstName;
    private String lastName;
    private String email;
    private int id;

    public Contact() {
        this(new Faker());
    }

    public Contact(Faker faker) {
        this(faker.name().firstName(), faker.name().lastName());
    }

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = this.firstName + "." + this.lastName + "@test.com";
    }

    public Contact(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put("firstName", this.getFirstName());
        params.put("lastName", this.getLastName());
        params.put("email", this.getEmail());
        return params;
    }
}
