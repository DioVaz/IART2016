/*
 * Gerador contem um calendario e as funções para ordenação das cadeiras
 */
package gerador_calendario;
import calendario.Calendario;

/**
 *
 * @author Diogo
 * @author José
 * @author Renato
 */
public class Gerador {
    Calendario calendario;
    
    /*
        *** CONSTRUTOR ***
    */
    Gerador(){
        //começa por ler ficheiros da base de dados
        this.calendario = new Calendario();
    }
}
