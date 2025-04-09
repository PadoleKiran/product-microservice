package com.devgen.shop.spring_boot.controller;

import com.devgen.shop.spring_boot.model.Category;
import com.devgen.shop.spring_boot.model.Product;
import com.devgen.shop.spring_boot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {


    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // search feature (all, category, name)
    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String category, @RequestParam(required = false) String name, @RequestParam(name="lower-price", required = false) Double lowerpice, @RequestParam(name = "higher-price", required = false) Double higherpice){
//        System.out.println(category + " category");

        System.out.println("lower pice : " + lowerpice + "\nhiger price : " + higherpice);
        // searching by category
        if (category != null) {
            Category cat = Category.valueOf(category);
            return productService.searchByCategory(cat);
        }

        // searching by name
        if (name != null) {
            return productService.searchByName(name);
        }

        if (lowerpice != null && higherpice != null) {
            return productService.searchByProductPriceRange(lowerpice, higherpice);
        }
        System.out.println("This is product controller ");
        return productService.getAll();
    }

    // Search by id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getById(id);
    }

    // add product
    @PostMapping
    public String addProduct(@RequestBody Product product){
        productService.add(product);
        return "Product added successfully";
    }

    // update product
    @PutMapping("/{id}")
    public String updateProduct(@RequestBody Product product, @PathVariable Long id){
        product.setId(id);
        boolean status = productService.updateProduct(product);
        if (status) {
            return "Product updated successfully";
        }
        else {
            return "Product Not Found or updated failed";
        }
    }

    // delete product
    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable Long id) {
        boolean status = productService.deleteProduct(id);
        if (status)
        {
            return "Product Deleted successfully";
        }
        else
        {
            return "product not found or deletion failed";
        }
    }

//    @GetMapping("/search/{category}")
//    public List<Product> getProductByCategory(@PathVariable Category category) {
//        return productService.getByCategory(category);
//    }
}
