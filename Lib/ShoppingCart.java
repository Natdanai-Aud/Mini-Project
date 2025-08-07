package Lib;

import java.util.ArrayList;

/**
 * ADT 
 */
public class ShoppingCart {
    private final PricingService pricingService ;
    private final ProductCatalog productCatalog ;
    private final ArrayList<CartItem> items ;

    // RI : ArrayList ภายในต้องไม่เป็น null และห้ามมี product ที่ซ้ำกันใน CartItem ที่แตกต่างกัน
    private void checkRep() {
        if(items == null){
           throw new RuntimeException("RI violated: products"); 
        }
        ArrayList<Product> seen = new ArrayList<>();
        // Check for dulicate products
        for (CartItem item : items) {
            if (item == null) {
                throw new RuntimeException("Item is null");
            }
            Product product = item.getProduct();
            if (seen.contains(product)) {
                throw new RuntimeException("Duplicate product in cart");
            }
            seen.add(product);
        }
    }
    /**
     * รับค่า pricingService และ productCatalog
     * @param pricingService เป็นจัดการโปรโมชัน
     * @param productCatalog เป็นแคตตาล็อกสินค้า
     */
    public ShoppingCart(PricingService pricingService ,ProductCatalog productCatalog){
        this.items = new ArrayList<>();
        this.pricingService = pricingService ;
        this.productCatalog = productCatalog ;
        checkRep();
    }

    /**
     * ใช้ productCatalog เพื่อค้นหา product
     * ถ้าเจอให้ตรวจสอบว่ามีอยู่ในตะกร้าแล้วหรือไม่ ถ้ามีให้เพิ่มจำนวน
     * ถ้าไม่มีให้สร้าง CartItem ใหม่
     * @param productId รหัสสินค้า
     * @param quantity จำนวนสินค้า
     */
    public void addItem(String productId, int quantity) {
        Product product = productCatalog.findById(productId) ; 
        if (product == null) {
            return;
        }
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                item.increaseQuantity(quantity);
                checkRep();
                return ;
            }
        }
        items.add(new CartItem(product, quantity));
        checkRep();
    }

    /**
     * ลบรหัสสินค้าที่ไม่เอาแล้ว
     * @param productId รหัสสินค้าที่จะลบออก
     */
    public void removeItem(String productId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct().getProductId().equals(productId)) {
                items.remove(i);
                checkRep();
                break ;
            }
        }
    }

    /**
     * ให้วนลูปในตะกร้าและใช้ PricingService ในการคำนวณราคาสุทธิ
     * @return
     */
    public double getTotalPrice() {
        double tatol = 0 ;
        for (CartItem item : items) {
            tatol += pricingService.calculateItemPrice(item);
        }
        return tatol ;
    }
    public int getItemCount() {
        return items.size();
    }
    public void clearCart(){
        items.clear();
        checkRep();
    }
    
}
