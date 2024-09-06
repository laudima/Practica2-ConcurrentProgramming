package threads;
/**
 * Clase que modela un Contador compartido
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Contador implements Runnable {
    int valor;//Variable compartida (LECTURA y ESCRITURA)
    int RONDAS = 10000; //Variable compartida (SOLO LECTURA)
    int HILOS = 2;

    public Contador(int valor){
        this.valor = valor;
    }

    @Override
    public void run() {
        aumentaContador();
    }

    public void aumentaContador(){
        for(int i=0; i<RONDAS; ++i){
            valor++;//Seccion Critica (CS)
            //Ejecucion 1
            //T1 T2
            //T1 Lee el valor de valor (0)
            //T2 Lee el valor de valor (0)
            //T1 escribe el nuevo valor (1)
            //T2 escribe el nuevo valor (1)

            //Ejecucion 2
            //T1 T2
            //T1 Lee el valor de valor (0)
            //T1 Escribe el valor de valor (1)
            //T2 Lee el valor de valor (1)
            //T2 Escribe el valor de valor (2)
        }
    }

    public static void main(String[] args)throws InterruptedException {
        Contador c = new Contador(0);
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);

        t1.start();t2.start();
        //-----
        t1.join();t2.join();

        System.out.println(c.valor);
    }
}