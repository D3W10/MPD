package pt.leirt.mpd;

import pt.leirt.mpd.products.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;


public class Store {

    private Collection<Electronics> catalog = new TreeSet<>((o1, o2) -> {
        int res = o1.getBrand().compareTo(o2.getBrand());
        if (res == 0)
            return o1.getName().compareTo(o2.getName());
        else
            return res;
    });

    public Store addCatalog(Electronics product) {
        catalog.add(product);
        return this;
    }

    // query methods

    public Iterable<Electronics> fromSamsung() {
        List<Electronics> samsungProds = new ArrayList<>();

        for(var p : catalog) {
            if (p.getBrand().equalsIgnoreCase("Samsung"))
                samsungProds.add(p);
        }

        return samsungProds;
    }

    public Iterable<TV> getAboveSizeTvs(double minInches) {
        List<TV> bigTvs = new ArrayList<>();

        for (var p : catalog){
            if(p.getClass() == TV.class && ((TV) p).getScreenSize() > minInches)
                bigTvs.add((TV) p);
        }

        return bigTvs;
    }

    public Iterable<Speaker> getSpeakersInPowerInterval(int minPower, int maxPower) {
        List<Speaker> speakers = new ArrayList<>();

        for (var p : catalog){
            if (p instanceof Speaker s && s.getPower() > minPower && s.getPower() < maxPower)
                speakers.add(s);
        }

        return speakers;
    }

    public Electronics getMostExpensiveCommunicationDevice() {
        Electronics result = null;

        for (var p : catalog){
            if (result == null || p.getCategory() == Electronics.Category.COMMUNICATIONS && p.getPrice() > result.getPrice())
                result = p;
        }

        return result;
    }

    public Electronics getMostExpensiveIndividualProductInPacks() {
        // TO IMPLEMENT
        return null;
    }

    static class DisplaySummary {
        // TO Define , must include product name and brand and display characteristics
        private final Screen displayItem;

        public DisplaySummary(Screen displayItem){
            this.displayItem = displayItem;
        }

        public String getSummary(){
            return displayItem.toString() + " " + displayItem.getScreenSize() + " " + displayItem.getResolution();
        }
    }

    public Iterable<DisplaySummary> getDisplaysSummary() {
        List<DisplaySummary> displaySummaries = new ArrayList<>();

        for (var p : catalog){
            if (p instanceof Screen s) {
                DisplaySummary ds = new DisplaySummary(s);
                displaySummaries.add(ds);
            }
        }

        return displaySummaries;
    }

    public SmartPhone getCheapestSmartPhoneWithBatteryGreaterThen(int minBatCapacity) {
        SmartPhone smartPhone = null;

        for (var p : catalog){
            if (p instanceof SmartPhone sp && (smartPhone == null || sp.getBatteryCapacity() > minBatCapacity && smartPhone.getPrice() < sp.getPrice()))
                smartPhone = sp;
        }

        return smartPhone;
    }

    public Iterable<Promo> getPromoTVsWith20PercentDiscount() {
        List<Promo> promoProduct = new ArrayList<>();

        for (var p : catalog){
            if (p instanceof Promo pTV && (pTV.getWrapper() instanceof TV && pTV.getDiscount() == 20))
                promoProduct.add(pTV);
        }

        return promoProduct;
    }
}
