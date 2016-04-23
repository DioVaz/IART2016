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
 
class Calendario_id {
    Dia dias[];
    int forca;
    
    public Calendario_id(int dias){
        Dia[] novo = new Dia[dias];
        this.dias=novo;
    }
    
    /**
     *
     * @param exame - ano do exame
     * @param dia - posição no array correspondente ao dia do exame
     * @param hora - posição no array dia correspondente à hora do exame {0-manhã, 1-tarde, 2-fim de tarde}
     */
    public void addExame(String cadeira, int dia, int hora){
        dias[dia].add(cadeira, hora);
    }
    
    public int numExames(){return dias.length*3;}

    String getCadeira(int dia, int hora) {
         String cadeira = dias[dia].getCadeira(hora);
         return cadeira;
    }

    boolean check(String cadeira, int dia, int hora) {
        String ncadeira = dias[dia].getCadeira(hora);
        if("0".equals(ncadeira)){
            addExame(cadeira,dia,hora);
            return true;
        }
        return false;
    }
    
    private static class Dia {
        String exames[];
        public Dia() {
           String novo[] = {"0","0","0"}; 
           this.exames=novo;
        }
        
        public void add(String cadeira, int pos){
            //verificar se pos valido
            if(pos>2){return;}
            //adicionar
            exames[pos]=cadeira;
        }

        private String getCadeira(int hora) {
            return exames[hora];
        }
    }
    
    public void setForca(int forca){
        this.forca=forca;
    }
    
    
}
