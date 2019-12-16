#encoding: utf-8

require_relative "jugador"
require_relative "gestor_estados"
require_relative "tablero"
require_relative "mazo_sorpresas"
require_relative "estados_juego"
require_relative "dado"
require_relative "sorpresa"
require_relative "tipo_sorpresa"
require_relative "titulo_propiedad"

module Civitas
class Civitas_juego  
  def initialize(nombres)
    @jugadores = []
    @indiceJugadorActual
    @tablero
    @mazo
    @estado
    @gestorEstados 
    
    for jugador in nombres
      insertar = Jugador.nombre(jugador)
      @jugadores << insertar
    end
    
    @gestorEstados = Gestor_estados.new()
    @estado = @gestorEstados.estado_inicial
    @indiceJugadorActual = Dado.instance.quienEmpieza(@jugadores.length)
    @mazo = MazoSorpresas.new()
    @tablero = Tablero.new(5)
    inicializarMazoSorpresas(@tablero)
    inicializarTablero(@mazo)
  end

  
  
  
  private
  
  def avanzaJugador
    jugadorActual = @jugadores[@indiceJugadorActual]
    posicionActual = jugadorActual.getNumCasillaActual
    tirada = Dado.instance.tirar
    posicionNueva = @tablero.nuevaPosicion(posicionActual, tirada)
    casilla = @tablero.getCasilla(posicionNueva)
    contabilizarPasosPorSalida(jugadorActual)
    jugadorActual.moverACasilla(posicionNueva)
    casilla.recibeJugador(@indiceJugadorActual, @jugadores)
    jugadorActual.moverACasilla(posicionNueva)
  end 
  
    
  #es un bucle? cuantas veces?
  def contabilizarPasosPorSalida(jugadorActual)
    while(@tablero.getPorSalida() > 0)
      jugadorActual.pasaPorSalida()
    end
  end
  
    
  def inicializarMazoSorpresas(tablero1)
    sorpresa1 = Sorpresa.tableroTexto(Tipo_sorpresa::PAGARCOBRAR, tablero1, 500, "PAGARCOBRAR")
    sorpresa2 = Sorpresa.tableroTexto(Tipo_sorpresa::PAGARCOBRAR, tablero1, -500, "PAGARCOBRAR")
    sorpresa3 = Sorpresa.tableroTexto(Tipo_sorpresa::IRCASILLA, tablero1, 5, "IRCASILLA")
    sorpresa4 = Sorpresa.tableroTexto(Tipo_sorpresa::IRCASILLA, tablero1, 12, "IRCASILLA")
    sorpresa5 = Sorpresa.tableroTexto(Tipo_sorpresa::IRCASILLA, tablero1, 18, "IRCASILLA")
    sorpresa6 = Sorpresa.tableroTexto(Tipo_sorpresa::PORCASAHOTEL, tablero1, 300, "PORCASAHOTEL")
    sorpresa7 = Sorpresa.tableroTexto(Tipo_sorpresa::PORCASAHOTEL, tablero1, -500, "PORCASAHOTEL")
    sorpresa8 = Sorpresa.tableroTexto(Tipo_sorpresa::PORJUGADOR, tablero1, 200, "PORJUGADOR")
    sorpresa9 = Sorpresa.tableroTexto(Tipo_sorpresa::PORJUGADOR, tablero1, -200, "PORJUGADOR")
    sorpresa10 = Sorpresa.tableroSolo(Tipo_sorpresa::SALIRCARCEL, tablero1)
    sorpresa11 = Sorpresa.tableroSolo(Tipo_sorpresa::IRCARCEL, tablero1)
        
    @mazo.alMazo(sorpresa1)
    @mazo.alMazo(sorpresa2)
    @mazo.alMazo(sorpresa3)
    @mazo.alMazo(sorpresa4)
    @mazo.alMazo(sorpresa5)
    @mazo.alMazo(sorpresa6)
    @mazo.alMazo(sorpresa7)
    @mazo.alMazo(sorpresa8)
    @mazo.alMazo(sorpresa9)
    @mazo.alMazo(sorpresa10)
    @mazo.alMazo(sorpresa11)
  end
  
  def inicializarTablero(mazo1)      
    titulo1 = Titulo_propiedad.new("Zero Two", 200, 50, 500, 500, 200)
    titulo2 = Titulo_propiedad.new("Inori Yuzuriha", 200, 50, 500, 500, 200)
    titulo3 = Titulo_propiedad.new("Nezuko Kamado", 300, 100, 700, 700, 300)
    titulo4 = Titulo_propiedad.new("Shouko Nishimiya", 300, 100, 700, 700 , 300)
    titulo5 = Titulo_propiedad.new("Yuno Gasai", 300, 100, 700, 700, 300)
    titulo6 = Titulo_propiedad.new("Isla", 500, 200, 1000, 1000, 500)
    titulo7 = Titulo_propiedad.new("Chizuru Hishiro", 500, 200, 1000, 1000, 500)
    titulo8 = Titulo_propiedad.new("Mikasa Ackerman", 500, 200, 1000, 1000, 500)
    titulo9 = Titulo_propiedad.new("Mitsuri Kanroji", 700, 400, 1500, 1500, 800)
    titulo10 = Titulo_propiedad.new("Shinobu Kochou", 700, 400, 1500, 1500, 800)
    titulo11 = Titulo_propiedad.new("Shinoa Hiiragi", 700, 400, 1500, 1500, 900)
    titulo12 = Titulo_propiedad.new("Krul Tepes", 100, 700, 2000, 2000, 1300)
        
    casilla1 = Casilla.titulo(titulo1);
    casilla2 = Casilla.titulo(titulo2);
    casilla3 = Casilla.titulo(titulo3);
    casilla4 = Casilla.titulo(titulo4);
    casilla5 = Casilla.titulo(titulo5);
    casilla6 = Casilla.titulo(titulo6);
    casilla7 = Casilla.titulo(titulo7);
    casilla8 = Casilla.titulo(titulo8);
    casilla9 = Casilla.titulo(titulo9);
    casilla10 = Casilla.titulo(titulo10);
    casilla11 = Casilla.titulo(titulo11);
    casilla12 = Casilla.titulo(titulo12);
    casilla13 = Casilla.mazo(mazo1, "SORPRESA")
    casilla14 = Casilla.mazo(mazo1, "SORPRESA")
    casilla15 = Casilla.mazo(mazo1, "SORPRESA")
    casilla16 = Casilla.casillaCarcel(5, "JUEZ")
    casilla17 = Casilla.cantidad(500, "IMPUESTO")
    casilla18 = Casilla.nombre("DESCANSO")
        
    @tablero.añadeCasilla(casilla1)
    @tablero.añadeCasilla(casilla2)
    @tablero.añadeCasilla(casilla3)
    @tablero.añadeCasilla(casilla4)
    @tablero.añadeCasilla(casilla5)
    @tablero.añadeCasilla(casilla6)
    @tablero.añadeCasilla(casilla7)
    @tablero.añadeCasilla(casilla8)
    @tablero.añadeCasilla(casilla9)
    @tablero.añadeCasilla(casilla10)
    @tablero.añadeCasilla(casilla11)
    @tablero.añadeCasilla(casilla12)
    @tablero.añadeCasilla(casilla13)
    @tablero.añadeCasilla(casilla14)
    @tablero.añadeCasilla(casilla15)
    @tablero.añadeCasilla(casilla16)
    @tablero.añadeCasilla(casilla17)
    @tablero.añadeCasilla(casilla18)
  end
  
  def pasarTurno
    @indiceJugadorActual = (@indiceJugadorActual + 1)%@jugadores.length
  end
  
  
   
  public
    
  def cancelarHipoteca(ip)
    @jugadores[@indiceJugadorActual].cancelarHipoteca(ip)
  end
      
  def comprar
    jugadorActual = @jugadores[@indiceJugadorActual]
    numCasillaActual = jugadorActual.getNumCasillaActual
    casilla = @tablero.getCasilla(numCasillaActual)
    titulo = casilla.getTituloPropiedad
    res = jugadorActual.comprar(titulo)
  end
  
  def construirCasa(ip)
    @jugadores[@indiceJugadorActual].construirCasa(ip)
  end
  
  def construirHotel(ip)
    @jugadores[@indiceJugadorActual].construirHotel(ip)
  end
  
  def finalDelJuego
    for jugador in @jugadores
      if(jugador.getSaldo() < 0)
        return true
      end
    end
    return false
  end
  
  def getCasillaActual
    casilla = getJugadorActual.getNumCasillaActual
    return @tablero.getCasilla(casilla)
  end
  
  def getJugadorActual
    return @jugadores[@indiceJugadorActual]
  end
      
  def infoJugadorTexto
    
  end
  
  def salirCarcelPagando
    @jugadores[@indiceJugadorActual].salirCarcelPagando()
  end
  
  def salirCarcelTirando
    @jugadores[@indiceJugadorActual].salirCarcelTirando()
  end
  
  def siguientePaso
    jugadorActual = @jugadores[@indiceJugadorActual]
    operacion = @gestorEstados.operaciones_permitidas(jugadorActual, @estado)
    
    if(operacion == Operaciones_juego::PASAR_TURNO)
      pasarTurno()
      siguientePasoCompletado(operacion)
    elsif(operacion == Operaciones_juego::AVANZAR)
      avanzaJugador()
      siguientePasoCompletado(operacion)
    end
    
      return operacion
  end

  def siguientePasoCompletado(operacion)
    jugador = @jugadores[@indiceJugadorActual]
    @estado = @gestorEstados.siguiente_estado(jugador, @estado, operacion)
  end
  
  def vender(ip)
    @jugadores[@indiceJugadorActual].vender(ip)
  end  
    
  def hipotecar(ip)
    @jugadores[@indiceJugadorActual].hipotecar(ip)
  end  
    
  def ranking
    top = []
    top = @jugadores
    return top.sort
  end
  
  def regalarPropiedad(jugador_que_da, jugador_que_recibe)
    resultado = false
    propiedades = jugador_que_da.getPropiedades
    propiedades_recibe = jugador_que_recibe.getPropiedades
    
    if(propiedades.length == 0)
      return false
    else
      casa = propiedades[0]
      casa.actualizaPropietarioPorConversion(jugador_que_recibe)
      
      jugador_que_da.eliminaCasa(0)
      propiedades = jugador_que_da.getPropiedades
      
      resultado = jugador_que_recibe.recibeCasa(casa)
      
      propiedades_recibe = jugador_que_recibe.getPropiedades
      
      if(resultado)
        Diario.instance.ocurre_evento("Se regala la propiedad " + casa.getNombre + " a " + jugador_que_recibe.getNombre)
      end
      
      return resultado
    end
  end
  
  def getJugadores
    return @jugadores
  end
  
end
end
