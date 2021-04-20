package OOPII_21999_219148_219160;

import java.util.ArrayList;
import java.util.Date;

class ArrayListException extends Exception{
    ArrayListException(String s){
        super(s);
    }
}




abstract class Traveller implements Comparable {
    private int[] termsRating;
    private float[] currentLocation;
    private float p;
    private Date timestamp;
    private String lastRecommendedCity;
    //constructor
    public Traveller(int[] termsRating, float[] currentLocation) {
        this.termsRating = termsRating;
        this.currentLocation = currentLocation;
    }

    //setters getters
    public int[] getTermsRating() {
        return termsRating;
    }

    public void setTermsRating(int[] termsRating) {
        this.termsRating = termsRating;
    }

    public float[] getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(float[] currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLastRecommendedCity() {
        return lastRecommendedCity;
    }

    public void setLastRecommendedCity(String lastRecommendedCity) {
        this.lastRecommendedCity = lastRecommendedCity;
    }

    //***************************************************************//

    public abstract double calculate_terms_similarity(int [] userTerms, int [] cityTerms);

    public float getP() {
        return p;
    }

    public void setP(float p) {
        this.p = p;
    }


    /*
        geodesic_vector[0] is longitude
        geodesic_vector[1] is latitude
        */
    //returns distance in KMs
    public  double calculate_distance(City targetCity) {
        if ((this.currentLocation[1] == targetCity.getGeodesic_vector()[1]) && (this.currentLocation[0] == targetCity.getGeodesic_vector()[0])) {
            return 0;
        }
        else {
            double theta = this.currentLocation[0] - targetCity.getGeodesic_vector()[0];
            double dist = Math.sin(Math.toRadians(this.currentLocation[1])) * Math.sin(Math.toRadians(targetCity.getGeodesic_vector()[1])) + Math.cos(Math.toRadians(this.currentLocation[1])) * Math.cos(Math.toRadians(targetCity.getGeodesic_vector()[1])) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344; // convert to kilometres
            return (dist);
        }
    }

    // Function to calculate the
    // log base 2 of an integer
    //https://www.geeksforgeeks.org/how-to-calculate-log-base-2-of-an-integer-in-java/
    public static double log2(double N)
    {
        // calculate log2 N indirectly
        // using log() method

        return (Math.log(N) / Math.log(2));
    }


    public double similarity_geodesic_vector(City targetCity){
        int maxDist =  15317 ; //https://www.distance.to/Athens/Sydney
        return log2(2/(2-calculate_distance(targetCity)/maxDist));
    }



    public double calculate_similarity(City targetCity){
        return p*calculate_terms_similarity(termsRating,targetCity.getTerms_vector()) + (1-p)* similarity_geodesic_vector(targetCity);
    }

    public City compare_cities(ArrayList<City> cityArrayList) throws ArrayListException {
        double max = -1f;
        int loc = 0;

        if (cityArrayList.size() == 0){
            throw new ArrayListException("Empty ArrayList");
        }
            loc = 0;
            max = calculate_similarity(cityArrayList.get(0));

       for (int i = 1;i < cityArrayList.size();i++){
           if (calculate_similarity(cityArrayList.get(i)) > max){
                loc = i;
                max = calculate_similarity(cityArrayList.get(i));
           }
       }
        return cityArrayList.get(loc);
    }
    public  City[] compare_cities(ArrayList<City> cityArrayList,int amount) throws ArrayListException {
        if (cityArrayList.size() < 2){
            throw new ArrayListException("Invalid ArrayList size");
        }
        City[] returnArr = new City[amount];
        double[] similarityArr = new double[cityArrayList.size()];
        int[] locArr = new int[cityArrayList.size()];
        for (int i = 0; i < cityArrayList.size();i++){
            similarityArr[i] = calculate_similarity(cityArrayList.get(i));
            locArr[i] = i;
        }
        int n = cityArrayList.size();
        double temp;
        int tmp2;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(similarityArr[j-1] < similarityArr[j]){
                    //swap elements
                    temp = similarityArr[j-1];
                    similarityArr[j-1] = similarityArr[j];
                    similarityArr[j] = temp;
                    tmp2 = locArr[j-1];
                    locArr[j-1] = locArr[j];
                    locArr[j] = tmp2;
                }

            }
        }
        for (int i =0 ; i < amount;i++){
            returnArr[i] = cityArrayList.get(locArr[i]);
        }
        return returnArr;
    }



    public int compareTo(Traveller o) {
        //aplos tsekare timestamps <3
        //returns 0 if dates are equal returns >0 if this is after 0 and <0 if o is after this
        return this.timestamp.compareTo(o.timestamp);
    }
}
