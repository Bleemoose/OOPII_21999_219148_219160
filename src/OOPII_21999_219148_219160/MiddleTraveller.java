package OOPII_21999_219148_219160;

import java.lang.Math;

public class MiddleTraveller extends Traveller{

    public MiddleTraveller(int[] termsRating, float[] currentLocation) {
        super(termsRating, currentLocation);
        int []terms_vector_tokyo ={0,1,2,3,0,1,2,1,1,0};
        int []terms_vector_rome = {4,1,3,0,0,3,0,5,1,2};
        int []terms_vector_london = {4,3,0,2,2,5,4,5,2,1};
        int [] terms_vector_user = {4,3,3,0,0,3,2,5,0,2};
        System.out.println(calculate_similarity(terms_vector_user, terms_vector_tokyo));
        System.out.println(calculate_similarity(terms_vector_user,terms_vector_rome));
        System.out.println(calculate_similarity(terms_vector_user,terms_vector_london));
        System.out.println(calculate_similarity(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}));
        System.out.println(calculate_similarity(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 8}));
        System.out.println(calculate_similarity(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 0}));
        System.out.println(calculate_similarity(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1}));
        System.out.println(calculate_similarity(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9, 9, 0, 0, 0, 0, 0}));
        System.out.println(calculate_similarity(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    }
    @Override
    public double calculate_similarity(int[] userTerms, int[] cityTerms){
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
