require_relative "tipo_casilla"

module Civitas
class Casilla
  @@carcel = 5
  
  def initialize(nombre, titulo, importe, numCasilla, mazo, tipo)
    @nombre = nombre
    @mazo = mazo
    @importe = importe
    @tipo = tipo
    @sorpresa = nil
    
    if(tipo == Tipo_casilla::CALLE)
      @tituloPropiedad = titulo

    end
  end
  
  def self.nombre(nombre)
    self.new(nombre, nil, nil, @@carcel, nil, Tipo_casilla::DESCANSO)
  end
  
  def self.titulo(titulo)
    self.new(titulo.getNombre, titulo, nil, @@carcel, nil, Tipo_casilla::CALLE)
  end
  
  def self.cantidad(cantidad, nombre)
    self.new(nombre, nil, cantidad, @@carcel, nil, Tipo_casilla::IMPUESTO)
  end
  
  def self.casillaCarcel(numCasillaCarcel, nombre)
    self.new(nombre, nil, nil, numCasillaCarcel, nil, Tipo_casilla::JUEZ)
  end
  
  def self.mazo(mazo, nombre)
    self.new(nombre, nil, nil, @@carcel, mazo, Tipo_casilla::SORPRESA)
  end
  
  
  
  
  
  private
  
  def recibeJugador_calle(iactual, todos)
    if(jugadorCorrecto(iactual, todos))
      informe(iactual, todos)
      jugador = todos[iactual]
      
      if(!@tituloPropiedad.tienePropietario)
        jugador.puedeComprarCasilla
      else
        @tituloPropiedad.tramitarAlquiler(jugador)
        
        if(@tituloPropiedad.tienePropietario)
          if(!@tituloPropiedad.esEsteElPropietario(jugador))
            precio = @tituloPropiedad.getPrecioAlquiler
            jugador.pagaAlquiler(precio)
            @tituloPropiedad.getPropietario.recibe(precio)
          end
        end
      end
    end
  end
  
  def recibeJugador_impuesto(iactual, todos)
    if (jugadorCorrecto(iactual, todos))
      informe()
      todos[iactual].pagaImpuesto(@impuesto)
    end
  end
  
  def recibeJugador_juez(iactual, todos)
    if (jugadorCorrecto(iactual, todos))
      informe(iactual, todos)
      todos[iactual].encarcelar(@@carcel)
    end
  end
  
  def recibeJugador_sorpresa(iactual, todos)
    if(jugadorCorrecto(iactual, todos))
      sorpresa = @mazo.siguiente()
      informe(iactual, todos)
      sorpresa.aplicarAJugador(iactual, todos)
    end
  end
  
    
  def informe(iactual, todos)
    Diario.instance.ocurre_evento("\nEl jugador " + todos[iactual].getNombre + " cae en" + toString())
  end
  
  
  
  
  public
  
  def getNombre()
    return @nombre
  end 
  
    
  def jugadorCorrecto(iactual, todos)
    if(iactual < todos.length)
      return true
    else
      return false
    end
  end
  
    
  def toString()
    cadena = "\nNombre: " + @nombre +
      "\nTipo: " + @tipo.to_s
    
    return cadena
  end
  
  def getTituloPropiedad
    return @tituloPropiedad
  end
  
  def recibeJugador(iactual, todos)
    if(@tipo == Tipo_casilla::CALLE)
      recibeJugador_calle(iactual, todos)
    elsif (@tipo == Tipo_casilla::IMPUESTO)
      recibeJugador_impuesto(iactual, todos)
    elsif (@tipo == Tipo_casilla::JUEZ)
      recibeJugador_juez(iactual, todos)
    elsif (@tipo == Tipo_casilla::SORPRESA)
      recibeJugador_sorpresa(iactual, todos)
    else
      informe(iactual, todos)
    end
  end
end
end
