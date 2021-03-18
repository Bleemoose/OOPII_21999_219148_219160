package OOPII_21999_219148_219160;

public class ElderTraveller extends Traveller{

    public ElderTraveller(int[][] termsRating, float[] currentLocation) {
        super(termsRating, currentLocation);
        int []terms_vector_tokyo ={0,1,2,3,0,1,2,1,1,0};
        int []terms_vector_rome = {4,1,3,0,0,3,0,5,1,2};
        int []terms_vector_london = {4,3,0,2,2,5,4,5,2,1};
        int [] terms_vector_user = {4,3,3,0,0,3,2,5,0,2};
        System.out.println(elder_distance(terms_vector_user, terms_vector_tokyo));
        System.out.println(elder_distance(terms_vector_user,terms_vector_rome));
        System.out.println(elder_distance(terms_vector_user,terms_vector_london));
        System.out.println(elder_distance(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}));
        System.out.println(elder_distance(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 8}));
        System.out.println(elder_distance(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 0}));
        System.out.println(elder_distance(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1}));
        System.out.println(elder_distance(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{9, 9, 9, 9, 9, 0, 0, 0, 0, 0}));
        System.out.println(elder_distance(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    }
    double elder_distance(int [] userTerms, int [] cityTerms){
        double sum_intersection=0,sum_union=0;
        for (int i = 0; i < userTerms.length; i++) {
            if(userTerms[i]>=1 && cityTerms[i]>=1){
                sum_intersection++;
            }
            if (userTerms[i]>=1 || cityTerms[i]>=1){
                sum_union++;
            }
        }
        if (sum_union==0){
            return 0;
        }
        return sum_intersection/sum_union;
    }
}
