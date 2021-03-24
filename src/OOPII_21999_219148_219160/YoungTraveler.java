package OOPII_21999_219148_219160;

import java.lang.Math;

public class YoungTraveler extends Traveller{


    public YoungTraveler(int[] termsRating, float[] currentLocation) {
        super(termsRating, currentLocation);
        this.setP(0.7f);
    }
    @Override
    public double calculate_terms_similarity(int [] userTerms, int [] cityTerms){
        double Sum = 0.0;
        for(int i=0;i<userTerms.length;i++) {
            Sum = Sum + Math.pow((userTerms[i]-cityTerms[i]),2);
        }
        return 1/(1+Math.sqrt(Sum));
    }
}
