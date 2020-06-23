package edu.dawndemeo.stocks.datamodels;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class PersonDaoTest {

    public static final String firstName = "Dawn";
    public  static final String lastName = "DeMeo";

    /**
     * Testing helper method for generating PersonDao test data
     *
     * @return a PersonDao object that uses static constants for data.
     */
    public static PersonDao createPerson() {
        PersonDao person = new PersonDao();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    @Test
    public void testPersonGettersAndSetters() {
        PersonDao person = createPerson();
        int id = 10;
        person.setId(id);
        assertEquals("first name matches", firstName, person.getFirstName());
        assertEquals("last name matches", lastName, person.getLastName());
        assertEquals("id matches", id, person.getId());

    }

}