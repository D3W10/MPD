package pt.leirt.mpd.products;

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
