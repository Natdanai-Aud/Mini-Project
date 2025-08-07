import Lib.*;
import Lib.Discount.*;

public class App {
    private static int passedCount = 0;
    private static int failedCount = 0;

    /**
     * เมธอดเสริมสำหรับตรวจสอบเงื่อนไขและพิมพ์ผลลัพธ์
     * @param testName ชื่อของกรณีทดสอบ
     * @param condition เงื่อนไขที่ต้องเป็นจริงเพื่อให้เทสต์ผ่าน
     */
    private static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("PASSED: " + testName);
            passedCount++;
        } else {
            System.out.println("FAILED: " + testName);
            failedCount++;
        }
    }

    /**
     * จุดเริ่มต้นการทำงานของโปรแกรมทดสอบ
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("--- Starting Comprehensive E-commerce System Tests ---");
        
        // --- Setup ---
        Product apple = new Product("P001", "Apple", 10.0);
        Product soda = new Product("P002", "Soda", 5.0);
        Product bread = new Product("P003", "Bread", 20.0);

        ProductCatalog catalog = new ProductCatalog();
        catalog.addProduct(apple);
        catalog.addProduct(soda);
        catalog.addProduct(bread);

        PricingService pricingService = new PricingService();
        pricingService.addStrategy("P001", new BogoDiscountStrategy());
        pricingService.addStrategy("P002", new BulkDiscountStrategy(6, 0.10));

        // --- Test Cases ---
        
        System.out.println("\n--- Testing ShoppingCart ---");
        ShoppingCart cart = new ShoppingCart(pricingService, catalog);
        check("New cart should be empty", cart.getItemCount() == 0 && cart.getTotalPrice() == 0.0);

        cart.addItem("P001", 3); // Apple x3
        check("Add new item correctly", cart.getItemCount() == 1 && cart.getTotalPrice() == 20.0); // BOGO: pay for 2

        cart.addItem("P001", 2); // Apple x3+2=5
        check("Add existing item increases quantity", cart.getItemCount() == 1 && cart.getTotalPrice() == 30.0); // BOGO: pay for 3

        cart.addItem("P002", 5); // Soda x5
        check("Add another new item", cart.getItemCount() == 2 && cart.getTotalPrice() == 30.0 + 25.0); // 55.0

        cart.addItem("P002", 1); // Soda x5+1=6
        check("Add item to meet bulk discount threshold", cart.getItemCount() == 2 && cart.getTotalPrice() == 30.0 + (30.0 * 0.9)); // 30 + 27 = 57.0

        cart.addItem("P999", 1); // Non-existent product
        check("Adding non-existent product does not change cart", cart.getItemCount() == 2 && cart.getTotalPrice() == 57.0);

        cart.addItem("P003", 0); // Invalid quantity
        check("Adding item with zero quantity does not change cart", cart.getItemCount() == 2 && cart.getTotalPrice() == 57.0);

        cart.removeItem("P002"); // Remove soda
        check("Remove item correctly updates count and price", cart.getItemCount() == 1 && cart.getTotalPrice() == 30.0);

        cart.removeItem("P999"); // Remove non-existent item
        check("Removing non-existent item does not change cart", cart.getItemCount() == 1 && cart.getTotalPrice() == 30.0);
        
        cart.clearCart();
        check("Clear cart works correctly", cart.getItemCount() == 0 && cart.getTotalPrice() == 0.0);

        System.out.println("\n--- Testing PricingService and Strategies ---");
        CartItem singleApple = new CartItem(apple, 1);
        CartItem twoApples = new CartItem(apple, 2);
        CartItem fiveSodas = new CartItem(soda, 5);
        CartItem tenSodas = new CartItem(soda, 10);
        CartItem normalBread = new CartItem(bread, 3);

        check("BOGO Strategy (1 item)", pricingService.calculateItemPrice(singleApple) == 10.0);
        check("BOGO Strategy (2 items)", pricingService.calculateItemPrice(twoApples) == 10.0);
        check("Bulk Strategy (below threshold)", pricingService.calculateItemPrice(fiveSodas) == 25.0);
        check("Bulk Strategy (above threshold)", pricingService.calculateItemPrice(tenSodas) == 45.0);
        check("Default Strategy", pricingService.calculateItemPrice(normalBread) == 60.0);

        pricingService.addStrategy("P001", new BulkDiscountStrategy(3, 0.50)); // เปลี่ยนโปรโมชัน Apple เป็นซื้อ 3 ลด 50%
        CartItem threeApples = new CartItem(apple, 3);
        check("Promotion update works", pricingService.calculateItemPrice(threeApples) == 15.0); // 30 * 0.5 = 15.0

        System.out.println("\n--- Testing ProductCatalog ---");
        check("Find existing product", catalog.findById("P001").equals(apple));
        check("Find non-existent product returns null", catalog.findById("P999") == null);

        // --- สรุปผล ---
        System.out.println("\n--------------------");
        System.out.println("--- Test Summary ---");
        System.out.println("Passed: " + passedCount + ", Failed: " + failedCount);
        if (failedCount == 0) {
            System.out.println("Excellent! All tests passed!");
        } else {
            System.out.println("Some tests failed.");
        }
    }
}
