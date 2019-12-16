package juegoTexto;

import civitas.CivitasJuego;
import civitas.Dado;
import java.util.ArrayList;

public class juegoTexto {
    public static void main(String[] args){
        VistaTextual vista = new VistaTextual();
        ArrayList<String> nombres = new ArrayList();
        Dado.getInstance().setDebug(false);
        
        nombres.add("Javi");
        nombres.add("Antonio");
        nombres.add("Pedro");
        
        CivitasJuego civitas = new CivitasJuego(nombres);
        
        Controlador controlador = new Controlador(civitas, vista);
        
        controlador.juega();
    }
}
