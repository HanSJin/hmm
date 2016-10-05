package hmm;

import hmm.WeatherExample.Weather;

public class FoodExample
{
    enum Region
    {
        한식,
        중식,
        일식,
        양식,
        기타
    }
    enum Flavor
    {
        매콤,
        담백,
        짭짤,
        느끼,
        달콤
    }
  
    static int[] states = new int[5];
    static int[] observations = new int[5];
   static double[] start_probability = new double[]{0.05, 0.4, 0.15, 0.25, 0.15};
    static double[][] transititon_probability = new double[][]{
            {0.4, 0.15, 0.15, 0.2, 0.1},
            {0.1, 0.2 , 0.3, 0.1, 0.3},
            {0.1, 0.3, 0.3, 0.2, 0.1},
            {0.2, 0.1, 0.2, 0.3, 0.1},
            {0.4, 0.1, 0.1, 0.1, 0.3},
    };
    static double[][] emission_probability = new double[][]{
    	  {0.3, 0.2, 0.1, 0.1, 0.1},
          {0.2, 0.2 ,0.5, 0.2, 0.1},
          {0.2, 0.3, 0.2, 0.2, 0.1},
          {0.1, 0.1, 0.2, 0.3, 0.3},
          {0.2, 0.2, 0.2, 0.2, 0.2},
    };

    public static void main(String[] args)
    {
    	for(Region region : Region.values()){
            states[region.ordinal()] = region.ordinal();
        }
    	for(Flavor flavor : Flavor.values()){
            states[flavor.ordinal()] = flavor.ordinal();
        }
    	
    	Hmm hmm = new Hmm(observations, states, start_probability, transititon_probability, emission_probability);
    	System.out.println("매콤->담백->짭짤->느끼->달콤 순으로 음식을 먹었을 때, 달콤한 음식이 한식일 확률은?");
    	System.out.println(hmm.forwardAlgo(0));
    	
    	System.out.println("매콤->담백->짭짤->느끼->달콤 순으로 음식을 먹었을 때, 매 끼니 먹었던 음식은 종류는?");
    	int[] result = hmm.viterbi();
        for (int r : result)
        {
            System.out.print(Region.values()[r] + " ");
        }
        System.out.println();
        
    }
}
