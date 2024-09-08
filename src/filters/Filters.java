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

    /**
     * Metodo que aplica un filtro de escala de grises a una imagen
     * @param img
     * @param row
     * @return
     */
    public static BufferedImage promedio(BufferedImage img, int row){
        int width = img.getWidth();

        for(int x =0; x< width; ++x){
            int pixel = img.getRGB(x, row);
            Color c = new Color(pixel);
            int gray = (c.getRed() + c.getGreen() + c.getBlue())/3;
            img.setRGB(x, row, new Color(gray, gray, gray).getRGB());
        } 
        return img; 
    }
    
    /**
     * Metodo que aplica un filtro de escala de grises a una imagen, de 
     * acuerdo a la formula de luminancia
     * @param img
     * @param row
     * @return
     */
    public static BufferedImage luma(BufferedImage img, int row){
        int width = img.getWidth();

        for(int x =0; x< width; ++x){
            int pixel = img.getRGB(x, row);
            Color c = new Color(pixel);
            double red = c.getRed();
            double green = c.getGreen();
            double blue = c.getBlue();
            int gray = (int)((red * 0.3) + (green * 0.59) + (blue * 0.11));
            img.setRGB(x, row, new Color(gray, gray, gray).getRGB());
        } 
        return img; 
    }

    /**
     * Metodo que aplica un filtro de escala de grises a una imagen, de 
     * acuerdo a la formula de correctitud
     * @param img
     * @param row
     * @return
     */
    public static BufferedImage correctitud2(BufferedImage img, int row){
        int width = img.getWidth();

        for(int x =0; x< width; ++x){
            int pixel = img.getRGB(x, row);
            Color c = new Color(pixel);
            double red = c.getRed();
            double green = c.getGreen();
            double blue = c.getBlue();
            int gray = (int)((red * 0.2126) + (green * 0.7152) + (blue * 0.0722));
            img.setRGB(x, row, new Color(gray, gray, gray).getRGB());
        } 
        return img; 
    }

    /**
     * Metodo que aplica un filtro de escala de grises a una imagen, de 
     * acuerdo al valor de una componente, en este caso el rojo
     * @param img
     * @param row
     * @return
     */
    public static BufferedImage singleColorRed(BufferedImage img, int row){
        int width = img.getWidth();

        for(int x =0; x< width; ++x){
            int pixel = img.getRGB(x, row);
            Color c = new Color(pixel);
            int red = c.getRed();
            img.setRGB(x, row, new Color(red,red, red).getRGB());
        } 
        return img; 
    }

    /**
     * Metodo que aplica un filtro un efecto como si estuviera dibujado con pluma,
     * @param img
     * @param row
     * @return
     */
    public static BufferedImage sharpen(BufferedImage imagen, int renglon) {
         
         int alto = imagen.getHeight();//n
         int ancho = imagen.getWidth();//m
         
         double [][] sharpen= {
             {0.0,-1.0,0.0},
             {-1.0,5.0,-1.0},
             {0.0,-1.0,0.0}
         };
         for(int x = 0; x <ancho-1; ++x){
             
                 double rojo =0;
                 double verde = 0;
                 double azul = 0;
                 
                 //Pixel1 (-1,-1)
 
                 int pixel = imagen.getRGB(((x-1)%ancho<0)?ancho-1:x-1,((renglon-1)%alto<0)?alto-1:renglon-1);
                 Color c = new Color(pixel);
                 rojo += c.getRed()* sharpen[0][0];
                 verde += c.getGreen()* sharpen[0][0];
                 azul += c.getBlue()* sharpen[0][0];
                 
                 //Pixel2 (0,-1)
                 pixel = imagen.getRGB(x%ancho,((renglon-1)%alto<0)?alto-1:renglon-1);
                 c = new Color(pixel);
                 rojo += c.getRed() * sharpen[0][1];
                 verde += c.getGreen() * sharpen[0][1];
                 azul += c.getBlue()* sharpen[0][1];
                 
                 //Pixel3 (1,-1)
                 pixel = imagen.getRGB((x+1)%ancho,((renglon-1)%alto<0)?alto-1:renglon-1);
                 c = new Color(pixel);
                 rojo += c.getRed()* sharpen[0][2];
                 verde += c.getGreen()* sharpen[0][2];
                 azul += c.getBlue()* sharpen[0][2];
                 
                 //Pixel4 (-1,0)
                 pixel = imagen.getRGB(((x-1)%ancho<0)?ancho-1:x-1,renglon%alto);
                 c = new Color(pixel);
                 rojo += c.getRed()* sharpen[1][0];
                 verde += c.getGreen()* sharpen[1][0];
                 azul += c.getBlue()* sharpen[1][0];

                 //Pixel5 (0,0) Centro
                pixel = imagen.getRGB(x,renglon);
                c = new Color(pixel);
                rojo += c.getRed()* sharpen[1][1];
                verde += c.getGreen()* sharpen[1][1];
                azul += c.getBlue()* sharpen[1][1];

                //Pixel6 (1,0)
                pixel = imagen.getRGB((x+1)%ancho,renglon%alto);
                c = new Color(pixel);
                rojo += c.getRed()* sharpen[1][2];
                verde += c.getGreen() * sharpen[1][2];
                azul += c.getBlue()* sharpen[1][2];

                //Pixel7 (-1,1)
                pixel = imagen.getRGB(((x-1)%ancho<0)?ancho-1:x-1,(renglon+1)%alto);
                c = new Color(pixel);
                rojo += c.getRed() * sharpen[2][0];
                verde += c.getGreen()* sharpen[2][0];
                azul += c.getBlue()* sharpen[2][0];

                //Pixel8 (0,1)
                pixel = imagen.getRGB(x%ancho,(renglon+1)%alto);
                c = new Color(pixel);
                rojo += c.getRed()* sharpen[2][1];
                verde += c.getGreen()* sharpen[2][1];
                azul += c.getBlue()* sharpen[2][1];

                //Pixel9 (1,1)
                pixel = imagen.getRGB((x+1)%ancho,(renglon+1)%alto);
                c = new Color(pixel);
                rojo += c.getRed()* sharpen[2][2];
                verde += c.getGreen()* sharpen[2][2];
                azul += c.getBlue()* sharpen[2][2];

                imagen.setRGB(x, renglon, new Color((int)rojo,(int)verde,(int)azul).getRGB());
         }
         return imagen;
    }

    /**
     * Metodo que aplica un filtro de componentes RGB a una imagen
     * @param imagen La imagen con la que se trabajara
     * @param renglon El renglon de la imagen
     * @param red El valor de la componente roja
     * @param green El valor de la componente verde
     * @param blue El valor de la componente azul
     * @return La imagen con el filtro aplicado
     */
    public static BufferedImage componentesRGB(BufferedImage imagen, int renglon, int red, int green, int blue){ 
        int ancho = imagen.getWidth();
        for(int x = 0; x < ancho; ++x){
            int pixel = imagen.getRGB(x, renglon);
            Color c = new Color(pixel);
            int r = (c.getRed() +red) % 256;
            int g = (c.getGreen() + green)%256;
            int b = (c.getBlue() + blue)%256;
            imagen.setRGB(x, renglon, new Color(r, g, b).getRGB());
        }
        return imagen;
    }

    /**
     * Metodo que aplica un filtro de alto contraste a una imagen
     * @param imagen La imagen con la que se trabajara
     * @param renglon El renglon de la imagen
     * @return La imagen con el filtro aplicado
     */
    public static BufferedImage altoContraste(BufferedImage imagen, int renglon){
        int ancho = imagen.getWidth();
        for(int x = 0; x < ancho; ++x){
            int pixel = imagen.getRGB(x, renglon);
            Color c = new Color(pixel);
            int gray = (c.getRed() + c.getGreen() + c.getBlue())/3; // Pasamos a escala de grises
            int g = (gray < 128) ? 0 : 255;
            imagen.setRGB(x, renglon, new Color(g, g, g).getRGB());
        }
        return imagen;
    }

    /**
     * Metodo que aplica el Metodo de blur usando una matriz 3x3 a una imagen
     * @param imagen La imagnen con la que se trabajara
     * @return La imagen con el filtro aplicado
     * @throws IOException 
     */
    public static BufferedImage blur1(BufferedImage imagen, int renglon){
         
         int alto = imagen.getHeight();//n
         int ancho = imagen.getWidth();//m
         
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

                 imagen.setRGB(x, renglon, new Color((int)rojo,(int)verde,(int)azul).getRGB());
             
         }
         return imagen;
     }

     public static BufferedImage blur2(BufferedImage imagen, int renglon){
         
         int alto = imagen.getHeight();//n
         int ancho = imagen.getWidth();//m
         
         double [][] blur= {
             {0,0,1,0,0},
             {0,1,1,1,0},
             {1,1,1,1,1},
             {0,1,1,1,0},
             {0,0,1,0,0}
         };

         for(int x=0; x<ancho; ++x){

            double rojo = 0;
            double verde = 0;
            double azul = 0;

            for(int i = 0; i < 5; ++i){
                for(int j = 0; j < 5; ++j){
                    if (x+j >= ancho || renglon+i >= alto) continue;
                    int pixel = imagen.getRGB((x+j)%ancho,((renglon+i)%alto));
                    Color c = new Color(pixel);
                    rojo += (c.getRed() * blur[i][j])/13;
                    verde += (c.getGreen() * blur[i][j])/13;
                    azul += (c.getBlue() * blur[i][j])/13;
                }
            }
            imagen.setRGB(x, renglon, new Color((int)rojo,(int)verde,(int)azul).getRGB());
         }
        return imagen;
    }

    public static BufferedImage motionBlur(BufferedImage imagen, int renglon){
         
         int alto = imagen.getHeight();//n
         int ancho = imagen.getWidth();//m
         
         double[][] blur  = {
                {1,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0},
                {0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,1,0,0,0,0},
                {0,0,0,0,0,0,1,0,0,0},
                {0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,1},
         };

         for(int x=0; x<ancho; ++x){

            double rojo = 0;
            double verde = 0;
            double azul = 0;

            for(int i = 0; i < 10; ++i){
                for(int j = 0; j < 10; ++j){
                    if (x+j >= ancho || renglon+i >= alto) continue;
                    int pixel = imagen.getRGB((x+j)%ancho,((renglon+i)%alto));
                    Color c = new Color(pixel);
                    rojo += (c.getRed() * blur[i][j])/13;
                    verde += (c.getGreen() * blur[i][j])/13;
                    azul += (c.getBlue() * blur[i][j])/13;
                }
            }
            imagen.setRGB(x, renglon, new Color((int)rojo,(int)verde,(int)azul).getRGB());
         }
        return imagen;
    }

}
