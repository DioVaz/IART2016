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
    int populacaoInicial = 1000;
    int numMutantes = 4;
    int dias = 11;
    int maxAlunosPrejudicados = 0;
    int tentativa_por_bot = 10000;
    int num_max_iteracoes = 90000;
    
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
        solve();
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
                    novo.setForca(alunos.getForca());
                    return novo;
                }
            }
        }
        else{
            for(int i = 0; i<populacaoRecurso.length;i++){
               if(i!=pos){
                    Calendario_id novo = crossOver(velho, populacaoRecurso[i]); 
                    novo.setForca(alunos.getForca());
                    return novo;
               } 
            }
        }
        //caso não consiga mutar o calendario escolhido com outro existente devolve null
        return null;
    }
    
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

    private Calendario_id crossOver(Calendario_id velho, Calendario_id velhoB) {
       //Ponto de corte aleatorio
        int posFinal = ((dias-1)*3)+2;
        int pos = (int)(Math.random() * posFinal);
        //tenta fazer crossover no ponto random para à frente
        for(int i = pos; i < posFinal;i++){
            //Adicionar criação de auxiliares, verificação de validade e calculo de força
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

    public void generateBots(int i) {
        baseDados.generateBots(i);
        baseDados.writeFiles();
    }

    
}
