package civitas;
/*
public class TestP1 {
    public static void main(String[] args) {
        int empieza, tirada = 0, ultimoResultado;
        int num1, num2, num3, num4;
        boolean salidaCarcel;
        String evento;
        Tablero tablero = new Tablero(5);
        
        num1 = num2 = num3 = num4 = 0;
        
        for(int i = 0; i < 100; i++){
            empieza = (Dado.getInstance()).quienEmpieza(4);
            if(empieza == 1)
                num1++;
            else if(empieza == 2)
                num2++;
            else if(empieza == 3)
                num3++;
            else if(empieza == 4)
                num4++;
            else 
                System.out.println("Empieza un jugador diferente, ERROR");
        }
        
        System.out.println("Jugador 1:   " + num1 + " veces");
        System.out.println("Jugador 2:   " + num2 + " veces");
        System.out.println("Jugador 3:   " + num3 + " veces");
        System.out.println("Jugador 4:   " + num4 + " veces");
        System.out.println();
        
        //Modificamos el estado debug del dado para realizar las pruebas
        (Dado.getInstance()).setDebug(false);
        
        tirada = (Dado.getInstance()).tirar();
        
        System.out.println("Valor de la tirada " + tirada);
        System.out.println();
        
        //Probamos que salirDeLaCarcel funcione correctamente
        salidaCarcel = (Dado.getInstance()).salgoDeLaCarcel();
        
        System.out.println("Sale de la carcel " + salidaCarcel);
        System.out.println();
        
        //Probamos que ultimoResultado funcione correctamente
        ultimoResultado = (Dado.getInstance()).getUltimoResultado();
        
        System.out.println("Valor del ultimo resultado " + ultimoResultado);
        System.out.println();
        
        //Probamos un valor de cada tipo enumerado
        System.out.println("Valor del tipo enumerado " + TipoCasilla.CALLE);
        System.out.println("Valor del tipo enumerado " + TipoCasilla.SORPRESA);
        System.out.println("Valor del tipo enumerado " + TipoCasilla.JUEZ);
        System.out.println("Valor del tipo enumerado " + TipoCasilla.IMPUESTO);
        System.out.println("Valor del tipo enumerado " + TipoCasilla.DESCANSO);
        System.out.println();
        
        //Probamos los metodos de diario
        (Diario.getInstance()).ocurreEvento("Esto es un evento de prueba");
        
        while((Diario.getInstance()).eventosPendientes()){
            evento = (Diario.getInstance()).leerEvento();
            System.out.println(evento);
        }
        
        System.out.println();
        
        //Probamos el tablero
        Casilla casilla1 = new Casilla("Casilla 1");
        Casilla casilla2 = new Casilla("Casilla 2");
        tablero.añadeCasilla(casilla1);
        tablero.añadeCasilla(casilla2);
        
        int pos = tablero.calcularTirada(4, 5);
        System.out.println("La nueva posicion es " + pos);
    }
}
*/