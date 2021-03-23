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
    private  double geodesic_distance(City targetCity) {
        if ((this.currentLocation[0] == targetCity.getGeodesic_vector()[0]) && (this.currentLocation[1] == targetCity.getGeodesic_vector()[1])) {
            return 0;
        }
        else {
            double theta = this.currentLocation[1] - targetCity.getGeodesic_vector()[1];
            double dist = Math.sin(Math.toRadians(this.currentLocation[0])) * Math.sin(Math.toRadians(targetCity.getGeodesic_vector()[0])) + Math.cos(Math.toRadians(this.currentLocation[0])) * Math.cos(Math.toRadians(targetCity.getGeodesic_vector()[0])) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }
    }

    public double calculate_similarity(City targetCity){
        return this.p*calculate_terms_similarity(termsRating,targetCity.getTerms_vector()) + (1-p)*geodesic_distance(targetCity);

    }

}



