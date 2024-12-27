package org.example.shoppingcart.model;

import org.example.shoppingcart.model.Product;

import javax.persistence.*;


import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product,Integer> products = new HashMap<>();

    public Cart() {
    }

    public Cart(Map<Product,Integer> products) {
        this.products = products;
    }

    public Map<Product,Integer> getProducts() {
        return products;
    }

//    Lặp qua products, so sánh id của sản phẩm cần kiểm tra với từng sản phẩm trong giỏ hàng.
//Trả về:
//true: Sản phẩm đã tồn tại.
//false: Sản phẩm chưa có trong giỏ hàng.
    private boolean checkItemInCart(Product product){
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if(entry.getKey().getId().equals(product.getId())){
                return true;
            }
        }
        return false;
    }

//    Tìm và trả về một mục (entry) cụ thể trong giỏ hàng dựa trên sản phẩm (Product).
    private Map.Entry<Product, Integer> selectItemInCart(Product product){
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if(entry.getKey().getId().equals(product.getId())){
                return entry;
            }
        }
        return null;
    }

//    Nếu sản phẩm chưa có trong giỏ hàng, thêm sản phẩm với số lượng là 1.
//    Nếu sản phẩm đã có, tăng số lượng lên 1.
    public void addProduct(Product product){
        if (!checkItemInCart(product)){
            products.put(product,1);
        } else {
            Map.Entry<Product, Integer> itemEntry = selectItemInCart(product);
            Integer newQuantity = itemEntry.getValue() + 1;
            products.replace(itemEntry.getKey(),newQuantity);
        }
    }
//Tính tổng số lượng tất cả sản phẩm trong giỏ hàng (bao gồm cả sản phẩm giống nhau).
//Ví dụ: Nếu giỏ hàng có:
//Sản phẩm A: 2 cái.
//Sản phẩm B: 3 cái.
//Kết quả: 2 + 3 = 5.
    public Integer countProductQuantity(){
        Integer productQuantity = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            productQuantity += entry.getValue();
        }
        return productQuantity;
    }


//    Chức năng: Tính số lượng loại sản phẩm khác nhau trong giỏ hàng.
//Ví dụ: Nếu giỏ hàng có 2 loại sản phẩm, kết quả sẽ là 2.
    public Integer countItemQuantity(){
        return products.size();
    }


//    Chức năng: Tính tổng tiền cần thanh toán cho giỏ hàng.
//Cách hoạt động:
//Lặp qua từng sản phẩm trong products.
//Lấy giá (price) của từng sản phẩm, nhân với số lượng, sau đó cộng dồn.
//Ví dụ:
//Sản phẩm A: Giá 10.000, số lượng 2 → 10.000 x 2 = 20.000.
//Sản phẩm B: Giá 15.000, số lượng 3 → 15.000 x 3 = 45.000.
//Kết quả: 20.000 + 45.000 = 65.000.
    public Float countTotalPayment(){
        float payment = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            payment += entry.getKey().getPrice() * (float) entry.getValue();
        }
        return payment;
    }


    // Nếu số lượng hiện tại > 1, giảm số lượng đi 1.
// Nếu số lượng là 1, xóa sản phẩm khỏi giỏ hàng.
    public void decreaseProduct(Product product) {
        if (checkItemInCart(product)) {
            Map.Entry<Product, Integer> itemEntry = selectItemInCart(product);
            if (itemEntry.getValue() > 1) {
                Integer newQuantity = itemEntry.getValue() - 1;
                products.replace(itemEntry.getKey(), newQuantity);
            } else {
                products.remove(itemEntry.getKey());
            }
        }
    }

}