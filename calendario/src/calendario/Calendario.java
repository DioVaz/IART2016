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
 */
public class Calendario {
    /*
        *** SAVE FILES ***
    */
    static String Alunos_File = "alunos.txt";
    static String Cadeiras_File = "cadeiras.txt";
    static String Exames_File = "exames.txt";
    static String Inscricoes_File = "inscricoes.txt";
    /*
        *** ARRAYS ***
    */
    Aluno alunos[];
    Cadeira cadeiras[];
    Exame epoca_normal[];
    Exame epoca_recurso[];
    
    /**
     * @param AlunosFile
     * @param CadeirasFile
     * @param Inscritos
     * @param Calendario
     */
    public static void readFiles(String AlunosFile, String CadeirasFile, String Inscritos, String Calendario){
        
    //deserialize the alunos file
    try(
      InputStream fileAlunos = new FileInputStream(AlunosFile);
      InputStream buffer = new BufferedInputStream(fileAlunos);
      ObjectInput input = new ObjectInputStream (buffer);
    ){
      //deserialize the List
      List<String> recoveredQuarks = (List<String>)input.readObject();
      //display its data
      for(String quark: recoveredQuarks){
        System.out.println("Recovered Quark: " + quark);
      }
    }
    catch(ClassNotFoundException ex){
      fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    }
    catch(IOException ex){
      fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
    }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //ler ficheiro e preencher array Cadeiras
        readFiles(Alunos_File,Cadeiras_File,Inscricoes_File, Exames_File);
        //ler ficheiro e preencher array Alunos e inicial
        //fazer magia de IART
        //apresentar e guardar resultado
    }
    
     // PRIVATE
    private static final Logger fLogger = Logger.getLogger(Calendario.class.getPackage().getName());
}
