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
    public String getEpoca(){
        if(epoca){return "NORMAL";}
        else{return "RECURSO";}
    }
    public Boolean getEpocaB(){
        return epoca;
    }
}
