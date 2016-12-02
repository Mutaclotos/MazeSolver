import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
/**
 * Write a description of class Main here.
 * Clase inicialisadora del programa. En esta se cargan las imagenes a ser utilizadas para las animaciones, se establece el movimiento
 * y posicionamiento de los vics así como las interacciones que estos objetos tienen entre ellos. Asimismo, se implementa el uso
 * de la tecla Esc para salir del programa cuando sea deseado y las teclas de flechas para mover al personaje. El laberinto es presentado
 * en pantalla completa.
 * 
 * @author (Matías Rodríguez Singer B15647) 
 * @version (1.0)
 */
public class Main extends JFrame implements KeyListener
{
    /**
     * Metodo principal que llama al metodo run()
     */
    public static void main(String args[])
    {
        Main m = new Main();
        m.run();
    }
    
    public static Maze laberinto;
    private MazeSolver recorrido;
    public static Avatar avatar;
    public static Muro muro[][] = new Muro[laberinto.width][laberinto.height];
    public static Entrada entrada[][] = new Entrada[laberinto.width][laberinto.height];
    public static Salida salida[][] = new Salida[laberinto.width][laberinto.height];
    private Victoria vic;
    private Animacion a;
    private Animacion b;
    private Animacion c;
    private Animacion d;
    private Animacion e;
    private Nivel n;
    private Image fondo1;
    private Image fondo2;
    private Image fondo3;
    private Image muro1;
    private Image avatar1;
    private Image avatar2;
    private Image avatar3;
    private Image avatar4;
    private Image avatar5;
    private Image avatar6;
    private Image avatar7;
    private Image avatar8;
    private Image entrada1;
    private Image salida1;
    private Image vic1;
    private Image vic2;
    private Image vic3;
    private Image vic4;
    private Image vic5;
    private Image vic6;
    private Image vic7;
    private Image vic8;
    private Image vic9;
    public static boolean running;
    public static boolean runRecorrido;
    private int index;
    private int theIndex;
    public static String level;
    public static int numNiveles;
    public static boolean victoria;
    //se establece la velocidad a la que el vic se moverá por la pantalla
    public static float VELOCIDAD = 0.3f;
    //se establecen los diferentes modos de resolución de pantalla disponibles a elegir. El programa se encargará de escojer el apropiado
    //para su computadora
    private static final DisplayMode modes1[] = {
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 24, 0),
        new DisplayMode(800, 600, 16, 0),
        new DisplayMode(640, 480, 32, 0),
        new DisplayMode(640, 480, 24, 0),
        new DisplayMode(640, 480, 16, 0),
    };
    
    
    /**
     * Método que carga las imagenes del personaje a utilizar por el jugador y añade las escenas para las animaciones
     */
    public void cargarAvatar()
    {
        //se carga cada imagen del avatar para su animacion
        avatar1 = new ImageIcon(getClass().getResource("av1.jpg")).getImage();
        avatar2 = new ImageIcon(getClass().getResource("av22.jpg")).getImage();
        avatar3 = new ImageIcon(getClass().getResource("av3.jpg")).getImage();
        avatar4 = new ImageIcon(getClass().getResource("av4.jpg")).getImage();
        avatar5 = new ImageIcon(getClass().getResource("av5.jpg")).getImage();
        avatar6 = new ImageIcon(getClass().getResource("av6.jpg")).getImage();
        avatar7 = new ImageIcon(getClass().getResource("av7.jpg")).getImage();
        avatar8 = new ImageIcon(getClass().getResource("av8.jpg")).getImage();
        
        a = new Animacion();
        a.addScene(avatar1, 250); //se añade cada imagen a la animacion
        a.addScene(avatar2, 250);
        a.addScene(avatar3, 250);
        a.addScene(avatar4, 250);
        a.addScene(avatar5, 250);
        a.addScene(avatar6, 250);
        a.addScene(avatar7, 250);
        a.addScene(avatar8, 250);
        
        for(int i = 0; i < laberinto.width; i++)
        {
            for(int j = 0; j < laberinto.height; j++)
            {
            	//se carga al avatar encima de la entrada
                if(Maze.MAZE[i][j] == 2)
                {
                    avatar = new Avatar(a);
                    avatar.setVelocityX(VELOCIDAD);
                    avatar.setVelocityY(VELOCIDAD); 
                    
                    int x = Math.round(avatar.getX()) + i * avatar.getWidth();
                    int y = Math.round(avatar.getY()) + j * avatar.getHeight();
                     //se establecen las coordenadas iniciales del avatar
                    avatar.setX(x);
                    avatar.setY(y);
                }
            }
        }
    }
    
    /**
     * Metodo que carga la imagen del fondo de pantalla
     */
    public void cargarFondo()
    {
        fondo1 = new ImageIcon(getClass().getResource("bg.jpg")).getImage();
        fondo2 = new ImageIcon(getClass().getResource("bg2.jpg")).getImage();
        fondo3 = new ImageIcon(getClass().getResource("bg3.jpg")).getImage();
    }
    
    /**
     * Metodo que carga la imagen para cada bloque de muro del laberinto, solo si este esta presente en el laberinto de la clase Maze. 
     */
    public void cargarMuro()
    {
        muro1 = new ImageIcon(getClass().getResource("wall1.jpg")).getImage();
        
        b = new Animacion();
        b.addScene(muro1, 250);
        
        for(int i = 0; i < laberinto.width; i++)
        {
            for(int j = 0; j < laberinto.height; j++)
            {
                if(Maze.MAZE[i][j] == 1)
                {
                     muro[i][j] = new Muro(b);
                     muro[i][j].setVelocityX(0); //los bloques de muro, entrada y salida no tienen movimiento
                     muro[i][j].setVelocityY(0);
                     
                     //se coloca cada bloque justo después del anterior, usando sus dimensiones como coordenadas
                     //en la pantalla
                     int x = Math.round(muro[i][j].getX()) + i * muro[i][j].getWidth(); 
                     int y = Math.round(muro[i][j].getY()) + j * muro[i][j].getHeight();
                     
                     muro[i][j].setX(x);
                     muro[i][j].setY(y);
                }
            }
        }
    }
    
    /**
     * Metodo que carga la imagen para cada bloque de entrada del laberinto, solo si este esta presente en el laberinto de la clase Maze.
     */
    public void cargarEntrada()
    {
        entrada1 = new ImageIcon(getClass().getResource("ent1.jpg")).getImage();
        
        c = new Animacion();
        c.addScene(entrada1, 250);
        
        for(int i = 0; i < laberinto.width; i++)
        {
            for(int j = 0; j < laberinto.height; j++)
            {
                if(Maze.MAZE[i][j] == 2)
                {    
                    entrada[i][j] = new Entrada(c);
                    entrada[i][j].setVelocityX(0);
                    entrada[i][j].setVelocityY(0);
                    
                    int x = Math.round(entrada[i][j].getX()) + i * entrada[i][j].getWidth();
                    int y = Math.round(entrada[i][j].getY()) + j * entrada[i][j].getHeight();
                    
                    entrada[i][j].setX(x);
                    entrada[i][j].setY(y);
                }
            }
        }
    }
    
    /**
     * Metodo que carga la imagen para cada bloque de salida del laberinto, solo si este esta presente en el laberinto de la clase Maze.
     */
    public void cargarSalida()
    {
        salida1 = new ImageIcon(getClass().getResource("ex1.jpg")).getImage();
        
        d = new Animacion();
        d.addScene(salida1, 250);
        
        for(int i = 0; i < laberinto.width; i++)
        {
            for(int j = 0; j < laberinto.height; j++)
            {
                if(Maze.MAZE[i][j] == 3)
                {    
                    salida[i][j] = new Salida(d);
                    salida[i][j].setVelocityX(0);
                    salida[i][j].setVelocityY(0);
                    
                    int x = Math.round(salida[i][j].getX()) + i * salida[i][j].getWidth();  
                    int y = Math.round(salida[i][j].getY()) + j * salida[i][j].getHeight()+ j/4; //se añade el j a la coordenada y para compensar 
                                                                                                  //por una desigualdad en las dimensiones
                                                                                                //de la imagen de la salida
                    salida[i][j].setX(x);
                    salida[i][j].setY(y);
                }
            }
        }
    }
    
    /**
     * Método que carga las imagenes del mensaje de victoria y añade las escenas para las animaciones
     */
    public void cargarVictoria()
    {
        vic1 = new ImageIcon(getClass().getResource("vic1.jpg")).getImage();
        vic2 = new ImageIcon(getClass().getResource("vic2.jpg")).getImage();
        vic3 = new ImageIcon(getClass().getResource("vic3.jpg")).getImage();
        vic4 = new ImageIcon(getClass().getResource("vic4.jpg")).getImage();
        vic5 = new ImageIcon(getClass().getResource("vic5.jpg")).getImage();
        vic6 = new ImageIcon(getClass().getResource("vic6.jpg")).getImage();
        vic7 = new ImageIcon(getClass().getResource("vic7.jpg")).getImage();
        vic8 = new ImageIcon(getClass().getResource("vic8.jpg")).getImage();
        vic9 = new ImageIcon(getClass().getResource("vic9.jpg")).getImage();
        
        e = new Animacion();
        e.addScene(vic1, 200);
        e.addScene(vic2, 200);
        e.addScene(vic3, 200);
        e.addScene(vic4, 200);
        e.addScene(vic5, 200);
        e.addScene(vic6, 200);
        e.addScene(vic7, 200);
        e.addScene(vic8, 200);
        e.addScene(vic9, 200);
        
        vic = new Victoria(e);
        vic.setVelocityX(VELOCIDAD);
        vic.setVelocityY(VELOCIDAD); 
                    
        vic.setX(220); //Se establecen las coordenadas iniciales del mensaje
        vic.setY(220);
    }
    
    /**
     * Metodo inicializador
     * 
     */
    public void init()
    {
        n = new Nivel();
        laberinto = new Maze();
        DisplayMode dm = n.findFirstCompatibleMode(modes1);
        n.setFullScreen(dm); //pone el programa en pantalla completa
        Window w = n.getFullScreenWindow();
        w.setFocusTraversalKeysEnabled(false); //evita uso de teclas especiales
        w.addKeyListener(this);
        running = true;
        runRecorrido = true;
        setIndex(4); // inicializa el movimiento del avatar en nulo
        level = "1";
        numNiveles = 1;
        victoria = false;
        recorrido = new MazeSolver();
        recorrido.iniciar(Maze.MAZE);
    }
    
    
    /**
     * Metodo principal llamado desde el main
     */
    public void run()
    {
        try
        {
            init();
            cargarFondo();
            cargarMuro();
            cargarEntrada();
            cargarSalida();
            cargarAvatar();
            cargarVictoria();
            //System.out.println("Laberinto original: ");
            //recorrido.display(Maze.MAZE);
            thread1.start(); //Se inicia el thread de recorrido del laberinto
            movieLoop();
        }finally
        {
            n.restoreScreen(); //devuelve la pantalla al modo normal una vez cerrado el programa
        }
    }
            
    //Thread que se encarga de procesar aparte el proceso de recorrido del laberinto, dandole tiempo al thread Main de refrescar la pantalla con movieLoop().
    Thread thread1 = new Thread()
            {
                public void run()
                {
                    while(runRecorrido)
                    {
                        try
                        {
                            recorrido.resolver();
                            Thread.sleep(500);
                        }
                        catch(Exception ex){}
                    }
                }
            };
    
    /**
     * Metodo que corre la animación entera por tiempo indefinido, hasta que la tecla Esc sea apretada
     */
    public void movieLoop()
    {
        long startingTime = System.currentTimeMillis();
        long cumTime = startingTime;
        
        while(running)
        {
            long timePassed = System.currentTimeMillis() - cumTime;
            cumTime += timePassed;
            
            update(timePassed);
            //dibuja y refresca la pantalla
            Graphics2D g = n.getGraphics();
            
            dibujar(g);
                        
            g.dispose();
            n.update();
            
            try
            {
                Thread.sleep(20); //Pone al thread a dormir por 20 milisegundos, despues refresca la pantalla
            }catch(Exception ex){}
        }
    }
    
    /**
     * Metodo que dibuja los graficos para cada elemento del juego(avatar, muro, fondos, mensajes, entrada y salida)
     */
    public synchronized void dibujar(Graphics g)
    {
        //Cambia el fondo en cada nivel
        if(level == "1")
        {
            g.drawImage(fondo1, -37, -37, null);
        }
        else if(level == "2")
        {
            g.drawImage(fondo2, -37, -37, null);
        }
        else if(level == "3")
        {
            g.drawImage(fondo3, -37, -37, null);
        }
        
        //Dibuja a cada sprite dependiendo de si sus coordenadas coinciden a las del laberinto
         for(int row = 0; row < laberinto.width; row++)
        {
           for(int column = 0; column < laberinto.height; column++)
           {
               if(Maze.MAZE[row][column] == 1) //dibujar solo si el elemento está presente en el laberinto
               {
                   if(muro[row][column] != null)
                   {
                        g.drawImage(muro[row][column].getImage(), Math.round(muro[row][column].getX()), Math.round(muro[row][column].getY()), null);
                        //System.out.println(row + " " + column);
                   }
               }
               else if(Maze.MAZE[row][column] == 2) //el avatar y la entrada son dibujados con las mismas coordenadas
               {
                   if(entrada[row][column] != null)
                   {
                        g.drawImage(entrada[row][column].getImage(), Math.round(entrada[row][column].getX()), Math.round(entrada[row][column].getY()), null);
                   }
                   if(avatar != null)
                   {
                        g.drawImage(avatar.getImage(), Math.round(avatar.getX()), Math.round(avatar.getY()), null);
                   }
               }
               else if(Maze.MAZE[row][column] == 3)
               {
                   if(salida[row][column] != null)
                   {
                        g.drawImage(salida[row][column].getImage(), Math.round(salida[row][column].getX()), Math.round(salida[row][column].getY()), null);
                   }
               }
           }
        }
        g.setColor(Color.WHITE); //para los mensajes arriba de la pantalla
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Esc para salir", 30,30);
        g.drawString("Nivel " + level + "/1", 400,30);
        //despliega el mensaje de victoria
        if(victoria)
        {
            //g.setColor(Color.GREEN);
            //g.setFont(new Font("Arial", Font.PLAIN, 40));
            //g.drawString("VICTORIA", 310,310);
            g.drawImage(vic.getImage(), Math.round(vic.getX()), Math.round(vic.getY()), null);
        }
    }
    
    /**
     * Metodo que refresca los sprites para producir el efecto de movimiento del avatar y del mensaje de victoria
     */
    public void update(long timePassed)
    {  
                if(avatar != null)
                {
                    if(getIndex() == 0)
                    {
                        //Movimiento arriba  
                        avatar.setY(Math.round(avatar.getY()) - avatar.getHeight());

                        //avatar.setVelocityY(-Math.abs(avatar.getVelocityY()));
                        //avatar.setVelocityX(0);
                    }
                    else if(getIndex() == 1)
                    {
                        //Movimiento abajo    
                        avatar.setY(Math.round(avatar.getY()) + avatar.getHeight());
                       
                        //avatar.setVelocityY(Math.abs(avatar.getVelocityY()));
                        //avatar.setVelocityX(0);
                    }
                    else if(getIndex() == 2)
                    {
                        //Movimiento derecha   
                        avatar.setX(Math.round(avatar.getX()) + avatar.getWidth());
                        
                        //avatar.setVelocityX(Math.abs(avatar.getVelocityX()));
                        //avatar.setVelocityY(0);
                    }
                    else if(getIndex() == 3)
                    {
                        //Movimiento izquierda   
                        avatar.setX(Math.round(avatar.getX()) - avatar.getWidth());
                        
                        //avatar.setVelocityX(-Math.abs(avatar.getVelocityX()));
                        //avatar.setVelocityY(0);
                    }
                    else if (getIndex() == 4)
                    {
                        //Movimiento nulo
                        avatar.setVelocityX(0);
                        avatar.setVelocityY(0);
                    }
                    avatar.update(timePassed);
                } 
                
                //Se establece el patron de movimiento del mensaje de victoria, el cual rebotara al colisionar con alguno de los extremos de la pantalla.
                if(vic.getX() < 0)
                {
                    vic.setVelocityX(Math.abs(vic.getVelocityX()));
                }
                else if(vic.getX() + vic.getWidth() >= n.getWidth())
                {
                    vic.setVelocityX(-Math.abs(vic.getVelocityX()));
                }
                
                if(vic.getY() < 0)
                {
                    vic.setVelocityY(Math.abs(vic.getVelocityY()));
                }
                else if(vic.getY() + vic.getHeight() >= n.getHeight())
                {
                    vic.setVelocityY(-Math.abs(vic.getVelocityY()));
                }
                vic.update(timePassed);
    }
    
    /**
     * Metodo que cierra el programa cuando la tecla Esc es precionada. Actualmente tiene un error peculiar en algunas computadoras.
     * Referirse a la documentación externa para más información. Tambien recibe los inputs de las teclas de flechas
     */
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_ESCAPE)
        {
            stop();
        }
        else
        {
            e.consume();
        }
    }

    public void setIndex(int i)
    {
        theIndex = i;
    }
    
    public int getIndex()
    {
        return theIndex;
    }
    
    /**
     * Override del metodo keyReleased. Cuando una tecla deja de ser apretada, el avatar se inmoviliza
     */
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        
        if(keyCode == KeyEvent.VK_UP)
        {
            setIndex(4);
            e.consume();
        }
        else if(keyCode == KeyEvent.VK_DOWN)
        {
            setIndex(4);
            e.consume();
        }
        else if(keyCode == KeyEvent.VK_RIGHT)
        {
            setIndex(4);
            e.consume();
        }
        else if(keyCode == KeyEvent.VK_LEFT)
        {
            setIndex(4);
            e.consume();
        }
        else
        {
            e.consume();
        }
    }

    //override del metodo keyTyped
    public void keyTyped(KeyEvent e)
    {
        e.consume();
    }
    
    /**
     * Metodo stop que detiene el programa cuando es activado
     */
    public void stop()
    {
        running = false;
        runRecorrido = false;
    }
}
