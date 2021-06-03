package planets.get;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetPlanetsTest extends BaseTest {

    @DisplayName("Read all Planets")
    @Test
    public void readAllPlanets() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PLANETS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getInt("count")).isEqualTo(61);
        assertThat(json.getList("results.name")).hasSize(10);
    }

    @DisplayName("Read one planet")
    @Test
    public void readOnePlanet() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + PLANETS + "/50")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Champala");
        assertThat(json.getString("rotation_period")).isEqualTo("27");
        assertThat(json.getString("climate")).isEqualTo("temperate");
        assertThat(json.getList("residents").get(0)).isEqualTo(BASE_URL + PEOPLE + "/59/");
    }

}

