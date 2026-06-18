package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.infrastructure.respository.S3FotoStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosArquivo) {

        String nomeArquivoAntigo = null;

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(
                fotoProduto.getRestauranteId(),
                fotoProduto.getProduto().getId()
        );

        //fotoExistente.ifPresent(produtoRepository::delete);

        if (fotoExistente.isPresent()) {
            nomeArquivoAntigo = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        fotoProduto.setNomeArquivo(fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo()));

        FotoProduto foto = produtoRepository.save(fotoProduto);

        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder().
                nomeArquivo(fotoProduto.getNomeArquivo()).
                contentType(fotoProduto.getContentType())
                .inputStream(dadosArquivo).build();

        fotoStorageService.substituir(nomeArquivoAntigo, novaFoto);
        return foto;
    }

    public FotoProduto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(() ->
                new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    @Transactional
    public void deletar(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscar(restauranteId, produtoId);

        fotoStorageService.remover(fotoProduto.getNomeArquivo());

        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

    }
}
