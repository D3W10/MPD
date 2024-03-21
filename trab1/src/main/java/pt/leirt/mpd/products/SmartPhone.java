package pt.leirt.mpd.products;

import org.json.JSONObject;

public class SmartPhone extends BaseElectronics implements Screen{

    private final Resolution res;
    private final double screenSize;
    private final int batteryCapacity;

    public SmartPhone(String name, String brand, double price, Resolution res, double screenSize, int batteryCapacity){
        super(name, brand, price);
        this.res = res;
        this.screenSize = screenSize;
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public Category getCategory() {
        return Category.COMMUNICATIONS;
    }

    @Override
    public String toJson() {
        JSONObject jObj = new JSONObject();

        jObj.put("res", res.getJson());
        jObj.put("screenSize", screenSize);
        jObj.put("batteryCapacity", batteryCapacity);
        jObj.put("type", "SmartPhone");

        return jObj.toString();
    }

    @Override
    public Resolution getResolution() {
        return res;
    }

    @Override
    public double getScreenSize() {
        return screenSize;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }
}
