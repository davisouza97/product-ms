package br.com.compassouol.productms.repository;

import br.com.compassouol.productms.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findProdutos(String q, String minPrice, String maxPrice);
}
