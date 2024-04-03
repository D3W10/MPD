package pt.leirt.mpd.products;

import org.json.JSONObject;

public class TV extends BaseElectronics implements Screen{
    private final Resolution res;    // in pixels
    private final double screenSize; // in inches

    public TV(String name, String brand, double price, Resolution res, double screenSize) {
        super(name, brand, price);
        this.res = res;
        this.screenSize = screenSize;
    }

    @Override
    public Category getCategory() {
        return Category.VIDEO;
    }

    @Override
    public String toJson() {
        JSONObject jObj = new JSONObject(super.toJson());

        jObj.put("resolution", res.getJson());
        jObj.put("screenSize", screenSize);
        jObj.put("type", "TV");

        return jObj.toString();
    }

    @Override
    public String toString() {
        return "Resolution: " + getResolution() + "\nScreen Size:" + getScreenSize();
    }

    @Override
    public Resolution getResolution() {
        return res;
    }

    @Override
    public double getScreenSize() {
        return screenSize;
    }
}
