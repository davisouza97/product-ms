package br.com.compassouol.productms.service;

import br.com.compassouol.productms.dto.ProdutoDTO;
import br.com.compassouol.productms.exception.ProdutoNaoEncontradoException;
import br.com.compassouol.productms.model.Produto;
import br.com.compassouol.productms.repository.ProdutoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoServiceTest {

    @Spy
    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    public void getProdutos_retorno(){
        Mockito.doReturn(listProdutos()).when(produtoRepository).findAll();
        List<ProdutoDTO> produtos = produtoService.getProdutos();

        Mockito.verify(produtoRepository, Mockito.times(1)).findAll();
        Assert.assertNotNull(produtos);
        Assert.assertEquals(3, produtos.size());
    }

    @Test
    public void getProdutos_retornoVazio(){
        Mockito.doReturn(List.of()).when(produtoRepository).findAll();
        List<ProdutoDTO> produtos = produtoService.getProdutos();

        Mockito.verify(produtoRepository, Mockito.times(1)).findAll();
        Assert.assertNotNull(produtos);
        Assert.assertEquals(0, produtos.size());
    }

    @Test
    public void getProdutos_Filtro_retornoIndividual(){
        Produto produto = getProduto(1);
        Mockito.doReturn(List.of(produto)).when(produtoRepository).findProdutos("dummmy name 1",null,null);
        List<ProdutoDTO> produtos = produtoService.getProdutos("dummmy name 1",null,null);

        Mockito.verify(produtoRepository, Mockito.times(1)).findProdutos("dummmy name 1",null,null);
        Assert.assertNotNull(produtos);
        Assert.assertEquals(1, produtos.size());
        Assert.assertEquals(produto.getName(), produtos.get(0).getName());
        Assert.assertEquals(produto.getDescription(),produtos.get(0).getDescription());
        Assert.assertEquals(produto.getPrice(), produtos.get(0).getPrice());
    }

    @Test
    public void getProdutos_Filtro_retornoVazio(){
        Mockito.doReturn(List.of()).when(produtoRepository).findProdutos("dummmy name 1",null,null);
        List<ProdutoDTO> produtos = produtoService.getProdutos("dummmy name 1",null,null);

        Mockito.verify(produtoRepository, Mockito.times(1)).findProdutos("dummmy name 1",null,null);
        Assert.assertNotNull(produtos);
        Assert.assertEquals(0, produtos.size());
    }

    @Test(expected = ProdutoNaoEncontradoException.class)
    public void getProduto_retornoVazio(){
        Mockito.doReturn(Optional.empty()).when(produtoRepository).findById(1);
        produtoService.getProduto("1");
    }

    @Test
    public void getProduto_retornoNormal(){
        Produto produtoMock = getProduto(1);
        Mockito.doReturn(Optional.of(produtoMock)).when(produtoRepository).findById(1);
        ProdutoDTO produto = produtoService.getProduto("1");
        Assert.assertNotNull(produto);
        Assert.assertEquals(produtoMock.getName(), produto.getName());
        Assert.assertEquals(produtoMock.getDescription(), produto.getDescription());
        Assert.assertEquals(produtoMock.getPrice(), produto.getPrice());
    }

    @Test
    public void saveProduto(){
        Produto produtoMock = getProduto(1);
        ProdutoDTO produtoDTO = getProdutoDTO(null);
        Mockito.doReturn(produtoMock).when(produtoRepository).save(Mockito.any());
        ProdutoDTO retorno = produtoService.saveProduto(produtoDTO);
        Assert.assertEquals(produtoDTO,retorno);
        Assert.assertEquals(produtoMock.getId().toString(),retorno.getId());
    }

    @Test(expected = ProdutoNaoEncontradoException.class)
    public void editProduto_ProdutoNaoEncontrado(){
        Mockito.doReturn(Optional.empty()).when(produtoRepository).findById(1);
        produtoService.editProduto("1", getProdutoDTO("1"));
    }

    @Test
    public void editProduto_ProdutoEncontrado(){
        Produto produto = getProduto(1);
        Produto produtoEditado = getProduto(1);
        produtoEditado.setName("outro nome");
        Mockito.doReturn(Optional.of(produto)).when(produtoRepository).findById(1);
        Mockito.doReturn(produtoEditado).when(produtoRepository).save(Mockito.any());
        ProdutoDTO produtoDTO = produtoService.editProduto("1", getProdutoDTO("1"));
        Assert.assertEquals(produtoEditado.getName(), produtoDTO.getName());
        Assert.assertEquals(produtoEditado.getDescription(), produtoDTO.getDescription());
        Assert.assertEquals(produtoEditado.getPrice(), produtoDTO.getPrice());
        Assert.assertEquals(produtoEditado.getId().toString(), produtoDTO.getId());
    }

    @Test(expected = ProdutoNaoEncontradoException.class)
    public void deleteProduto_ProdutoNaoEncontrado(){
        Mockito.doReturn(Optional.empty()).when(produtoRepository).findById(1);
        produtoService.deleteProduto("1");
    }

    @Test
    public void deleteProduto_ProdutoEncontrado(){
        Mockito.doReturn(Optional.of(getProduto(1))).when(produtoRepository).findById(1);
        produtoService.deleteProduto("1");
        Mockito.verify(produtoRepository, Mockito.times(1)).findById(1);
        Mockito.verify(produtoRepository, Mockito.times(1)).delete(Mockito.any());
    }

    private List<Produto> listProdutos() {
        Produto p1 = getProduto(1);
        Produto p2 = getProduto(2);
        Produto p3 = getProduto(3);
        return List.of(p1,p2,p3);
    }

    private Produto getProduto(Integer number) {
        Produto produto = new Produto();
        produto.setId(number);
        produto.setName("dummmy name "+ number);
        produto.setDescription("dummmy description "+ number);
        produto.setPrice(BigDecimal.valueOf(number));
        return produto;
    }

    private ProdutoDTO getProdutoDTO(String id) {
        ProdutoDTO produto = new ProdutoDTO();
        produto.setId(id);
        produto.setName("dummmy name");
        produto.setDescription("dummmy description");
        produto.setPrice(BigDecimal.TEN);
        return produto;
    }
}
