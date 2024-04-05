package pt.leirt.mpd;

import pt.leirt.mpd.products.*;

import java.util.*;

public class StoreImproved {

    private final Collection<Electronics> catalog = new TreeSet<>((o1, o2) -> {
        int res = o1.getBrand().compareTo(o2.getBrand());
        if (res == 0)
            return o1.getName().compareTo(o2.getName());
        else
            return res;
    });

    public StoreImproved addCatalog(Electronics product) {
        catalog.add(product);
        return this;
    }

    // query methods

    public Iterable<Electronics> fromSamsung() {
        return QueryUtils.filter(catalog, e -> e.getBrand().equalsIgnoreCase("Samsung"));
    }

    public Iterable<TV> getAboveSizeTvs(double minInches) {
        return QueryUtils.map(QueryUtils.filter(catalog, e -> e instanceof TV t && t.getScreenSize() > minInches), p -> (TV) p);
    }

    public Iterable<Speaker> getSpeakersInPowerInterval(int minPower, int maxPower) {
        return QueryUtils.map(QueryUtils.filter(catalog, e -> e instanceof Speaker s && s.getPower() > minPower && s.getPower() < maxPower), p -> (Speaker) p);
    }

    public Electronics getMostExpensiveCommunicationDevice() {
        Iterable<Electronics> iterable = QueryUtils.filter(catalog, p -> p.getCategory() == Electronics.Category.COMMUNICATIONS);

        return QueryUtils.reduce(iterable, null, (e1, e2) -> e1 == null || e2.getPrice() > e1.getPrice() ? e2 : e1);
    }

    public Electronics getMostExpensiveIndividualProductInPacks() {
        Iterable<Pack> packs = QueryUtils.map(QueryUtils.filter(catalog, p -> p instanceof Pack), p -> (Pack) p);
        Iterable<Electronics> electronics = QueryUtils.flatMap(packs, e -> e);

        return QueryUtils.reduce(electronics, null, (e1, e2) -> e1 == null || e2.getPrice() > e1.getPrice() ? e2 : e1);
    }

    public Iterable<Store.DisplaySummary> getDisplaysSummary() {
        List<Store.DisplaySummary> result = new ArrayList<>();

        QueryUtils.reduce(catalog, result, (ds, e) -> {
            if (e instanceof Screen s) {
                ds.add(new Store.DisplaySummary(s));
                return ds;
            }

            return ds;
        });

        return result;
    }

    public SmartPhone getCheapestSmartPhoneWithBatteryGreaterThen(int minBatCapacity) {
        Iterable<SmartPhone> iterable = QueryUtils.map(QueryUtils.filter(catalog, p -> p instanceof SmartPhone), p -> (SmartPhone) p);

        return QueryUtils.reduce(QueryUtils.filter(iterable, p -> p.getBatteryCapacity() > minBatCapacity), null, (sp1, sp2) -> sp1 == null || sp2.getPrice() < sp1.getPrice() ? sp2 : sp1);
    }

    public Iterable<Promo> getPromoTVsWith20PercentDiscount() {
        return QueryUtils.map(QueryUtils.filter(catalog, e -> e instanceof Promo p && (p.getWrappee() instanceof TV && p.getDiscount() == 20)), p -> (Promo) p);
    }
}