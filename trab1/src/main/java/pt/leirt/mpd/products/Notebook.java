package pt.leirt.mpd.products;

public class Notebook extends BaseElectronics implements Screen{

    private final Resolution resolution;
    private final double screenSize;
    private final int batteryCapacity;
    private final int usbPorts;

    protected Notebook(String name, String brand, double price, Resolution resolution, double screenSize, int batteryCapacity, int usbPorts) {
        super(name, brand, price);
        this.resolution = resolution;
        this.screenSize = screenSize;
        this.batteryCapacity = batteryCapacity;
        this.usbPorts = usbPorts;
    }

    @Override
    public Category getCategory() {
        return Category.INFORMATICS;
    }

    @Override
    public Resolution getResolution() {
        return resolution;
    }

    @Override
    public double getScreenSize() {
        return screenSize;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public int getUsbPorts() {
        return usbPorts;
    }
}
