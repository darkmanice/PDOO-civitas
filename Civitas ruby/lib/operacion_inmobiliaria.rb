module Civitas
class Operacion_inmobiliaria
  def initialize(gest, ip)
    @numPropiedad = ip
    @gestion = gest
    
    def getGestion
      return @gestion
    end
    
    def getNumPropiedad
      return @numPropiedad
    end
  end
end
end
