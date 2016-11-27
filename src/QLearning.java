import java.awt.Point;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
 
/**
 * @author Kunuk Nykjaer
 */
public class QLearning {
    final DecimalFormat df = new DecimalFormat("#.##");
 
    // path finding
    final double alpha = 0.1;
    final double gamma = 0.9;
 
/* 
dimensiones laberinto = 11 x 8

0 = camino
1 = muro
14 estados: 
			1 entrada = e
			1 salida = s
			6 bifurcaciones = A, C, E, G, I, J
			6 caminos sin salida = B, D, F, H, K, L

			
   0 1 2 3 4 5 6 7 8 9 10
   
0  1 1 1 1 1 1 1 1 1 1 1
1  1 0 0 0 1 F 1 0 0 H 1    
2  1 0 1 B 1 0 1 0 1 1 1
3  1 0 1 1 1 E 0 G 1 s 1
4  1 A 0 C 0 0 1 I 0 0 1
5  1 0 1 0 1 1 1 0 1 1 1
6  1 e 1 D 1 L 0 J 0 K 1
7  1 1 1 1 1 1 1 1 1 1 1
*/
    //Points con las coordenadas de la matriz anterior
    static final Point entrada = new Point(6,1);
    static final Point salida = new Point(3,9);
 
    static final Point stateA = new Point(4,1);
    static final Point stateB = new Point(2,3);
    static final Point stateC = new Point(4,3);
    static final Point stateD = new Point(6,3);
    static final Point stateE = new Point(3,5);
    static final Point stateF = new Point(1,5);
    static final Point stateG = new Point(3,7);
    static final Point stateH = new Point(1,9);
    static final Point stateI = new Point(4,7);
    static final Point stateJ = new Point(6,7);
    static final Point stateK = new Point(6,9);
    static final Point stateL = new Point(6,5);
    
    /*//Por alguna estupida razon eclipse no reconoce Map
    final Point pointA = new Point(2,1);
    
    Map<Integer, Point> mapa = new HashMap<Integer, Point>();
    
    //map.put(stateA, pointA);
    Map map = new HashMap();*/

    final int statesCount = 14;
    final Point[] states = new Point[]{entrada,stateA,stateB,stateC,stateD,stateE,stateF,stateG,stateH,stateI,stateJ,stateK,stateL,salida};
    
 
    // http://en.wikipedia.org/wiki/Q-learning
    // http://people.revoledu.com/kardi/tutorial/ReinforcementLearning/Q-Learning.htm
 
    // Q(s,a)= Q(s,a) + alpha * (R(s,a) + gamma * Max(next state, all actions) - Q(s,a))
 
    int[][] R = new int[statesCount][statesCount]; // reward lookup
    double[][] Q = new double[statesCount][statesCount]; // Q learning
 
    Point[] actionsFromEntrada = new Point[] { stateA };
    Point[] actionsFromA = new Point[] { entrada, stateB, stateC };
    Point[] actionsFromB = new Point[] { stateA };
    Point[] actionsFromC = new Point[] { stateA, stateD, stateE };
    Point[] actionsFromD = new Point[] { stateC };
    Point[] actionsFromE = new Point[] { stateC, stateF, stateG };
    Point[] actionsFromF = new Point[] { stateE };
    Point[] actionsFromG = new Point[] { stateE, stateH, stateI };
    Point[] actionsFromH = new Point[] { stateG };
    Point[] actionsFromI = new Point[] { stateG, salida, stateJ };
    Point[] actionsFromJ = new Point[] { stateI, stateK, stateL };
    Point[] actionsFromK = new Point[] { stateJ };
    Point[] actionsFromL = new Point[] { stateJ };    
    Point[] actionsFromSalida = new Point[] { salida };
    
    Point[][] actions = new Point[][] { actionsFromEntrada, actionsFromA, actionsFromB, actionsFromC,
            							actionsFromD, actionsFromE, actionsFromF, actionsFromG, actionsFromH, actionsFromI, 
            							actionsFromJ, actionsFromK, actionsFromL, actionsFromSalida };
 
    String[] stateNames = new String[] { "entrada", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "salida" };
 
    public QLearning() {
        init();
    }
 
    public void init() 
    {          	
        R[getIndex(stateI)][getIndex(salida)] = 100; // recompenza por pasar de I a salida
        //R[getIndex(stateF)][getIndex(stateC)] = 100; // from f to c     
    }
    
    public int getIndex(Point state)
    {
    	int index = 0;
    	
    	for(Point st : states)
    	{
    		if(st == state)
    		{
    			return index;
    		}
    		index++;
    	}
    	
    	return 0;
    }
 
    public static void main(String[] args) {
        long BEGIN = System.currentTimeMillis();
 
        QLearning obj = new QLearning();
        
        obj.run();
        obj.printResult();
        obj.showPolicy();
 
        long END = System.currentTimeMillis();
        System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");
    }
 
    void run() {
        /*
         1. Set parameter , and environment reward matrix R 
         2. Initialize matrix Q as zero matrix 
         3. For each episode: Select random initial state 
            Do while not reach goal state o 
                Select one among all possible actions for the current state o 
                Using this possible action, consider to go to the next state o 
                Get maximum Q value of this next state based on all possible actions o 
                Compute o Set the next state as the current state
         */
 
        // For each episode
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) { // train episodes
            // Select random initial state
        	//int randomState = rand.nextInt(statesCount);
            //Point state = states[randomState];
        	Point state = entrada;
            while (state != salida) // goal state
            {
                // Select one among all possible actions for the current state
                Point[] actionsFromState = actions[getIndex(state)];
                 
                // Selection strategy is random in this example
                int index = rand.nextInt(actionsFromState.length);
                Point action = actionsFromState[index];
 
                // Action outcome is set to deterministic in this example
                // Transition probability is 1
                Point nextState = action; // data structure
 
                // Using this possible action, consider to go to the next state
                double q = Q(state, action);
                double maxQ = maxQ(nextState);
                int r = R(state, action);
 
                double value = q + alpha * (r + gamma * maxQ - q);
                setQ(state, action, value);
 
                // Set the next state as the current state
                state = nextState;
            }
        }
    }
 
    double maxQ(Point s) {
        Point[] actionsFromState = actions[getIndex(s)];
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actionsFromState.length; i++) {
            Point nextState = actionsFromState[i];
            double value = Q[getIndex(s)][getIndex(nextState)];
 
            if (value > maxValue)
                maxValue = value;
        }
        return maxValue;
    }
 
    // get policy from state
    Point policy(Point state) {
        Point[] actionsFromState = actions[getIndex(state)];
        double maxValue = Double.MIN_VALUE;
        Point policyGotoState = state; // default goto self if not found
        for (int i = 0; i < actionsFromState.length; i++) {
        	Point nextState = actionsFromState[i];
            double value = Q[getIndex(state)][getIndex(nextState)];
 
            if (value > maxValue) {
                maxValue = value;
                policyGotoState = nextState;
            }
        }
        return policyGotoState;
    }
 
    double Q(Point s, Point a) {
        return Q[getIndex(s)][getIndex(a)];
    }
 
    void setQ(Point s, Point a, double value) {
        Q[getIndex(s)][getIndex(a)] = value;
    }
 
    int R(Point s, Point a) {
        return R[getIndex(s)][getIndex(a)];
    }
 
    void printResult() {
        System.out.println("Print result");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("out from " + stateNames[i] + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                System.out.print(df.format(Q[i][j]) + " ");
            }
            System.out.println();
        }
    }
 
    // policy is maxQ(states)
    void showPolicy() {
        System.out.println("\nshowPolicy");
        for (int i = 0; i < states.length; i++) {
            Point from = states[i];
            Point to =  policy(from);
            System.out.println("from "+stateNames[getIndex(from)]+" goto "+stateNames[getIndex(to)]);
        }           
    }
}