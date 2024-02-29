package pt.leirt.mpd.products;

import org.json.JSONArray;
import org.json.JSONObject;

public class Resolution {
    public final int height; // pixels height
    public final int width;  // pixels width

    public Resolution(int width, int height) {
        this.width = width; this.height = height;
    }

    public JSONObject getJson( ) {
        JSONObject json = new JSONObject();
        json.put("width", width);
        json.put("height", height);
        return json;
    }
}
