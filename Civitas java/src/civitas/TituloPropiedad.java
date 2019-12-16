package civitas;

public class TituloPropiedad {
    private static float factorInteresesHipoteca = (float)1.1;
    private float alquilerBase;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    }
    
    //Completar adecuadamente
    boolean cancelarHipoteca(Jugador jugador){
        hipotecado = false;
        return true;
    }
    
    int cantidadCasasHoteles(){
        return numCasas + numHoteles;
    }
    
    //Completar adecuadamente
    boolean comprar(Jugador jugador){
       return true; 
    }
    
    //Completar adecuadamente
    boolean construirCasa(Jugador jugador){
        numCasas++;
        return true;
    }
    
    //Completar adecuadamente
    boolean construirHotel(Jugador jugador){
        numHoteles++;
        return true;
    }

    
    boolean derruirCasas(int n, Jugador jugador){
        if(jugador == getPropietario() && numCasas >= n){
            numCasas -= n;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean esEsteElPropietario(Jugador jugador){
        if(jugador == propietario)
            return true;
        else
            return false;
    }
    
    public boolean getHipotecado(){
        if(hipotecado)
            return true;
        else
            return false;
    }
    
    float getImporteCancelarHipoteca(){
        return hipotecaBase * factorInteresesHipoteca;
    }
    
    float getImporteHipoteca(){
        return hipotecaBase;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    int getNumCasas(){
        return numCasas;
    }
    
    int getNumHoteles(){
        return numHoteles;
    }
    
    float getPrecioAlquiler(){
        if(getHipotecado() || propietarioEncarcelado())
            return 0;
        else {
            double alquiler = alquilerBase*(1 + (numCasas*0.5) + (numHoteles*2.5));
            return (float)alquiler;             
        }
    }
    
    float getPrecioCompra(){
        return precioCompra;
    }
    
    float getPrecioEdificar(){
        return precioEdificar;
    }
    
    private float getPrecioVenta(){
        return precioCompra + (precioEdificar*factorRevalorizacion);
    }
    
    Jugador getPropietario(){
        return propietario;
    }
    
    
    
    //Completar adecuadamente
    boolean hipotecar(Jugador jugador){
        hipotecado = true;
        
        return true;
    }
    
    private boolean propietarioEncarcelado(){
        if(propietario.isEncarcelado())
            return true;
        else
            return false;
    }
    
    boolean tienePropietario(){
        if(propietario != null)
            return true;
        else
            return false;
    }
    
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe){
        nombre = nom;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        precioCompra = pc;
        precioEdificar = pe;
        propietario = null;
        numCasas = 0;
        numHoteles = 0;
        hipotecado = false;
    }
    
    public String toString(){
      String cadena =  ("\n\nCalle: " + nombre + 
      "\nPrecio de compra: " + precioCompra +
      "\nAlquiler base: " + alquilerBase +
      "\nPrecio de edificación: " + precioEdificar +
      //"\nHipoteca base: " + hipotecaBase +
      //"\nHipotecado: " + hipotecado +
      //"\nFactor de interes hipoteca: " + factorInteresesHipoteca +
      "\nNumero de casas: " + numCasas +
      "\nNumero de hoteles: " + numHoteles + "\n\n"
      //"\nFactor de revalorización: " + factorRevalorizacion
              );
      
      return cadena;
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && jugador != getPropietario()){
            jugador.pagaAlquiler(getPrecioAlquiler());
            getPropietario().recibe(getPrecioAlquiler());
        }
    }
    
    boolean vender(Jugador jugador){
        if(jugador == getPropietario() && !getHipotecado()){
            jugador.recibe(precioCompra);
            jugador = null;
            numCasas = 0;
            numHoteles = 0;
            return true;
        } else
            return false;
    }
}
