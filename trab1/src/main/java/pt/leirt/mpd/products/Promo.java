package pt.leirt.mpd.products;

public class Promo implements Electronics{

    private final int discount;
    private final Electronics product;

    public Promo(Electronics product, int discount){
        this.discount = discount;
        this.product = product;
    }

    @Override
    public String getName() {
        return "Promo " + product.getName();
    }

    @Override
    public double getPrice() {
        return product.getPrice() - product.getPrice() * discount * 0.01;
    }

    @Override
    public Category getCategory() {
        return product.getCategory();
    }

    @Override
    public String getBrand() {
        return product.getBrand();
    }

    public int getDiscount() {
        return discount;
    }

    public Electronics getWrappee(){
        return product;
    }
}