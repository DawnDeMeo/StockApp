package edu.dawndemeo.stocks.datamodels;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author dawndemeo
 */
@Entity
@Table(name = "person")
public class PersonDao {

    private int id;
    private String firstName;
    private String lastName;

    /**
     * Primary Key - Unique ID for a particular row in the person table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the person table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the person's first name
     */
    @Basic
    @Column(name = "first_name", nullable = false, length = 256)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Specify the person's first name
     * @param firstName a String value
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return the person's last name
     */
    @Basic
    @Column(name = "last_name", nullable = false, length = 256)
    public String getLastName() {
        return lastName;
    }

    /**
     * Specify the person's last name
     * @param lastName a String value
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDao that = (PersonDao) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "{\"PersonDao\":{"
                + "\"id\":\"" + id + "\""
                + ", \"firstName\":\"" + firstName + "\""
                + ", \"lastName\":\"" + lastName + "\""
                + "}}";
    }
}
