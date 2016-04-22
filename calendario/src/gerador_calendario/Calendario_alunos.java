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
        Inscritos[] alunos;
        public Dia() {
           Inscritos novo[] = {null,null,null}; 
           this.alunos=novo;
        }
        
        public void add(int ins[], int pos){
            //verificar se pos valido
            if(pos>2){return;}
            //adicionar
            Inscritos novo= new Inscritos(ins);
            alunos[pos]=novo;
        }
    }

    private static class Inscritos {
        int inscritos[];
        public Inscritos(int inscritos[]){
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
    }
    
    public int getForca(){
        //substituir forca por contagem de alunos prejudicados
        return forca;
    }
}
