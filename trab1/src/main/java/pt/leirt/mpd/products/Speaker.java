package pt.leirt.mpd.products;

import org.json.JSONObject;

public class Speaker extends BaseElectronics {
    private final double power; // in Watts

    public Speaker(String name, String brand, double price, double power) {
        super(name, brand, price);
        this.power = power;
    }

    @Override
    public Category getCategory() {
        return Category.AUDIO;
    }

    @Override
    public String toJson() {
        JSONObject jObj = new JSONObject();

        jObj.put("power", power);
        jObj.put("type", "Speaker");

        return jObj.toString();
    }

    @Override
    public String toString() {
        return "Power: " + getPower();
    }

    public double getPower() {
        return power;
    }
}
