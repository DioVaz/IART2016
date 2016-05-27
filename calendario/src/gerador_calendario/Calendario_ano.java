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
        for(int i =0;i<novo.length;i++){
            novo[i]= new Dia();
        }
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

    boolean valid_insertion_day(int anoCadeira, int dia) {
        if(!dias[dia].valid_insertion(anoCadeira)) return false;
        //dia anterior
        if(dia>0){
            if (!dias[dia-1].valid_insertion(anoCadeira)) return false;
        }
        if(dia<dias.length-1){
            if (!dias[dia+1].valid_insertion(anoCadeira)) return false;
        }
        return true;
    }

    int alternative_hour(int dia) {
        return dias[dia].alternative_hour_aux();
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
        
        public boolean valid_insertion(int anoCadeira){
            for(int i = 0; i<exames.length; i++){
                if(exames[i]==anoCadeira){
                    return false;
                }
            }
            return true;
        }

        private int alternative_hour_aux() {
            for(int i = 0; i<exames.length; i++){
                if(exames[i]==0) return i;
            }
            return 0;
        }
    }
    
    public Boolean checkValidadeAnos(int anoCadeira) {
        //verificar se existem exames do mesmo ano com menos de 2 dias de diferença
        for(int i = 1; i<dias.length;i++){
            int aLen = 3;
            int bLen = 3;
            int[] c= new int[aLen+bLen];
            System.arraycopy(dias[i-1].exames, 0, c, 0, aLen);
            System.arraycopy(dias[i].exames, 0, c, aLen, bLen);
            for (int j = 0; j<c.length-1;j++){
                for (int z = j+1; z<c.length;z++){
                    if(c[j]==c[z]) return false;
                }
            }
        }
        return true;
    }
}
