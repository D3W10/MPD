package pt.leirt.mpd;

import pt.leirt.mpd.products.*;

import java.util.List;

public class StoreDB {
    public final static Resolution hd = new Resolution(1024, 768);
    public final static Resolution fullHd = new Resolution(1920, 1080);
    public final static Resolution uhd = new Resolution(3840, 2160);
    
    // TVs
    public final static TV sonyX95 = new TV("X95", "Sony", 3000, uhd, 95.0);
    public final static TV samsungU7 = new TV("u7", "Samsung", 2000, uhd, 60);
    public final static TV sonyBravia = new TV("Sony Bravia", "Sony", 1300, fullHd, 40.0);
    public final static TV lgOLED = new TV("LG OLED", "LG", 1800, hd, 70.0);
    public final static TV lgNanoCell =  new TV("LG NanoCell", "LG", 1100, uhd, 85.0);

    // Speakers
    public final static Speaker jblX300 = new Speaker("x300", "JBL", 100, 40);
    public final static Speaker samsungS250 = new Speaker("s250", "Samsung", 200, 60);
    public final static Speaker jblCharge1 = new Speaker("Charge 1", "JBL", 50, 20);
    public final static Speaker jblCharge2 = new Speaker("Charge 2", "JBL", 60, 30);
    public final static Speaker jblCharge3 = new Speaker("Charge 3", "JBL", 160, 70);
    
    // SmartPhones
    public final static SmartPhone iPhone15 = new SmartPhone("Apple", "i15", 1300, uhd, 6, 4000);
    public final static SmartPhone samsungS23 = new SmartPhone("Samsung", "s23", 1200, uhd, 6.5, 4500);
    public final static SmartPhone onePlus10P= new SmartPhone("OnePlus 10 Pro", "OnePlus", 900, uhd, 6.7, 5000);
    public final static SmartPhone googlePixel7 = new SmartPhone("Google Pixel 7", "Google", 800, uhd, 6.2, 4200);
    public final static SmartPhone xiaomiMi12 = new SmartPhone("Xiaomi Mi 12", "Xiaomi", 900, uhd, 6.8, 4800);

    //Notebooks
    public final static Notebook dellXPS15 = new Notebook("Dell XPS 15", "Dell", 1499.99, uhd, 15.6, 70, 3);
    public final static Notebook hpSpectreX360 = new Notebook("HP Spectre x360", "HP", 1299.99, uhd, 13.3, 65, 2);
    public final static Notebook lenovoThinkPadX1 = new Notebook("Lenovo ThinkPad X1 Carbon", "Lenovo", 1599.99, uhd, 14, 80, 4);
    public final static Notebook asusZenBookPro = new Notebook("Asus ZenBook Pro Duo", "Asus", 1999.99, uhd, 15.6, 90, 3);
    public final static Notebook RazerBlade15 = new Notebook("Razer Blade 15", "Razer", 1799.99, uhd, 15.6, 80, 3);

    // Packs
    public final static Pack packSamsung = new Pack("SamsungBrand", List.of(samsungU7,samsungS250,samsungS23 ));
    public final static Pack packTvs = new Pack("TVS", List.of(samsungU7, sonyX95 ));
    
    public final static Pack packBig = new Pack("bigPack", List.of(iPhone15, packSamsung, jblX300,  packTvs));
}
