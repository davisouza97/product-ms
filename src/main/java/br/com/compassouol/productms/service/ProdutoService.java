package br.com.compassouol.productms.service;

import br.com.compassouol.productms.dto.ProdutoDTO;
import br.com.compassouol.productms.exception.ProdutoNaoEncontradoException;
import br.com.compassouol.productms.model.Produto;
import br.com.compassouol.productms.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<ProdutoDTO> getProdutos() {
        return produtoRepository.findAll().stream().map(
                produto -> modelMapper.map(produto, ProdutoDTO.class)
        ).collect(Collectors.toList());
    }

    public List<ProdutoDTO> getProdutos(String q,String minPrice,String maxPrice) {
        return produtoRepository.findProdutos(q, minPrice, maxPrice).stream().map(
                produto -> modelMapper.map(produto, ProdutoDTO.class)
        ).collect(Collectors.toList());
    }

    public ProdutoDTO getProduto(String id) {
        return modelMapper.map(getProdutoEntity(Integer.parseInt(id)), ProdutoDTO.class);
    }

    public ProdutoDTO saveProduto(ProdutoDTO produtoDTO) {
        Produto produto = modelMapper.map(produtoDTO, Produto.class);
        produto = produtoRepository.save(produto);
        produtoDTO.setId(produto.getId().toString());
        return produtoDTO;
    }

    public ProdutoDTO editProduto(String id, ProdutoDTO produtoDTO) {
        Produto produto = getProdutoEntity(Integer.parseInt(id));
        produto.setName(produtoDTO.getName());
        produto.setDescription(produtoDTO.getDescription());
        produto.setPrice(produtoDTO.getPrice());
        produto = produtoRepository.save(produto);
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public void deleteProduto(String id) {
        produtoRepository.delete(getProdutoEntity(Integer.parseInt(id)));
    }

    private Produto getProdutoEntity(Integer id) {
        return produtoRepository.findById(id).orElseThrow(ProdutoNaoEncontradoException::new);
    }
}
