package Lib;

/**
     * ADT ที่เปลี่ยนแปลงได้ (Mutable) สำหรับเก็บข้อมูลสินค้า 1 รายการ
     */
public class CartItem {
    private final Product product;
    private int quantity;

    // Rep Inveriant (RI):
    // - product is not null.
    // - quantity > 0.
    //
    // Abstraction Function (AF):
    // - AF(product, quantity) = An item in a shopping cart
    //   for the given 'product' with the specified 'quantity'.

    /**
     * ตรวจสอบว่า Rep Inveriant เป็นจริงหรือไม่
     */
    private void checkRep(){
        if(product == null ){
            throw new RuntimeException("RI violated: product");
        }
        if(quantity <= 0){
            throw new RuntimeException("RI violated: quantity");
        }  
    }

    /**
     * สร้างรายการสินค้าในตะกร้า
     * @param product อ็อบเจกต์สินค้า
     * @param quantity จำนวนสินค้า ต้องมากกว่า 0
     */
    public CartItem(Product product,int quantity){
        this.product = product;
        this.quantity = quantity;
        checkRep();
    }

    /**
     * @return อ็อบเจกต์ product
     */
    public Product getProduct(){return product;}
    
    public int getQuantity(){ return quantity; }

    /**
     * เพิ่มจำนวนสินค้าในรายการนี้
     * @param amount จำนวนที่ต้องการเพิ่ม (ต้องเป็นค่าบวก)
     */
    public void increaseQuantity(int amount){
        if(amount > 0){
            this.quantity += amount ;
        }
        checkRep(); //ตรวจสอบหลังการเปลี่ยนแปลงสถานะ
    }
}
