import java.awt.Image;

/**
 * Clase que define los atributos del sprite/imagen utilizada para generar el avatar utilizado por el jugador. 
 * Se retornan todos los valores necesarios para calcular las dimensiones de cada sprite y su movimiento.
 * 
 * @author (Mat�as Rodr�guez Singer B15647) 
 * @version (1.0)
 */
public class Avatar
{
   Main main = new Main();
   private Animacion a;
   private float x;
   private float y;
   private float vx;
   private float vy;
   
   
   //Constructor
   public Avatar(Animacion a)
   {
       this.a = a;
   }
   //cambia la posici�n del avatar mediante la formula de distancia = velocidad * tiempo
   public void update(long timePassed)
   {
       x += vx * timePassed;
       y += vy * timePassed;
       a.update(timePassed);
   }
   
   public float getX()
   {
       return x;
   }
   
   public float getY()
   {
       return y;
   }
   //establece la posicion x del sprite
   public void setX(float x)
   {
       this.x = x;
   }
   //establece la posicion y del sprite
   public void setY(float y)
   {
       this.y = y;
   }
   
   //retorna el ancho del sprite
   public int getWidth()
   {
       return a.getImage().getWidth(null);
   }
   
   //eretorna el largo del sprite
   public int getHeight()
   {
       return a.getImage().getHeight(null);
   }
   
   //retorna velocidad horizontal
   public float getVelocityX()
   {
       if(vx == 0)
       {
           vx = main.VELOCIDAD;
       }
       return vx;
   }
   //retorna velocidad vertical
   public float getVelocityY()
   {
       if(vy == 0)
       {
           vy = main.VELOCIDAD;
       }
       return vy;
   }
   
   //establece velocidad horizontal
   public void setVelocityX(float vx)
   {
       this.vx = vx;
   }
   //establece velocidad vertical
   public void setVelocityY(float vy)
   {
       this.vy = vy;
   }
   //establece la animaci�n para el sprite
   public void setAnimation(Animacion a)
   {
       this.a = a;
   }
   
   //retorna el sprite/imagen
   public Image getImage()
   {
       return a.getImage();
   }
}
