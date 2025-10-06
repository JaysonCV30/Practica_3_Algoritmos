package Logica;

public class ColaSimple<T> {
    private int inicio;
    private int fin;
    private int max;
    private T[] colaSimple;
    
    public ColaSimple(){
        this.inicio = -1;
        this.fin = -1;
        this.max = 5;
        colaSimple = (T[]) new Object [max];
    }
    
    public void insertar(T dato){
        if(fin < colaSimple.length){
            fin = fin+1;
            colaSimple[fin] = dato;
            if(fin==0){
                inicio = 0;
            }
        } else {
            System.out.println("Desbordamiento");
        }
    }
    
    public T eliminar(){
        T dato = null;
        if(inicio!=-1){
            dato = colaSimple[inicio];
            if(inicio == fin){
                inicio = -1;
                fin = -1;
            } else {
                inicio = inicio+1;
            }
        } else {
            System.out.println("Subdesbordamiento");
        }
        return dato;
    }
    
    public T verProximo(){
        T objeto = null;
        if(inicio==-1){
            System.out.println("cola vacia");
        } else {
            objeto = colaSimple[inicio];
        }
        return objeto;
    }
}