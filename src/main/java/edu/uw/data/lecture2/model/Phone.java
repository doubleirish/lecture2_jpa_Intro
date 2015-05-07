package edu.uw.data.lecture2.model;

import java.io.*;

/**
 * Created by credmond on 03/03/15.
 */

public class Phone implements Comparable,Serializable{
  private static final long serialVersionUID = 2L;


  private Integer id;


  private User user;


  private String label;

  private String phoneNumber;

  public Phone() {
  }

  public Phone(String label, String phoneNumber) {
    this.label = label;
    this.phoneNumber = phoneNumber;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getLabel() {
    return label;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  @Override
  public int compareTo(Object obj) {
    int compare =0;
    Phone that = (Phone) obj;
    return this.phoneNumber.compareTo(that.phoneNumber);
  }

  @Override
  public String toString() {
    return "" +label + "='" + phoneNumber + "' "  ;
  }


  public static class Builder {
    private Phone phone;

    public Builder() {
      phone = new Phone();
    }

    public Builder builder() {
      return new Builder();
    }

    public Builder label(String label) {
      phone.label =label;
      return this;
    }

    public Builder number(String number) {
      phone.phoneNumber =number;
      return this;
    }

    public Phone build() {
      //validate ??;
      return phone;
    }
  }
}
