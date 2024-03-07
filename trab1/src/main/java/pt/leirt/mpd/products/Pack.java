package pt.leirt.mpd.products;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pack implements Iterable<Electronics>, Electronics {
    private String name;
    private List<Electronics> products;
    private Iterator<Electronics> iterator = null;

    public Pack(String name, List<Electronics> products) {
        this.name = name;
        this.products = products;
    }

    @Override
    public Iterator<Electronics> iterator() {
        if (iterator == null) {
            List<Electronics> allProducts = new ArrayList<>();

            products.forEach(product -> {
                if (product instanceof Pack pack) {

                    for (Electronics electronics : pack)
                        allProducts.add(electronics);
                } else
                    allProducts.add(product);
            });

            iterator = allProducts.iterator();
        }

        return iterator;
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
        return null;
    }

    @Override
    public String getBrand() {
        return null;
    }
}
