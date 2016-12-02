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
19 estados: 
			1 entrada = e
			1 salida = s
			6 bifurcaciones = A, C, E, G, I, J
			6 caminos sin salida = B, D, F, H, K, L
			5 esquinas = m, n, o, p, q

			
   0 1 2 3 4 5 6 7 8 9 10
   
0  1 1 1 1 1 1 1 1 1 1 1
1  1 m 0 n 1 F 1 p 0 H 1    
2  1 0 1 B 1 0 1 0 1 1 1
3  1 0 1 1 1 E 0 G 1 s 1
4  1 A 0 C 0 o 1 I 0 q 1
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
    
    static final Point statem = new Point(1,1);
    static final Point staten = new Point(1,3);
    static final Point stateo = new Point(4,5);
    static final Point statep = new Point(1,7);
    static final Point stateq = new Point(4,9); 
    
    /* 0 1 2 3 4 5 6 7 8 9 10
    
    7  1 1 1 1 1 1 1 1 1 1 1
    6  1 m 0 n 1 F 1 p 0 H 1    
    5  1 0 1 B 1 0 1 0 1 1 1
    4  1 0 1 1 1 E 0 G 1 s 1
    3  1 A 0 C 0 o 1 I 0 q 1
    2  1 0 1 0 1 1 1 0 1 1 1
    1  1 e 1 D 1 L 0 J 0 K 1
    0  1 1 1 1 1 1 1 1 1 1 1
    */

    final static int statesCount = 19;
    final int numRepeticiones = 4;
    final static Point[] states = new Point[]{entrada,stateA,stateB,stateC,stateD,stateE,stateF,stateG,
    									stateH,stateI,stateJ,stateK,stateL,statem,staten,stateo,statep,stateq,salida};
    
 
    // Q(s,a)= Q(s,a) + alpha * (R(s,a) + gamma * Max(next state, all actions) - Q(s,a))
 
    public static int[][] R = new int[statesCount][statesCount]; // reward lookup
    public static double[][] Q = new double[statesCount][statesCount]; // Q learning
 
    Point[] actionsFromEntrada = new Point[] { stateA };
    
    Point[] actionsFromA = new Point[] { entrada, statem, stateC };
    Point[] actionsFromB = new Point[] { staten };
    Point[] actionsFromC = new Point[] { stateA, stateD, stateo };
    Point[] actionsFromD = new Point[] { stateC };
    Point[] actionsFromE = new Point[] { stateo, stateF, stateG };
    Point[] actionsFromF = new Point[] { stateE };
    Point[] actionsFromG = new Point[] { stateE, statep, stateI };
    Point[] actionsFromH = new Point[] { statep };
    Point[] actionsFromI = new Point[] { stateG, stateq, stateJ };
    Point[] actionsFromJ = new Point[] { stateI, stateK, stateL };
    Point[] actionsFromK = new Point[] { stateJ };
    Point[] actionsFromL = new Point[] { stateJ };   
    
    Point[] actionsFromm = new Point[] { staten, stateA };
    Point[] actionsFromn = new Point[] { stateB, statem };
    Point[] actionsFromo = new Point[] { stateE, stateC };
    Point[] actionsFromp = new Point[] { stateH, stateG };
    Point[] actionsFromq = new Point[] { salida, stateI };
    
    Point[] actionsFromSalida = new Point[] { salida };
    
    Point[][] actions = new Point[][] { actionsFromEntrada, actionsFromA, actionsFromB, actionsFromC,
            							actionsFromD, actionsFromE, actionsFromF, actionsFromG, actionsFromH, 
            							actionsFromI, actionsFromJ, actionsFromK, actionsFromL, actionsFromm, 
            							actionsFromn, actionsFromo, actionsFromp, actionsFromq, actionsFromSalida };
 
    String[] stateNames = new String[] { "entrada", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "m", "n", "o", "p", "q", "salida" };
 
    public QLearning() 
    {
        init();
    }
 
    public void init() 
    {          	
        R[getIndex(stateq)][getIndex(salida)] = 100; // recompenza por pasar de I a salida   
    }
    
    public static int getIndex(Point state)
    {
    	int index = 0;
    	
    	for(Point st : states)
    	{
    		if(st.equals(state))
    		{
    			return index;
    		}
    		index++;
    	}
    	
    	return 0;
    }
 
    public static void main(String[] args) 
    {
        long BEGIN = System.currentTimeMillis();
 
        QLearning obj = new QLearning();
        
        obj.run();
        obj.printResult();
        obj.showPolicy();
 
        long END = System.currentTimeMillis();
        System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");
    }
 
    public void run() 
    {
 
        
        for (int i = 0; i < numRepeticiones; i++) 
        { // train episodes
            // Select random initial state
        	//int randomState = rand.nextInt(statesCount);
            //Point state = states[randomState];
        	Point state = entrada;
        	Point lastState = entrada;
            while (state != salida) // goal state
            {
                // Select one among all possible actions for the current state
                Point[] actionsFromState = actions[getIndex(state)];
                
                Point action = getActionsFromState(actionsFromState, state, lastState); 
                
                // Action outcome is set to deterministic in this example
                // Transition probability is 1
                Point nextState = action; // data structure
                
                // Using this possible action, consider to go to the next state
                double q = Q(state, action);
                double maxQ = maxQ(nextState);
                int r = R(state, action);
 
                double value = q + alpha * (r + gamma * maxQ - q);
                setQ(state, action, value);
                
                //Set the current state as the lastState
                lastState = state;
                // Set the next state as the current state
                state = nextState;
            }
        }
    }
    
    // Selection strategy is random if the current state is not a corner
    private Point getActionsFromState(Point[] actions, Point currentState, Point lastState)
    {
    	Point action = null;
    	Random rand = new Random();
    	
        int index = rand.nextInt(actions.length);
        action = actions[index];
        
    	return action;
    }
 
    private double maxQ(Point s) 
    {
        Point[] actionsFromState = actions[getIndex(s)];
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actionsFromState.length; i++) 
        {
            Point nextState = actionsFromState[i];
            double value = Q[getIndex(s)][getIndex(nextState)];
 
            if (value > maxValue)
                maxValue = value;
        }
        return maxValue;
    }
 
    // get policy from state
    public Point policy(Point state) 
    {
        Point[] actionsFromState = actions[getIndex(state)];
        double maxValue = Double.MIN_VALUE;
        Point policyGotoState = state; // default goto self if not found
        for (int i = 0; i < actionsFromState.length; i++) 
        {
        	Point nextState = actionsFromState[i];
            double value = Q[getIndex(state)][getIndex(nextState)];
 
            if (value > maxValue) 
            {
                maxValue = value;
                policyGotoState = nextState;
            }
        }
        return policyGotoState;
    }
 
    double Q(Point s, Point a) 
    {
        return Q[getIndex(s)][getIndex(a)];
    }
 
    void setQ(Point s, Point a, double value)
    {
        Q[getIndex(s)][getIndex(a)] = value;
    }
 
    int R(Point s, Point a) 
    {
        return R[getIndex(s)][getIndex(a)];
    }
 
    void printResult() 
    {
        System.out.println("Print result");
        for (int i = 0; i < Q.length; i++) 
        {
            System.out.print("out from " + stateNames[i] + ":  ");
            for (int j = 0; j < Q[i].length; j++) 
            {
                System.out.print(df.format(Q[i][j]) + " ");
            }
            System.out.println();
        }
    }
 
    // policy is maxQ(states)
    void showPolicy() 
    {
        System.out.println("\nshowPolicy");
        for (int i = 0; i < states.length; i++)
        {
            Point from = states[i];
            Point to =  policy(from);
            System.out.println("from "+stateNames[getIndex(from)]+" goto "+stateNames[getIndex(to)]);
            System.out.println("from "+from.toString()+" goto "+to.toString());
        }           
    }
}