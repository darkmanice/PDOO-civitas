package civitas;

import java.util.ArrayList;

public class Jugador implements Comparable<Jugador> {
    protected static final int CasasMax = 4;
    protected static final int CasasPorHotel = 4;
    protected boolean encarcelado;
    protected static final int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static final float PasoPorSalida = 1000;
    protected static final float PrecioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    private static final float SaldoInicial = 7500;
    private ArrayList<TituloPropiedad> propiedades;
    private Sorpresa Salvoconducto;
    
    
    //propiedad.hipotecar pone hipotecado a true?
    boolean cancelarHipoteca(int ip){
       boolean result = false;
       
       if(encarcelado)
           return result;
       else if(existeLaPropiedad(ip)){
           TituloPropiedad propiedad = propiedades.get(ip);
           float cantidad = propiedad.getImporteCancelarHipoteca();
           boolean puedoGastar = puedoGastar(cantidad);
           
           if(puedoGastar){
               result = propiedad.cancelarHipoteca(this);
               if(!result){
                   if(propiedad.getHipotecado()){
                       if(propiedad.esEsteElPropietario(this)){
                           paga(propiedad.getImporteCancelarHipoteca());
                           propiedad.hipotecar(this);
                           result = true;
                       }
                   }
                       
               } else
                   Diario.getInstance().ocurreEvento("\nEl jugador "+nombre+ " cancela la hipoteca de la propiedad "+ip);
           }
       }
       return result;
    }
    
    
    int cantidadCasasHoteles(){
        int cantidad = 0;
        
        for(int i = 0; i < propiedades.size(); i++)
            cantidad += propiedades.get(i).cantidadCasasHoteles();
        
        return cantidad;
    }
    
    public int compareTo(Jugador otro){
        return Float.compare(saldo, otro.saldo);
    }
    
    boolean comprar(TituloPropiedad titulo){
        boolean result = false;
        
        if(encarcelado){
            return result;
        } else if(puedeComprar){
            float precio = titulo.getPrecioCompra();
            
            if(puedoGastar(precio)){
                result = titulo.comprar(this);
                result = false;
                
                if(!titulo.tienePropietario()){
                    titulo.actualizaPropietarioPorConversion(this);;
                    result = true;
                    paga(titulo.getPrecioCompra());
                }
                
                if(result){
                    propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + " compra la propiedad " + titulo.toString());
                }
                puedeComprar = false;
            }
        }
        return result;
    }
    
    boolean construirCasa(int ip){
        boolean result = false;
        boolean puedoEdificarCasa = false;
        TituloPropiedad propiedad = null;
        
        if(encarcelado)
            return result;
        else{
            boolean existe = existeLaPropiedad(ip);
            
            if(existe)
                propiedad = propiedades.get(ip);
                puedoEdificarCasa = puedoEdificarCasa(propiedad);
                float precio = propiedad.getPrecioEdificar();
                
                if(puedoGastar(precio)){
                    if(propiedad.getNumCasas() < getCasasMax())
                        puedoEdificarCasa = true;
                }
                
                if(puedoEdificarCasa)
                    result = propiedad.construirCasa(this);
                
                if(result)
                    Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " construye una casa en la propiedad " + ip);
        }
        return result;  
    }
    
    //propiedad.constuirHotel para incrementar el numero de hoteles?
    boolean construirHotel(int ip){
        boolean result = false;
        
        if(encarcelado){
            return result;
        } else if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
            puedoEdificarHotel = false;
            float precio = propiedad.getPrecioEdificar();
            
            if(puedoGastar(precio)){
                if(propiedad.getNumHoteles() < getHotelesMax()){
                    if(propiedad.getNumCasas() >= getCasasPorHotel())
                        puedoEdificarHotel = true;
                }
            }
            
            if(puedoEdificarHotel){
                result = propiedad.construirHotel(this);
                result = false;
                
                if(propiedad.esEsteElPropietario(this)){
                    paga(propiedad.getPrecioEdificar());
                    propiedad.construirHotel(this);
                    result = true;
                }
                
                int casasPorHotel = getCasasPorHotel();
                propiedad.derruirCasas(casasPorHotel, this);
                Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " construye hotel en la propiedad " + ip);
            }
        }
        return result;
    }
    
    protected boolean debeSerEncarcelado(){
        if(isEncarcelado())
           return false;
        else if(!tieneSalvoconducto())
            return true;
        else if(tieneSalvoconducto()){
            perderSalvoconducto();
            Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " pierde el salvoconducto y se libra de la cárcel");
            return false;
        } else
            return false;
    }
    
    boolean enBancarrota(){
        if(saldo < 0)
            return true;
        else
            return false;
    }

    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " es encarcelado");
        }
        return encarcelado;
    }
    
    private boolean existeLaPropiedad(int ip){
        return ip < propiedades.size();
    }
    
    private int getCasasMax(){
        return CasasMax;
    }
    
    private int getHotelesMax(){
        return HotelesMax;
    }
    
    int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    protected String getNombre(){
        return nombre;
    }
    
    public int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    private float getPrecioLibertad(){
        return PrecioLibertad;
    }
    
    private float getPremioPasoSalida(){
        return PasoPorSalida;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
    boolean getPuedeComprar(){
        return puedeComprar;
    }
    
    protected float getSaldo(){
        return saldo;
    }
    
    //propiedad.hipotecar para poner hipotecado a true?
    boolean hipotecar(int ip){
        boolean result = false;
        
        if(encarcelado){
            return result;
        }
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip);
            result = propiedad.hipotecar(this);
            boolean salida = false;
            
            if(!propiedad.getHipotecado() && propiedad.esEsteElPropietario(this)){
                recibe(propiedad.getImporteHipoteca());
                propiedad.hipotecar(this);
                salida = true;
            }
            result = salida;
        }
        
        if(result){
            Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " hipoteca la propiedad " + ip);
        }
        
        return result;
    }
    
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    Jugador(String nombre1){
        propiedades = new ArrayList<TituloPropiedad>();
        nombre = nombre1;
        numCasillaActual = 0;
        encarcelado = false;
        saldo = SaldoInicial;
        puedeComprar = true;
        Salvoconducto = null;
    }
    
    protected Jugador(Jugador otro){
        propiedades = otro.propiedades;
        numCasillaActual = otro.numCasillaActual;
        nombre = otro.nombre;
        encarcelado = otro.encarcelado;
        saldo = otro.saldo;
        puedeComprar = otro.puedeComprar;
        Salvoconducto = otro.Salvoconducto;
    }
    
    boolean modificarSaldo(float cantidad){
        saldo += cantidad;
        Diario.getInstance().ocurreEvento("\nSe modifica el saldo del jugador " + nombre + " a " + saldo);
        return true;    
    }
    
    boolean moverACasilla(int numCasilla){
        if(isEncarcelado())
            return false;
        else {
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " se mueve a la casilla " + numCasillaActual);
            return true;
        }
    }
    
    boolean obtenerSalvoConducto(Sorpresa sorpresa){
        if(isEncarcelado())
            return false;
        else {
            Salvoconducto = sorpresa;
            return true;
        }
        
    }
    
    boolean paga(float cantidad){
        return modificarSaldo(cantidad * -1);
    }
    
    boolean pagaAlquiler(float cantidad){
        if(isEncarcelado())
            return false;
        else
            return paga(cantidad);

    }
    
    boolean pagaImpuesto(float cantidad){
        if(isEncarcelado())
            return false;
        else
            return paga(cantidad);

    }
    
    boolean pasoPorSalida(){
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " pasa por salida");
        return true;
    }
    
    private void perderSalvoconducto(){
        Salvoconducto.usada();
        Salvoconducto = null;
    }
    
    boolean puedeComprarCasilla(){
        if(isEncarcelado())
            puedeComprar = false;
        else
            puedeComprar = true;
        
        return puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando(){
        return saldo >= PrecioLibertad;
    }
    
    
    //Completar adecuadamente
    private boolean puedoEdificarCasa(TituloPropiedad titulo){
        if(titulo.getNumCasas() < CasasMax && titulo.getPrecioEdificar() <= saldo)
            return true;
        else
            return false;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad titulo){
        if(titulo.getNumHoteles() < HotelesMax && titulo.getPrecioEdificar() <= saldo)
            return true;
        else
            return false;
    }
    
    private boolean puedoGastar(float cantidad){
        if(isEncarcelado())
            return false;
        else
            return saldo >= cantidad;
    }
    
    boolean recibe(float cantidad){
        if(isEncarcelado())
            return false;
        else
            return modificarSaldo(cantidad);
    }
    
    boolean salirCarcelPagando(){
        if(isEncarcelado() && puedeSalirCarcelPagando()){
            paga(PrecioLibertad);
            encarcelado = false;
            Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " sale de la carcel pagando");
            return true;
        } else
            return false;
    }
    
    boolean salirCarcelTirando(){
        if(isEncarcelado()){
            if(Dado.getInstance().salgoDeLaCarcel()){
                encarcelado = false;
                Diario.getInstance().ocurreEvento("\nEl jugador " + nombre + " sale de la carcel tirando");
            }
        }
        return encarcelado;
    }
    
    boolean tieneAlgoQueGestionar(){
        return propiedades.size() > 0;
    }
    
    boolean tieneSalvoconducto(){
        if(Salvoconducto != null)
            return true;
        else
            return false;
    }
    
    public String toString(){
        String cadena = "\nJugador: " + nombre +
                "\nSaldo: " + saldo + 
                "\nEncarcelado: " + encarcelado +
                "\nSalvoconducto: " + Salvoconducto;
        
        return cadena;
    }
    
    boolean vender(int ip){
        boolean resultado;
        if(isEncarcelado())
            return false;
        else if(existeLaPropiedad(ip)){
            resultado = propiedades.get(ip).vender(this);
            if(resultado){
                propiedades.remove(ip);
                System.out.println("\n\nTAMAÑO PROPIEDADES: " + propiedades.size());
                Diario.getInstance().ocurreEvento("\nSe vende la propiedad");
                return true;
            } else
                return false;
        } else
            return false;
    }
    
    boolean recibeCasa(TituloPropiedad propiedad){
        if(!encarcelado){
            propiedades.add(propiedad);
            return true;
        } else
            return false;
    }
}
