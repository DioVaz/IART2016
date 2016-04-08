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
public class Aluno {
    int id;
    String nome;

    Aluno(int id, String nome){
        this.id=id;
        this.nome=nome;
    }
    
    int getID(){return id;}
    String getNome(){return nome;}
    
}
