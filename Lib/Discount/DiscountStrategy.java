package Lib.Discount;

import Lib.CartItem;

/**
 *  Interface สำหรับกลยุทธ์การคำนวณราคาสินค้า
 */
public interface DiscountStrategy {
    /**
     * คำนวณราคาสุทธิ์สำหรับสินค้า 1 รายการ
     * @param item รายการสินค้าในตะกร้า
     * @return ราคาสุทธิ์หลังใช้โปรโมชัน
     */
    public double calculatePrice(CartItem item);
}
