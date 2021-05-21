package br.com.compassouol.productms.repository;

import br.com.compassouol.productms.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p " +
            "WHERE (:nameOrDescription IS NULL OR " +
            "(p.description LIKE :nameOrDescription OR p.name LIKE :nameOrDescription)) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Produto> findProdutos(@Param("nameOrDescription") String nameOrDescription,
                               @Param("minPrice") BigDecimal minPrice,
                               @Param("maxPrice") BigDecimal maxPrice);
}
