package pt.leirt.mpd.products;

public class Promo implements Electronics{

    private final double discount;
    private final Electronics product;

    public Promo(double discount, Electronics product){
        this.discount = discount;
        this.product = product;
    }

    @Override
    public String getName() {
        return product.getName();
    }

    @Override
    public double getPrice() {
        return product.getPrice() * discount;
    }

    @Override
    public Category getCategory() {
        return product.getCategory();
    }

    @Override
    public String getBrand() {
        return product.getBrand();
    }

    public double getDiscount() {
        return discount;
    }
}
