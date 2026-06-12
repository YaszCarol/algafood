package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso";

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscar(cozinhaId);
        Cidade cidade = cidadeService.buscar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscar(Long id) {
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradaException(id));
    }

    @Transactional
    public void remover(Long restauranteId) {

        try {
            if (!restauranteRepository.existsById(restauranteId)) {
                throw new RestauranteNaoEncontradaException(restauranteId);
            }

            restauranteRepository.deleteById(restauranteId);

            // necessario pois o metodo está usando @Transactional que realiza de fato a operação (commit)
            // no fim do metodo e entao exceçoes sao lançadas pós catch.
            // estamos forçando a realizaçao da operação para que caso haja exceçoes de
            // DataIntegrityViolationException seja possível a sua captura
            restauranteRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_RESTAURANTE_EM_USO, restauranteId));

        }
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = this.buscar(restauranteId);

        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = this.buscar(restauranteId);

        restaurante.inativar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = this.buscar(restauranteId);

        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        restaurante.desassociarFormaPagamento(formaPagamento);

    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = this.buscar(restauranteId);

        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        restaurante.associarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = this.buscar(restauranteId);

        restaurante.fechar();
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = this.buscar(restauranteId);

        restaurante.abrir();
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = this.buscar(restauranteId);

        Usuario usuario = usuarioService.buscar(responsavelId);

        restaurante.desassociarResponsavel(usuario);

    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = this.buscar(restauranteId);

        Usuario usuario = usuarioService.buscar(responsavelId);

        restaurante.associarResponsavel(usuario);
    }

    @Transactional
    public void inativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::inativar);
    }

    @Transactional
    public void ativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }

    public boolean containsFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        return restauranteRepository.existsByIdAndFormasPagamentoId(restauranteId, formaPagamentoId);
    }
}
