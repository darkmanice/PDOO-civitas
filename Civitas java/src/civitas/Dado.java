package civitas;
import java.util.Random;

public class Dado {
    private Random random;
    private int ultimoResultado;
    private boolean debug;
    static final private Dado instance = new Dado();
    static final private int salidaCarcel = 5;
    
    private Dado(){
        debug = false;
        ultimoResultado = 0;
        random = new Random();        
    }
    
    static public Dado getInstance(){
        return instance;
    }
    
    int tirar(){
        int genera;
        
        if(!debug){
            genera = random.nextInt(5) + 1;
        } else
            genera = 1;
        
        ultimoResultado = genera;
        return genera;
    }
    
    boolean salgoDeLaCarcel(){
        int valor = this.tirar();
        if(valor == salidaCarcel)
            return true;
        else
            return false;
    }
    
    int quienEmpieza(int n){
        return random.nextInt(n) + 1;
    }
    
    public void setDebug(boolean d){
        debug = d;
        (Diario.getInstance()).ocurreEvento("Se modifica el estado del dado debug a " + debug);
    }
    
    int getUltimoResultado(){
        return ultimoResultado;
    }
}
