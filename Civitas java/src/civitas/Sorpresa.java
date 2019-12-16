package civitas;

import java.util.ArrayList;

public class Sorpresa{
    private String texto;
    private int valor;
    private Tablero tablero;
    private MazoSorpresas mazo;
    private TipoSorpresa tipo;    
        
    Sorpresa(TipoSorpresa tipo1, Tablero tablero1){
        init();
        tipo = tipo1;
        tablero = tablero1;
    }
    
    Sorpresa(TipoSorpresa tipo1, Tablero tablero1, int valor1, String texto1){
        init();
        tipo = tipo1;
        tablero = tablero1;
        valor = valor1;
        texto = texto1;
    }
    
    Sorpresa(TipoSorpresa tipo1, int valor1, String texto1){
        init();
        tipo = tipo1;
        valor = valor1;
        texto = texto1;
    }
    
    Sorpresa(TipoSorpresa tipo1, MazoSorpresas mazo1){
        init();
        tipo = tipo1;
        mazo = mazo1;
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            if(tipo == TipoSorpresa.IRCARCEL)
                aplicarAJugador_irACarcel(actual, todos);
            else if(tipo == TipoSorpresa.IRCASILLA)
                aplicarAJugador_irACasilla(actual, todos);
            else if(tipo == TipoSorpresa.PAGARCOBRAR)
                aplicarAJugador_pagarCobrar(actual, todos);
            else if(tipo == TipoSorpresa.PORCASAHOTEL)
                aplicarAJugador_porCasaHotel(actual, todos);
            else if(tipo == TipoSorpresa.PORJUGADOR)
                aplicarAJugador_porJugador(actual, todos);
            else if(tipo == TipoSorpresa.SALIRCARCEL)
                aplicarAJugador_salirCarcel(actual, todos);
            else 
                System.out.println("AplicaraJugador ha dadao error");
        }
    }
    
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos){
        informe(actual, todos);
        int casillaActual = todos.get(actual).getNumCasillaActual();
        tablero.calcularTirada(casillaActual, valor);
    }
    
    private void aplicarAJugador_irACarcel(int actual, ArrayList<Jugador> todos){
        todos.get(actual).encarcelar(tablero.getCarcel());
    }
    
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos){
        informe(actual, todos);
        todos.get(actual).modificarSaldo(valor);
    }
    
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos){
        informe(actual, todos);
        todos.get(actual).modificarSaldo(valor*todos.get(actual).cantidadCasasHoteles());    

    }
    
    private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos){
        informe(actual, todos);
        Sorpresa sorpresa1 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, -1, "PAGARCOBRAR");
        
        for(int i = 0; i < todos.size(); i++){
            if(i != actual)
                sorpresa1.aplicarAJugador(i, todos);
        }
        
        Sorpresa sorpresa2 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor*(todos.size() - 1), "PAGARCOBRAR");
        sorpresa2.aplicarAJugador(actual, todos);
    }
    
    private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos){
        informe(actual, todos);
        boolean tiene = false;
        
        for(int i = 0; i < todos.size(); i++){
            if(todos.get(actual).tieneSalvoconducto())
                tiene = true;
        }
        
        if(!tiene){
            todos.get(actual).obtenerSalvoConducto(this);
            salirDelMazo();
        }
    }
    
    private void informe(int actual, ArrayList<Jugador> todos){
        (Diario.getInstance()).ocurreEvento("Se aplica una sorpresa al jugador " + todos.get(actual).getNombre());
    }
    
    private void init(){
        valor = -1;
        mazo = null;
        tablero = null;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        if(actual < todos.size())
            return true;
        else
            return false;
    }
    
    void salirDelMazo(){
        if(tipo == TipoSorpresa.SALIRCARCEL){
            System.out.println("TIPO SORPRESA SALIR DEL MAZO: " + tipo);
            mazo.inhabilitarCartaEspecial(this);
        }
    }
    
    public String toString(){
        return texto;
    }
    
    void usada(){
        if(tipo == TipoSorpresa.SALIRCARCEL)
            mazo.habilitarCartaEspecial(this);
    }
}
