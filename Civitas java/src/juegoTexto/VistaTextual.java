package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.GestionesInmobiliarias;
import civitas.Jugador;
import civitas.TituloPropiedad;

class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {
      int opcion = menu ("¿Desea comprar la calle?",
              new ArrayList<> (Arrays.asList("NO", "SI")));
      return Respuestas.values()[opcion];
  }

  void gestionar () {
      ArrayList<String> lista = new ArrayList();
      lista.add("VENDER");
      lista.add("HIPOTECAR");
      lista.add("CANCELAR_HIPOTECA");
      lista.add("CONSTRUIR_CASA");
      lista.add("CONSTRUIR_HOTEL");
      lista.add("TERMINAR");
      lista.add("REGALAR CASA");
      
      iGestion = menu("¿Gestion inmobiliaria elegida?", lista);
      
      iPropiedad = juegoModel.getJugadorActual().getNumCasillaActual();
  }
  
  int seleccionPropiedad(){
      ArrayList<String> propiedades= new ArrayList();
      ArrayList<TituloPropiedad> titulos = juegoModel.getJugadorActual().getPropiedades();
      
      for(int i = 0; i < titulos.size(); i++)
        propiedades.add(titulos.get(i).getNombre());
      
      int ip = menu("¿Que propiedad desea seleccionar?", propiedades);
      
      return ip;
  }
  
  public int getGestion(){
      return iGestion;
  }
  
  public int getPropiedad(){
      return iPropiedad;
  }
    

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
      System.out.println("\nSiguiente operacion: " + operacion);
  }


  void mostrarEventos() {
      String evento;
      
      while(Diario.getInstance().eventosPendientes()){
          evento = Diario.getInstance().leerEvento();
          System.out.println(evento);
      }      
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        actualizarVista();
    }
  
  void actualizarVista(){
      juegoModel.getJugadorActual().toString();
      juegoModel.getCasillaActual().toString();
  } 
}
