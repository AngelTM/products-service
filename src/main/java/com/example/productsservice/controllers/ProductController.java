package com.example.productsservice.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.productsservice.controllers.dto.ProductDTO;
import com.example.productsservice.entities.Product;
import com.example.productsservice.service.IProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/find/{id}")
    private ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDTO produtDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();

            return ResponseEntity.ok(produtDTO);
        }

        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/findAll")
    private ResponseEntity<?> findAll() {
        List<ProductDTO> productList = productService.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build())
                .toList();
        return ResponseEntity.ok(productList);
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if (productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null) {
            return ResponseEntity.badRequest().build();
        }

        Product product = Product.builder()
                                 .id(productDTO.getId())
                                 .name(productDTO.getName())
                                 .price(productDTO.getPrice())
                                 .maker(productDTO.getMaker())
                                 .build();

        productService.save(product);
        return ResponseEntity.created(new URI("/api/product/save")).build();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){

        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();

            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setMaker(productDTO.getMaker());
            productService.save(product);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        if(id != null){
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body("El parametro id se encuentra vacio");
    }
}
