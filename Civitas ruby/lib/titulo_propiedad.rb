#encoding: utf-8

module Civitas
class Titulo_propiedad
  @@factorInteresesHipoteca = 1.1
  
  def initialize(nombre, precioAlquiler, factor, precioHipoteca, precioCompra, precioEdificar)
    @nombre = nombre
    @alquilerBase = precioAlquiler
    @factorRevalorizacion = factor
    @hipotecaBase = precioHipoteca
    @precioCompra = precioCompra
    @precioEdificar = precioEdificar 
    @hipotecado = false
    @numCasas = 0
    @numHoteles = 0
    @propietario = nil
  end
  
  

  
  private
  
  def getImporteHipoteca
    return @hipotecaBase
  end  
    
  def getPrecioVenta
    return @precioCompra + (@precioEdificar * @factorRevalorizacion)
  end  
    
  def propietarioEncarcelado
    if(@propietario.isEncarcelado)
      return true
    else
      return false
    end
  end
  
  
  
  
  
  
  public
      
  def getHipotecado
    return @hipotecado
  end
  
  def toString
    puts ("\nCalle: " + @nombre.to_s + 
      "\nPrecio de compra: " + @precioCompra.to_s +
      "\nPropietario: " + @propietario.getNombre.to_s +
      "\nAlquiler base: " + @alquilerBase.to_s +
      "\nPrecio de edificación: " + @precioEdificar.to_s +
      #"\nHipoteca base: " + @hipotecaBase.to_s +
      "\nHipotecado: " + @hipotecado.to_s +
      #"\nFactor de interes hipoteca: " + @factorInteresesHipoteca.to_s +
      "\nNumero de casas: " + @numCasas.to_s +
      "\nNumero de hoteles: " + @numHoteles.to_s
  )
  end
  
    
  def actualizaPropietarioPorConversion(jugador)
    @propietario = jugador
  end
  
  def cancelarHipoteca()
    
  end
  
  def cantidadCasasHoteles()
    return @numCasas + @numHoteles
  end
  
  #COMPLETAR
  def comprar(jugador)
    return true
  end
  
  #COMPLETAR
  def construirCasa(jugador)
    @numCasas = @numCasas + 1
    return true
  end
  
  #COMPLETAR
  def construirHotel(jugador)
    @numHoteles = @numHoteles + 1
    return true
  end
  
  def derruirCasas(n, jugador)
    if(esEsteElPropietario(jugador) && getNumCasas >= n)
      @numCasas = @numCasas - n
      return true
    else
      return false
    end
  end
  
  def getImporteCancelarHipoteca
    return @hipotecaBase * @@factorInteresesHipoteca
  end
  
  def getNombre
    return @nombre
  end
  
  def getNumCasas
    return @numCasas
  end
  
  def getNumHoteles
    return @numHoteles
  end
  
  def getPrecioCompra
    return @precioCompra
  end
  
  def getPrecioEdificar
    return @precioEdificar
  end
  
  def getPropietario
    return @propietario
  end
  
  #COMPLETAR
  def hipotecar(jugador)
    @hipotecado = true
    return true
  end
  
  def tienePropietario
    if(@propietario == nil)
      return false
    else
      return true
    end
  end
 
  def tramitarAlquiler(jugador)
    if(@propietario == -1 && jugador != @propietario)
      jugador.pagaAlquiler
      @propietario.recibe(getPrecioAlquiler)
    end
  end
  
  def vender(jugador)
    if(esEsteElPropietario(jugador) && !getHipotecado)
      jugador.recibe(@precioCompra)
      @propietario = nil
      @numCasas = 0
      @numHoteles = 0
      return true
    else
      return false
    end  
  end
      
  def esEsteElPropietario(jugador)
    if(jugador == @propietario)
      return true
    else
      return false
    end
  end
  
  def getPrecioAlquiler
    if(getHipotecado || propietarioEncarcelado)
      return 0
    else
      return @alquilerBase*(1 + (@numCasas*0.5) + (@numHoteles*2.5))
    end
  end  
  
end
end