
import java.util.*;

/**
 * Write a description of class Maze here.
 * Clase que genera un laberinto aleatoriamente, utilizando recursividad para definir los distintos caminos.
 * Se establece semi-aleatoriamente una o más salidas así como una entrada, y se devuelve dicho laberinto
 * en código de numeros para ser utilizado en la clase Main. 
 * 
 * @author (Matías Rodríguez Singer B15647) 
 * @version (3.0)
 */
public class Maze 
{
    
    //3 = salida, 2 = entrada
    public static int[][] MAZE = 
    							{{1,1,1,1,1,1,1,1,1,1,1},
    							  {1,0,0,0,1,0,1,0,0,0,1},
    							  {1,0,1,0,1,0,1,0,1,1,1},
    							  {1,0,1,1,1,0,0,0,1,3,1},
    							  {1,0,0,0,0,0,1,0,0,0,1},
    							  {1,0,1,0,1,1,1,0,1,1,1},
    							  {1,2,1,0,1,0,0,0,0,0,1},
    							  {1,1,1,1,1,1,1,1,1,1,1}};
    
    public static int height = MAZE[0].length; // Se recomienta que las dimensiones sean impares.
    public static int width = MAZE.length;
    
    private int entrada;
    /**
     * Método main
     */
    public static void main(String[] args)
    {
        Maze m = new Maze();
    }
    
    /**
     * Constructor
     */
    public Maze()
    {
        //generarMaze();
        //insertarEntrada(MAZE);
        //insertarSalida(MAZE);
        //display(MAZE); 
        //sacar de comentario este llamado para desplegar en la consola el laberinto en código de números
        //0 es camino libre, 1 es muro, 2 es entrada y 3 es salida
    }
    
    public int[][] getMaze()
    {
        return MAZE;
    }
}
