package org.example.shoppingcart.controller;

import org.example.shoppingcart.model.Cart;
import org.example.shoppingcart.model.Product;
import org.example.shoppingcart.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@SessionAttributes("cart")
public class ProductController {
    @Autowired
    private IProductService productService;

    // nếu session hiện tại không có thuộc tính cart, Spring sẽ gọi phương thức setupCart() để tạo và lưu nó vào session.
    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @GetMapping("/shop")
    public ModelAndView showShop() {
        ModelAndView modelAndView = new ModelAndView("/shop");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id,
                            @ModelAttribute Cart cart,
                            @RequestParam("action") String action) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return "/error_404";
        }
        if (action.equals("show")) {
            cart.addProduct(productOptional.get());
            return "redirect:/shopping-cart";
        } if (action.equals("decrease")) {
            cart.decreaseProduct(productOptional.get());
            return "redirect:/shopping-cart";
        }

        cart.addProduct(productOptional.get());
        return "redirect:/shop";
    }








}