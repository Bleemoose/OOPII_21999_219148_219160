package OOPII_21999_219148_219160;


public class ElderTraveller extends Traveller{

    public ElderTraveller() {

    }

    public ElderTraveller(int[] termsRating, float[] currentLocation, String fullName) {

        super(termsRating, currentLocation,fullName);
        this.setP(0.3f);
    }

    @Override
    public double calculate_terms_similarity(int[] userTerms, int[] cityTerms){
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
