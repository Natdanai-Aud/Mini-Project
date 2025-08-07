package Lib.Discount;

import Lib.CartItem;

/**
 * กลยุทธ์ส่วนลด Bogo ซื้อ 1 แถม 1
 */
public class BogoDiscountStrategy implements DiscountStrategy{

    @Override
    public double calculatePrice(CartItem item) {
         return item.getProduct().getPrice() * Math.round(item.getQuantity())/2.0;
    }
    
}
