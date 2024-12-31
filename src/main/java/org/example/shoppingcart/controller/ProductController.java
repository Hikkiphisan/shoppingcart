package org.example.shoppingcart.controller;

import org.example.shoppingcart.model.Cart;
import org.example.shoppingcart.model.Product;
import org.example.shoppingcart.model.ProductForm;
import org.example.shoppingcart.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
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





//thu nghiem
@GetMapping("/product/create")
public ModelAndView showCreateForm() {
    ModelAndView modelAndView = new ModelAndView("/uploadImage_Product");
    modelAndView.addObject("productForm", new ProductForm());
    return modelAndView;
}

    @Value("${message.properties}")
    private String fileUpload;
    // Phương thức thêm sản phẩm có hình ảnh
    @PostMapping("/product/save")
    public ModelAndView saveProduct(@ModelAttribute ProductForm productForm) {
        // Lấy file hình ảnh từ form
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
//        String fileUpload = "D:\\images\\"; // Đặt đường dẫn thư mục lưu trữ ảnh

        try {
            // Lưu file vào thư mục
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Tạo đối tượng sản phẩm và lưu vào cơ sở dữ liệu
        Product product = new Product(productForm.getId(), productForm.getName(),
                productForm.getPrice(), productForm.getDescription(), fileName);
        productService.save(product);

        // Trả về ModelAndView với thông báo thành công
        ModelAndView modelAndView = new ModelAndView("/uploadImage_Product");
        modelAndView.addObject("productForm", productForm);
        modelAndView.addObject("message", "Created new product successfully!");
        return modelAndView;
    }





}