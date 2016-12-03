import java.awt.Point;
/**
 * MazeSolver
 *
 *Clase que utiliza un método recursivo con backtracking para encontrar el camino
 *desde la entrada hasta la salida del laberinto.
 *
 *@author (Matías Rodríguez Singer B15647) 
 *@version (1.0)
 */
public class MazeSolver
{
    public static int[][] maze;
    Main main = new Main();
    QLearning qlearning = new QLearning();
    // Coordenadas de la entrada y la salida
    private Point inicio;
    private Point fin;
    
    private int counter = 0;

    // Constantes que forman el laberinto
    public static final int MURO = 1;
    public static final int VACIO = 0;
    public static final int CAMINO = 4;
    public static final int ENTRADA = 2;
    public static final int SALIDA = 3;

    /**
     * Método que cumple la funcion de un constructor, detectando al laberinto, su entrada y su salida.
     */
    public void iniciar(int[][] laberinto) 
    {
        maze = laberinto;
        //inicio = getInicio();
        //fin = getFinal();
        inicio = Maze.entrada;
        fin = Maze.salida;
    }

    
    /**
     * Método que inicializa la primera llamada al metodo encontrar camino. 
     * Reporta el exito o fracaso en encontrar un camino por el laberinto. 
     * Ademas borra el laberinto actual de la pantalla cuando se reporta un exito
     * y crea uno nuevo, para desplegarlo nuevamente. Antes de generar el cuarto laberinto, 
     * se declara la victoria y se finaliza el programa.
     */
    public void resolver()
    {
    	//Se entrena al agente
    	
    	System.out.println("Empezando recorrido.");
    	qlearning.run();
    	qlearning.showPolicy();
        //counter++;
        /*if(encontrarCamino(inicio))
        {
			//System.out.println("Laberinto resuelto");
            //display(maze);
            while(counter <= 3) //Mientras no se hayan recorrido 3 niveles, continuar ejecutando
            {
                if(counter == 1)
                {
                    Main.level = "2";
                }
                else if(counter == 2)
                {
                     Main.level = "3";
                }
                else if(counter == 3)
                {
                    //System.out.println("Victoria!");
                    Main.victoria = true; //Se despliega el mensaje de victoria
                    Main.runRecorrido = false; //Se termina el proceso del thread1
                    break;
                }
                //System.out.println("Nuevo laberinto "+counter);
                
                //Borra los elementos del nivel anterior para generar uno nuevo
                for(int i = 0; i < Main.laberinto.width; i++)
                {
                    for(int j = 0; j < Main.laberinto.height; j++)
                    {
                        if(Maze.MAZE[i][j] == 1)
                        {    
                            Main.muro[i][j] = null;
                        }
                        if(Maze.MAZE[i][j] == 2)
                        {    
                            Main.entrada[i][j] = null;
                        }
                        if(Maze.MAZE[i][j] == 3)
                        {    
                            Main.salida[i][j] = null;
                        }
                    }
                }
                Main.avatar = null; //Borra al avatar anterior
        		Main.laberinto = null;
                Main.laberinto = new Maze(); //Crea un nuevo laberinto, y lo llena de todos sus elementos
                main.cargarMuro();
                main.cargarEntrada();
                main.cargarSalida();
                main.cargarAvatar();
                main.cargarVictoria();
                iniciar(Maze.MAZE);
                //display(Maze.MAZE);
                
                resolver(); //Se llama al metodo resolver nuevamente para encontrar la solucion de este nuevo laberinto
            }*/
    		encontrarCamino(inicio);
    	
        	System.out.println("Recorrido terminado.");
            Main.victoria = true; //Se despliega el mensaje de victoria
            Main.runRecorrido = false; //Se termina el proceso del thread1
        
    }

    /**
     * 
     * Método recursivo por backtracking del programa. Desde la celda actual, intenta
     * ir arriba, abajo, derecha o izquierda a la siguiente celda. Si el movimiento es
     * valido, lo ejecuta y hace el llamado recursivo para el siguiente movimiento. En
     * caso contrario, se produce el backtracking. En el caso que no hay movimientos 
     * validos, se retorna false y se generan nuevas desiciones desde el movimineto 
     * anterior. El algoritmo termina cuando se llega a la salida.
     * 
     */
    private void encontrarCamino(Point celda) 
    {
        /*// Si se encontró la salida, retorna true
        if(labTerminado(celda)) 
        {
            maze[celda.x][celda.y] = SALIDA;
            return true;
        }

        // arreglo de todos los movimientos posibles. Se considera cada uno.
        Point[] celdasAdyacentes = getCeldasAdyacentes(celda);

        for (Point movimiento : celdasAdyacentes)
        {
            if(esValido(movimiento)) 
            {
                entrarCelda(movimiento);

                esperar();
                
                if(encontrarCamino(movimiento))
                {
                    return true;
                }
                
                salirCelda(movimiento);

                esperar();
            }
        }*/

        //Si no hay movimientos legales desde esta celda, o todos los movimientos
        //intentados han conducido a backtracks, se retorna false para comunicarle 
        //a la llamada de la celda anterior que un camino diferente debe ser tomado. 
    	
        
        Point celdaActual = celda;
        //System.out.println("Policy desde A a C: "+ qlearning.policy(qlearning.stateA));
        System.out.println("Inicio en entrada con coordenadas: " + celda);
        Point celdaSiguiente;
        while(!celdaActual.equals(fin))
        {
        	//System.out.println("Celda actual: " + celdaActual);
        	celdaSiguiente = qlearning.policy(celdaActual);
        	//System.out.println("Policy celda siguiente: " + qlearning.policy(celdaActual));
        	
        	//Si la siguiente celda del recorrido esta mas arriba que la celdaActual
        	if(celdaSiguiente.y < celdaActual.y)
        	{
        		while(!celdaActual.equals(celdaSiguiente))
        		{
        			//mover arriba una posicion
        			celdaActual = getCeldaArriba(celdaActual);
        			System.out.println("Moviendo arriba.");
        			esperar();
        		}
        	}
        	//Si la siguiente celda del recorrido esta mas a la derecha que la celdaActual
        	else if(celdaSiguiente.x > celdaActual.x)
        	{
        		while(!celdaActual.equals(celdaSiguiente))
        		{
        			//mover derecha una posicion
        			celdaActual = getCeldaDerecha(celdaActual);
        			System.out.println("Moviendo derecha.");
        			esperar();
        		}
        	}
        	//Si la siguiente celda del recorrido esta mas abajo que la celdaActual
        	else if(celdaSiguiente.y > celdaActual.y)
        	{
        		while(!celdaActual.equals(celdaSiguiente))
        		{
        			//mover abajo una posicion
        			celdaActual = getCeldaAbajo(celdaActual);
        			System.out.println("Moviendo abajo.");
        			esperar();
        		}
        	}
        	//Si la siguiente celda del recorrido esta mas a la izquierda que la celdaActual
        	else if(celdaSiguiente.x < celdaActual.x)
        	{
        		while(!celdaActual.equals(celdaSiguiente))
        		{
        			//mover izquierda una posicion
        			celdaActual = getCeldaIzquierda(celdaActual);
        			System.out.println("Moviendo izquierda.");
        			esperar();
        		}
        	}
        }
        System.out.println("Recorrido terminado.");
        //return true;
    }

    /**
     * Método que detiene al Thread por un periodo de tiempo, con el fin de poder observar el movimiento 
     * del avatar.
     */
    private void esperar() 
    {
        try 
        {
            Thread.sleep(300);
        } catch(InterruptedException e) {}
    }
    
    /**
     * Método que retorna un arreglo conteniendo las coordenadas de todas las
     * celdas a las que se puede mover desde la celda actual.
     */
    private Point[] getCeldasAdyacentes (Point celda) 
    {
        Point[] celdasAdyacentes = new Point[4];

        celdasAdyacentes[0] = new Point(celda.x + 1, celda.y); //abajo
        celdasAdyacentes[1] = new Point(celda.x, celda.y + 1); //derecha
        celdasAdyacentes[2] = new Point(celda.x - 1, celda.y); //izquierda
        celdasAdyacentes[3] = new Point(celda.x, celda.y - 1); //arriba

        return celdasAdyacentes;
    }
    
    private Point getCeldaArriba(Point celda)
    {
    	Point arriba = new Point(celda.x, celda.y - 1);
    	entrarCelda(arriba);
    	return arriba;
    }
    
    private Point getCeldaAbajo(Point celda)
    {
    	Point abajo = new Point(celda.x, celda.y + 1);
    	entrarCelda(abajo);
    	return abajo;
    }
    
    private Point getCeldaDerecha(Point celda)
    {
    	Point derecha = new Point(celda.x + 1, celda.y);
    	entrarCelda(derecha);
    	return derecha;
    }
    
    private Point getCeldaIzquierda(Point celda)
    {
    	Point izquierda = new Point(celda.x - 1, celda.y);
    	entrarCelda(izquierda);
    	return izquierda;
    }

    /**
     * Método que verifica si un movimiento es valido. Si la celda a la que se pretende 
     * pasar esta fuera del laberinto, se retorna false.
     */
    private boolean esValido (Point celda)
    {
        if ( celda.x < 0 || celda.x >= maze.length ||
        celda.y < 0 || celda.y >= maze[celda.x].length )
        {
            return false;
        }
        return ( maze[celda.x][celda.y] == VACIO || maze[celda.x][celda.y] == SALIDA);
    }

    /**
     * Método que añade una celda de CAMINO al laberinto, para marcar el recorrido del
     * avatar. Ademas refresca la posicion del sprite del avatar en pantalla.
     */
    private void entrarCelda (Point celda) 
    {
        maze[celda.x][celda.y] = CAMINO; 
        Main.avatar.setX(celda.x * Main.avatar.getWidth()); 
        Main.avatar.setY(celda.y * Main.avatar.getHeight());
    }

    /**
     * Método que remueve una celda del camino reemplazandola con VACIO.
     * Ademas refresca la posicion del sprite del avatar en pantalla.
     */
    private void salirCelda(Point celda)
    {
        //maze[celda.x][celda.y] = VACIO;
        Main.avatar.setX(celda.x * Main.avatar.getWidth());
        Main.avatar.setY(celda.y * Main.avatar.getHeight());
    }

    /**
     * Método que identifica si la celda a la que se ha entrado es la salida
     */
    private boolean labTerminado (Point celda) 
    {
        return celda.equals(fin);
    }

    /**
     * Método que localiza y da las coordenadas del inicio del laberinto
     */
    private Point getInicio()
    {
        Point inicio = encontrarCelda(ENTRADA);
        if (inicio == null)
        {
            throw new IllegalStateException("El laberinto no tiene inicio!");
        }
        return inicio;
    }

    /**
     * Método que localiza y da las coordenadas del final del laberinto
     */
    private Point getFinal()
    {
        Point fin = encontrarCelda(SALIDA);
        if (fin == null)
        {
            throw new IllegalStateException("El laberinto no tiene final!");
        }
        return fin;
    }

    /**
     * Método que retorna las coordenadas de la primera celda conteniendo un valor 
     * determinado.
     */
    private Point encontrarCelda(int c) 
    {
        for (int i = 0; i < maze.length; i++) 
        {
            for (int j = 0; j < maze[i].length; j++) 
            {
                if (maze[i][j] == c) 
                {
                    return new Point(i, j);
                }
            }
        }        
        return null;
    }

    /**
     * Método que despliega en la consola el laberinto
     */
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

    
    /**
     * Método main. Genera un nuevo laberinto y llama al metodo resolver, 
     * para desplegar el laberinto original y el laberinto resuelto.
     */
    public static void main (String[] args)
    {
        Maze maze = new Maze();

        MazeSolver solucion = new MazeSolver();
        solucion.iniciar(Maze.MAZE);
        System.out.println("Laberinto original: ");
        solucion.display(Maze.MAZE);

        solucion.resolver();
    }
}

