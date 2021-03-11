package OOPII_21999_219148_219160;

public class City {
    private int[] terms_vector;
    private float[] geodesic_vector;

    //constructor
    public City(int[] terms_vector, float[] geodesic_vector) {
        this.terms_vector = terms_vector;
        this.geodesic_vector = geodesic_vector;
    }


    //setters getters
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
}
