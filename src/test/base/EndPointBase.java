package test.base;


import io.restassured.response.ValidatableResponse;
import model.Contact;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;

public class EndPointBase extends TestBase {

    public final int SUCCESS_CODE = 200;
    public final String REF_URI = "http://host:port/api/v1/contacts/";

    public ValidatableResponse healthCheck() {
        return given().when()
                .get("/healthcheck")
                .then().statusCode(200);
    }

    public ValidatableResponse getContacts() {
        return given().when()
                .get("/api/v1/contacts")
                .then();
    }

    public ValidatableResponse getContact(Contact contact) {
        return given().when()
                .get("/api/v1/contacts?firstName="+ contact.getFirstName() +"&email="+ contact.getEmail() +"")
                .then();
    }

    public ValidatableResponse getContactById(int id) {
        return given().when().
                get("/api/v1/contacts/" + id)
                .then();
    }

    public ValidatableResponse createContact(Contact contact) {
        return given().contentType("application/json").when().body(contact.getRequestParams())
                .post("/api/v1/contacts").then();
    }

    public Contact createContact() {
        Contact contact = new Contact();
        contact.setId(createContact(contact).extract().path("data.id[0]"));
        return contact;
    }

    public ValidatableResponse updateContact(Contact contact, int id) {
        return given().contentType("application/json").when().body(contact.getRequestParams())
                .put("/api/v1/contacts/" + id).then();
    }

    public ValidatableResponse patchContact(Contact contact, int id) {
        return given().contentType("application/json").when().body(contact.getRequestParams())
                .patch("/api/v1/contacts/" + id).then();
    }

    public ValidatableResponse deleteContact( int id) {
        return given().when().delete("/api/v1/contacts/" + id).then();
    }

    public void validateResponse(ValidatableResponse response, Contact contact) {
        response.body("data.id[0]", equalTo(contact.getId()))
                .body("data.info.email[0]", equalTo(contact.getEmail()))
                .body("data.info.firstName[0]", equalTo(contact.getFirstName()))
                .body("data.info.lastName[0]", equalTo(contact.getLastName()))
                .body("data.refs.patch[0]", equalTo(REF_URI + contact.getId()))
                .body("data.refs.get[0]", equalTo(REF_URI + contact.getId()))
                .body("data.refs.delete[0]", equalTo(REF_URI + contact.getId()))
                .body("data.refs.put[0]", equalTo(REF_URI + contact.getId()))
                .body("message", isEmptyString())
                .body("status", equalTo(SUCCESS_CODE));
    }
}
