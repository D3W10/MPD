package pt.leirt.mpd.products;

public class SmartPhone extends BaseElectronics{

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

    public Resolution getRes() {
        return res;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }
}
