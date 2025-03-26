package com.devgen.shop.spring_boot.service;

import com.devgen.shop.spring_boot.model.Category;
import com.devgen.shop.spring_boot.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private Map<Long, Product> products;

    private Long id;
    public ProductService() {
        this.products = new HashMap<>();
        this.id = 1L;

        initalizeProducts();
    }

    private void initalizeProducts() {

        // you can use this but
//        Product product1 = new Product(1,"mobile", Category.ELECRONICS, 100.0);
//        Product product2 = new Product(2,"tv", Category.ELECRONICS, 500.0);
//        Product product3 = new Product(3,"Shirt", Category.CLOTH, 200.0);
//
//        products.put(product1.getId(), product1);
//        products.put(product2.getId(), product2);
//        products.put(product3.getId(), product3);
//        System.out.println(product1.getId());

        // this is shortcut for above
        add(new Product(1,"mobile", Category.ELECRONICS, 100.0));
        add(new Product(2,"tv", Category.ELECRONICS, 500.0));
        add(new Product(3,"Shirt", Category.CLOTH, 200.0));
    }

    public void add(Product product) {
        product.setId(id);
        products.put(id, product);
        id++;
    }

    public Product getById(Long id) {
        return products.get(id);
    }

    public List<Product> getAll() {
        System.out.println("product service get all method is call");
        return new ArrayList<>(products.values());
    }

    public boolean deleteProduct(Long id) {
        return products.remove(id) != null;
    }



    public boolean updateProduct(Product newProduct) {
        Product existingProduct = products.get(newProduct.getId());

        if(existingProduct != null) {
            existingProduct.setName(newProduct.getName());
            existingProduct.setPrice(newProduct.getPrice());
            return true;
        }
        else {
            return false;
        }
    }

    // this api is for search products by category
    public List<Product> getByCategory(Category category) {
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getCategory().equals(category)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }
}
