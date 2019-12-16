module Civitas
  module Gestiones_inmobiliarias
    VENDER = :vender
    HIPOTECAR = :hipotecar
    CANCELAR_HIPOTECA  = :cancelar_hipoteca
    CONSTRUIR_CASA = :construir_casa
    CONSTRUIR_HOTEL = :construir_hotel
    TERMINAR = :terminar
    REGALAR_CASA = :regalar_casa
  end
  
  $lista_Gestiones = [Gestiones_inmobiliarias::VENDER, Gestiones_inmobiliarias::HIPOTECAR, Gestiones_inmobiliarias::CANCELAR_HIPOTECA,
    Gestiones_inmobiliarias::CONSTRUIR_CASA, Gestiones_inmobiliarias::CONSTRUIR_HOTEL, Gestiones_inmobiliarias::TERMINAR, Gestiones_inmobiliarias::REGALAR_CASA]
end
