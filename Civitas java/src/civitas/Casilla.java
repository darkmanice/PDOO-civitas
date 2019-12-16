package civitas;

import java.util.ArrayList;

public class Casilla {
    private static int carcel;
    private float importe;
    private String nombre;
    private TipoCasilla tipo;
    private TituloPropiedad tituloPropiedad;
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;
    
    
    Casilla(String nombre1){
        init();
        nombre = nombre1;
        tipo = TipoCasilla.DESCANSO;
    }
    
    Casilla(TituloPropiedad titulo){
        init();
        nombre = titulo.getNombre();
        tipo = TipoCasilla.CALLE;
        tituloPropiedad = titulo;
    }
    
    Casilla(float cantidad, String nombre1){
        init();
        nombre = nombre1;
        importe = cantidad;
        tipo = TipoCasilla.IMPUESTO;
    }
    
    Casilla(int numCasillaCarcel, String nombre1){
        init();
        nombre = nombre1;
        tipo = TipoCasilla.JUEZ;
        carcel = numCasillaCarcel;
    }
    
    Casilla(MazoSorpresas mazo1, String nombre1){
        init();
        nombre = nombre1;
        mazo = mazo1;
        tipo = TipoCasilla.SORPRESA;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }
    
    private void informe(int iactual, ArrayList<Jugador> todos){
        String nombre = todos.get(iactual).getNombre();
        String cadena = toString();
        
        Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " ha caído en: \n" + cadena);
    }
    
    private void init(){
        importe = 0;
        mazo = null;
        sorpresa = null;
        tituloPropiedad = null;
        tipo = null;
        nombre = "";
    }
    
    public boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos){
        if(iactual < todos.size())
            return true;
        else
            return false;
    }
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        if(tipo == TipoCasilla.CALLE)
            recibeJugador_calle(iactual, todos);
        else if(tipo == TipoCasilla.IMPUESTO)
            recibeJugador_impuesto(iactual, todos);
        else if(tipo == TipoCasilla.JUEZ)
            recibeJugador_juez(iactual, todos);
        else if(tipo == TipoCasilla.SORPRESA)
            recibeJugador_sorpresa(iactual, todos);
        else
            informe(iactual, todos);
    }
    
    private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            Jugador jugador = todos.get(iactual);
            
            if(!tituloPropiedad.tienePropietario()){
                jugador.puedeComprarCasilla();
            } else {
                tituloPropiedad.tramitarAlquiler(jugador);
                
                if(tituloPropiedad.tienePropietario()){
                    if(tituloPropiedad.esEsteElPropietario(jugador)){
                        jugador.pagaAlquiler(tituloPropiedad.getPrecioAlquiler());
                        tituloPropiedad.getPropietario().recibe(tituloPropiedad.getPrecioAlquiler());
                    }
                }
            }
        }
    }
    
    private void recibeJugador_impuesto(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            todos.get(iactual).pagaImpuesto(importe);
        }
    }
    
    private void recibeJugador_juez(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            todos.get(iactual).encarcelar(carcel);
        }
    }
    
    private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual, todos)){
            Sorpresa sorpresa = mazo.siguiente();
            informe(iactual, todos);
            sorpresa.aplicarAJugador(iactual, todos);
        }
    }
    
    public String toString(){
        String cadena = "\nNombre: " + nombre +
            "\nTipo: " + tipo + "\n\n";
        
        return cadena;
    }
}
