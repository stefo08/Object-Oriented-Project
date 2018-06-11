package Controller;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import Model.DAO.DAOFactory;
import Model.DAO.FileSystem;
import Model.DAO.PageDAO;
import Model.DAO.TranscriptionDAO;
import Model.VO.Page;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller_Transcription {
	
	private DAOFactory mysqlfactory;
	private PageDAO pagedao;
	private TranscriptionDAO trandao;
	private FileSystem FS;
	private static Page toTranscrib;
	
	
	public Controller_Transcription() {
		
		mysqlfactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		FS = new FileSystem();
		trandao = mysqlfactory.getTranscriptionDAO();
		pagedao = mysqlfactory.getPageDAO();
		
	}
	
	public void SetTranscriber(int Uspage, int IDPage) {
		
		pagedao.setPageTranscriber(Uspage, IDPage);
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Operazione Eseguita");
    	alert.setHeaderText(null);
    	alert.setContentText("Pagina assegnata con successo");
    	alert.showAndWait();
    	
	}
	
	/*
	 * Il metodo aggiorna il file txt dell'opera in modo da mantenere le modifiche apportate constantemente anche quelle
	 * fatte da più uploader;
	 */
	
	public void SaveTranscription(int numtra, String tit, String text, int intidpage) throws IOException {
		
		String url = "C:/Users/angel/Desktop/Opere/" + tit + "/filetemp.txt";
		Path path = (Path) Paths.get(url);
		if (!(Files.exists(path))){
			Files.createFile(path);
		}
		File f = new File("C:/Users/angel/Desktop/Opere/" +tit +"/filetemp.txt");
		FileWriter fileWriter = new FileWriter(f);
		fileWriter.write(text);
		fileWriter.close();
		FS.InsertTranscription(f, numtra, tit);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Messaggio interno");
		alert.setHeaderText(null);
    	alert.setContentText("Modifiche apportate con successo");
    	alert.showAndWait();
				
	}
	
	/*
	 * Il Controller della View che permette di effettuare la trascrizione richiedere il testo della trascrizione
	 * già presente nella Cartella relativa alla pagina il testo contenuto nel file alla classe FileSystem. Il metodo getTranscriptionText
	 * prende il path della trascrizione e recupera il testo che viene poi restituito al Controller della View che
	 * inizializza la TextArea;
	 */
	
	public String getTranscriptionText() {
		
		return FS.getTranscriptionText(toTranscrib);
		
	}
	
	public String getTranscriptionCompleteText(Page p) {
		
		return FS.getTranscriptionCompleteText(p);
		
	}
	
	
	/*
	 * Quando il revisore decide su quale pagina effettuare la trascrizione, la pagina viene memorizzata nel controller
	 * che inizializza una var. statica. la variabile Page viene poi restituita successivamente;
	 */
	
	public void setPageToTranscribe(Page p){
		toTranscrib = p;
	}

	public Page getPageToTranscribe(){
		return toTranscrib;
	}
	
	public List<Page> getAssignedPage(int id){
		return trandao.getAssignedPages(id);
	}

}
