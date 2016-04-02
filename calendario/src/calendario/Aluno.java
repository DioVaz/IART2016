/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendario;

/**
 *
 * @author Diogo
 */
public class Aluno {
    int id;
    String nome;
    Cadeira epoca_normal[];
    Cadeira epoca_recurso[];

    Aluno(int id, String nome){
        this.id=id;
        this.nome=nome;
        epoca_normal=null;
        epoca_recurso=null;
    }
    
    void inscricao(Cadeira epoca_normal[], Cadeira epoca_recurso[]){
        this.epoca_normal=epoca_normal;
        this.epoca_recurso=epoca_recurso;
    }
}
