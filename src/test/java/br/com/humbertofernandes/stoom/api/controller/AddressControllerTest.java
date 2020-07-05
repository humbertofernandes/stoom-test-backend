package br.com.humbertofernandes.stoom.api.controller;

import br.com.humbertofernandes.stoom.api.model.Address;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasEntry;

/**
 * @author Humberto Tadeu de Paiva Gomes Fernandes
 */
@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AddressControllerTest {

    private static final long ID = 1L;
    private static final String STREET_NAME = "R. Zuneide Aparecida Marin";
    private static final String NUMBER = "43";
    private static final String NEIGHBOURHOOD = "Jardim Santa Genebra II (Barao Geraldo)";
    private static final String CITY = "Campinas";
    private static final String STATE = "SP";
    private static final String COUNTRY = "Brasil";
    private static final String ZIPCODE = "13084780";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void should_getAll_address() {
        given()
                .get("/address")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("id", containsInAnyOrder(1, 2, 3, 4, 5),
                        "streetName", containsInAnyOrder("R. Zuneide Aparecida Marin", "Rua Vinte e Cinco de Dezembro", "Avenida Brigadeiro Faria Lima", "Avenida Benjamin Constant", "Rua Alto de Santa Helena"));
    }

    @Test
    public void should_get_address_by_id() {
        given()
                .pathParam("id", 1L)
                .get("/address/{id}")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1),
                        "streetName", equalTo("R. Zuneide Aparecida Marin"),
                        "number", equalTo("43"),
                        "neighbourhood", equalTo("Jardim Santa Genebra II (Barao Geraldo)"),
                        "city", equalTo("Campinas"),
                        "state", equalTo("SP"),
                        "country", equalTo("Brasil"),
                        "zipcode", equalTo("13084780"));
    }

    @Test
    public void should_return_error_not_found_when_get_address_by_id() {
        given()
                .pathParam("id", 10L)
                .get("/address/{id}")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("statusCode", equalTo(404),
                        "reasonPhrase", equalTo("Not Found"),
                        "errors", hasItems(hasEntry("code", "address-8")));
    }

    @Test
    public void should_save_a_new_address() {
        Address address = new Address();
        address.setStreetName(STREET_NAME);
        address.setNumber(NUMBER);
        address.setNeighbourhood(NEIGHBOURHOOD);
        address.setCity(CITY);
        address.setState(STATE);
        address.setCountry(COUNTRY);
        address.setZipcode(ZIPCODE);

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(address)
                .when()
                .post("/address")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:" + port + "/address/6"))
                .body("id", equalTo(6),
                        "streetName", equalTo(STREET_NAME),
                        "number", equalTo(NUMBER),
                        "neighbourhood", equalTo(NEIGHBOURHOOD),
                        "city", equalTo(CITY),
                        "state", equalTo(STATE),
                        "country", equalTo(COUNTRY),
                        "zipcode", equalTo(ZIPCODE));
    }

    @Test
    public void should_update_a_address() {
        Address address = new Address();
        address.setStreetName(STREET_NAME);
        address.setNumber(NUMBER);
        address.setNeighbourhood(NEIGHBOURHOOD);
        address.setCity(CITY);
        address.setState(STATE);
        address.setCountry(COUNTRY);
        address.setZipcode(ZIPCODE);

        given()
                .pathParam("id", 3L)
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(address)
                .when()
                .put("/address/{id}")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(3),
                        "streetName", equalTo(STREET_NAME),
                        "number", equalTo(NUMBER),
                        "neighbourhood", equalTo(NEIGHBOURHOOD),
                        "city", equalTo(CITY),
                        "state", equalTo(STATE),
                        "country", equalTo(COUNTRY),
                        "zipcode", equalTo(ZIPCODE));
    }


    @Test
    public void should_not_update_aluno_not_exist() {
        Address address = new Address();
        address.setStreetName(STREET_NAME);
        address.setNumber(NUMBER);
        address.setNeighbourhood(NEIGHBOURHOOD);
        address.setCity(CITY);
        address.setState(STATE);
        address.setCountry(COUNTRY);
        address.setZipcode(ZIPCODE);

        given()
                .pathParam("id", 6L)
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(address)
                .when()
                .put("/address/{id}")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("statusCode", equalTo(404),
                        "reasonPhrase", equalTo("Not Found"),
                        "errors", hasItems(hasEntry("code", "address-8")));
    }

    @Test
    public void should_delete_a_aluno() {
        given()
                .pathParam("id", 1L)
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .when()
                .delete("/address/{id}")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void should_not_delete_a_aluno_not_exist() {
        given()
                .pathParam("id", 6L)
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .when()
                .delete("/address/{id}")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("statusCode", equalTo(404),
                        "reasonPhrase", equalTo("Not Found"),
                        "errors", hasItems(hasEntry("code", "address-8")));
    }
}
