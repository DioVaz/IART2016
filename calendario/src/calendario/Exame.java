/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendario;

/**
 *
 * @author Diogo
 * @author José
 * @author Renato
 */
public class Exame {
    int id;
    String cadeira;
    int IDinscritos[];
    boolean epoca;
    
    public Exame(int id, String cadeira,int IDinscritos[], boolean recurso){
        this.id=id;
        this.cadeira=cadeira;
        this.IDinscritos=IDinscritos;
        this.epoca=recurso;
    }
    
    int getID(){return id;}
    public String getCadeira(){return cadeira;}
    public String getinscritos(){
        String inscritos="";
        for(int i = 0; i<IDinscritos.length;i++){
            inscritos=inscritos+"-"+IDinscritos[i];
        }
        return inscritos;
    }
    public int[] getAlunos(){return IDinscritos;}
    public String getEpoca(){
        if(epoca){return "NORMAL";}
        else{return "RECURSO";}
    }
    public Boolean getEpocaB(){
        return epoca;
    }

    boolean isCadeira(String idCadeira, Boolean epoca) {
        if(this.epoca==epoca)
        return this.cadeira.equals(idCadeira);
        else
        return false;
    }
    
    public void inscreveAluno(int id){      
        int[] myIntArray = {id};
        int aLen = IDinscritos.length;
        int bLen = myIntArray.length;
        int[] c= new int[aLen+bLen];
        System.arraycopy(IDinscritos, 0, c, 0, aLen);
        System.arraycopy(myIntArray, 0, c, aLen, bLen);
        this.IDinscritos= c;
    }
    
    public boolean inscrito(int IDaluno){
        for(int i=0;i<IDinscritos.length;i++){
            if(IDinscritos[i]==IDaluno) return true;
        }
        return false;
    }
}
