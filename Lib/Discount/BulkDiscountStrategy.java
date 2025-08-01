package Lib.Discount;

import Lib.CartItem;

/**
 * กลยุทธ์ส่วนลด Bulk (ซื้อเยอะลดราคา)
 */
public class BulkDiscountStrategy implements DiscountStrategy{
    private final int minimumQuantity;
    private final double discountPercentage ;
    
    public BulkDiscountStrategy(int minimumQuantity, double discountPercentage){
        this.minimumQuantity = minimumQuantity ;
        this.discountPercentage = discountPercentage ;
    }
    @Override
    public double calculatePrice(CartItem item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculatePrice'");
    }
    
}
