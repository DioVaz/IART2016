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

    private static class Dia {
        int exames[];
        public Dia() {
           int novo[] = {0,0,0}; 
           this.exames=novo;
        }
        
        public void add(int exame, int pos){
            //verificar se pos valido
            //adicionar
            exames[pos]=exame;
        }
    }
}
