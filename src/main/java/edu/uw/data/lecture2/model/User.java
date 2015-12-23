package edu.uw.data.lecture2.model;

import javax.persistence.*;
import java.io.*;
import java.util.*;

/**
 * User can have one Address but many Phone Numbers
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "ACTIVE_SINCE")
    private Date activeSince;


    @Transient
    private Address address;


    @Transient
    private Set<Phone> phoneNumbers = new TreeSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(Date activeSince) {
        this.activeSince = activeSince;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Phone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<Phone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(Phone phone) {
        this.phoneNumbers.add(phone);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", activeSince=" + activeSince +
                ", address=" + address +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }

    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            user.setId(id);
            return this;
        }

        public Builder userName(String userName) {
            user.setUserName(userName);
            return this;
        }

        public Builder firstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Builder activeSince(Date activeSince) {
            user.setActiveSince(activeSince);
            return this;
        }

        public Builder address(Address address) {
            user.setAddress(address);
            return this;
        }

        public Builder phoneNumbers(Set<Phone> phoneNumbers) {
            user.setPhoneNumbers(phoneNumbers);
            return this;
        }

        public Builder phone(Phone phone) {
            user.getPhoneNumbers().add(phone);
            return this;
        }

        public Builder phone(String label, String phoneNum) {
            user.getPhoneNumbers().add(new Phone(label, phoneNum));
            return this;
        }

        public User build() {
            //validate ??;
            return user;
        }
    }
}
