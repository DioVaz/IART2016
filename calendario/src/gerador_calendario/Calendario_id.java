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
        for(int i =0;i<novo.length;i++){
            novo[i]= new Dia();
        }
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
    
    int postoDia(int pos){
        int hora = pos % 3;
        return (pos-hora)/3;
    }
    
    int postoHora(int pos){
        return pos%3;
    }

    Calendario_id crossOver(Calendario_id calendarioB, int pos) {
        Calendario_id novo = new Calendario_id(numDias());
        novo.crossOverAux(dias,pos,true);
        novo.crossOverAux(calendarioB.dias,pos,false);
        return novo;
    }

    private void crossOverAux(Dia[] dias, int pos, boolean b) {
        if(b){
            for(int i=0;i<pos;i++){
                this.dias[i]=dias[i];
            }
        }
        else{
            for(int i=pos;i<dias.length;i++){
                this.dias[i]=dias[i];
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

        private boolean existe(String cadeira) {
            for(int i = 0; i<exames.length;i++){
                if(cadeira.equals(exames[i]))return true;
            }
            return false;
        }
        
        private int contaCadeiras(){
            int count=0;
           for(int i = 0; i<exames.length;i++){
                if(exames[i]!="0")count++;
            }
            return count; 
        }
    }
    
    public void setForca(int forca){
        this.forca=forca;
    }
    public int getForca(){
        return forca;
    }
    public int contaCadeiras(){
        int contador=0;
        for(int i=0;i<dias.length;i++){
            contador+=dias[i].contaCadeiras();
        }
        return contador;
    }
    public Boolean checkValidadeIDs(String[] cadeiras){
        for(int i = 0; i<numExames()-1;i++){
            String cadeira = getCadeira(postoDia(i),postoHora(i));
            if(!"0".equals(cadeira)){
                for(int j = i+1; j<numExames();j++){
                    String cadeiraB = getCadeira(postoDia(j),postoHora(j));
                    if(cadeira.equals(cadeiraB)) return false;
                }
            }
        }
        for(int i=0;i<cadeiras.length;i++){
            for(int j=0; j<dias.length;j++){
                if(dias[j].existe(cadeiras[i])){j=dias.length;}
                else if(j==dias.length-1){
                    return false;}
            }
        }
        return true;
    }
   
    
}
