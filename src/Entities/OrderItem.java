package Entities;

public class OrderItem {

    private Product product;
    private int quantity;

    public OrderItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public void increase() { quantity++; }
    public void decrease() { quantity--; }

    public int getQuantity() { return quantity; }
    public Product getProduct() { return product; }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " x" + quantity +
                " = " + getTotalPrice() + " ₺";
    }
}
