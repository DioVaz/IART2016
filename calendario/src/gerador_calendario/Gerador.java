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
        
    /* *** BASE DE DADOS E VARIAVEIS DE SISTEMA *** */
    Calendario baseDados;
    Calendario_id[] populacaoNormal;
    Calendario_id[] populacaoRecurso;
    int populacaoInicial = 10;
    int numMutantes = 2;
    int dias = 20;
    int horas_por_dia=3;
    int maxAlunosPrejudicados = 0;
    int max_geracoes = 20;
    int tentativa_por_bot = 10000;
    int num_max_iteracoes = 90000;
    int maximo_tentativas_mutacao = 3000;
    
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
        //começa por ler ficheiros da base de dados e criar estruturas auxiliares
        this.baseDados = new Calendario();
        this.anos= new Calendario_ano(dias);
        this.alunos = new Calendario_alunos(dias);
        this.cadeiras= baseDados.getCadeiras();
    }
    
    /*
        *** PROCURAR SOLUÇÃO ***
    */
    public void solve(){
        /*
            Época normal
        */
        System.out.println("*** EPOCA NORMAL ***");
        geraPopulacaoInicial(true);
        ordena_populacao(true);
        int geracao = 0;
        while(populacaoNormal[0].getForca()>maxAlunosPrejudicados && geracao<max_geracoes){
            System.out.println("GERACAO: "+geracao+" forca = "+populacaoNormal[0].getForca());
            geraPopulacaoMutante(true);
            ordena_populacao(true);
            geracao++;
        }
        System.out.println("GERACAO: "+geracao+" forca = "+populacaoNormal[0].getForca());
        /*
            Época de recurso
        */
        System.out.println("*** EPOCA RECURSO ***");
        geraPopulacaoInicial(false);
        ordena_populacao(false);
        geracao = 0;
        while(populacaoRecurso[0].getForca()>maxAlunosPrejudicados && geracao<max_geracoes){
            System.out.println("GERACAO: "+geracao+" forca = "+populacaoRecurso[0].getForca());
            geraPopulacaoMutante(false);
            ordena_populacao(false);
            geracao++;
        }
        System.out.println("GERACAO: "+geracao+" forca = "+populacaoRecurso[0].getForca());
        System.out.println("*** TERMINADO ***");
    }
    
    /*
        *** CRIAR POPULAÇÃO ***
    */
    void geraPopulacaoInicial(Boolean epoca){
        //estruturas para guardar os diferentes especimes
        if (epoca) {populacaoNormal = new Calendario_id[populacaoInicial+numMutantes];}
        else {populacaoRecurso = new Calendario_id[populacaoInicial+numMutantes];}
        //loop de criação de especimes
        for(int i = 0; i<populacaoInicial; i++){
            System.out.println("Individuo: "+i);
            //gerar um novo calendario
            geraCalendario(epoca);
            if (epoca){populacaoNormal[i] = ids;}
            else {populacaoRecurso[i] = ids;}
        }
        //ordenar por forca
        //a implementar--- ordena populacao
    }
    
    void geraPopulacaoMutante (Boolean epoca){
        int mutantes = 0;
        int tentativas = 0;
        while(tentativas<maximo_tentativas_mutacao && mutantes<numMutantes){
            if(epoca){
                populacaoNormal[populacaoInicial+mutantes] = geraMutante(epoca);
                if(populacaoNormal[populacaoInicial+mutantes]==null){
                    System.out.println("mutante null");
                    tentativas++;
                }
                else{
                    System.out.println("Mutante "+mutantes+" criado com sucesso.");
                    mutantes++;
                }
            }
            else{
                populacaoRecurso[populacaoInicial+mutantes] = geraMutante(epoca);
                if(populacaoRecurso[populacaoInicial+mutantes]==null){
                    System.out.println("mutante null");
                    tentativas++;
                }
                else{
                    System.out.println("Mutante "+mutantes+" criado com sucesso.");
                    mutantes++;
                }
            }  
        }
    }
    
    /*
        *** FUNÇÕES AUXILIARES PARA CRIAR POPULAÇÃO ***
    */
    
    //NOVO ESPECIME
    void geraCalendario(Boolean epoca){
        //CRIA UM CALENDARIO ALEATORIAMENTE E CRIA ESTRUTURAS AUXILIARES
        int tentativas = 0;
        while(!randomCalendario(epoca)){
            System.out.println("tentativa: "+tentativas);
            tentativas++;
            if(tentativas>tentativa_por_bot){
                System.out.println("Numero de tentativas ultrapassado para criar especime, altere valores de configuracao em Gerador.java");
                return;
            }  
        }
        //CONTA ALUNOS PREJUDICADOS POR EXAMES MUITO PROXIMOS
        ids.setForca(alunos.getForca());
    }
    
    public boolean randomCalendario(boolean epoca) {
        this.ids = new Calendario_id(dias);
        this.anos= new Calendario_ano(dias);
        this.alunos = new Calendario_alunos(dias);
        int count=0;
        int iteracoes = 0;
        while(count<cadeiras.length){
            iteracoes++;
            if(iteracoes>num_max_iteracoes) return false;
            int dia = (int)(Math.random() * dias-1);
            int hora =(int)(Math.random() * 2);
            if(check_if_empty_and_valid(cadeiras[count],dia,hora)){
                String cadeiraID = ids.getCadeira(dia, hora);
                Exame exame = baseDados.getExame(cadeiraID, epoca);
                anos.addExame(baseDados.getAnoExame(cadeiraID), dia, hora);
                alunos.addInscritos(exame.getAlunos(), dia, hora);
                count++;
                
            }
        }
        return true;
    }
     
    boolean check_if_empty_and_valid(String cadeira, int dia, int hora) {
        int anoCadeira = baseDados.getAnoExame(cadeira);
        String ncadeira = ids.getCadeira(dia,hora);
        if("0".equals(ncadeira)){//EMPTY
            if(anos.valid_insertion_day(anoCadeira, dia)){//VALID
                ids.addExame(cadeira,dia,hora);
                return true;
            }    
        }
        int nova_hora = anos.alternative_hour(dia);
        if(nova_hora>0){//VALID ALTERNATIVE HOUR
            if(anos.valid_insertion_day(anoCadeira, dia)){
                ids.addExame(cadeira,dia,hora);
                return true;
            }   
        }
            
        return false;
    }
    //_________________________________________
    
    //NOVO MUTANTE
    int[] randomList(){
        int[] idCalendarios = {-1,-1};
        for(int i =0;i<2;i++){
            int newPos = (int)(Math.random() * populacaoInicial-1);
            while(!randomListAux(idCalendarios,newPos)){
                newPos = (int)(Math.random() * populacaoInicial-1);
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
    
    Calendario_id geraMutante(Boolean epoca){
        //CRIA UM NOVO CALENDARIO APARTIR DE CROSS OVER COM OUTRO CALENDARIO_ID
        int[] idCalendarios = randomList();
        if (epoca){
            Calendario_id novo = crossOver(populacaoNormal[idCalendarios[0]], populacaoNormal[idCalendarios[1]]);
            if(novo!=null){
                geraAuxiliares(novo, epoca);
                novo.setForca(alunos.getForca());
                return novo;
            }
            return null;
        }
        else{
            Calendario_id novo = crossOver(populacaoRecurso[idCalendarios[0]], populacaoRecurso[idCalendarios[1]]);
            if(novo!=null){
                geraAuxiliares(novo, epoca);
                novo.setForca(alunos.getForca());
                return novo;
            }
            return null;
        }
    }
    
    private void geraAuxiliares(Calendario_id exames, Boolean epoca){
        this.anos= new Calendario_ano(dias);
        this.alunos = new Calendario_alunos(dias);
        for(int i = 0; i<dias;i++){
            for(int j = 0; j<3; j++){
                String cadeiraID = exames.getCadeira(i,j);
                if(!"0".equals(cadeiraID)){
                    Exame exame = baseDados.getExame(cadeiraID, epoca);
                    anos.addExame(baseDados.getAnoExame(cadeiraID), i, j);
                    alunos.addInscritos(exame.getAlunos(), i, j);
                }
            }
        }
        
    }
    
    private Calendario_id crossOver(Calendario_id velho, Calendario_id velhoB) {
       //Ponto de corte aleatorio
        int pos = (int)(Math.random() * dias);
        Calendario_id novo = velho.crossOver(velhoB, pos);
        if(novo.checkValidadeIDs()){return novo;}
        return null;
    }
    //_________________________________________
    
    //SELECÇÃO NATURAL (Ordenação da população do mais forte para o mais fraco)
    public void ordena_populacao(boolean epoca){
        Calendario_id [] novo = new Calendario_id[populacaoInicial+numMutantes];
        int pos = 0;
        int forca = 0;
        int posT = -1;
        int contaPopulacao = contaPopulacao(epoca);
        while(pos<contaPopulacao){
            posT = findNext(epoca, contaPopulacao, forca, posT);
            if(epoca){
                novo[pos]=populacaoNormal[posT];
                forca =populacaoNormal[posT].getForca();
            }
            else{
                novo[pos]=populacaoRecurso[posT];
                forca =populacaoRecurso[posT].getForca();
            }
            pos++;
        }
        if(epoca){populacaoNormal=novo;}
        else{populacaoRecurso=novo;}
    }
    
    private int findNext(boolean epoca, int numPopulacao, int forca, int posT){
        if(posT==numPopulacao-1){
            posT=-1;
            forca++;
        }
        posT++;
        int actual =0;
        for (int i = posT;i<numPopulacao;i++){
            actual=i;
            if(epoca){
                if(populacaoNormal[i].getForca()==forca){return i;}
                if (i==numPopulacao-1){
                    i=-1;
                    forca++;
                }
            }
            else{
                if(populacaoRecurso[i].getForca()==forca){return i;}
                if (i==numPopulacao-1){
                    i=-1;
                    forca++;
                } 
            }
        }
        return actual;
    }
    
    private int contaPopulacao(boolean epoca){
        if(epoca){
            for (int i = 0; i<populacaoNormal.length;i++){
                if(populacaoNormal[i]==null) return i;
            }
            return populacaoNormal.length;
        }
        else{
            for (int i = 0; i<populacaoRecurso.length;i++){
                if(populacaoRecurso[i]==null) return i;
            }
            return populacaoRecurso.length;
        }
    }
    //_________________________________________
    
    //INSERÇÃO DE ALUNOS NA BASE DE DADOS
    public void generateBots(int i) {
        baseDados.generateBots(i);
        baseDados.writeFiles();
    }

    /*
        *** IMPRESSÃO DOS DADOS NO ECRÃ ***
    */
    public int getDias(){return dias;}
    
    public String[] getHeaders(){
        String[] novo = new String[dias+1];
        for(int i=0;i<novo.length;i++){
            if(i==0){novo[i]="Hora";}
            else{novo[i]="Dia "+i;}
        }
        return novo;
    }
    public Object [][] getTab(Boolean epoca){
        Object [][] novo = new Object[horas_por_dia+1][dias+1];
        for(int i = 0; i<horas_por_dia;i++){
           for(int j = 0; j<=dias;j++){
               int turno =i+1;
               if (j==0){novo[i][j]="Turno "+turno;}
               else{
                   if (epoca){novo[i][j]=baseDados.getNomeCadeira(populacaoNormal[0].getCadeira(j-1, i));}
                   else{ novo[i][j]=baseDados.getNomeCadeira(populacaoRecurso[0].getCadeira(j-1, i));}
               }
           } 
        }
        return novo;
    }
    
}
