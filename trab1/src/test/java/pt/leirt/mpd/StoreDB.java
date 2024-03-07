package pt.leirt.mpd;

import pt.leirt.mpd.products.*;

import java.util.List;

public class StoreDB {
    public final static Resolution hd = new Resolution(1024, 768);
    public final static Resolution fullHd = new Resolution(1920, 1080);
    public final static Resolution uhd = new Resolution(3840, 2160);
    
    // TVs
    public final static TV sonyX95 = new TV("X95", "Sony", 3000, uhd, 65.0);
    public final static TV samsungU7 = new TV("u7", "Samsung", 2000, uhd, 60);
    
    // Speakers
    public final static Speaker jblX300 = new Speaker("x300", "JBL", 100, 40);
    public final static Speaker samsungS250 = new Speaker("s250", "Samsung", 200, 60);
    
    // SmartPhones
    //public final static SmartPhone iPhone15 = new SmartPhone("Apple", "i15", 1300, uhd, 6, 4000);
    //public final static SmartPhone samsungS23 = new SmartPhone("Samsung", "s23", 1200, uhd, 6.5, 4500);
    
    // Packs
    //public final static Pack packSamsung = new Pack("SamsungBrand", List.of(samsungU7,samsungS250,samsungS23 ));
    //public final static Pack packTvs = new Pack("TVS", List.of(samsungU7, sonyX95 ));
    
    //public final static Pack packBig = new Pack("bigPack", List.of(iPhone15, packSamsung, jblX300,  packTvs));
}
