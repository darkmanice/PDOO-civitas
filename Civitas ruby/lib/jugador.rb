#encoding: utf-8

module Civitas
class Jugador
  @@CASASMAX = 4
  @@CASASPORHOTEL = 4
  @@HOTELESMAX = 4
  @@PASOPORSALIDA = 1000
  @@PRECIOLIBERTAD = 200
  @@SALDOINICIAL = 7500 
  
  def initialize(propiedades, encarcelado, nombre, numCasillaActual, puedeComprar, salvoconducto)
    @propiedades = propiedades
    @encarcelado = encarcelado
    @nombre = nombre
    @numCasillaActual = numCasillaActual
    @puedeComprar = puedeComprar
    @saldo = @@SALDOINICIAL
    @salvoconducto = salvoconducto
  end
  
  def cancelarHipoteca(ip)
    result = false;
    
    if(@encarcelado)
      return result
    end
    
    if(existeLaPropiedad(ip))
      propiedad = @propiedades[ip]
      cantidad = propiedad.getImporteCancelarHipoteca
      puedoGastar = puedoGastar(cantidad)
      
      if(puedoGastar)
        result = propiedad.cancelarHipoteca
        result = false
        
        if(propiedad.getHipotecado)
          if(propiedad.esEsteElPropietario(self))
            paga(propiedad.getImporteCancelarHipoteca)
            propiedad.hipotecar
            result = true
          end
        end
        
        if(result)
          Diario.instance.ocurre_evento("El jugador " + @nombre + " cancela la hipoteca de la propiedad " + ip.to_s)
        end
      end
    end
    
    return result
  end
  
  def cantidadCasasHoteles
    cantidad = 0
    
    for propiedad in @propiedades
      cantidad = cantidad + propiedad.cantidadCasasHoteles;
    end
    
    return cantidad
  end
  
  def comprar(titulo)
    result = false
    
    if(@encarcelado)
      return result
    end
    
    if(@puedeComprar)
      precio = titulo.getPrecioCompra
      
      if(puedoGastar(precio))
        result = titulo.comprar(self)
        result = false
        
        if(!titulo.tienePropietario)
          titulo.actualizaPropietarioPorConversion(self)
          result = true
          paga(precio)
        end
        
        if(result)
          @propiedades << titulo
          Diario.instance.ocurre_evento("\nEl jugador " + @nombre + " compra la propiedad " + titulo.getNombre)
        end
        @puedeComprar = false
      end
    end
    
    return result
  end
  
  
  #EL JUGADOR NO PAGA POR EDIFICAR?
  def construirCasa(ip)
    result = false
    puedoEdificarCasa = false
    
    if(@encarcelado)
      return result
    end
    
    if(!@encarcelado)
      existe = existeLaPropiedad(ip)
      
      if(existe)
        propiedad = @propiedades[ip]
        puedoEdificarCasa = puedoEdificarCasa(propiedad)
        precio = propiedad.getPrecioEdificar
        
        if(puedoGastar(precio) && @@CASASMAX > propiedad.getNumCasas)
          puedoEdificarCasa = true
        end
        
        if(puedoEdificarCasa)
          result = propiedad.construirCasa(ip)
          paga(precio)
          
          if(result)
            Diario.instance.ocurre_evento("\nEl jugador " + @nombre + " construye casa en la propiedad " + ip.to_s)
          end
        end
      end
    end
    
    return result
  end
  
  def construirHotel(ip)
    result = false
    
    if(@encarcelado)
      return result
    end
    
    if(existeLaPropiedad(ip))
      propiedad = @propiedades[ip]
      puedoEdificarHotel = puedoEdificarHotel(propiedad)
      puedoEdificarHotel = false
      precio = propiedad.getPrecioEdificar
      
      if(puedoGastar(precio))
        if(propiedad.getNumHoteles < @@HOTELESMAX)
          if(propiedad.getNumCasas >= @@CASASPORHOTEL)
            puedoEdificarHotel = true
          end
        end
      end
      
      if(puedoEdificarHotel)
        result = propiedad.construirHotel(ip)
        result = false
        
        if(propiedad.esEsteElPropietario(self))
          paga(precio)
          propiedad.construirHotel(ip)
          result = true
        end
        
        casasPorHotel = getCasasPorHotel
        propiedad.derruirCasas(casasPorHotel, self)
        Diario.instance().ocurre_evento("\nEl jugador " + @nombre + " construye un hotel en la propiedad " + ip.to_s)
      end
    end
    
    return result
  end
  
  def enBancarrota
    return @saldo < 0
  end
  
  
  def encarcelar(numCasillaCarcel)
    if(debeSerEncarcelado())
      moverACasilla(numCasillaCarcel)
      @encarcelado = true
      Diario.instance.ocurre_evento("\nEl jugador: " + @nombre + " entra a la carcel")
    end
    return @encarcelado
  end
  
  def getCasasPorHotel
    return @@CASASPORHOTEL
  end
  
  def getNumCasillaActual
    return @numCasillaActual
  end
  
  def getPuedeComprar
    return @puedeComprar
  end
  
  def hipotecar(ip)
    result = false
    
    if(@encarcelado)
      return result
    end
    
    if(existeLaPropiedad(ip))
      propiedad = @propiedades[ip]
      result = propiedad.hipotecar(self)
      salida = false
      
      if(!propiedad.getHipotecado && propiedad.esEsteElPropietario(self))
        recibe(propiedad.getImporteCancelarHipoteca)
        propiedad.hipotecar(self)
        salida = true
      end
      
      result = salida
    end
    
    if(result)
      Diario.instance.ocurre_evento("\nEl jugador " + @nombre + " hipoteca la propiedad " + ip.to_s)
    end
    
    return result
  end
  
  def self.nombre(nombre)
    self.new([], false, nombre, 0, true, false)
  end
  
  def self.copia(otro)
    @nombre = otro.nombre 
    @propiedades = otro.propiedades
    @encarcelado = otro.encarcelado
    @numCasillaActual = otro.numCasillaActual
    @puedeComprar = otro.puedeComprar
    @saldo = otro.saldo
    @salvoconducto = otro.salvoconducto
    return self
  end
  
  def modificarSaldo(cantidad)
    @saldo = @saldo + cantidad
    Diario.instance.ocurre_evento("\nSaldo de " + @nombre + " modificado a: " + @saldo.to_s)
  end
  
  def moverACasilla(numCasilla)
    if(@encarcelado)
      return false
    else
      @numCasillaActual = numCasilla
      @Comprar = false
      Diario.instance.ocurre_evento("\nEl jugador " + @nombre + " se mueve a la casilla " + numCasilla.to_s)
      
      return true
    end
  end
  
  def obtenerSalvoConducto(sorpresa)
    if(@encarcelado)
      return false
    else
      @salvoconducto = sorpresa
    end
  end
  
  def paga(cantidad)
    valor = modificarSaldo(cantidad * -1)
    
    return valor
  end
  
  def pagaAlquiler(cantidad)
    if(@encarcelado)
      return false
    else
      valor = paga(cantidad)
      return valor
    end
  end
  
  def pagaImpuesto(cantidad)
    if(@encarcelado)
      return false
    else
      valor = paga(cantidad)
      return valor
    end
  end
  
  def pasaPorSalida
    modificarSaldo(@@PASOPORSALIDA)
    Diario.instance.ocurre_evento("\nEl jugador " + @nombre + " pasa por salida")
    return true
  end
  
  def puedeComprarCasilla
    if(@encarcelado)
      @puedeComprar = false
    else
      @puedeComprar = true
    end
    
    return @puedeComprar
  end
  
  def recibe(cantidad)
    if(@encarcelado)
      return false
    else
      valor = modificarSaldo(cantidad)
      return valor
    end
  end
  
  def salirCarcelPagando
    if(@encarcelado && puedeSalirCarcelPagando())
      paga(@@PRECIOLIBERTAD)
      @encarcelado = false
      Diario.instance.ocurre_evento("\nEl jugador " + @nombre + " sale de la carcel pagando")
      return true
    else
      return false
    end
  end
  
  def salirCarcelTirando
    resultado = Dado.salgoDeLaCarcel()
    if(resultado)
      @encarcelado = false
      Diario.instance.ocurre_evento("\nEl jugador " + @nombre + " sale de la carcel tirando")
    end
    
    return @encarcelado
  end
  
  def tieneAlgoQueGestionar
    return @propiedades.length > 0
  end
  
  def tieneSalvoConducto
    if(@salvoconducto == nil)
      return true
    else
      return false
    end
  end
  
  
  
  
  
  
  
  #El titulo de propiedad con numero ip es asi?
  def vender(ip)
    if(@encarcelado)
      return false
    elsif (existeLaPropiedad(ip))
      resultado = @propiedades[ip].vender(self)
      if(resultado)
        @propiedades.delete_at(ip)
        Diario.instance.ocurre_evento("\nSe vende la propiedad")
        return true 
      else
        return false
      end
    else
      return false
    end
  end
  
  private
  
  def existeLaPropiedad(ip)
    return @propiedades.length > ip
  end
  
  def getCasasMax()
    return @@CASASMAX
  end
  
  def getHotelesMax
    return @@HOTELESMAX
  end
  
  def getPrecioLibertad
    return @@PRECIOLIBERTAD
  end  
    
  def getPremioPasoSalida
    return @@PASOPORSALIDA
  end  
    
  def perderSalvoConducto
    @salvoconducto.usada()
    @salvoconducto = nil
  end
  
  def puedeSalirCarcelPagando
    if(@saldo >= @@PRECIOLIBERTAD)
      return true
    else
      return false
    end
  end
      
  def puedoEdificarCasa(propiedad)
    
  end
  
  def puedoEdificarHotel(propiedad)
    
  end
  
  def puedoGastar(precio)
    if(@encarcelado)
      return false
    elsif (@saldo >= precio)
      return true
    else
      return false
    end
  end
  
  public
  
  def <=>(otro)
    return @saldo <=> otro.getSaldo
  end  
    
  def isEncarcelado
    if(@encarcelado)
      return true
    else
      return false
    end
  end
  
  def toString
    cadena = "\nJugador: " + @nombre.to_s +
      "\nSaldo: " + @saldo.to_s + 
      "\nEncarcelado: " + @encarcelado.to_s +
      "\nSalvoconducto: " +@salvoconducto.to_s;
    
    return cadena;
        
  end
  
    
  def debeSerEncarcelado
    if(@encarcelado)
      return true
    elsif (!tieneSalvoConducto())
      return true
    elsif (tieneSalvoConducto())
      perderSalvoConducto()
      Diario.instance.ocurre_evento("\nEl jugador se libra de la cárcel con salvoconducto")
    end
  end
  
  def getNombre
    return @nombre
  end
  
  def getPropiedades
    return @propiedades
  end  
    
  def getSaldo
    return @saldo
  end
  
  def recibeCasa(propiedad)
    if(!@encarcelado)
      @propiedades << propiedad
      return true
    else
      return false
    end
  end
  
  def getPropiedades
    return @propiedades
  end
  
  def eliminaCasa(ip)
    @propiedades.delete_at(ip)
  end
  
end
end
