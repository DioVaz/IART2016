/*
 * Gerador contem um calendario e as funções para ordenação das cadeiras
 */
package gerador_calendario;
import calendario.Calendario;
import calendario.Exame;

/**
 *
 * @author Diogo
 * @author José
 * @author Renato
 */
public class Gerador {
    Calendario baseDados;
    
    /*Calendarios com os anos dos exames*/
    Calendario_ano anos_normal;
    Calendario_ano anos_recurso;
    /*Calendarios com os IDs das cadeiras*/
    Calendario_id epocanormal;
    Calendario_id epocarecurso;
    /*Populacao*/
    Calendario_id[] populacaoNormal;
    Calendario_id[] populacaoRecurso;
    
    /*
        *** CONSTRUTOR ***
    */
    Gerador(){
        //começa por ler ficheiros da base de dados e gerar calendarios auxiliares
        this.baseDados = new Calendario();
        leCalendarios();   
    }
    
    /*
        *** CRIAR POPULAÇÃO ***
    */
    void geraPopulacao(){
        int populacaoInicial = 10;
        for(int i = 0; i<populacaoInicial; i++){
            //adicionar um novo calendario
            geraCalendario();
        }
    }
    
    Calendario_id geraMutante(Calendario_id velho){
        //CRIA UM NOVO CALENDARIO APARTIR DE CROSS OVER COM OUTRO CALENDARIO_ID
        Calendario_id novo = null;
        return novo;
    }
    
    void geraCalendario(){
       //CRIA UM CALENDARIO ALEATORIO
       //a implementar
       //VERIFICA SE CALENDÁRIO É VALIDO - Sem exames do mesmo ano com menos de dois dias de distancia
       anos_normal.checkValidade();
       anos_recurso.checkValidade();
       //CONTA ALUNOS PREJUDICADOS POR EXAMES MUITO PROXIMOS
       contaAlunos();
    }
    void contaAlunos(){
        //compara duas listas de inscritos dos varios exames conta os repetidos
    }
    private void leCalendarios(){
        for(int i = 0; i<baseDados.getNumExames();i++){
            int totalNormal=0;
            int totalRecurso=0;
            Exame exame = baseDados.getExame(i);
            if(exame.getEpocaB()){
            //epoca NORMAL
                anos_normal.addExame(baseDados.getAnoExame(exame.getCadeira()), (i-(i%3))/3, i%3);
                epocanormal.addExame(exame.getCadeira(), (i-(i%3))/3, i%3);
                totalNormal++;
            }
            else{
            //epoca RECURSO
                totalRecurso=i-totalNormal;
                anos_recurso.addExame(baseDados.getAnoExame(exame.getCadeira()), (totalRecurso-(totalRecurso%3))/3, totalRecurso%3);
                epocarecurso.addExame(exame.getCadeira(), (totalRecurso-(totalRecurso%3))/3, totalRecurso%3);
            }
        }
        
    }
}
