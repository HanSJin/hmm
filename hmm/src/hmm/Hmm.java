package hmm;

public class Hmm {
	static double[][] V;
	static double[][] A;
    static int[][] path;
    
    static int[] states;
    static int[] obs;
    static double[] start_p;
    static double[][] trans_p;
    static double[][] emit_p;

    public Hmm(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emit_p){
    	Hmm.states = states;
    	Hmm.obs = obs;
    	Hmm.start_p = start_p;
    	Hmm.trans_p = trans_p;
    	Hmm.emit_p = emit_p;

		V = new double[obs.length][states.length];
		A = new double[obs.length][states.length];
    	path = new int[states.length][obs.length];
    }
    //전향 알고리즘 -> "평가"
    public static double forwardAlgo(int X){
    	for (int y : states)
        {
            A[0][y] = start_p[y] * emit_p[y][obs[0]];
        }
    	
    	 for (int t = 1; t < obs.length; ++t)
         {
    		 if(t==obs.length-1){
    			 for(int u : states)
    	    		 A[obs.length-1][X] += A[t - 1][u] * trans_p[u][X] * emit_p[X][obs[t]];
    			 
    			 break;
    		 }
    		 
             for (int y : states)
             {
                 for (int y0 : states)
                 {
                     A[t][y] += A[t - 1][y0] * trans_p[y0][y] * emit_p[y][obs[t]];
                 }
             }
         }
    	 
    	 return A[obs.length-1][X];
    }
    // 비터비 알고리즘 -> "디코딩"
	public static int[] viterbi()
    {
        for (int y : states)
        {
            V[0][y] = start_p[y] * emit_p[y][obs[0]];
            path[y][0] = y;
        }

        for (int t = 1; t < obs.length; ++t)
        {
            int[][] newpath = new int[states.length][obs.length];

            for (int y : states)
            {
                double prob = -1;
                int state;
                for (int y0 : states)
                {
                    double nprob = V[t - 1][y0] * trans_p[y0][y] * emit_p[y][obs[t]];
                    if (nprob > prob)
                    {
                        prob = nprob;
                        state = y0;
                        V[t][y] = prob;
                        System.arraycopy(path[state], 0, newpath[y], 0, t);
                        newpath[y][t] = y;
                    }
                }
            }

            path = newpath;
        }

        double prob = -1;
        int state = 0;
        for (int y : states)
        {
            if (V[obs.length - 1][y] > prob)
            {
                prob = V[obs.length - 1][y];
                state = y;
            }
        }

        return path[state];
    }
}
