package films.get;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetFilmTest extends BaseTest {

    @DisplayName("Read all films")
    @Test
    public void getAllFilms() {
        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + FILMS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getInt("count")).isEqualTo(7);

    }

    @DisplayName("Read one film by ID")
    @Test
    public void getOneFilmById() {
        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + FILMS + "/7/")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("title")).isEqualTo("The Force Awakens");
        assertThat(json.getString("director")).isEqualTo("J. J. Abrams");
        assertThat(json.getString("release_date")).isEqualTo("2015-12-11");
        assertThat(json.getList("planets").get(0)).isEqualTo(BASE_URL + PLANETS + "/61/");

    }

}
