
import java.awt.Point;
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
	public static final int MURO = 1;
    public static final int VACIO = 0;
    public static final int CAMINO = 4;
    public static final int ENTRADA = 2;
    public static final int SALIDA = 3;
	
    private static Point entrada;
    private static Point salida;
	private static Point celdaActual; // MOMENTÁNEAMENTE
	
	// Se llena con entrada, la lista de estados (retornada por el metodo reconoceEstados y salida
	private static List<List<Point>> acciones = new ArrayList<List<Point>>();
    
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
    	List<Point> estadosPrueba = new ArrayList<Point>();
        estadosPrueba = reconoceEstados(MAZE);
        System.out.println(estadosPrueba);
        System.out.println("\nEntrada: ");
        System.out.println(entrada);
        System.out.println("\nSalida: ");
        System.out.println(salida); 
        rellenarAcciones();
    }
    
    public static List<Point> reconoceEstados(int[][] matriz){
        Point tempPoint;
        List<Point> estados = new ArrayList<Point>();
        for(int i = 1; i < width - 1;  i++)
          for(int j = 1; j < height - 1; j++)
            if( matriz[i][j] == 2 | matriz[i][j] == 3){
              if(matriz[i][j] == 2)
                entrada = new Point(i,j);
              else
                salida = new Point(i,j);
            }
            else
            {
              if(matriz[i][j] == 0)
                if( matriz[i-1][j] == 1 && matriz[i+1][j] == 0 && matriz[i][j-1] == 0 && matriz[i][j+1] == 0 ||
                    matriz[i-1][j] == 0 && matriz[i+1][j] == 0 && matriz[i][j-1] == 1 && matriz[i][j+1] == 0 ||
                    matriz[i-1][j] == 0 && matriz[i+1][j] == 0 && matriz[i][j-1] == 0 && matriz[i][j+1] == 1 ||
                    matriz[i-1][j] == 0 && matriz[i+1][j] == 1 && matriz[i][j-1] == 0 && matriz[i][j+1] == 0 ||
                    matriz[i-1][j] == 1 && matriz[i+1][j] == 0 && matriz[i][j-1] == 0 && matriz[i][j+1] == 1 ||
                    matriz[i-1][j] == 0 && matriz[i+1][j] == 1 && matriz[i][j-1] == 1 && matriz[i][j+1] == 0 ||
                    matriz[i-1][j] == 0 && matriz[i+1][j] == 1 && matriz[i][j-1] == 0 && matriz[i][j+1] == 1 ||
                    matriz[i-1][j] == 1 && matriz[i+1][j] == 0 && matriz[i][j-1] == 1 && matriz[i][j+1] == 0 ||
                    matriz[i-1][j] == 0 && matriz[i+1][j] == 0 && matriz[i][j-1] == 0 && matriz[i][j+1] == 0 ||
                    matriz[i-1][j] == 1 && matriz[i+1][j] == 1 && matriz[i][j-1] == 0 && matriz[i][j+1] == 1 ||
                    matriz[i-1][j] == 1 && matriz[i+1][j] == 1 && matriz[i][j-1] == 1 && matriz[i][j+1] == 0 ||
                    matriz[i-1][j] == 0 && matriz[i+1][j] == 1 && matriz[i][j-1] == 1 && matriz[i][j+1] == 1 ||
                    matriz[i-1][j] == 1 && matriz[i+1][j] == 0 && matriz[i][j-1] == 1 && matriz[i][j+1] == 1)
                {
                      tempPoint = new Point(i,j);
                      estados.add(tempPoint);         
                }
            }
        	celdaActual = entrada; // MOMENTÁNEAMENTE
            return estados;
       }
      
      public static boolean asignarAcciones()
      {
    	  Point estadoAnterior = entrada;
    	  Point estadoActual = entrada;
    	  return asignarAccionesRec(entrada,estadoAnterior,estadoActual);   
      }
      
      
    public static boolean asignarAccionesRec(Point celda,Point estadoAnterior, Point estadoActual){ 
        if(celda.equals(salida)) 
        {
        	int index = acciones.indexOf(estadoActual);
        	if(!acciones.get(index).contains(salida))
        	{
        		acciones.get(index).add(salida);
        	}
        	if(!acciones.get(acciones.size()-1).contains(salida))
        	{
        		acciones.get(acciones.size()-1).add(salida);
        	}
        	
            return true;
        }
        if (acciones.contains(celda))
        {
        	if(!estadoActual.equals(entrada))
        	{
        		estadoAnterior = estadoActual;
        		estadoActual = celda;
        		int indexAnterior = acciones.indexOf(estadoAnterior);
        		if(!acciones.get(indexAnterior).contains(estadoActual))
            	{
        			acciones.get(indexAnterior).add(estadoActual);
            	}
        		
        		int indexActual = acciones.indexOf(estadoActual);
        		if(!acciones.get(indexActual).contains(estadoAnterior))
            	{
        			acciones.get(indexActual).add(estadoAnterior);
            	}
        		
        	}
        	else
        	{
        		estadoActual = celda;
        		int indexActual = acciones.indexOf(estadoActual);
        		if(!acciones.get(indexActual).contains(entrada))
            	{
        			acciones.get(indexActual).add(entrada);	
            	}
        			
        	}
        }
        
        
        // arreglo de todos los movimientos posibles. Se considera cada uno.
        Point[] celdasAdyacentes = getCeldasAdyacentes(celda);
        for (Point movimiento : celdasAdyacentes)
        {
            if(esValido(movimiento)) 
            {	
                //entrarCelda(movimiento);
                celdaActual = movimiento;
            	
                if(asignarAccionesRec(movimiento,estadoAnterior,estadoActual))
                {
                    return true;
                }
                
                // salirCelda(movimiento);
                celdaActual = movimiento;
            }
        }
        return false;	
    }

    private static Point[] getCeldasAdyacentes (Point celda) 
    {
    	Point[] celdasAdyacentes = new Point[4];
    	
    	List<Integer> aleatorizacion = new ArrayList<>();
    	for (int i = 0; i <= 3; i++){
    	    aleatorizacion.add(i);
    	}
    	Collections.shuffle(aleatorizacion);
    	
    	
        celdasAdyacentes[aleatorizacion.get(0)] = new Point(celda.x + 1, celda.y); //abajo
        celdasAdyacentes[aleatorizacion.get(1)] = new Point(celda.x, celda.y + 1); //derecha
        celdasAdyacentes[aleatorizacion.get(2)] = new Point(celda.x - 1, celda.y); //izquierda
        celdasAdyacentes[aleatorizacion.get(3)] = new Point(celda.x, celda.y - 1); //arriba

        return celdasAdyacentes;
    }
    
    private static void rellenarAcciones()
    {
    	for(int i = 0; i < 1000; i++)
    	{
    		if(asignarAcciones())
    		{
    			
    		}
    	}
    	
    }
      
      
    private static boolean esValido (Point celda){
        if ( celda.x < 0 || celda.x >= MAZE.length ||
        celda.y < 0 || celda.y >= MAZE[celda.x].length )
        {
            return false;
        }
        return ( MAZE[celda.x][celda.y] == VACIO || MAZE[celda.x][celda.y] == SALIDA);
    }
    
    public int[][] getMaze()
    {
        return MAZE;
    }
    
    public void display(int matrix[][])
    {
        for(int row = 0; row < matrix.length; row++)
        {
            for(int column = 0; column < matrix[row].length; column++)
            {
                System.out.print(matrix[row][column]+" ");
            }
            System.out.println();
        }
    }
}
