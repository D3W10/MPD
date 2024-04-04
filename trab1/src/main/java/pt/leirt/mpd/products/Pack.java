package pt.leirt.mpd.products;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Pack implements Iterable<Electronics>, Electronics {
    private String name;
    private List<Electronics> products;
    @Internal
    private List<Electronics> allProducts;

    public Pack(String name, List<Electronics> products) {
        this.name = name;
        this.products = products;

        allProducts = new ArrayList<>();

        products.forEach(product -> {
            if (product instanceof Pack pack) {
                for (Electronics electronics : pack)
                    allProducts.add(electronics);
            }
            else
                allProducts.add(product);
        });
    }

    @Override
    public Iterator<Electronics> iterator() {
        return allProducts.iterator();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        double totalPrice = 0;

        for (var product : products)
            totalPrice += product.getPrice();

        return totalPrice;
    }

    @Override
    public Category getCategory() {
        return Category.PACK;
    }

    @Override
    public String getBrand() {
        return "Pack";
    }

    @Override
    public String toJson() {
        JSONObject jObj = new JSONObject();
        JSONArray array = new JSONArray();

        jObj.put("name", name);
        products.forEach(electronic -> array.put(new JSONObject(electronic.toJson())));
        jObj.put("products", array);
        jObj.put("type", "Pack");

        return jObj.toString();
    }
}
