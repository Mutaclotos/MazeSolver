
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
    public static int height = 8; // Se recomienta que las dimensiones sean impares.
    public static int width = 11;
    public static int[][] MAZE = {{1,1,1,1,1,1,1,1,1,1,1},
    							  {1,0,0,0,1,0,1,0,0,0,1},
    							  {1,0,1,0,1,0,1,0,1,1,1},
    							  {1,0,1,1,1,0,0,0,1,0,1},
    							  {1,0,0,0,0,0,1,0,0,0,1},
    							  {1,0,1,0,1,1,1,0,1,1,1},
    							  {1,0,1,0,1,0,0,0,0,0,1},
    							  {1,1,1,1,1,1,1,1,1,1,1}};//  = new int[width][height];
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
    
    
    /**
     * Método que crea una matriz llena de muro y define el bloque inicial a ser "escarbado" para crear el camino
     */
    public int[][] generarMaze() 
     {
        for (int i = 0; i < MAZE.length; i++)
        {
            for (int j = 0; j < MAZE[i].length; j++)
            {
                MAZE[i][j] = 1;
            }
        }
        Random random = new Random();
         // f para fila, c para columna
         // Genera una fila aleatoria como coordenada del bloque inicial
        int f = random.nextInt(width);
        while(f == 0 || f == width - 1 || f%2 == 0)//si este bloque se encuentra en uno de los muros externos, 
                                                   //o una de sus coordenadas es par, volver a generar una coordenada
        {
            f = random.nextInt(width);
        }
         // Genera una columna aleatoria como coordenada del bloque inicial
        int c = random.nextInt(height);
        while(c == 0 || c == height - 1 || c%2 == 0)
        {
            c = random.nextInt(height);
        }
         // Celda inicial
        MAZE[f][c] = 2;
        
         //　Genera el laberinto con el metodo recursivo
        crearCamino(f, c);
     
        return MAZE;
     }
    
    /**
     * Metodo que mediante un llamado recursivo genera un camino de 0's por la matriz de
     * 1's. Se escoge al azar la direccion siguiente a tomar ara el camino desde una celda respectiva.
     * Se verifica en cada llamado si el movimiento es valido. En caso contrario, se intenta de nuevo
     * con otro movimiento. El algoritmo termina cuando ya no hay mas opciones para crear camino.
     */
    public void crearCamino(int f, int c) 
    {
         // 4 direcciones aleatorias
         Integer[] randomDireccion = generarDirecciones();
         // Se examina cada direccion. 
         for (int i = 0; i < randomDireccion.length; i++)
         {
             switch(randomDireccion[i])
             {
                 case 1: // Arriba. Para cada direccion se examina si dos posiciones mas allá se alcanza 
                         //el limite del laberinto o un espacio vacio de camino.
                     if (f - 2 == 0)
                     {
                         continue;
                     }
                     if (caminoValido(f - 2, c)) //se comprueba si se puede generar camino en dicha celda
                     {
                         MAZE[f-2][c] = 0; //se reemplazan ambos 1's con 0's para crear el camino
                         MAZE[f-1][c] = 0;
                         crearCamino(f - 2, c); //llamada recursiva para repetir el metodo hasta que no haya mas espacio disponible
                     }
                     else
                     {
                         continue;
                     }
                 break;
                 case 2: // Derecha
                     if (c + 2 == width - 1)
                     {
                         continue;
                     }     
                     if (caminoValido(f, c + 2))
                     {
                         MAZE[f][c + 2] = 0;
                         MAZE[f][c + 1] = 0;
                         crearCamino(f, c + 2);
                     }
                     else
                     {
                         continue;
                     }
                 break;
                 case 3: // Abajo
                     if (f + 2 == height - 1)
                     {
                         continue;
                     }
                     if (caminoValido(f + 2, c))
                     {
                             MAZE[f+2][c] = 0;
                             MAZE[f+1][c] = 0;
                             crearCamino(f + 2, c);
                     }
                     else
                     {
                         continue;
                     }
                 break;
                 case 4: // Izquierda
                     if (c - 2 == 0)
                     {
                         continue;
                     }
                     if (caminoValido(f, c - 2))
                     {
                         MAZE[f][c - 2] = 0;
                         MAZE[f][c - 1] = 0;
                         crearCamino(f, c - 2);
                     }
                     else
                     {
                         continue;
                     }
                 break;
             }
         }
     }
     
     /**
     * Genera un arreglo con direcciones aleatorias de 1 a 4. 
     * Retorna un arreglo con las direcciones en orden aleatorio.
     */
    public Integer[] generarDirecciones() 
    {
         ArrayList<Integer> randomDir = new ArrayList<Integer>();
         for (int i = 0; i < 4; i++)
         {
              randomDir.add(i + 1);
         }
         Collections.shuffle(randomDir);
     
         return randomDir.toArray(new Integer[4]);
    }
    
    
     /**
     * Verifica que la fila y columna de la celda se encuentren dentro del laberinto y que la celda a escarbar
     *no esté ya vacia.
     */
    private boolean caminoValido(int row, int col) 
    {
        if (row >= 0 && row < width && col >= 0 && col < height && !(this.MAZE[row][col] == 0)) 
        {
            return true;
        }
        return false;
    }
    
    //Metodo que inserta la Salida, examinando la presencia de caminos sin salida
    public int[][] insertarSalida(int matrix[][])
    {
       Random randomSalida = new Random();
       int counter = 0;
       outerloop:
       do
       {
           for(int f = 0; f < MAZE.length; f++)
           {
               for(int c = 0; c < MAZE[f].length; c++)
               {
                   boolean haySalida = randomSalida.nextBoolean();
                   //Se examina para cada celda del laberinto si se encuentra con un camino sin salida
                   //(espacio vacio con 3 muros). Si se da el caso, hay una posibilidad de 1/2 de que esa
                   //casilla se convierta en una salida. Esto con el fin de aleatorizar más la posicion de cada salida
                   if(matrix[f][c] == 0 && MAZE[f-1][c] == 1 && MAZE[f][c + 1] == 1 && MAZE[f+1][c] == 1)
                   {
                       //UP, RIGHT, DOWN
                       if(haySalida)
                       {
                           matrix[f][c] = 3; //se crea una salida
                           counter++;
                           break outerloop; //se rompe con ambos loops
                       }
                   }
                   else if(matrix[f][c] == 0 && MAZE[f][c + 1] == 1 && MAZE[f+1][c] == 1 && MAZE[f][c - 1] == 1)
                   {
                       //RIGHT, DOWN, LEFT
                       if(haySalida)
                       {
                           matrix[f][c] = 3;
                           counter++;
                           break outerloop;
                       }
                   }
                   else if(matrix[f][c] == 0 && MAZE[f+1][c] == 1 && MAZE[f][c - 1] == 1 && MAZE[f-1][c] == 1)
                   {
                       //DOWN, LEFT, UP
                       if(haySalida)
                       {
                           matrix[f][c] = 3;
                           counter++;
                           break outerloop;
                       }
                   }
                   else if(matrix[f][c] == 0 && MAZE[f][c - 1] == 1 && MAZE[f-1][c] == 1 && MAZE[f][c + 1] == 1)
                   {
                       //LEFT, UP, RIGHT
                       if(haySalida)
                       {
                           matrix[f][c] = 3;
                           counter++;
                           break outerloop;
                       }
                   }
               }
           }
       }while(counter == 0);
       return matrix;
    }
    
    //Metodo similar a insertarSalida(), solo que crea una entrada en el primer camino sin salida que encuantre
    public int[][] insertarEntrada(int matrix[][])
    {
       outerloop:
        for(int f = 0; f < MAZE.length; f++)
       {
           for(int c = 0; c < MAZE[f].length; c++)
           {
               if(matrix[f][c] == 0 && MAZE[f-1][c] == 1 && MAZE[f][c + 1] == 1 && MAZE[f+1][c] == 1)
               {
                   //UP, RIGHT, DOWN
                   matrix[f][c] = 2;
                   break outerloop;
               }
               else if(matrix[f][c] == 0 && MAZE[f][c + 1] == 1 && MAZE[f+1][c] == 1 && MAZE[f][c - 1] == 1)
               {
                   //RIGHT, DOWN, LEFT
                   matrix[f][c] = 2;
                   break outerloop;
               }
               else if(matrix[f][c] == 0 && MAZE[f+1][c] == 1 && MAZE[f][c - 1] == 1 && MAZE[f-1][c] == 1)
               {
                   //DOWN, LEFT, UP
                   matrix[f][c] = 2;
                   break outerloop;
               }
               else if(matrix[f][c] == 0 && MAZE[f][c - 1] == 1 && MAZE[f-1][c] == 1 && MAZE[f][c + 1] == 1)
               {
                   //LEFT, UP, RIGHT
                   matrix[f][c] = 2;
                   break outerloop;
               }
           }
       }
       return matrix;
    }
    
    //Metodo para desplegar en la consola el laberinto en codigo de numeros
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
    
    public int[][] getMaze()
    {
        return MAZE;
    }
}
