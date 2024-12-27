package org.example.shoppingcart.service;

import org.example.shoppingcart.model.Product;
import org.example.shoppingcart.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    IProductRepository iProductRepository;


    @Override
    public Iterable findAll() {
        return iProductRepository.findAll();
    }


    //khong dung
    @Override
    public void save(Product product) {
       iProductRepository.save(product);
    }

    @Override
    public Optional findById(Long id) {
        return iProductRepository.findById(id);
    }


    //khong dung
    @Override
    public void remove(Long id) {
        iProductRepository.deleteById(id);
    }
}
