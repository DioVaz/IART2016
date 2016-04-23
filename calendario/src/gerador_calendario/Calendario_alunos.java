/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerador_calendario;

/**
 *
 * @author Diogo
 * @author José
 * @author Renato
 *
 * 
**/
public class Calendario_alunos {
    Dia dias[];
    int forca;
    
    public Calendario_alunos(int dias){
        Dia[] novo = new Dia[dias];
        this.dias=novo;
    }
    
    /**
     *
     * @param ins - array com os id's dos alunos inscritos no exame
     * @param dia - posição no array correspondente ao dia do exame
     * @param hora - posição no array dia correspondente à hora do exame {0-manhã, 1-tarde, 2-fim de tarde}
     */
    public void addInscritos(int ins[], int dia, int hora){
        dias[dia].add(ins, hora);
    }
    
    private static class Dia {
        Horas[] horas;
        public Dia() {
           Horas novo[] = {null,null,null}; 
           this.horas=novo;
        }
        
        public void add(int ins[], int pos){
            //verificar se pos valido
            if(pos>2){return;}
            //adicionar
            Horas novo= new Horas(ins);
            horas[pos]=novo;
        }
        
        public Horas[] getHoras(){return horas;}
        
        public int getRepetidos(Horas[] diaSeguinte){
            int count = 0;
            Horas[] both = concat(diaSeguinte);
            for(int i=0; i<both.length-1;i++){
                for(int j=i+1;j<both.length;j++){
                    int [] inscritosB = both[j].getInscritos();
                    for (int w = 0; w<inscritosB.length;w++){
                       if(both[i].estaInscrito(inscritosB[w])) count++; 
                    }
                }
            }
            return count;
        }
        
        public Horas[] concat(Horas[] b) {
            int aLen = horas.length;
            int bLen = b.length;
            Horas[] c= new Horas[aLen+bLen];
            System.arraycopy(horas, 0, c, 0, aLen);
            System.arraycopy(b, 0, c, aLen, bLen);
            return c;
         }
        
    }
    public int numExames(){return dias.length*3;}
    
    private static class Horas {
        int inscritos[];
        public Horas(int inscritos[]){
            this.inscritos=inscritos;
        }
        Boolean estaInscrito(int idAluno){
            for(int i=0;i<inscritos.length;i++){
                if(inscritos[i]==idAluno){
                    return true;
                }
            }
            return false;
        }
        public int[] getInscritos(){return inscritos;}
    }
    
    public int getForca(){
        int numExames = dias.length*3;
        int count=0;
        for (int i = 1; i<numExames;i++){
            count+=dias[i].getRepetidos(dias[i-1].getHoras());
        }
        return count;
    }
}
