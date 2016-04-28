/*
 * Gerador contem um calendario e as funções para ordenação das cadeiras
 */
package gerador_calendario;
import calendario.Calendario;
import calendario.Exame;
import java.util.Arrays;

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
    int dias = 10;
    int maxAlunosPrejudicados = 0;
    
    /* *** ESTRUTURAS AUXILIARES *** */
    /*Calendarios com os anos dos exames*/
    Calendario_ano anos;
    /*Calendarios com os IDs das cadeiras*/
    Calendario_id ids;
    /*Calendarios com os IDs dos alunos inscritos*/
    Calendario_alunos alunos;
    /*Lista de cadeiras*/
    String[] cadeiras;
    
    
    /*
        *** CONSTRUTOR ***
    */
    public Gerador(){
        //começa por ler ficheiros da base de dados
        this.baseDados = new Calendario();
    }
    
    /*
        *** PROCURAR SOLUÇÃO ***
    */
    public void solve(){
        /*
            Época normal
        */
        geraPopulacaoInicial(true);
        //ordenar vector ids por força sortList();
        Arrays.sort(populacaoNormal);
        while(getForcaMelhorEspecime().getForca()>maxAlunosPrejudicados){
            int[] idCalendarios = randomList();
            geraPopulacaoMutante(idCalendarios,true);
            //ordenar vector ids por força sortList();
        }
        
        /*
            Época de recurso
        */
        geraPopulacaoInicial(false);
        //ordenar vector ids por força sortList();
        Arrays.sort(populacaoRecurso);
        while(getForcaMelhorEspecime().getForca()>maxAlunosPrejudicados){
            int[] idCalendarios = randomList();
            geraPopulacaoMutante(idCalendarios,false);
            //ordenar vector ids por força sortList();
        }
    }
    
    /*
        *** FUNÇÕES AUXILIARES DE CRIAR POPULAÇÃO ***
    */
    void geraPopulacaoInicial(Boolean epoca){
        int tentativas=0;
        if (epoca) {populacaoNormal = new Calendario_id[populacaoInicial+numMutantes];}
        else {populacaoRecurso = new Calendario_id[populacaoInicial+numMutantes];}
        for(int i = 0; i<populacaoInicial; i++){
            //adicionar um novo calendario
            while(!geraCalendario(epoca))tentativas++;
            if (epoca){populacaoNormal[i] = ids;}
            else {populacaoRecurso[i] = ids;}
        }
        //ordenar por forca
        //a implementar--- ordena populacao
    }
    
    void geraPopulacaoMutante (int idCalendarios[], Boolean epoca){
        for(int i= 0; i<idCalendarios.length;i++){
            //a modificar por aceitar null
            if(epoca){
                populacaoNormal[populacaoInicial+i] = geraMutante(populacaoNormal[idCalendarios[i]], i, epoca);
                if(populacaoNormal[populacaoInicial+i]==null){
                    System.out.println("mutante null");
                }
            }
            else{
                populacaoRecurso[populacaoInicial+i] = geraMutante(populacaoRecurso[idCalendarios[i]], i, epoca);
                if(populacaoRecurso[populacaoInicial+i]==null){
                    System.out.println("mutante null");
                }
            }
        }
    }
    
    int[] randomList(){
        int[] idCalendarios = new int[numMutantes];
        for(int i =0;i<numMutantes;i++){
            int newPos = (int)(Math.random() * numMutantes-1);
            while(randomListAux(idCalendarios,newPos)){
                newPos = (int)(Math.random() * numMutantes-1);
            }
            idCalendarios[i]=newPos;
        }
        return idCalendarios;
    }
    
    private boolean randomListAux(int[] idCalendarios, int newPos) {
        for(int i=0;i<idCalendarios.length;i++){
            if(idCalendarios[i]==newPos) return false;
        }
        return true;
    }
    
    Calendario_id geraMutante(Calendario_id velho, int pos, Boolean epoca){
        //CRIA UM NOVO CALENDARIO APARTIR DE CROSS OVER COM OUTRO CALENDARIO_ID
        if (epoca){
            for(int i = 0; i<populacaoNormal.length;i++){
                if(i!=pos){
                    Calendario_id novo = crossOver(velho, populacaoNormal[i]);
                    geraAuxiliares(novo, epoca);
                    if(anos.checkValidadeAnos()){
                        novo.setForca(alunos.getForca());
                        return novo;
                    }
                }
            }
        }
        else{
            for(int i = 0; i<populacaoRecurso.length;i++){
               if(i!=pos){
                    Calendario_id novo = crossOver(velho, populacaoRecurso[i]); 
                    geraAuxiliares(novo, epoca);
                    if(anos.checkValidadeAnos()){
                        novo.setForca(alunos.getForca());
                        return novo;
                    }
               } 
            }
        }
        //caso não consiga mutar o calendario escolhido com outro existente devolve null
        return null;
    }
    
    Boolean  geraCalendario(Boolean epoca){
       //CRIA UM CALENDARIO ALEATORIO
       ids = randomCalendario();
       //GERAR AUXILIARES
       geraAuxiliares(ids, epoca);
       //VERIFICA SE CALENDÁRIO É VALIDO - Sem exames do mesmo ano com menos de dois dias de distancia
       if(anos.checkValidadeAnos()){
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

    private Calendario_id randomCalendario() {
        Calendario_id novo = new Calendario_id(dias);
        int count=0;
        while(count<cadeiras.length){
            int dia = (int)(Math.random() * dias-1);
            int hora =(int)(Math.random() * 2);
            if(novo.check(cadeiras[count],dia,hora)){
                count++;
            }
        }
        return novo;
    }

    private Calendario_id crossOver(Calendario_id velho, Calendario_id velhoB) {
       //Ponto de corte aleatorio
        int posFinal = ((dias-1)*3)+2;
        int pos = (int)(Math.random() * posFinal);
        //tenta fazer crossover no ponto random para à frente
        for(int i = pos; i < posFinal;i++){
            Calendario_id novo = velho.crossOver(velhoB,i);
            if(novo.checkValidadeIDs()){
                return novo;
            }
        }
        for(int i = pos-1; i <= 0;i--){
            Calendario_id novo = velho.crossOver(velhoB,i);
            if(novo.checkValidadeIDs()){
                return novo;
            }
        }
        return null; 
    }

    private Calendario_id getForcaMelhorEspecime() {
        int[] powerArray = new int[populacaoNormal.length];
        for( int i=0;i<populacaoNormal.length;i++){
            powerArray[i]=populacaoNormal[i].getForca();
        }
        Arrays.sort(powerArray);
        return findMelhor(powerArray[0]);
    }

    private Calendario_id findMelhor(int j) {
        for (Calendario_id populacaoNormal1 : populacaoNormal) {
            if (populacaoNormal1.getForca() == j) {
                return populacaoNormal1;
            }
        }
        return null;
    }

    
}
