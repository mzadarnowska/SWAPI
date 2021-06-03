package people.get;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPeopleTest extends BaseTest {

    @DisplayName("Read all People resource")
    @Test
    public void readAllPeople() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PEOPLE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getInt("count")).isEqualTo(87);
    }

    @DisplayName("Read second character")
    @Test
    public void readOnePerson() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PEOPLE + "/2")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("C-3PO");
        assertThat(json.getString("height")).isEqualTo("167");
        assertThat(json.getString("mass")).isEqualTo("75");
        assertThat(json.getList("films").get(0)).isEqualTo(BASE_URL + FILMS + "/1/");
    }

    @DisplayName("Test to read Invalid person")
    @Test
    public void readOneInvalidPerson() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PEOPLE + "/1000")
                .then()
                .statusCode(404)
                .extract()
                .response();
    }
}

