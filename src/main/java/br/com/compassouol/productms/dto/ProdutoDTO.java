package br.com.compassouol.productms.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoDTO {
    private String id;
    @NotBlank(message = "Campo 'name' obrigatório")
    private String name;
    @NotBlank(message = "Campo 'description' obrigatório")
    private String description;
    @DecimalMin(value = "0.01", message = "Campo 'price' deve ser positivo")
    @NotNull(message = "Campo 'price' obrigatório")
    private BigDecimal price;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
