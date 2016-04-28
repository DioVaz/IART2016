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
        this.forca = 9999;
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
    public int numDias(){return dias.length;}

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
    
    int postoDia(int pos){
        int hora = pos % 3;
        return (pos-hora)/3;
    }
    
    int postoHora(int pos){
        return pos%3;
    }

    Calendario_id crossOver(Calendario_id calendarioB, int pos) {
        Calendario_id novo = new Calendario_id(numDias());
        int hora = postoHora(pos);
        int dia = postoDia(pos);
        novo.crossOverAux(dias,dia,hora,true);
        novo.crossOverAux(calendarioB.dias,dia,hora,false);
        return novo;
    }

    private void crossOverAux(Dia[] dias, int dia, int hora, boolean b) {
        if(b){
            int i=0;
            for(;i<dia;i++){
                addDia(dias[i],i);
            }
            for(int j=0;j<hora+1;j++){
                addHora(dias[i],i,j);
            }
        }
        else{
            int i=dia;
            for(int j=dia;j<3;j++){
                addHora(dias[i],i,j);
            }
            for(;i<dias.length;i++){
                addDia(dias[i],i);
            }
             
        }
    }

    private void addDia(Dia dia, int i) {
        dias[i]=dia;
    }

    private void addHora(Dia dia, int i, int j) {
        addExame(dia.getCadeira(j),i,j);
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
    public int getForca(){
        return forca;
    }
    public Boolean checkValidadeIDs(){
        for(int i = 0; i<numExames()-1;i++){
            String cadeira = getCadeira(postoDia(i),postoHora(i));
            if(!"0".equals(cadeira)){
                for(int j = i+1; j<numExames();j++){
                    String cadeiraB = getCadeira(postoDia(j),postoHora(j));
                    if(cadeira.equals(cadeiraB)) return false;
                }
            }
        }
        return true;
    }
   
}
