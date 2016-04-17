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
    public Exame getExame(int pos){
        return exames[pos];
    }
    public int getAnoExame(String IDCadeira){
        for(int i=0; i<cadeiras.length;i++){
            if (cadeiras[i].getId().equals(IDCadeira)){
                return cadeiras[i].getAno();
            }
        }
        return 0;
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
     * @param AlunosFile
     * @param CadeirasFile
     * @param Inscritos
     * @param Calendario
     */
    public static void writeFiles(String AlunosFile, String CadeirasFile, String Calendario){ 
        saveAlunos(AlunosFile);
        saveCadeiras(CadeirasFile);
        saveCalendario(Calendario);
   }
        
    // PRIVATE
    private static final Logger fLogger = Logger.getLogger(Calendario.class.getPackage().getName());
}
