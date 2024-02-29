package pt.leirt.mpd;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import pt.leirt.mpd.products.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Store {
    private Collection<Electronics> catalog = new ArrayList<>();

    public Store addCatalog(Electronics product) {
        catalog.add(product);
        return this;
    }

    // query methods

    public Iterable<Electronics> fromSamsung() {
        List<Electronics> samsungProds = new ArrayList<>();
        //var samsungProds1 = new ArrayList<Electronics>();

        for(var p : catalog) {
            if (p.getBrand().equalsIgnoreCase("Samsung"))
                samsungProds.add(p);
        }
        return samsungProds;
    }

    public Iterable<TV> getAboveSizeTvs(double minInches) {
        // TO IMPLEMENT
        return null;
    }

    public Iterable<Speaker> getSpeakersInPowerInterval(int minPower, int maxPower) {
        // TO IMPLEMENT
        return null;
    }

    public Electronics getMostExpensiveCommunicationDevice() {
        // TO IMPLEMENT
        return null;
    }

    public Electronics getMostExpensiveIndividualProductInPacks() {
        // TO IMPLEMENT
        return null;
    }

    static class DisplaySummary {
        // TO Define , must include product name and brand and display characteristics
    }

    public Iterable<DisplaySummary> getDisplaysSummary() {
        // TO IMPLEMENT
        return null;
    }

    public SmartPhone getCheapestSmartPhoneWithBatteryGreaterThen(int minBatCapacity) {
        // TO IMPLEMENT
        return null;
    }

    public Iterable<Promo> getPromoTVsWith20PercentDiscount() {
        // TO IMPLEMENT
        return null;
    }


}
