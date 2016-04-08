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
    
    Exame(int id, String cadeira,int IDinscritos[], boolean recurso){
        this.id=id;
        this.cadeira=cadeira;
        this.IDinscritos=IDinscritos;
        this.epoca=recurso;
    }
    
    int getID(){return id;}
    String getCadeira(){return cadeira;}
    String getinscritos(){
        String inscritos="";
        for(int i = 0; i<IDinscritos.length;i++){
            inscritos=inscritos+"-"+IDinscritos[i];
        }
        return inscritos;
    }
    String getEpoca(){
        if(epoca){return "NORMAL";}
        else{return "RECURSO";}
    }
}
