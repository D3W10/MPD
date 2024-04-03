package pt.leirt.mpd.products;

import org.json.JSONObject;

import java.util.Objects;

public abstract class BaseElectronics implements Electronics {

    private final String name;
    private final String brand;
    private final double price;

    protected BaseElectronics(String name, String brand, double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    // Product implementation

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public String getBrand() {
        return brand;
    }


    public String toString() {
        String s = String.format("%s %s: price %.2f euros", brand, name, price);
        return s;
    }

    @Override
    public String toJson() {
        JSONObject jObj = new JSONObject();

        jObj.put("name", name);
        jObj.put("brand", brand);
        jObj.put("price", price);

        return jObj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseElectronics that = (BaseElectronics) o;
        return Double.compare(price, that.price) == 0 && Objects.equals(name, that.name) && Objects.equals(brand, that.brand);
    }
}
