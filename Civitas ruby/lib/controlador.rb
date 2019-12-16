require_relative "operaciones_juego"
require_relative "civitas_juego"
require_relative "vista_textual"
require_relative "operacion_inmobiliaria"
require_relative "gestiones_inmobiliarias"

module Civitas
class Controlador
  def initialize(juego, vista)
    @civitas = juego
    @vista = vista
  end
  
  def juega
    @vista.setCivitasJuego(@civitas)
    
    while(!@civitas.finalDelJuego)
      @vista.actualizarVista
      @vista.pausa
      operacion = @civitas.siguientePaso
      @vista.mostrarSiguienteOperacion(operacion)
      
      if(operacion != Operaciones_juego::PASAR_TURNO)
        @vista.mostrarEventos
      end
      
      if(!@civitas.finalDelJuego)
        if(operacion == Operaciones_juego::COMPRAR)
          respuesta = @vista.comprar
          
          if(respuesta == Respuestas::SI)
            @civitas.comprar
            @civitas.siguientePasoCompletado(operacion)
          end
          
        elsif (operacion == Operaciones_juego::GESTIONAR)
          @vista.gestionar
          gestion = @vista.getGestion
          propiedad = @vista.getPropiedad
          inmobiliaria = Operacion_inmobiliaria.new($lista_Gestiones[gestion], propiedad)
          
          if(inmobiliaria.getGestion == Gestiones_inmobiliarias::TERMINAR)
            @civitas.siguientePasoCompletado(operacion)
          elsif (inmobiliaria.getGestion == Gestiones_inmobiliarias::CANCELAR_HIPOTECA)
            seleccion = @vista.seleccionPropiedad
            @civitas.cancelarHipoteca(seleccion)
          elsif (inmobiliaria.getGestion == Gestiones_inmobiliarias::CONSTRUIR_CASA)
            seleccion = @vista.seleccionPropiedad
            @civitas.construirCasa(seleccion)
          elsif (inmobiliaria.getGestion == Gestiones_inmobiliarias::CONSTRUIR_HOTEL)
            seleccion = @vista.seleccionPropiedad
            @civitas.construirHotel(seleccion)
          elsif (inmobiliaria.getGestion == Gestiones_inmobiliarias::HIPOTECAR)
            seleccion = @vista.seleccionPropiedad
            @civitas.hipotecar(seleccion)
          elsif (inmobiliaria.getGestion == Gestiones_inmobiliarias::VENDER)
            seleccion = @vista.seleccionPropiedad
            @civitas.vender(seleccion)
          elsif (inmobiliaria.getGestion == Gestiones_inmobiliarias::REGALAR_CASA)
            jugadores = @civitas.getJugadores
            
            if(@civitas.getJugadorActual == jugadores[0])
              @civitas.regalarPropiedad(@civitas.getJugadorActual, jugadores[1])
            else
              @civitas.regalarPropiedad(@civitas.getJugadorActual, jugadores[0])
            end
          end
          
        elsif (operacion == Operaciones_juego::SALIR_CARCEL)
          carcel = @vista.salirCarcel
          
          if(carcel == Salidas_carcel::PAGANDO)
            @civitas.salirCarcelPagando
          elsif (carcel == Salidas_carcel::TIRANDO)
            @civitas.salirCarcelTirando
          end
        end
      end
    end
    
    @civitas.ranking
  end
end
end
