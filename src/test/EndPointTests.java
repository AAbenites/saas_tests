package test;

import model.Contact;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.base.EndPointBase;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isEmptyString;


public class EndPointTests extends EndPointBase {

    @BeforeClass
    public void cleanUp() {
        ArrayList<Integer> ids = getContacts().statusCode(SUCCESS_CODE).extract().path("data.id");
        ids.forEach(i -> deleteContact(i));
    }

    @Test
    public void getHealthcheck() {
        healthCheck().assertThat().body(equalTo("live"));
    }

    @Test
    public void creatingContact() {
        Contact contact = new Contact();
        createContact(contact).statusCode(201)
                .body("data.id[0]", greaterThan(0))
                .body("data.info.email[0]", equalTo(contact.getEmail()))
                .body("data.info.firstName[0]", equalTo(contact.getFirstName()))
                .body("data.info.lastName[0]", equalTo(contact.getLastName()))
                .body("message", isEmptyString())
                .body("status", equalTo(SUCCESS_CODE));
    }

    @Test
    public void findingContacts() {
        Contact contact = createContact();
        validateResponse(getContact(contact).statusCode(SUCCESS_CODE), contact);
    }

    @Test
    public void gettingContactById() {
        Contact contact = createContact();
        validateResponse(getContactById(contact.getId()).statusCode(SUCCESS_CODE), contact);
    }

    @Test
    public void updatingContact() {
        Contact contactToUpdate = createContact();
        Contact contact = new Contact();
        contact.setId(contactToUpdate.getId());
        validateResponse(updateContact(contact, contact.getId()).statusCode(SUCCESS_CODE), contact);
    }

    @Test
    public void patchingContact() {
        Contact contact = createContact();
        Contact contactNext = new Contact();
        contact.setEmail(contactNext.getEmail());
        patchContact(contact, contact.getId()).statusCode(SUCCESS_CODE)
                .body("data.info.email[0]", equalTo(contactNext.getEmail()))
                .body("data.info.firstName[0]", equalTo(contact.getFirstName()))
                .body("data.info.lastName[0]", equalTo(contact.getLastName()));
    }

    @Test
    public void deletingContact() {
        Contact contact = createContact();
        deleteContact(contact.getId()).statusCode(SUCCESS_CODE);
        getContactById(contact.getId()).statusCode(404);
    }
}
