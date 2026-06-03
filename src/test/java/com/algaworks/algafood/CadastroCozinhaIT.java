package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import tools.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    List<Cozinha> cozinhas = new ArrayList<>();

    private int indiceCozinhaAmericana;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();

        prepararDados();
    }

    @Test
    public void deveRetornar200_QuandoConsultarCozinhas() {

        RestAssured.given()
                .accept(ContentType.JSON)
                .when().get()
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente(){

        RestAssured.given()
                .pathParam("cozinhaId", indiceCozinhaAmericana)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo("Americana"));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente(){

        int indiceAleatorio = cozinhas.size() + 1;

        RestAssured.given()
                .pathParam("cozinhaId", indiceAleatorio)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveConterTodasCozinhasCriadas_QuandoConsultarCozinhas() {

        RestAssured.given()
                .accept(ContentType.JSON)
                .when().get()
                .then().body("", Matchers.hasSize(cozinhas.size()));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {

        Cozinha cozinha = cozinhas.get(0); // ou qualquer índice
        String json = objectMapper.writeValueAsString(cozinha);

        RestAssured.given()
                .body(json)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private void prepararDados() {

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Tailandesa");
        cozinhaService.salvar(cozinha);

        cozinhas.add(cozinha);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Americana");
        cozinhaService.salvar(cozinha1);

        indiceCozinhaAmericana = 2;
        cozinhas.add(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Tailandesa");
        cozinhaService.salvar(cozinha2);

        cozinhas.add(cozinha2);

        Cozinha cozinha3 = new Cozinha();
        cozinha3.setNome("Tailandesa");
        cozinhaService.salvar(cozinha3);

        cozinhas.add(cozinha3);
    }


}
