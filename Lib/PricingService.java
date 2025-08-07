package Lib;

import java.util.ArrayList;

import Lib.Discount.DefaultPricingStrategy;
import Lib.Discount.DiscountStrategy;

/**
 * คลาสทำหน้าที่เป็นผู้จัดการโปรโมชัน
 */
public class PricingService {
    private record StrategyRule(String sku, DiscountStrategy stetegy) {}
    private final ArrayList<StrategyRule> strategies = new ArrayList<>();
    private final DiscountStrategy defaulStrategy = new DefaultPricingStrategy() ;

    /**
     * ลงทะเบียนกลยุทธ์ส่วนลดสำหรับสินค้า sku ที่กำหนด
     * หากมีโปรโมชันสำหรับ sku นี้อยู่แล้ว จะถูกแทนที่ด้วยอันใหม่
     * @param sku รหัสสินค้าที่ต้องการผูกกับโปรโมชัน
     * @param stetegy กลยุทธ์ส่วนลดที่ใช้
     */

    public void addStrategy(String sku, DiscountStrategy strategy) {
        StrategyRule ruleToRemove = null ;
        for(StrategyRule rule : strategies) {
            if (rule.sku().equals(sku)) {
                ruleToRemove = rule ;
                break ;
            }
        }
        if (ruleToRemove != null) {
            strategies.remove(ruleToRemove);
        }
        strategies.add(new StrategyRule(sku, strategy));
    }
    /**
     * คำนวณราคาสุทธิของสินค้า 1 รายการโดยการใช้กลยุทธ์ที่เหมาะสม
     * @param item รายการสินค้าที่ต้องการคำนวณราคา
     * @return ราคาสุทธิหลังหักส่วนลด
     */

     public double calculateItemPrice(CartItem item){
        String sku = item.getProduct().getProductId() ;
        for(StrategyRule rule : strategies){
            if (rule.sku().equals(sku)) {
                return rule.stetegy().calculatePrice(item);
            }
        }
        return defaulStrategy.calculatePrice(item) ;
     }
    

}
