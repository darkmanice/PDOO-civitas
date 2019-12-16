require_relative "vista_textual"
require_relative "civitas_juego"
require_relative "controlador"

module Civitas
  class Juego_texto

    def self.main   
      nombres = []
        
      nombres << "Javi"
      nombres << "Pedro"
      nombres << "Antonio"
    
      vista = Vista_textual.new
      civitas = Civitas_juego.new(nombres)
      controlador = Controlador.new(civitas, vista)
      controlador.juega
    end
  end
  
  Juego_texto.main
end
