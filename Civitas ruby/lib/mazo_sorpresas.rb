module Civitas
class MazoSorpresas
  
  def initialize(d=false)
    @barajada = false
    @usadas = 0
    @debug = d
    @sorpresas = []
    @cartasEspeciales = []
    @ultimaSorpresa = nil
    
    if(@debug)
      Diario.instance.object_id.ocurre_evento("El modo debug de mazo sorpresas se pone a 1")
    end
  end
  
  
  
  public
  
  def alMazo(s)
    if(!@barajada)
      @sorpresas.push(s)
    end
  end
  
  def siguiente()
    if(!@barajado || @sorpresas.length == @usadas)
      if(!@debug)
        @sorpresas = @sorpresas.shuffle
        @usadas = 0
        @barajada = true
      end
    end
    
    @usadas = @usadas + 1
    temporal = @sorpresas[0]
    @sorpresas.delete_at(0)
    @sorpresas.push(temporal)
    @ultimaSorpresa = temporal
        
    return temporal
  end
  
  def inhabilitarCartaEspecial(sorpresa)
    encontrado = @sorpresas.find_index(sorpresa)
    
    if(encontrado)
      carta = @sorpresas[encontrado]
      @sorpresas.delete_at(encontrado)
      @cartasEspeciales.push(carta)
      
      Diario.instance.object_id.ocurre_evento("Se inhabilita una carta especial")
    end
  end
  
  def habilitarCartaEspecial(sorpresa)
    encontrado = @cartasEspeciales.find_index(sorpresa)
    
    if(encontrado)
      carta = @cartasEspeciales[encontrado]
      @cartasEspeciales.delete_at[encontrado]
      @sorpresas.push(carta)
      
      Diario.instance.object_id.ocurre_evento("Se habilita una carta especial")
    end
  end  
end
end
