# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#encoding: utf-8

#Se omite el atributo random ya que no es necesario para generar aleatorios.

require "singleton"

module Civitas
class Dado
    include Singleton
    
    @@salidaCarcel = 5
    
  def initialize
    @ultimoResultado = 0
    @debug = false
    @diario = Diario.instance
  end
  
  def tirar()
    aleatorio = 1
    
    if(!@debug)
      aleatorio = rand(1..6)
    end
    
    @ultimoResultado = aleatorio
    return aleatorio
  end
  
  def salgoDeLaCarcel
    valor = tirar
    if(valor == @@salidaCarcel)
      return true
    else
      return false
    end
  end
  
  def quienEmpieza(n)
    aleatorio = rand(1..(n-1))
    return aleatorio
  end
  
  def setDebug(d)
    @debug = d
    @diario.ocurre_evento("\nEl modo debug de dado se pone a " + @debug.to_s)
  end
  
  def getUltimoResultado
    return @ultimoResultado
  end
  
end
end
