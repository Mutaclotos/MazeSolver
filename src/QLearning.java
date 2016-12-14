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
    final double alpha = 0.5;
    final double gamma = 0.5;
 
    
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

    final int numRepeticiones = 50;
    
 
    // Q(s,a)= Q(s,a) + alpha * (R(s,a) + gamma * Max(next state, all actions) - Q(s,a))
 
    
    public static double[][] Q = new double[Maze.stateCount][Maze.stateCount]; // Q learning
 
 
    public QLearning() 
    {
        //init();
    }
    
    public static int getIndex(Point state)
    {
    	int index = 0;
    	
    	for(Point st : Maze.estadosQL)
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
        Maze m = new Maze(3);
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
        	Point state = Maze.entrada;
        	Point lastState = Maze.entrada;
            while (state != Maze.salida) // goal state
            {
                // Select one among all possible actions for the current state
                Point[] actionsFromState = Maze.accionesQL[getIndex(state)];
                
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
        System.out.println("qlearning");
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
        Point[] actionsFromState = Maze.accionesQL[getIndex(s)];
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
        Point[] actionsFromState = Maze.accionesQL[getIndex(state)];
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
        return Maze.R[getIndex(s)][getIndex(a)];
    }
 
    void printResult() 
    {
        System.out.println("Print result");
        for (int i = 0; i < Q.length; i++) 
        {
            System.out.print("out from " + Maze.nombresEstadoQL[i] + ":  ");
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
        for (int i = 0; i < Maze.estadosQL.length; i++)
        {
            Point from = Maze.estadosQL[i];
            Point to =  policy(from);
            System.out.println("from "+Maze.nombresEstadoQL[getIndex(from)]+" goto " + Maze.nombresEstadoQL[getIndex(to)]);
            System.out.println("from "+from.toString()+" goto "+to.toString());
        }           
    }
}