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
    boolean recurso;
    
    Exame(int id, String cadeira,int IDinscritos[], boolean recurso){
        this.id=id;
        this.cadeira=cadeira;
        this.IDinscritos=IDinscritos;
        this.recurso=recurso;
        
    }
}
