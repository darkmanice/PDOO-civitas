package juegoTexto;

import civitas.CivitasJuego;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.GestionesInmobiliarias;
import civitas.SalidasCarcel;
import civitas.Jugador;
import java.util.ArrayList;

public class Controlador {
    private CivitasJuego civitas;
    private VistaTextual vista;
    
    Controlador(CivitasJuego juego, VistaTextual vista1){
        civitas = juego;
        vista = vista1;
    }
    
    void juega(){
        vista.setCivitasJuego(civitas);
        while(!civitas.finalDelJuego()){
            vista.actualizarVista();
            vista.pausa();
            OperacionesJuego operacion = civitas.siguientePaso();
            vista.mostrarSiguienteOperacion(operacion);
            
            if(operacion != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            
            if(!civitas.finalDelJuego()){
                if(operacion == OperacionesJuego.COMPRAR){
                    Respuestas respuesta = vista.comprar();
                    
                    if(respuesta == Respuestas.SI){
                        civitas.comprar();
                        civitas.siguientePasoCompletado(operacion);
                    }
                                
                } else if (operacion == OperacionesJuego.GESTIONAR){
                    vista.gestionar();
                    int gestion = vista.getGestion();
                    int propiedad = vista.getPropiedad();
                    int seleccion;
                    ArrayList<Jugador> jugadores;
                    OperacionInmobiliaria inmobiliaria = new OperacionInmobiliaria(GestionesInmobiliarias.values()[gestion], propiedad);

                    if(inmobiliaria.getGestion() == GestionesInmobiliarias.TERMINAR)
                        civitas.siguientePasoCompletado(operacion);
                    else if(inmobiliaria.getGestion() == GestionesInmobiliarias.CANCELAR_HIPOTECA){
                        seleccion = vista.seleccionPropiedad();
                        civitas.cancelarHipoteca(seleccion);
                    } else if(inmobiliaria.getGestion() == GestionesInmobiliarias.CONSTRUIR_CASA){
                        seleccion = vista.seleccionPropiedad();
                        civitas.construirCasa(seleccion);
                    } else if(inmobiliaria.getGestion() == GestionesInmobiliarias.CONSTRUIR_HOTEL){
                        seleccion = vista.seleccionPropiedad();
                        civitas.construirHotel(seleccion);
                    } else if(inmobiliaria.getGestion() == GestionesInmobiliarias.HIPOTECAR){
                        seleccion = vista.seleccionPropiedad();
                        civitas.hipotecar(seleccion);
                    } else if(inmobiliaria.getGestion() == GestionesInmobiliarias.VENDER){
                        seleccion = vista.seleccionPropiedad();
                        civitas.vender(seleccion);
                    } else if(inmobiliaria.getGestion() == GestionesInmobiliarias.REGALAR_CASA){
                        jugadores = civitas.getJugadores();
                        
                        if(civitas.getJugadorActual() == jugadores.get(0))
                            civitas.regalarPropiedad(civitas.getJugadorActual(), jugadores.get(1));
                        else
                            civitas.regalarPropiedad(civitas.getJugadorActual(), jugadores.get(0));
                    }
                    
                } else if(operacion == OperacionesJuego.SALIR_CARCEL){
                    SalidasCarcel carcel = vista.salirCarcel();
                    
                    if(carcel == SalidasCarcel.PAGANDO){
                        civitas.salirCarcelPagando();
                    } else if(carcel == SalidasCarcel.TIRANDO){
                        civitas.salirCarcelTirando();
                    }
                }
            }
        }
        //Cuando el juego acabe
        civitas.ranking();
    }
}
