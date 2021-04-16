package OOPII_21999_219148_219160;

import java.lang.Math;

public class MiddleTraveller extends Traveller{

    public MiddleTraveller(int[] termsRating, float[] currentLocation) {
        super(termsRating, currentLocation);
        this.setP(0.5f);
    }
    @Override
    public double calculate_terms_similarity(int[] userTerms, int[] cityTerms){
        int sum = 0,sum_a=0,sum_b=0;
        for (int i = 0; i < userTerms.length; i++) {
            sum+=userTerms[i]*cityTerms[i];
            sum_a+= Math.pow(userTerms[i],2);
            sum_b+= Math.pow(cityTerms[i],2);
        }
        if(sum_a==0 || sum_b==0){
            return 0;
        }
        return sum/(Math.sqrt(sum_a)*Math.sqrt(sum_b));
    }



}
