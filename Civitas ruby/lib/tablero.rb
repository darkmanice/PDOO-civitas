#encoding: utf-8

require_relative "casilla"

module Civitas
class Tablero
  
  def initialize(indice)
    @casillas = []
    @porSalida = 0
    @tieneJuez = false
    @numCasillaCarcel
    
    if(indice >= 1)
      @numCasillaCarcel = indice
    else
      @numCasillaCarcel = 1;
    end
    
    salida = Casilla.nombre("Salida")
    @salida = salida
    @casillas << salida
  end
  
  
  
  
  private
  
  def correcto
    if(@casillas.length > @numCasillaCarcel)
      return true
    else
      return false
    end
  end
  
  def correctoCasilla(numCasilla)
    if(correcto && @casillas.length > numCasilla && numCasilla >= 0)
      return true
    else
      return false
    end
  end
  
  
  
  
  public
  
  def getCasillas
    return @casillas
  end
  
    
  def getCarcel()
    return @numCasillaCarcel
  end
  
  def getPorSalida()
    if(@porSalida > 0)
      aDevolver = @porSalida
      @porSalida = @porSalida - 1
      return aDevolver
    else
      return @porSalida      
    end
  end
  
  #Se elimina la segunda vez que se introduce la carcel
  def añadeCasilla(casilla)
    if(@casillas.length == @numCasillaCarcel - 1)
      carcel = Casilla.casillaCarcel(5, "Carcel")
      @casillas.push(carcel)
    end
    @casillas.push(casilla)

  end
  
  def añadeJuez()
    if(!@tieneJuez)
      juez = Casilla.casillaCarcel(5, "Juez")
      @casillas.push(juez)
      @tieneJuez = true
    end
  end
  
  def getCasilla(numCasilla)
    if(correctoCasilla(numCasilla))
        return @casillas[numCasilla]
    else
      return nil
    end
  end
  
  def nuevaPosicion(actual, tirada)
    if(!correcto())
      return -1
    else
      destino = (actual + tirada) % @casillas.length
      vuelta = actual + tirada
      
      if(destino != vuelta)
        @porSalida = @porSalida + 1
      end
      
      return destino
    end
  end
  
  def calcularTirada(origen, destino)
    tirada = origen - destino
    
    if(tirada < 0)
      tirada = tirada + @casillas.length
    end
    
    return tirada
  end
end
end