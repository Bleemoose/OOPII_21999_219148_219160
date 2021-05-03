package OOPII_21999_219148_219160;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;


/*
geodesic_vector[0] is longitude
geodesic_vector[1] is latitude
 */

/*
terms_vector[0]=Museum
terms_vector[1]=History
terms_vector[2]=Car
terms_vector[3]=Bike
terms_vector[4]=Food
terms_vector[5]=Mountain
terms_vector[6]=Cafe
terms_vector[7]=Shopping
terms_vector[8]=Sea
terms_vector[9]=Nightlife
 */

class InvalidInputException extends Exception{
    InvalidInputException(String s){
        super(s);
    }
}

public class City {
    private String name;
    private int[] terms_vector;
    private float[] geodesic_vector;

    //constructor
    public City(String name) throws IOException, InvalidInputException {
        this.name=name;
        geodesic_vector = getLocInfo(name);
        terms_vector = getTerms(name);

    }

   //setters and getters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        String APIid="eeba41d7d3d95a8dad6d4c3ae375f602";
        ObjectNode node=new ObjectMapper().readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q="+name+"&APPID="+APIid),ObjectNode.class);
        JsonNode coords=node.get("coord");
        data[0]=Float.parseFloat(coords.get("lon").asText());
        data[1]=Float.parseFloat(coords.get("lat").asText());
        return data;
        }

    private int[] getTerms(String name) throws IOException, InvalidInputException {
        int[] terms_temp=new int[10];
        String nameFinal;
        String [] term_names={"museum","history","car","bike","food","mountain","cafe","shopping","sea","nightlife"};
        if(name.lastIndexOf(",")!=-1){
            nameFinal = name.substring( 0, name.indexOf(",")); //removes comma from search
        }
        else{
            throw new InvalidInputException(name);
        }
        final ObjectNode node=new ObjectMapper().readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+nameFinal+"&format=json&formatversion=2"),ObjectNode.class);
        JsonNode jsonPages=node.get("query").get("pages");
        for (int i = 0; i < terms_temp.length; i++) {
            terms_temp[i]=CountWords.countCriterionfCity(jsonPages.toString(),term_names[i]);
        }
        return terms_temp;
    }
}
