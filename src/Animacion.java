import java.awt.Image;
import java.util.ArrayList;

/**
 * Clase que permite la animación de los sprites utilizados en el programa. Se genera un ArrayList con imagenes(escenas) que cambian
 * cada determinada cantidad de tiempo. Se otorga una cantidad indefinidad de escenas por sprite y la clase permite que la animación
 * corra de manera independiente al movimiento del sprite.
 * 
 * @author (Matías Rodríguez Singer B15647) 
 * @version (1.0)
 */
public class Animacion
{
    private ArrayList scenes;
    private int sceneIndex;
    private long movieTime;
    private long totalTime;
    //Constructor
    public Animacion()
    {
        scenes = new ArrayList();
        totalTime = 0;
        start();
    }
    
    //añade escena al ArrayList y establece el tiempo para cada escena
    public synchronized void addScene(Image i, long t)
    {
        totalTime += t;
        scenes.add(new OneScene(i, totalTime));
    }
    
    //empieza la animación desde el inicio
    public synchronized void start()
    {
        movieTime = 0;
        sceneIndex = 0;
    }
    
    //cambia las escenas
    public synchronized void update(long timePassed)
    {
        if(scenes.size() > 1)
        {
            movieTime += timePassed;
            if(movieTime >= totalTime)
            {
                movieTime = 0;
                sceneIndex = 0;
            }
            while(movieTime > getScene(sceneIndex).endTime)
            {
                sceneIndex++;
            }
        }
    }
    
    //retorna la escena actual de la animación
    public synchronized Image getImage()
    {
        if(scenes.size() == 0)
        {
            return null;
        }
        else
        {
            return getScene(sceneIndex).pic;
        }
    }
    
    //retorna la escena
    private OneScene getScene(int x)
    {
        return (OneScene)scenes.get(x);
    }
    
    private class OneScene 
    {
        Image pic;
        long endTime;
        
        public OneScene(Image pic, long endTime)
        {
            this.pic = pic;
            this.endTime = endTime;
        }
    }
}