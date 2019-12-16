package civitas;

import java.util.ArrayList;
import java.util.Collections;

public class CivitasJuego {
    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores;
    private EstadosJuego estado;
    private GestorEstados gestorEstados;
    private Tablero tablero;
    private MazoSorpresas mazo;
    
    private void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida(jugadorActual);  
    }
    
    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
    
    public CivitasJuego(ArrayList<String> nombres){
        jugadores = new ArrayList();
        Jugador temporal;
        
        for(int i = 0; i < nombres.size(); i++){
            temporal = new Jugador(nombres.get(i));
            jugadores.add(temporal);
        }
        
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        
        indiceJugadorActual = Dado.getInstance().quienEmpieza(nombres.size() - 1);
        tablero = new Tablero(5);
        inicializarMazoSorpresas(tablero);
        inicializarTablero(mazo);
    }
    
    public boolean comprar(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo  = casilla.getTituloPropiedad();
        boolean res = jugadorActual.comprar(titulo);
        return res;
    }
    
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(tablero.getPorSalida() > 0)
            jugadorActual.pasoPorSalida();
    }
    
    public boolean finalDelJuego(){
        boolean bancarrota = false;
        
        for(int i = 0; i < jugadores.size(); i++){
            if(jugadores.get(i).enBancarrota())
                bancarrota = true;
        }
        
        return bancarrota;
    }
    
    public Casilla getCasillaActual(){
        int casilla = getJugadorActual().getNumCasillaActual();
        return tablero.getCasilla(casilla);
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }
    
    public String infoJugadorTexto(){
        return jugadores.get(indiceJugadorActual).toString();
    }
    
    private void inicializarMazoSorpresas(Tablero tablero1){
        mazo = new MazoSorpresas();
        
        Sorpresa sorpresa1 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, tablero1, 500, "PAGARCOBRAR");
        Sorpresa sorpresa2 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, tablero1, -500, "PAGARCOBRAR");
        Sorpresa sorpresa3 = new Sorpresa(TipoSorpresa.IRCASILLA, tablero1, 5, "IRCASILLA");
        Sorpresa sorpresa4 = new Sorpresa(TipoSorpresa.IRCASILLA, tablero1, 12, "IRCASILLA");
        Sorpresa sorpresa5 = new Sorpresa(TipoSorpresa.IRCASILLA, tablero1, 18, "IRCASILLA");
        Sorpresa sorpresa6 = new Sorpresa(TipoSorpresa.PORCASAHOTEL, tablero1, 300, "PORCASAHOTEL");
        Sorpresa sorpresa7 = new Sorpresa(TipoSorpresa.PORCASAHOTEL, tablero1, -500, "PORCASAHOTEL");
        Sorpresa sorpresa8 = new Sorpresa(TipoSorpresa.PORJUGADOR, tablero1, 200, "PORJUGADOR");
        Sorpresa sorpresa9 = new Sorpresa(TipoSorpresa.PORJUGADOR, tablero1, -200, "PORJUGADOR");
        Sorpresa sorpresa10 = new Sorpresa(TipoSorpresa.SALIRCARCEL, tablero1);
        Sorpresa sorpresa11 = new Sorpresa(TipoSorpresa.IRCARCEL, tablero1);
        
        mazo.alMazo(sorpresa1);
        mazo.alMazo(sorpresa2);
        mazo.alMazo(sorpresa3);
        mazo.alMazo(sorpresa4);
        mazo.alMazo(sorpresa5);
        mazo.alMazo(sorpresa6);
        mazo.alMazo(sorpresa7);
        mazo.alMazo(sorpresa8);
        mazo.alMazo(sorpresa9);
        mazo.alMazo(sorpresa10);
        mazo.alMazo(sorpresa11);
    }
    
    private void inicializarTablero(MazoSorpresas mazo1){        
        TituloPropiedad titulo1 = new TituloPropiedad("Zero Two", 200, 50, 500, 500, 200);
        TituloPropiedad titulo2 = new TituloPropiedad("Inori Yuzuriha", 200, 50, 500, 500, 200);
        TituloPropiedad titulo3 = new TituloPropiedad("Nezuko Kamado", 300, 100, 700, 700, 300);
        TituloPropiedad titulo4 = new TituloPropiedad("Shouko Nishimiya", 300, 100, 700, 700 , 300);
        TituloPropiedad titulo5 = new TituloPropiedad("Yuno Gasai", 300, 100, 700, 700, 300);
        TituloPropiedad titulo6 = new TituloPropiedad("Isla", 500, 200, 1000, 1000, 500);
        TituloPropiedad titulo7 = new TituloPropiedad("Chizuru Hishiro", 500, 200, 1000, 1000, 500);
        TituloPropiedad titulo8 = new TituloPropiedad("Mikasa Ackerman", 500, 200, 1000, 1000, 500);
        TituloPropiedad titulo9 = new TituloPropiedad("Mitsuri Kanroji", 700, 400, 1500, 1500, 800);
        TituloPropiedad titulo10 = new TituloPropiedad("Shinobu Kochou", 700, 400, 1500, 1500, 800);
        TituloPropiedad titulo11 = new TituloPropiedad("Shinoa Hiiragi", 700, 400, 1500, 1500, 900);
        TituloPropiedad titulo12 = new TituloPropiedad("Krul Tepes", 100, 700, 2000, 2000, 1300);
        
        Casilla casilla1 = new Casilla(titulo1);
        Casilla casilla2 = new Casilla(titulo2);
        Casilla casilla3 = new Casilla(titulo3);
        Casilla casilla4 = new Casilla(titulo4);
        Casilla casilla5 = new Casilla(titulo5);
        Casilla casilla6 = new Casilla(titulo6);
        Casilla casilla7 = new Casilla(titulo7);
        Casilla casilla8 = new Casilla(titulo8);
        Casilla casilla9 = new Casilla(titulo9);
        Casilla casilla10 = new Casilla(titulo10);
        Casilla casilla11 = new Casilla(titulo11);
        Casilla casilla12 = new Casilla(titulo12);
        Casilla sorpresa1 = new Casilla(mazo1, "SORPRESA");
        Casilla sorpresa2 = new Casilla(mazo1, "SORPRESA");
        Casilla sorpresa3 = new Casilla(mazo1, "SORPRESA");
        Casilla casilla16 = new Casilla(5, "JUEZ");
        Casilla casilla17 = new Casilla(500, "IMPUESTO");
        Casilla casilla18 = new Casilla("DESCANSO");
        
        tablero.añadeCasilla(casilla1);
        tablero.añadeCasilla(casilla2);
        tablero.añadeCasilla(casilla3);
        tablero.añadeCasilla(casilla16);
        tablero.añadeCasilla(casilla4);
        tablero.añadeCasilla(sorpresa1);
        tablero.añadeCasilla(casilla5);
        tablero.añadeCasilla(casilla6);
        tablero.añadeCasilla(casilla17);
        tablero.añadeCasilla(casilla7);
        tablero.añadeCasilla(casilla8);
        tablero.añadeCasilla(sorpresa2);
        tablero.añadeCasilla(casilla9);
        tablero.añadeCasilla(casilla10);
        tablero.añadeCasilla(casilla11);
        tablero.añadeCasilla(casilla12);
        tablero.añadeCasilla(sorpresa3);
        tablero.añadeCasilla(casilla18);      
    }
    
    private void pasarTurno(){
        indiceJugadorActual = (indiceJugadorActual + 1)%jugadores.size();
    }
    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> ranking = new ArrayList();
        ranking = jugadores;
        Collections.sort(ranking);
        return ranking;
    }
    
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
        
        if(operacion == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        } else if(operacion == OperacionesJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        
        return operacion;
    }    
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        Jugador jugador = jugadores.get(indiceJugadorActual);
        estado = gestorEstados.siguienteEstado(jugador, estado, operacion);
    }
    
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
    
    public boolean regalarPropiedad(Jugador jugador_que_da, Jugador jugador_que_recibe){
        ArrayList<TituloPropiedad> propiedades = jugador_que_da.getPropiedades();
        ArrayList<TituloPropiedad> propiedades_recibe = jugador_que_recibe.getPropiedades();
        TituloPropiedad casa;
        boolean resultado = false;
        
        if(propiedades.size() == 0){
            return false;
        } else
            casa = propiedades.get(0);
            casa.actualizaPropietarioPorConversion(jugador_que_recibe);
            
            propiedades.remove(0);
            
            
            resultado = jugador_que_recibe.recibeCasa(casa);
            
            
            propiedades_recibe = jugador_que_recibe.getPropiedades();
                        
            if(resultado)
                Diario.getInstance().ocurreEvento("Se regala la propiedad " + casa.getNombre() + " a " + jugador_que_recibe.getNombre());
            
            return resultado;
    }
    
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
}
