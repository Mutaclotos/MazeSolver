import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
/**
 * Clase utilizada para entrar en el modo de pantalla completa. La utilización de BufferStrategy mejora la calidad de las animaciones
 * considerablemente. Se comparan las distintas resoluciones disponibles con las resoluciones del monitor para determinar la 
 * resolución optima para la computadora en la que se corre el programa. Esto mejora el rendimiento.
 * 
 * @author (Matías Rodríguez Singer B15647) 
 * @version (1.0)
 */
public class Nivel
{
    private GraphicsDevice vc;
    
    //Constructor
    //le da acceso a la tarjeta grafica al monitor
    public Nivel()
    {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = e.getDefaultScreenDevice();
    }
    
    //retorna todos los modos de resolución compatibles para la tarjeta grafica
    public DisplayMode[] getCompatibleDisplayModes()
    {
        return vc.getDisplayModes();
    }
    
    //compara la resolución existente con las resoluciones disponibles para determinar cual es la óptima
    public DisplayMode findFirstCompatibleMode(DisplayMode modes[])
    {
        DisplayMode goodModes[] = vc.getDisplayModes();
        for(int x = 0; x < modes.length; x++)
        {
            for(int y = 0; y < goodModes.length; y++)
            {
                if(displayModesMatch(modes[x], goodModes[y]))
                {
                    return modes[x];
                }
            }
        }
        return null;
    }
    
    //retorna la resolucion actual
    public DisplayMode getCurrentDisplayMode()
    {
        return vc.getDisplayMode();
    }
    
    //verifica si los dos modos son congruentes
    public boolean displayModesMatch(DisplayMode m1, DisplayMode m2)
    {
        if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight())
        {
            return false;
        }
        if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth())
        {
            return false;
        }
        if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate())
        {
            return false;
        }
        return true;
    }
    
    //transforma el lienzo a pantalla completa
    public void setFullScreen(DisplayMode dm)
    {
        JFrame f = new JFrame();
        f.setUndecorated(true);
        f.setIgnoreRepaint(true);
        f.setResizable(false);
        vc.setFullScreenWindow(f);
        
        if(dm != null && vc.isDisplayChangeSupported())
        {
            try
            {
                vc.setDisplayMode(dm);
            }catch(Exception ex){}
        }
        f.createBufferStrategy(2); //este metodo mejora la calidad de las animaciones considerablemente,
        //efectivamente eliminando el parpadeo de las imagenes
    }
    
    //el objeto grafico es establecido con el BufferStrategy
    public Graphics2D getGraphics()
    {
        Window w = vc.getFullScreenWindow();
        if(w != null)
        {
            BufferStrategy s = w.getBufferStrategy();
            return (Graphics2D)s.getDrawGraphics();
        }
        else
        {
            return null;
        }
    }
    
    //refresca la muestra de pantalla
    public void update()
    {
        Window w = vc.getFullScreenWindow();
        if(w != null)
        {
            BufferStrategy s = w.getBufferStrategy();
            if(!s.contentsLost())
            {
                s.show();
            }
        }
    }
    
    //retorna la pantalla completa
    public Window getFullScreenWindow()
    {
        return vc.getFullScreenWindow();
    }
    
    //retorna el ancho de pantalla
    public int getWidth()
    {
        Window w = vc.getFullScreenWindow();
        if(w != null)
        {
            return w.getWidth();
        }
        else
        {
            return 0;
        }
    }
    
    //retorna el largo de pantalla
    public int getHeight()
    {
        Window w = vc.getFullScreenWindow();
        if(w != null)
        {
            return w.getHeight();
        }
        else
        {
            return 0;
        }
    }
    
    //salir de pantalla completa
    public void restoreScreen()
    {
        Window w = vc.getFullScreenWindow();
        if(w != null)
        {
            w.dispose();
        }
        vc.setFullScreenWindow(null);
    }
    
    //crea imagen compatible con el monitor
    public BufferedImage createCompatibleImage(int w, int h, int t)
    {
        Window win = vc.getFullScreenWindow();
        if(win != null)
        {
            GraphicsConfiguration gc = win.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, t);
        }
        return null;
    }
}