package Entities;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public void increase() {
        quantity++;
    }

    public void decrease() {
        if (quantity > 1) quantity--;
    }

    public double getTotalPrice() {
        return quantity * product.getPrice();
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return product.getName() + " x" + quantity + "  " + getTotalPrice() + " ₺";
    }
}
