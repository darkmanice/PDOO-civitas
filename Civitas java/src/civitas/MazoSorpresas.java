package civitas;
import java.util.ArrayList;
import java.util.Collections;

public class MazoSorpresas{
    ArrayList<Sorpresa> sorpresas;
    boolean barajada;
    int usadas;
    boolean debug;
    ArrayList<Sorpresa> cartasEspeciales;
    Sorpresa ultimaSorpresa;

    private void init(){
        barajada = false;
        usadas = 0;
        sorpresas = new ArrayList();
        cartasEspeciales = new ArrayList();
    }

    MazoSorpresas(boolean valor){
        debug = valor;
        init();
        if(debug)
            (Diario.getInstance()).ocurreEvento("Se modifica el estado debug de MazoSorpresas a" + debug);
    }

    MazoSorpresas(){
        debug = false;
        init();
    }

    void alMazo(Sorpresa s){
        if(!barajada)
            sorpresas.add(s);
    }

    Sorpresa siguiente(){
        if(!barajada || usadas > sorpresas.size()){
            if(!debug){
                Collections.shuffle(sorpresas);
                usadas = 0;
                barajada = true;
                usadas++;
            }
        }
        
        Sorpresa temporal = sorpresas.get(0);
        ultimaSorpresa = temporal;
        sorpresas.remove(0);
        sorpresas.add(temporal);

        return temporal;
    }

    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        boolean contiene;
        contiene = sorpresas.contains(sorpresa);

        if(contiene){
            int indice;
            Sorpresa carta;
            indice = sorpresas.indexOf(sorpresa);
            carta = sorpresas.get(indice);
            sorpresas.remove(sorpresa);
            cartasEspeciales.add(carta);

            (Diario.getInstance()).ocurreEvento("Se inhabilita una carta especial");
        }
    }

    void habilitarCartaEspecial(Sorpresa sorpresa){
        boolean contiene;
        contiene = cartasEspeciales.contains(sorpresa);

        if(contiene){
            int indice;
            Sorpresa carta;

            indice = cartasEspeciales.indexOf(sorpresa);
            carta = cartasEspeciales.get(indice);
            sorpresas.add(carta);

            (Diario.getInstance()).ocurreEvento("Se habilita una carta especial");
        }
    }
}
