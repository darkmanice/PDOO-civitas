package civitas;
import java.util.ArrayList;

public class Tablero {
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    Tablero(int indice){
        if(indice >= 1)
            numCasillaCarcel = indice;
        else 
            numCasillaCarcel = 1;
        
        casillas = new ArrayList<Casilla>();
        Casilla salida = new Casilla("Salida");
        casillas.add(salida);
        
        porSalida = 0;
        tieneJuez = false;
    }
    
    private boolean correcto(){
        if(casillas.size() > numCasillaCarcel)
            return true;
        else
            return false;
    }
    
    private boolean correcto(int numCasilla){
        if(this.correcto() && numCasilla >= 0 && numCasilla < casillas.size())
            return true;
        else
            return false;
    }
    
    int getCarcel(){
        return numCasillaCarcel;
    }
    
    //Decremento en uno para poder hacer el paso paso por salida correctamente, no utilizar para saber el numero de veces pasadas por salida
    int getPorSalida(){
        porSalida--;
        return porSalida + 1;
    }
    
    //numCasillaCarcel - 1 ya que el size empieza en 0 pero las casillas en la pos 1
    void añadeCasilla(Casilla casilla){
        if(casillas.size() == numCasillaCarcel - 1){
            Casilla carcel = new Casilla(5, "CARCEL");
            casillas.add(carcel);
        }
        
        casillas.add(casilla);
    }
    
    void añadeJuez(){
        if(!tieneJuez){
            tieneJuez = true;
            Casilla juez = new Casilla(5, "Juez");
            casillas.add(juez);            
        }
    }
    
    Casilla getCasilla(int numCasilla){
        if(this.correcto(numCasilla))
            return casillas.get(numCasilla);
        else
            return null;
    }
    
    public ArrayList<Casilla> getCasillas(){
        return casillas;        
    }
    
    int nuevaPosicion(int actual, int tirada){
        if(this.correcto()){
            int nuevaPosicion = (actual + tirada) % casillas.size();
            
            if(actual + tirada != nuevaPosicion){
                porSalida++;
            }
            
            return nuevaPosicion;
        } else
            return -1;        
    }
    
    int calcularTirada(int origen, int destino){
        int resultado = destino - origen;
        
        if(resultado < 0)
            resultado = resultado + casillas.size();
        
        return resultado;
    }
}
