package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.PropertyBindingException;
import tools.jackson.databind.json.JsonMapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteService.listar();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable Long restauranteId) {
        return restauranteService.buscar(restauranteId);
    }

    @PostMapping
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {

        try {
            return restauranteService.salvar(restaurante);
        } catch (CozinhaNaoEncontradaException exception) {
            throw new NegocioException(exception.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@RequestBody Restaurante restaurante, @PathVariable Long restauranteId) {
        Restaurante restauranteEncontrado = restauranteService.buscar(restauranteId);

        BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id",
                "formasPagamento", "endereco", "dataCadastro", "produtos");
        ;
        try {
            return restauranteService.salvar(restauranteEncontrado);
        } catch (CozinhaNaoEncontradaException exception) {
            throw new NegocioException(exception.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public Restaurante atualizacaoParcial(@PathVariable Long restauranteId,
                                          @RequestBody Map<String, Object> campos,
                                          HttpServletRequest request) {

        Restaurante restauranteAtual = restauranteService.buscar(restauranteId);

        merge(campos, restauranteAtual, request);

        return atualizar(restauranteAtual, restauranteId);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
                       HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            // usada para converter objetos (ou Map) em outros tipos de objeto Java.
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper = JsonMapper.builder()
                    .enable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                    .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build();

            // Converte o Map "dadosOrigem" (geralmente vindo de um PATCH) para um objeto
            // Restaurante.
            // Isso facilita pegar os valores já convertidos para os tipos corretos dos
            // campos da entidade.
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem,
                    Restaurante.class);

            // Percorre cada propriedade enviada na requisição (nome do campo e valor).
            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {

                // Procura via reflexão (reflection) o campo correspondente dentro da classe
                // Restaurante.
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);

                // Permite acessar o campo mesmo que ele seja private.
                field.setAccessible(true);

                // Obtém o valor já convertido do objeto restauranteOrigem.
                // Isso garante que o tipo esteja correto (ex: Long, BigDecimal, etc.).
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                // Define o novo valor no objeto restauranteDestino,
                // que é a entidade que será atualizada no banco.
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (PropertyBindingException e) {

            throw new HttpMessageNotReadableException(
                    e.getMessage(),
                    e,
                    serverHttpRequest
            );
        }
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long restauranteId) {
        restauranteService.remover(restauranteId);
    }
}
