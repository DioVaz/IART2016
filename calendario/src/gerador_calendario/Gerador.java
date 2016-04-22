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
    
    /*Populacao*/
    Calendario_id[] populacaoNormal;
    Calendario_id[] populacaoRecurso;
    int populacaoInicial = 10;
    int numMutantes = 4;
    
    /* *** ESTRUTURAS ALEATOREAS *** */
    /*Calendarios com os anos dos exames*/
    Calendario_ano anos;
    /*Calendarios com os IDs das cadeiras*/
    Calendario_id ids;
    /*Calendarios com os IDs dos alunos inscritos*/
    Calendario_alunos alunos;
    
    
    /*
        *** CONSTRUTOR ***
    */
    Gerador(){
        //começa por ler ficheiros da base de dados
        this.baseDados = new Calendario();
    }
    
    /*
        *** CRIAR POPULAÇÃO ***
    */
    void geraPopulacaoInicial(Boolean epoca){
        if (epoca) {populacaoNormal = new Calendario_id[populacaoInicial+numMutantes];}
        else {populacaoRecurso = new Calendario_id[populacaoInicial+numMutantes];}
        for(int i = 0; i<populacaoInicial; i++){
            //adicionar um novo calendario
            geraCalendario(epoca);
            if (epoca){populacaoNormal[i] = ids;}
            else {populacaoRecurso[i] = ids;}
        }
        //ordenar por forca
        //a implementar--- ordena populacao
    }
    
    void geraPopulacaoMutante (int idCalendarios[]){
        for(int i= 0; i<idCalendarios.length;i++){
            populacaoNormal[populacaoInicial+i] = geraMutante(populacaoNormal[idCalendarios[i]]);
        }
    }
    
    Calendario_id geraMutante(Calendario_id velho){
        //CRIA UM NOVO CALENDARIO APARTIR DE CROSS OVER COM OUTRO CALENDARIO_ID
        Calendario_id novo = null;
        return novo;
    }
    
    Boolean  geraCalendario(Boolean epoca){
       //CRIA UM CALENDARIO ALEATORIO
       //a implementar  UPDTATE(Calendario_id);
       //GERAR AUXILIARES
       geraAuxiliares(ids, epoca);
       //VERIFICA SE CALENDÁRIO É VALIDO - Sem exames do mesmo ano com menos de dois dias de distancia
       if(anos.checkValidade()){
           //CONTA ALUNOS PREJUDICADOS POR EXAMES MUITO PROXIMOS
           ids.setForca(alunos.getForca());
           return true;
       }
       return false;
    }
    
    private void geraAuxiliares(Calendario_id exames, Boolean epoca){
        for(int i = 0; i<exames.numExames();i++){
            for(int j = 0; j<3; j++){
                String cadeiraID = exames.getCadeira(i,j);
                Exame exame = baseDados.getExame(cadeiraID, epoca);
                anos.addExame(baseDados.getAnoExame(cadeiraID), i, j);
                alunos.addInscritos(exame.getAlunos(), i, j);
            }
        }
        
    }
}
