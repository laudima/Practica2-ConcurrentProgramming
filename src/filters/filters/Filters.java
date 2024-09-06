package filters;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Filters {

    public static final String IMG_PATH = "C:\\Users\\lr255029\\OneDrive - Teradata\\Documents\\Escuela\\Semestre7\\Concurrente\\Practica2\\Practica2-ConcurrentProgramming\\src\\filters\\image\\";

    /**
     * Metodo que carga una imagen
     * @param ruta La ruta de la imagen
     * @return La Imagen en un BufferedImage
     * @throws IOException 
     */
    public BufferedImage leeImagen(String ruta) throws IOException{
        try{
            InputStream input = new FileInputStream(ruta);
            ImageInputStream imageInput = ImageIO.createImageInputStream(input);
            return ImageIO.read(imageInput);
        }catch(FileNotFoundException e){
            System.out.println("No se encontro el archivo");
            return null;
        }
    }

    /**
     * Metodo que guarda una imagen en archivo png
     * @param imagen La imagen
     * @param nombre El nombre del archivo a guardar
     * @throws IOException 
     */
    public static void guardaImagen(BufferedImage imagen, String nombre) throws IOException{
        ImageIO.write(imagen,"jpeg",new File(nombre));
    }

    /**
     * Returns a {@link BufferedImage} with the specified image type, where the
     * graphical content is a copy of the specified image.
     * 
     * @param img    The image to copy.
     * @param imageType  The image type for the image to return.
     * @return      A copy of the specified image.
     */
    public static BufferedImage copia(BufferedImage img, int imageType){
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, imageType);
        Graphics g = newImage.createGraphics();

        g.drawImage(img, 0, 0, null);

        g.dispose();

        return newImage;
    }

    public static BufferedImage averaging(BufferedImage img, int row){
        BufferedImage copy = copia(img, BufferedImage.TYPE_INT_RGB);
        int width = copy.getWidth();

        for(int x =0; x< width; ++x){
            int pixel = img.getRGB(x, row);
            Color c = new Color(pixel);
            int gray = (c.getRed() + c.getGreen() + c.getBlue())/3;
            copy.setRGB(x, row, new Color(gray, gray, gray).getRGB());
        } 
        return copy; 
    }
    

    /**
     * Metodo que aplica el Metodo de blur usando una matriz 3x3 a una imagen
     * @param imagen La imagnen con la que se trabajara
     * @return La imagen con el filtro aplicado
     * @throws IOException 
     */
    public static BufferedImage blur1(BufferedImage imagen, int renglon) throws IOException {
        BufferedImage copia = copia(imagen,BufferedImage.TYPE_INT_RGB);
         
         int alto = copia.getHeight();//n
         int ancho = copia.getWidth();//m
         
         double [][] blur= {
             {0.0,0.2,0.0},
             {0.2,0.2,0.2},
             {0.0,0.2,0.0}
         };
         for(int x = 0; x <ancho-1; ++x){
             
                 double rojo =0;
                 double verde = 0;
                 double azul = 0;
                 
                 //Pixel1 (-1,-1)
 
                 int pixel = imagen.getRGB(((x-1)%ancho<0)?ancho-1:x-1,((renglon-1)%alto<0)?alto-1:renglon-1);
                 Color c = new Color(pixel);
                 rojo += c.getRed()* blur[0][0];
                 verde += c.getGreen()* blur[0][0];
                 azul += c.getBlue()* blur[0][0];
                 
                 //Pixel2 (0,-1)
                 pixel = imagen.getRGB(x%ancho,((renglon-1)%alto<0)?alto-1:renglon-1);
                 c = new Color(pixel);
                 rojo += c.getRed() * blur[0][1];
                 verde += c.getGreen() * blur[0][1];
                 azul += c.getBlue()* blur[0][1];
                 
                 //Pixel3 (1,-1)
                 pixel = imagen.getRGB((x+1)%ancho,((renglon-1)%alto<0)?alto-1:renglon-1);
                 c = new Color(pixel);
                 rojo += c.getRed()* blur[0][2];
                 verde += c.getGreen()* blur[0][2];
                 azul += c.getBlue()* blur[0][2];
                 
                 //Pixel4 (-1,0)
                 pixel = imagen.getRGB(((x-1)%ancho<0)?ancho-1:x-1,renglon%alto);
                 c = new Color(pixel);
                 rojo += c.getRed()* blur[1][0];
                 verde += c.getGreen()* blur[1][0];
                 azul += c.getBlue()* blur[1][0];
                 
                 //Pixel5 (0,0) Centro
                 pixel = imagen.getRGB(x,renglon);
                 c = new Color(pixel);
                 rojo += c.getRed()* blur[1][1];
                 verde += c.getGreen()* blur[1][1];
                 azul += c.getBlue()* blur[1][1];
                 
                 //Pixel6 (1,0)
                 pixel = imagen.getRGB((x+1)%ancho,renglon%alto);
                 c = new Color(pixel);
                 rojo += c.getRed()* blur[1][2];
                 verde += c.getGreen() * blur[1][2];
                 azul += c.getBlue()* blur[1][2];
                 
                 //Pixel7 (-1,1)
                 pixel = imagen.getRGB(((x-1)%ancho<0)?ancho-1:x-1,(renglon+1)%alto);
                 c = new Color(pixel);
                 rojo += c.getRed() * blur[2][0];
                 verde += c.getGreen()* blur[2][0];
                 azul += c.getBlue()* blur[2][0];
                 
                 //Pixel8 (0,1)
                 pixel = imagen.getRGB(x%ancho,(renglon+1)%alto);
                 c = new Color(pixel);
                 rojo += c.getRed()* blur[2][1];
                 verde += c.getGreen()* blur[2][1];
                 azul += c.getBlue()* blur[2][1];
                 
                 //Pixel9 (1,1)
                 pixel = imagen.getRGB((x+1)%ancho,(renglon+1)%alto);
                 c = new Color(pixel);
                 rojo += c.getRed()* blur[2][2];
                 verde += c.getGreen()* blur[2][2];
                 azul += c.getBlue()* blur[2][2];
                 
                 //rojo /= 5;
                 //verde /= 5;
                 //azul /= 5;
                 
                 //System.out.println("r: "+rojo+"v: "+verde+"a: "+azul);
                 copia.setRGB(x, renglon, new Color((int)rojo,(int)verde,(int)azul).getRGB());
             
         }
         guardaImagen(copia, IMG_PATH+"klimtBlur.jpeg");
         return copia;
     }

}
