package Lib.Discount;

import Lib.CartItem;

/**
 * กลยุทธ์ส่วนลด Bogo ซื้อ 1 แถม 1
 */
public class BogoDiscountStrategy implements DiscountStrategy{

    @Override
    public double calculatePrice(CartItem item) {
        int quantity = item.getQuantity(); //ค้นหาจำนวนรายการสินค้า
        double price = item.getProduct().getPrice(); //ค้นหาราคาสินค้า
        int quantityToPay = (quantity/2) + (quantity % 2); //(จำนวนสินค้า / 2) + (เศษจากจำนวนสินค้าเมื่อหาร 2)
        return price * quantityToPay ; //ส่งราคา * จำนวนสินค้าที่คำนวณได้
    }
    
}
