package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import Model.VO.Opera;
import Model.VO.Page;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FileSystem {
	
 private Opera op;
 
 public FileSystem() {
	 
 }

 /*
  * Passo al metodo il titolo dell'opera inserita e creo una cartella "Nome Opera" 
  * dentro la cartella Opere dove verranno memorizzate le pagine con le relative
  * trascrizioni
  */
 public void CreateOperaDirectory(String tit){
	
	try {
	String url = System.getProperty("user.home") +"/Desktop/Opere/" + tit +"/";
	Path path = (Path) Paths.get(url);
	   if (!Files.exists(path)) {
		Files.createDirectory(path);
        System.out.println("Directory created");
     } else System.out.println("Directory Alreadyexist");
	
} catch (IOException e) {}
}
 /*
  * Memorizza la scansione passata dall'utente nella Directory dell'opera corrispondente
  * alla scansione salvando la scansione come "Pagina" + numero pagine ES: "Pagina1"
  * per favorire il recupero successivo dal DB. Viene inoltre creato un file Trascrizione(numero pagina).txt
  * che servirà successivamente per la trascrizione della pagina stessa;
  */
 
 public void InsertPageImage(File f, Opera p, String n){
	 
	  op = p;
	  try {
	    String path = System.getProperty("user.home") +"/Desktop/Opere/" +op.getTitolo() +"/";
	    Files.copy(f.toPath(),
	    		(new File(path + "Pagina" +n +".jpg")).toPath(),
	    		StandardCopyOption.REPLACE_EXISTING);
	    String url = System.getProperty("user.home") +"/Desktop/Opere/" + op.getTitolo() + "/Trascrizione" +n +".txt";
		Path pathtra = (Path) Paths.get(url);
		if (!(Files.exists(pathtra))){
			Files.createFile(pathtra);
		}
	  } catch (IOException exc) {exc.printStackTrace();
	  Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Errore inserimento");
		alert.setHeaderText("Errore inserimento pagina nel sistema");
		alert.setContentText("Non è stato possibile aggiungere la pagina nel sistema, Errore interno."
				+ "riprovare");
		alert.showAndWait();} 
	  
	}
 
 /*
  * Il metodo prende in Input un file Image della copertina dell'opera e lo memorizza
  * nella directory della stessa
  */
 
 public void InsertCopertina(File f, Opera p) {
	 op = p;
	 try {
		    String path = System.getProperty("user.home") +"/Desktop/Opere/" +op.getTitolo() +"/";
		    Files.copy(f.toPath(),
		    		(new File(path + "Copertina.jpg")).toPath(),
		    		StandardCopyOption.REPLACE_EXISTING);
		  } catch (IOException exc) {exc.printStackTrace();
		  Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore inserimento");
			alert.setHeaderText("Errore inserimento Copertina Opera");
			alert.setContentText("Non è stato possibile aggiungere la copertina selezionata nel sistema, Errore interno."
					+ "riprovare");
			alert.showAndWait();}
		}
 
 /*
  *Il metodo salva il file della pagina nella cartella Corretta nel formato "Trascrizione(numeropagina).txt
  *Inizialmente il file è vuoto. Questo per permettere la visualizzazione del file di testo nella ricerca anche
  *se la pagina non è stata ancora trascritta. Inoltre per permettere ai trascrittore di lavorarci; 
  */
 
 public void InsertTranscription(File f, int n, String tit) {
	 
	 try {
		 String path = System.getProperty("user.home") +"/Desktop/Opere/" +tit +"/";
		 Files.copy(f.toPath(), (new File(path + "Trascrizione" +n +".txt")).toPath(),
 		StandardCopyOption.REPLACE_EXISTING);
	 }catch (IOException exc) {exc.printStackTrace(); System.out.println("Errore salvataggio file");
	 Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Errore inserimento");
		alert.setHeaderText("Errore inserimento Trascrizione");
		alert.setContentText("Non è stato possibile aggiungere la trascrizione selezionata nel sistema, Errore interno."
				+ "riprovare");
		alert.showAndWait();}
 }
 
 public String getTranscriptionCompleteText(Page p) {
		
		String text = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(p.getTrascr().getPath()));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine(); 
			while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
			String everything = sb.toString();
			text = everything;
		} catch (IOException e) {e.printStackTrace();}
		finally {
			try {
			if (br != null) br.close();
		} catch (IOException exc) {exc.printStackTrace();}
		}
		return text;
		
	}
 
 public String getTranscriptionText(Page toTranscrib) {
		
		String text = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(toTranscrib.getTrascr().getPath()));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine(); 
			while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
			String everything = sb.toString();
			text = everything;
		} catch (IOException e) {e.printStackTrace();}
		finally {
			try {
			if (br != null) br.close();
		} catch (IOException exc) {exc.printStackTrace();}
		}
		return text;
		
	}
 
 }

