package Controller;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.File;
import java.util.List;
import java.util.TreeSet;

import Model.DAO.DAOFactory;
import Model.DAO.FileSystem;
import Model.DAO.PageDAO;
import Model.VO.Opera;
import Model.VO.Page;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class Controller_Page {
	
	private DAOFactory mysqlfactory;
	private PageDAO pagedao;
	private FileSystem FS;
	Controller_Opera controp;
		
	public Controller_Page() {
		
		mysqlfactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		pagedao = mysqlfactory.getPageDAO();
		FS = new FileSystem();
		controp = new Controller_Opera();
		
	}
	
	public boolean insertPage(File f, Image ins, Image std,  String numpage, Opera p) {
		
		boolean insert = false;
		
		if (ins.equals(std)){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Errore dati Input");
			alert.setContentText("Il campo immagine deve essere non vuoto");
			alert.showAndWait();
			
		}
		else {
		
	    
		File b = new File(System.getProperty("user.home") + "/Desktop/Opere/" + p.getTitolo() +"/Trascrizione" +numpage +".txt");	
		File a = new File(System.getProperty("user.home") + "/Desktop/Opere/" + p.getTitolo() +"/Pagina" +numpage +".jpg");
		if (pagedao.insertPage(p.getId() , a.getPath() , numpage, b.getPath())){
		FS.InsertPageImage(f, p, numpage);
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Operazione Eseguita");
    	alert.setHeaderText(null);
    	alert.setContentText("Nuova pagina inserita nell'Opera: " +p.getTitolo());
    	alert.showAndWait();
    	insert = true;
    	
		}
		
		else {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore interno");
			alert.setHeaderText("Errore inserimento pagina");
			alert.setContentText("La pagina numero: " +numpage + " è già presente nel sistema");
			alert.showAndWait();
			
		}
		}
		
		return insert;
		}
		
	public List<Page> getOperaPages(Opera p){
			
			List<Page> temp = pagedao.getOperaPages(p);
			return temp;
			
		}
		
	public TreeSet<Opera> getOpereToRewiew(){
			
			return pagedao.getOperetoReview();
		}
	
	public void AcceptPage(Page p) {
		
		pagedao.acceptPage(p.getID());
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Operazione Eseguita");
    	alert.setHeaderText(null);
    	alert.setContentText("La scansione n°: " +p.getNumeropagina()
    	+" è stata accettata");
    	alert.showAndWait();
    	
	}
	
	public List<Page> getPagineOpereClose(){
		return pagedao.getOperaClosePage(controp.getOperaToAssign());
	}
	
	public void RefusePage(Page p) {
		
		pagedao.refusePage(p.getID());
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Operazione Eseguita");
    	alert.setHeaderText(null);
    	alert.setContentText("La scansione n°: " +p.getNumeropagina()
    	+" è stata rifiutata");
    	alert.showAndWait();
    	
	}
		
}
