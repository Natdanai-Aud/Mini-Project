import Lib.CartItem;
import Lib.Product;
import Lib.ProductCatalog;

public class Test1 {
    public static void main(String[] args) {
        Product a = new Product("P001", "Apple", 10.0);
        Product b = new Product("P002", "Soda", 5.0);
        Product c = new Product("P003", "Apple", 20.0);

        ProductCatalog catalog = new ProductCatalog();
        catalog.addProduct(a);
        catalog.addProduct(b);
        catalog.addProduct(c);
        catalog.addProduct(a);

        CartItem item1 = new CartItem(catalog.findById("P001"), 2);
        CartItem item2 = new CartItem(b, 3);
    }
}
