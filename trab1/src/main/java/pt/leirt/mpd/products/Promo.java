package pt.leirt.mpd.products;

import org.json.JSONObject;

public class Promo implements Electronics {

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

    @Override
    public String toJson() {
        JSONObject jObj = new JSONObject();

        jObj.put("discount", discount);
        jObj.put("product", new JSONObject(product.toJson()));
        jObj.put("type", "Promo");

        return jObj.toString();
    }

    public int getDiscount() {
        return discount;
    }

    public Electronics getWrappee(){
        return product;
    }
}