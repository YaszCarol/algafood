package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontrado;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoService grupoService;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontrado(usuarioId));
    }

    @Transactional
    public void salvar(Usuario usuario) {

        // tira a entidade do contexto de persisencia do JPA (salvar atualizaçoes automaticamente)
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        // "Já existe um usuário com esse e-mail e ele não sou eu."
        if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)){

            throw new NegocioException(String.format("Já existe um usuario cadastrado com o e-mail %s" ,usuario.getEmail()));
        }

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletar(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new UsuarioNaoEncontrado(usuarioId);
        }

        usuarioRepository.deleteById(usuarioId);
    }

    @Transactional
    public void atualizarSenha(Long usuarioId, String novaSenha, String senhaAtual) {

        Usuario usuario = this.buscar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = this.buscar(usuarioId);

        Grupo grupo = grupoService.buscar(grupoId);

        usuario.desassociarGrupo(grupo);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = this.buscar(usuarioId);

        Grupo grupo = grupoService.buscar(grupoId);

        usuario.associarGrupo(grupo);
    }


}
