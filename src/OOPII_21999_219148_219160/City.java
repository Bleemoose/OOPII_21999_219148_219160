package OOPII_21999_219148_219160;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;


/*
geodesic_vector[0] is longitude
geodesic_vector[1] is latitude
 */

public class City {
    public String name;
    private int[] terms_vector=new int[10];
    private float[] geodesic_vector=new float[2];

    //constructor
    public City(String name) throws IOException {
        this.terms_vector = terms_vector;
        this.geodesic_vector = geodesic_vector;
        geodesic_vector=getLocInfo(name);
    }

   //setters and getters

    public int[] getTerms_vector() {
        return terms_vector;
    }

    public void setTerms_vector(int[] terms_vector) {
        this.terms_vector = terms_vector;
    }

    public float[] getGeodesic_vector() {
        return geodesic_vector;
    }

    public void setGeodesic_vector(float[] geodesic_vector) {
        this.geodesic_vector = geodesic_vector;
    }

    //location retriever
    private float[] getLocInfo(String  name) throws IOException {
        float[] data=new float[2];
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q="+name+"&appid=eeba41d7d3d95a8dad6d4c3ae375f602")
                .method("GET", null)
                .build();
        String response = client.newCall(request).execute().body().string();
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        JsonObject jsonCoord=jsonResponse.getAsJsonObject("coord");
        data[0]= Float.parseFloat(jsonCoord.get("lon").toString());
        data[1]= Float.parseFloat(jsonCoord.get("lat").toString());
        return data;
    }
}
