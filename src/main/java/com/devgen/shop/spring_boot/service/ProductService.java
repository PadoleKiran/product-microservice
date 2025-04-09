package com.devgen.shop.spring_boot.service;

import com.devgen.shop.spring_boot.model.Category;
import com.devgen.shop.spring_boot.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
    public List<Product> searchByCategory(Category category) {
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getCategory().equals(category)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    public List<Product> searchByName(String name) {
//        List<Product> matchingProducts = new ArrayList<>();
//        for (Product product : products.values()) {
//            if (product.getName().equals(name)) {
//                matchingProducts.add(product);
//            }
//        }
//        return matchingProducts;

        // logic in one line with using stream
        return products.values().stream()
                .filter(product -> isNameMatching(name, product))
                .collect(toList());
    }

    private static boolean isNameMatching(String name, Product product) {
        return product.getName().toLowerCase().contains(name.toLowerCase());
    }

    public List<Product> searchByProductPriceRange(Double lowerpice, Double higherpice) {
        List<Product> matchingProducts = products.values().stream()
                .filter(product -> isPriceRangeValid(lowerpice, higherpice, product))
//                .sorted((Product p1, Product p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                .collect(Collectors.toList());
//        List<Product> allProducts = new ArrayList<>(matchingProducts);
//        Collections.sort(matchingProducts, (Product p1, Product p2) -> Double.compare(p1.getPrice(), p2.getPrice()));

//        return products.values().stream().filter(product -> isPriceRangeValid(lowerpice, higherpice, product)).toList();
        return matchingProducts;
    }

    private boolean isPriceRangeValid(Double lowerpice, Double higherpice, Product product) {
        return product.getPrice() >= lowerpice && product.getPrice() <= higherpice;
    }
}
