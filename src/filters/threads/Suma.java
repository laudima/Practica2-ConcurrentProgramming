package threads;
import java.util.List;
import java.util.ArrayList;

public class Suma implements Runnable {
    private int[][] matrizA;
    private int[][] matrizB;
    private int[][] matriz;
    public static final int HILOS = 10;

    public Suma(int[][] matrizA, int[][] matrizB){
        this.matrizA = matrizA;
        this.matrizB = matrizB;
        this.matriz = new int[matrizA.length][matrizA.length];
    }

    @Override
    public void run() {
        int algo = Integer.parseInt(Thread.currentThread().getName());
        sumaConcurrente(algo);
    }

    /**
     * Metodo que suma 2 matrices de manera secuencial
     */
    public void suma(){
        for(int i=0; i<matrizA.length; i++){
            for(int j=0; j<matrizB.length; j++){
                matriz[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }
    }

    public void sumaConcurrente(int otraCosa){
        //el Numero Hilos * complejidad del metodo => 2 * n => 2n => O(n)
        for(int k = 0;  k < matriz.length; ++k){
            matriz[k][otraCosa] = matrizA[k][otraCosa] + matrizB[k][otraCosa];
        }        
    }

    public static void main(String[] args) throws InterruptedException{
        long inicio = System.nanoTime();
        long fin;
        int[][] matrizF = {{1,2},{1,2}};//n >= 50
        List<Thread> hilos = new ArrayList<>();
        Suma s = new Suma(matrizF,matrizF);

        for(int i = 0;  i < matrizF.length; ++i){
            Thread t = new Thread(s,""+i);
            t.start();
            hilos.add(t);

            //Metodo facil de pensar, pero no tanta optimizacion
            if(hilos.size() == HILOS){
                for(Thread h : hilos){
                    h.join();
                }
                hilos.clear();
            }

            //Metodo dificil de pensar, pero con mayor optmizacion
        }

        for(Thread h : hilos){
            h.join();
        }
        fin = System.nanoTime();
        System.out.println("TF: " + (fin-inicio));
    }
}   