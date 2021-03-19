package OOPII_21999_219148_219160;

import java.lang.Math;
public class YoungTraveler extends Traveller{


    int []terms_vector_tokyo ={0,1,2,3,0,1,2,1,1,0};
    int []terms_vector_rome = {4,1,3,0,0,3,0,5,1,2};
    int []terms_vector_london = {4,3,0,2,2,5,4,5,2,1};
    int [] terms_vector_user = {4,3,3,0,0,3,2,5,0,2};

    //hmm?
    public YoungTraveler(int[] termsRating, float[] currentLocation) {
        super(termsRating, currentLocation);
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
    public double calculate_similarity (int [] userTerms, int [] cityTerms){
        double Sum = 0.0;
        for(int i=0;i<userTerms.length;i++) {
            Sum = Sum + Math.pow((userTerms[i]-cityTerms[i]),2);
        }
        return 1/(1+Math.sqrt(Sum));
    }
}
