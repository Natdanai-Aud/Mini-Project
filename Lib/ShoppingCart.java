package Lib;

import java.util.ArrayList;

/**
 * ADT 
 */
public class ShoppingCart {
    private final PricingService pricingService ;
    private final ProductCatalog productCatalog ;
    private ArrayList<Product> products = new ArrayList<>();

    // RI : ArrayList ภายในต้องไม่เป็น null และห้ามมี product ที่ซ้ำกันใน CartItem ที่แตกต่างกัน
    private void checkRep() {
        if(products == null){
           throw new RuntimeException("RI violated: products"); 
        }
        // Check for dulicate products
        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size(); j++) {
                if(products.get(i).equals(products.get(j))){
                    throw new RuntimeException("RI violated: products"); 
                }
            }
        }
    }
    /**
     * รับค่า pricingService และ productCatalog
     * @param pricingService เป็นจัดการโปรโมชัน
     * @param productCatalog เป็นแคตตาล็อกสินค้า
     */
    public ShoppingCart(PricingService pricingService ,ProductCatalog productCatalog){
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
        if (productCatalog.findById(productId) == null) {
            addItem(productId, quantity);
        }else{
            quantity +=1;
        }
        checkRep();
    }

    /**
     * ลบรหัสสินค้าที่ไม่เอาแล้ว
     * @param productId รหัสสินค้าที่จะลบออก
     */
    public void removeItem(String productId) {
        removeItem(productId);
        checkRep();
    }

    /**
     * ให้วนลูปในตะกร้าและใช้ PricingService ในการคำนวณราคาสุทธิ
     * @return
     */
    public double getTotalPrice() {
        for (PricingService pricingService : pricingService) {
            return pricingService;
        }
    }
    public int getItemCount() {
        return getItemCount()+1;
    }
    
    
}
