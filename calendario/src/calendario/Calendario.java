/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendario;
import java.io.*;
import java.util.*;
import java.util.logging.*;


/**
 *
 * @author Diogo
 * @author José
 * @author Renato
 */
public class Calendario {
    /*
        *** SAVE FILES ***
    */
    static String Alunos_File = "alunos.txt";
    static String Cadeiras_File = "cadeiras.txt";
    static String Calendario_File = "calendario.txt";
    /*
        *** ARRAYS ***
    */
    static Aluno alunos[];
    static Cadeira cadeiras[];
    static Exame[] exames;

    /*
        *** CONSTRUTOR ***
    */
    public Calendario(){
        readFiles(Alunos_File,Cadeiras_File,Calendario_File);
    }
    
    /**
     *  FUNÇÕES AUXILIARES 
     */
    
    public int getNumExames(){
        return exames.length;
    }
    public Exame getExame(String idCadeira, Boolean epoca){
        for(int i=0; i<exames.length;i++){
            if(exames[i].isCadeira(idCadeira, epoca)){
                return exames[i];
            }
        }
        return null;
    }
    static public int getAnoExame(String IDCadeira){
        for(int i=0; i<cadeiras.length;i++){
            if (cadeiras[i].getId().equals(IDCadeira)){
                return cadeiras[i].getAno();
            }
        }
        return 0;
    }
    
    static public String getNomeCadeira(String IDCadeira){
        for(int i=0; i<cadeiras.length;i++){
            if (cadeiras[i].getId().equals(IDCadeira)){
                return cadeiras[i].getNome();
            }
        }
        return " ";
    }
    
    public String[] getCadeiras(){
        String[] ids = new String[cadeiras.length];
        for(int i = 0; i<cadeiras.length;i++){
                ids[i]=cadeiras[i].getId();
        }
        return ids;
    }
    
    /**
     *  FUNÇÕES AUXILIARES SAVE/LOAD
     */
     /**
     * @param CalendarioFile
     **/
    static void loadCalendario(String CalendarioFile){
        try (BufferedReader br = new BufferedReader(new FileReader(CalendarioFile))) {
            System.out.println("*** EXAMES: ***");
            String line;
            int id =0;
            line = br.readLine();
            int total = Integer.parseInt(line);
            exames = new Exame[total];
            while ((line = br.readLine()) != null) {
                // process the line. Exame(int id, String cadeira,int IDinscritos[], boolean recurso)
                System.out.println(line);
                String[] parts = line.split("-");
                String recurso = parts[0];//NORMAL/RECURSO
                Boolean epoca = recurso.equals("NORMAL");
                String idCadeira = parts[1];//ID CADEIRA
                //INSCRITOS
                int totalIns;
                totalIns = parts.length-2;
                int[] inscritos = new int[totalIns];
                for(int count = 2;count<parts.length;count++){
                    String num = parts[count];
                    inscritos[count-2] = Integer.parseInt(num);
                }
                Exame novo = new Exame(id,idCadeira,inscritos,epoca);
                exames[id]=novo;
                id++;
                }
        }   catch (IOException ex) {
                Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param CadeirasFile
     **/
    static void loadCadeiras(String CadeirasFile){
        try (BufferedReader br = new BufferedReader(new FileReader(CadeirasFile))) {
            System.out.println("*** CADEIRAS: ***");
            String line;
            int i =0;
            line = br.readLine();
            int total = Integer.parseInt(line);
            cadeiras = new Cadeira[total];
            while ((line = br.readLine()) != null) {
                // process the line.
                System.out.println(line);
                String[] parts = line.split("-");
                String part1 = parts[0];
                String part2 = parts[1]; 
                String part3 = parts[2];
                int numero2 = Integer.parseInt(part3);
                Cadeira novo = new Cadeira(part1,part2, numero2);
                cadeiras[i]=novo;
                i++;
                }
        }   catch (IOException ex) {
                Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param AlunosFile
     **/
    static void loadAlunos(String AlunosFile){
        try (BufferedReader br = new BufferedReader(new FileReader(AlunosFile))) {
            System.out.println("*** ALUNOS: ***");
            String line;
            int i =0;
            line = br.readLine();
            int total = Integer.parseInt(line);
            alunos = new Aluno[total];
            while ((line = br.readLine()) != null) {
                // process the line.
                System.out.println(line);
                String[] parts = line.split("-");
                String part1 = parts[0];
                int numero = Integer.parseInt(part1);
                String part2 = parts[1]; 
                String part3 = parts[2];
                int numero2 = Integer.parseInt(part3);
                Aluno novo = new Aluno(numero,part2,numero2);
                alunos[i]=novo;
                i++;
                }
        }   catch (IOException ex) {
                Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    /**
     * @param AlunosFile
     * @param CadeirasFile
     * @param CalendarioFile
     */
    static void readFiles(String AlunosFile, String CadeirasFile, String CalendarioFile){ 
        loadAlunos(AlunosFile);
        loadCadeiras(CadeirasFile);
        loadCalendario(CalendarioFile);
    }
    
    static void saveAlunos(String AlunosFile){
       File file =new File(AlunosFile);
        PrintWriter writer;
        try {
            writer = new PrintWriter(AlunosFile, "UTF-8");
            int total=alunos.length;
            writer.println(total);
            for (int i=0; i<total;i++){
               String registo = alunos[i].getID()+"-"+alunos[i].getNome()+"-"+alunos[i].getAno();
               writer.println(registo); 
            }
            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    static void saveCadeiras(String CadeirasFile){
       File file =new File(CadeirasFile);
        PrintWriter writer;
        try {
            writer = new PrintWriter(CadeirasFile, "UTF-8");
            int total=cadeiras.length;
            writer.println(total);
            for (int i=0; i<total;i++){
               String registo = cadeiras[i].getId()+"-"+cadeiras[i].getNome()+"-"+cadeiras[i].getAno();
               writer.println(registo); 
            }
            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    static void saveCalendario(String CalendarioFile){
       File file =new File(CalendarioFile);
        PrintWriter writer;
        try {
            writer = new PrintWriter(CalendarioFile, "UTF-8");
            int total=exames.length;
            writer.println(total);
            for (int i=0; i<total;i++){
               String registo = exames[i].getEpoca()+"-"+exames[i].getCadeira()+exames[i].getinscritos();
               writer.println(registo); 
            }
            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    /**
     * 
     */
    public static void writeFiles(){ 
        saveAlunos(Alunos_File);
        saveCadeiras(Cadeiras_File);
        saveCalendario(Calendario_File);
   }
    
    /*
        *** GERAR BD ***
    */
    public static void generateBots(int botsPorAno){
        List<Aluno> alunosL = new ArrayList<Aluno>();
        while(botsPorAno>0){
            alunosL.add(novoBot("1",botsPorAno));
            alunosL.add(novoBot("2",botsPorAno));
            alunosL.add(novoBot("3",botsPorAno));
            alunosL.add(novoBot("4",botsPorAno));
            botsPorAno--;
        }
        Aluno[] simpleArray = new Aluno[ alunosL.size() ];
        alunosL.toArray( simpleArray );
        int aLen = alunos.length;
        int bLen = simpleArray.length;
        Aluno[] c= new Aluno[aLen+bLen];
        System.arraycopy(alunos, 0, c, 0, aLen);
        System.arraycopy(simpleArray, 0, c, aLen, bLen);
        alunos=c;
    }
    
    private static Aluno novoBot(String anoS,int aluno) {
        aluno+=10;
        int foo = Integer.parseInt(anoS+aluno);
        int ano = Integer.parseInt(anoS);
        Aluno novo = new Aluno(foo,"Aluno Exemplar",ano);
        inscreveAlunoBot(foo,ano);
        return novo;
    }
    
    private static void inscreveAlunoBot(int idAluno, int ano){
        for(int i=0;i<exames.length;i++){
            String idCadeira;
            idCadeira = exames[i].getCadeira();
            int anoC = getAnoExame(idCadeira);
            if(ano==anoC){
               exames[i].inscreveAluno(idAluno);
           } 
        }
    }
    
    public static void api_inscreve_alunos() throws IOException {
        Calendario novo = new Calendario();
        int opcao=1;
        while(opcao==1){
            System.out.println("*** INSCRICAO ALUNOS: ***");
            System.out.println("Escolher opcao:");
            System.out.println("1 - Novo Aluno"); 
            System.out.println("2 - Sair"); 
            Scanner in = new Scanner(System.in); 
            opcao=in.nextInt();
            if(opcao==1){
                System.out.println("Nome:"); 
                Scanner nomeS = new Scanner(System.in); 
                String nome=nomeS.next();
                System.out.println("Ano:"); 
                Scanner anoS = new Scanner(System.in);
                int ano = anoS.nextInt();
                System.out.println("ID:"); 
                Scanner idS = new Scanner(System.in);
                int id = idS.nextInt();
                while(inscrito(id)){
                    System.out.println("Já existe, manda outro ID:"); 
                    idS = new Scanner(System.in);
                    id = idS.nextInt();
                }
                
                System.out.println(nome);
                Aluno novoAluno = new Aluno (id,nome,ano);
                Aluno[] simpleArray = {novoAluno};
                menuInscreveAluno(id);
                int aLen = alunos.length;
                int bLen = simpleArray.length;
                Aluno[] c= new Aluno[aLen+bLen];
                System.arraycopy(alunos, 0, c, 0, aLen);
                System.arraycopy(simpleArray, 0, c, aLen, bLen);
                alunos=c; 
            }
        }
        novo.writeFiles();
        System.out.println("*** ADEUS TONE ***");
    }
    
    static boolean inscrito(int IDaluno){
        for(int i=0;i<alunos.length;i++){
            if(alunos[i].getID()==IDaluno) return true;
        }
        return false;
    }
    
    static void menuInscreveAluno(int idAluno){
        //NORMAL
        boolean notdone=true;
        System.out.println("*** EPOCA NORMAL ***");
        int count = 7;
        while(notdone && count>0){
            System.out.println("Escolha as cadeiras - "+ count+ " maximo");
            for (int i =0; i<cadeiras.length;i++){
                System.out.println(i+" - "+cadeiras[i].getNome()+" - "+cadeiras[i].getAno());
            }
            System.out.println("Opcao:"); 
            getInput (true, idAluno);
            count--;
        }
        //RECURSO
        notdone=true;
        System.out.println("*** EPOCA RECURSO ***");
        count = 7;
        while(notdone && count>0){
            System.out.println("Escolha as cadeiras - "+ count+ " maximo");
            for (int i =0; i<cadeiras.length;i++){
                System.out.println(i+" - "+cadeiras[i].getNome()+" - "+cadeiras[i].getAno());
            }
            System.out.println("Opcao:"); 
            getInput (false, idAluno);
            count--;
        }
    }
    
    static void getInput (boolean epoca, int idAluno){
        while (true){
            Scanner idS = new Scanner(System.in);
            int idCadeiraS = idS.nextInt();
            if(idCadeiraS<cadeiras.length){
                String idCadeira = cadeiras[idCadeiraS].getId();
                for (int i =0; i<exames.length;i++){
                    if(exames[i].getEpocaB()==epoca && exames[i].getCadeira().equals(idCadeira)){
                        if(!exames[i].inscrito(idAluno)){
                            exames[i].inscreveAluno(idAluno);
                            return;
                        }
                        System.out.println("Aluno ja inscrito nessa cadeira");
                    }
                }
            }
            System.out.println("Input invalido, tente novamente");
        }
    }
}