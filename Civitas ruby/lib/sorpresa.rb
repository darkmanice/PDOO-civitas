module Civitas
class Sorpresa
  
  def initialize(tipo, tablero, valor, texto, mazo)
    @tipo = tipo
    @tablero = tablero
    @valor = valor
    @texto = texto
    @mazo = mazo
  end
  
  def self.tableroSolo(tipo, tablero)
    self.new(tipo, tablero, -1, nil, nil)
  end
  
  def self.tableroTexto(tipo, tablero, valor, texto)
    self.new(tipo, tablero, valor, texto, nil)
  end
  
  def self.valor(tipo, valor, texto)
    self.new(tipo, nil, valor, texto, nil)
  end
  
  def self.mazo(tipo, mazo)
    self.new(tipo, nil, -1, nil, mazo)
  end
  
  
  
  
  private
  
   def aplicaAJugador_irACasilla(actual, todos)
    if(jugadorCorrecto(actual, todos))
      jugador = todos[actual]
      informe(actual, todos)
      casillaActual = jugador.getNumCasillaActual
      tirada = @tablero.calcularTirada(casillaActual, @valor)
      nuevaPosicion = @tablero.nuevaPosicion(casillaActual, tirada)
      jugador.moverACasilla(nuevaPosicion)
      @tablero.getCasilla(nuevaPosicion).recibeJugador(actual, todos)
    end
  end
  
  def aplicarAJugador_irACarcel(actual, todos)
    if(jugadorCorrecto(actual, todos))
      jugador = todos[actual]
      informe(actual, todos)
      juador.encarcelar(tablero.getCarcel())
    end
  end
  
  def aplicarAJugador_pagarCobrar(actual, todos)
    if(jugadorCorrecto(actual, todos))
      informe(actual, todos)
      jugador = todos[actual]
      jugador.modificarSaldo(@valor)
    end
  end
  
  def aplicarAJugador_porCasaHotel(actual, todos)
    if(jugadorCorrecto(actual, todos))
      informe(actual, todos)
      jugador = todos[actual]
      numCasasHoteles = jugador
      jugador.modificarSaldo(valor*jugador.cantidadCasasHoteles)
    end
  end
  
  def aplicarAJugador_porJugador(actual, todos)
    if(jugadorCorrecto(actual, todos))
      informe(actual, todos)
      jugador = todos[actual]
      sorpresa1 = valor.new(Tipo_sorpresa::PAGARCOBRAR, @valor*-1, nil)
      
      for i in (1..todos.length)
        if(todos[i] != actual)
          sorpresa1.aplicarAJugador_pagarCobrar(i, todos)
        end
      end
      
      sorpresa2 = valor.new(Tipo_sorpresa::PAGARCOBRAR, @valor*(todos.length - 1), nil)
      sorpresa2.aplicarAJugador_pagarCobrar(actual, todos)
    end
  end
  
  def aplicarAJugador_salirCarcel(actual, todos)
    if(jugadorCorrecto(actual, todos))
      encuentra = false
      informe(actual, todos)
      
      for i in (1..todos.length)
        if(todos[i].tieneSalvoConducto)
          encuentra = true
        end
      end
      
      if(!encuentra)
        todos[actual].obtenerSalvoConducto
        salirDelMazo
      end
    end
  end
      
  def informe(actual, todos)
    Diario.instance.ocurre_evento("\nSe aplica sorpresa al jugador: " + todos[actual].getNombre)
  end
  
  
  
  
  
  public
  
  def jugadorCorrecto(actual, todos)
    if(actual < todos.length)
      return true
    else
      return false
    end
  end
  
  def toString
    return @texto
  end
  
  
    
  def aplicarAJugador(actual, todos)
    if(@tipo == Tipo_sorpresa::IRCASILLA)
      aplicarAJugador_irACasilla(actual, todos)
    elsif (@tipo == Tipo_sorpresa::IRCARCEL)
      aplicarAJugador_irACarcel(actual, todos)
    elsif (@tipo == Tipo_sorpresa::PAGARCOBRAR)
      aplicarAJugador_oagarCobrar(actual, todos)
    elsif (@tipo == Tipo_sorpresa::PORCASAHOTEL)
      aplicarAJugador_porCasaHotel(actual, todos)
    elsif (@tipo == Tipo_sorpresa::PORJUGADOR)
      aplicarAJugador_porJugador(actual, todos)
    elsif (@tipo == Tipo_sorpresa::SALIRCARCEL)
      aplicarAJugador_salirCarcel(actual, todos)
    else
      puts("\n\n\nTipo de sorpresa erronea")
    end
  end
  
  def salirDelMazo
    if(@tipo == Tipo_sorpresa::SALIRCARCEL)
      @mazo.inhabilitarCartaEspecial(self)
    end
  end
  
  def usada
    if(@tipo == Tipo_sorpresa::SALIRCARCEL)
      @mazo.habilitarCartaEspecial(self)
    end
  end
end
end
