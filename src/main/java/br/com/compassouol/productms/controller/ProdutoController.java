package br.com.compassouol.productms.controller;

import br.com.compassouol.productms.dto.ProdutoDTO;
import br.com.compassouol.productms.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> produtos() {
        return ResponseEntity.ok(produtoService.getProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> produtosById(@PathVariable("id") String id) {
        return ResponseEntity.ok(produtoService.getProduto(id));
    }

    @GetMapping("/search")
    public String produtosByFilter(@RequestParam(required = false) String q,@RequestParam(required = false) String min_price,
                                   @RequestParam(required = false) String max_price) {
        return q;
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProdutos(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.saveProduto(produtoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> editarProdutos(@PathVariable("id") String id, @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.editProduto(id,produtoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarProdutos(@PathVariable("id") String id){
        produtoService.deleteProduto(id);
        return ResponseEntity.ok().build();
    }
}
