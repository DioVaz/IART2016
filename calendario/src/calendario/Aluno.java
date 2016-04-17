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
    int ano;

    Aluno(int id, String nome, int ano){
        this.id=id;
        this.nome=nome;
        this.ano=ano;
    }
    
    int getID(){return id;}
    int getAno(){return ano;}
    String getNome(){return nome;}
    
}
