package org.example.shoppingcart.model;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm {
    private Long id;
    private String name;
    private String description;
    private MultipartFile image;
    private double price;


    public ProductForm() {
    }

    public ProductForm(Long id, String name, String description, MultipartFile image, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}