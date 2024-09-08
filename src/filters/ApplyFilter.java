package filters;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ApplyFilter implements Runnable {

    public static final int HILOS = 100;
    public static final String IMG_PATH = "C:\\Users\\lr255029\\OneDrive - Teradata\\Documents\\Escuela\\Semestre7\\Concurrente\\Practica2\\Practica2-ConcurrentProgramming\\src\\filters\\image\\";


    private BufferedImage imagen;

    public ApplyFilter(BufferedImage imagen) {
        this.imagen = imagen;
    }
    @Override
    public void run() {
        int threadRow = Integer.parseInt(Thread.currentThread().getName());
        imagen = Filters.blur2(imagen,threadRow);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        long inicio = System.nanoTime();

        List<Thread> hilos = new ArrayList<>();
        Filters f = new Filters();
        
        BufferedImage imagen = f.leeImagen(IMG_PATH+"klimt.jpeg");
        BufferedImage copia = Filters.copia(imagen, BufferedImage.TYPE_INT_RGB);

        int alto = imagen.getHeight();

        for(int i = 0; i < alto; ++i){
            Thread t = new Thread(new ApplyFilter(copia),""+i);
            t.start();
            hilos.add(t);

            if (hilos.size() == HILOS){
                for(Thread h : hilos){
                    h.join();
                }
                hilos.clear();
            }
        }

        Filters.guardaImagen(copia, IMG_PATH+"klimt-blur2.jpeg");
        long fin = System.nanoTime();
        System.err.println("Numero de hilos: "+HILOS);
        System.err.println("Tiempo de ejecucion: "+(fin-inicio)/1.0e9);
    }
    
}
