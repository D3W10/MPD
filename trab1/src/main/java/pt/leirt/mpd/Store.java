package pt.leirt.mpd;

import org.json.JSONArray;
import org.json.JSONObject;
import pt.leirt.mpd.products.*;

import java.util.*;

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

        speakers.sort(Comparator.comparingInt(o -> (int) o.getPower()));

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
        Electronics result = null;

        for (var p : catalog) {
            if (p instanceof Pack pack) {
                for (var product : pack) {
                    if (result == null || product.getPrice() > result.getPrice())
                        result = product;
                }
            }
        }

        return result;
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
            if (p instanceof Promo pTV && (pTV.getWrappee() instanceof TV && pTV.getDiscount() == 20))
                promoProduct.add(pTV);
        }

        return promoProduct;
    }

    public String toJson() {
        JSONObject jObj = new JSONObject();
        JSONArray jArray = new JSONArray();

        for (Electronics electronic : catalog)
            jArray.put(new JSONObject(electronic.toJson()));

        jObj.put("catalog", jArray);

        return jObj.toString();
    }

    public JSONObject fromJson(String json) {
        JSONObject jObj = new JSONObject(json);

        for (Object item : jObj.getJSONArray("catalog")) {
            if (item instanceof JSONObject eletronic)
                catalog.add(jsonToObject(eletronic));
        }

        return jObj;
    }

    private Electronics jsonToObject(JSONObject jObj) {
        return switch (jObj.getString("type")) {
            case "Speaker" ->
                    new Speaker(jObj.getString("name"), jObj.getString("brand"), jObj.getDouble("price"), jObj.getDouble("power"));
            case "Notebook" ->
                    new Notebook(jObj.getString("name"), jObj.getString("brand"), jObj.getDouble("price"), new Resolution(jObj.getJSONObject("resolution").getInt("width"), jObj.getJSONObject("resolution").getInt("height")), jObj.getDouble("screenSize"), jObj.getInt("batteryCapacity"), jObj.getInt("usbPorts"));
            case "TV" ->
                    new TV(jObj.getString("name"), jObj.getString("brand"), jObj.getDouble("price"), new Resolution(jObj.getJSONObject("resolution").getInt("width"), jObj.getJSONObject("resolution").getInt("height")), jObj.getDouble("screenSize"));
            case "SmartPhone" ->
                    new SmartPhone(jObj.getString("name"), jObj.getString("brand"), jObj.getDouble("price"), new Resolution(jObj.getJSONObject("resolution").getInt("width"), jObj.getJSONObject("resolution").getInt("height")), jObj.getDouble("screenSize"), jObj.getInt("batteryCapacity"));
            case "Promo" ->
                    new Promo(jsonToObject(jObj.getJSONObject("product")), jObj.getInt("discount"));
            case "Pack" -> {
                List<Electronics> le = new ArrayList<>();

                for (Object item : jObj.getJSONArray("products")) {
                    if (item instanceof JSONObject prod)
                        le.add(jsonToObject(prod));
                }

                yield new Pack(jObj.getString("name"), le);
            }
            default -> null;
        };
    }
}