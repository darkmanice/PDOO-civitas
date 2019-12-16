#encoding:utf-8
require_relative "respuestas"
require_relative 'operaciones_juego'
require 'io/console'

module Civitas

  class Vista_textual

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end

    
    def comprar
      opciones = ["NO", "SI"]
      opcion = menu("¿Deseas comprar la casilla?", opciones)
      
      return $lista_Respuestas[opcion]
    end

    
    #Definicion de los atributos sin initilize?
    def gestionar
      opciones = ["VENDER", "HIPOTECAR", "CANCELAR_HIPOTECA", "CONSTRUIR_CASA", "CONSTRUIR_HOTEL" , "TERMINAR", "REGALAR_CASA"]
      
      @iGestion = menu("¿Gestion inmobiliaria elegida?", opciones)
      @iPropiedad = @juegoModel.getJugadorActual.getNumCasillaActual
    end

    def getGestion
      return @iGestion
    end

    def getPropiedad
      return @iPropiedad
    end

    def mostrarSiguienteOperacion(operacion)
      puts("\nSiguiente operacion: " + operacion.to_s)
    end

    def mostrarEventos
      while(Diario.instance.eventos_pendientes)
        evento = Diario.instance.leer_evento
        puts(evento)
      end
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         @iGestion = -1
         @iPropiedad = -1
         actualizarVista
    end

    def actualizarVista
      @juegoModel.getJugadorActual.toString
      @juegoModel.getCasillaActual.toString
    end
    
    def salirCarcel
      opciones = ["Pagando", "Tirando el dado"]
      opcion = menu("Elige la forma para salir de la carcel", opciones)
      
      return $lista_Salidas[opcion]
    end
    
    def seleccionPropiedad
      propiedades = @juegoModel.getJugadorActual.getPropiedades
      opciones = []
      
      for nombre in propiedades
        opciones << nombre.getNombre
      end
      
      ip = menu("¿Que propiedad desea seleccionar?", opciones)
      
      return ip
    end

    
  end

end
