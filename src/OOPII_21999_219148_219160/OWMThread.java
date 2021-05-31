package OOPII_21999_219148_219160;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URL;

public class OWMThread extends Thread{
    float[] data=new float[2];
    String name=new String();
    @Override
    public void run() {
        String APIid="eeba41d7d3d95a8dad6d4c3ae375f602";
        ObjectNode node= null;
        try {
            node = new ObjectMapper().readValue(new URL("https://api.openweathermap.org/data/2.5/weather?q="+name+"&APPID="+APIid), ObjectNode.class);
        } catch (IOException e) {
            name="CNF";
        }
        if(name!="CNF") {
            JsonNode coords = node.get("coord");
            data[0] = Float.parseFloat(coords.get("lon").asText());
            data[1] = Float.parseFloat(coords.get("lat").asText());
        }
        else {
            data[0] = 0;
            data[1]= 0;
        }
    }


    public void setCityName(String name) {
        this.name = name;
    }

    public float[] getData() {
        return data;
    }

    public String getCityName() {
        return name;
    }
}
