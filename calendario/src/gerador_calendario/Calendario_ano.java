/*
 * Lista com os anos dos exames a realizar numa determinada época
 * Consite num array de arrays de inteiros, cada array representa um dia e os inteiros correspondem ao ano dos exames
 */
package gerador_calendario;

/**
 *
 * @author Diogo
 * @author José
 * @author Renato
 */
public class Calendario_ano {
    Dia dias[];
    
    public Calendario_ano(int dias){
        Dia[] novo = new Dia[dias];
        this.dias=novo;
    }
    
    /**
     *
     * @param exame - ano do exame
     * @param dia - posição no array correspondente ao dia do exame
     * @param hora - posição no array dia correspondente à hora do exame {0-manhã, 1-tarde, 2-fim de tarde}
     */
    public void addExame(int exame, int dia, int hora){
        dias[dia].add(exame, hora);
    }
    
    private static class Dia {
        int exames[];
        public Dia() {
           int novo[] = {0,0,0}; 
           this.exames=novo;
        }
        
        public void add(int exame, int pos){
            //verificar se pos valido
            if(pos>2){return;}
            //adicionar
            exames[pos]=exame;
        }
    }
    
    public Boolean checkValidadeAnos() {
        //verificar se existem exames do mesmo ano com menos de 2 dias de diferença
        return true;
    }
}
