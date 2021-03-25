package OOPII_21999_219148_219160;

abstract class Traveller {
    private int[] termsRating;
    private float[] currentLocation;
    private float p;
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

}



