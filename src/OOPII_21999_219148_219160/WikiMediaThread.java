package OOPII_21999_219148_219160;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URL;

public class WikiMediaThread extends Thread{
    int[] terms_temp=new int[10];
    String name=new String();
    String returned=new String();

    public String getReturned() {
        return returned;
    }

    public void setCityName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        String nameFinal = null;
        String[] parts = new String[2];
        //look for these words
        String[] term_names = {"museum", "history", "car", "bike", "food", "mountain", "cafe", "shopping", "sea", "nightlife"};
        parts = name.split("(?=,)");
        String afterComma = "";
        if (parts.length == 2) {
            afterComma = parts[1];
        }

        if (name.lastIndexOf(",") != -1 && afterComma.length() == 3) {
            nameFinal = name.substring(0, name.indexOf(",")); //removes comma from search
        } else {
            returned = "INV";
        }
        try {
            final ObjectNode node = new ObjectMapper().readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + nameFinal + "&format=json&formatversion=2"), ObjectNode.class);
            JsonNode jsonPages = node.get("query").get("pages");
            for (int i = 0; i < terms_temp.length; i++) {
                terms_temp[i] = CountWords.countCriterionfCity(jsonPages.toString(), term_names[i]);
            }
        } catch (IOException e) {
            returned = "CNF";
        }

    }

    public int[] getTerms_temp() {
        return terms_temp;
    }
}
