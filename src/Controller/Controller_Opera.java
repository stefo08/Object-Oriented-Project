package Controller;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import Model.DAO.DAOFactory;
import Model.DAO.FileSystem;
import Model.DAO.OperaDAO;
import Model.VO.Opera;
import Model.VO.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class Controller_Opera {
	
	private DAOFactory mysqlfactory;
	private OperaDAO operadao;
	private User uploader;
	private FileSystem FS;
	private static Opera operatoup;
	private Controller_User contrus;
	private static Opera op, toReview, toassign, Tosee; 
			
	public Controller_Opera() {
		
		mysqlfactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		operadao = mysqlfactory.getOperaDAO();
		contrus = new Controller_User();
		FS = new FileSystem();
		uploader = contrus.getLogger();
				
	}
	
	public List<Opera> getOpere(String tit){
		return operadao.getOpera(tit);
	}
	
	public void InsertOpera(String t, String aut, String gen, String is, int ap, String ln) {
		
		String path = System.getProperty("user.home") +"/Desktop/Opere/" +t +"/" +"Copertina.jpg";
		System.out.println(path);
		operadao.InsertOP(t, aut, gen, is , ap, ln, uploader.getIDdb() , path);
		operatoup = new Opera();
		operatoup.setTitolo(t);
		
	}
	
	public List<Opera> getOperaToAddPage(){
		
		List<Opera> opereuploader = new ArrayList<Opera>();
		opereuploader = operadao.getOperetoaddPage(uploader.getIDdb());
		return opereuploader;
		
	}
	
	public void CloseOperaUpload(Opera p) {
		
		operadao.CloseUpload(p);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Upload Opera");
		alert.setHeaderText(null);
		alert.setContentText("Opera pubblicata nel sistema");
		alert.showAndWait();
		
	}
	
	public List<Opera> getCloseOpera(){
		return operadao.getCloseOpera();
	}
		
    /*
    * Verifica se i dati inseriti nella View sono non nulli in modo da garantire correttezza nell'Upload
    * dell'Opera nel DataBase
    */
	
	public boolean IsAllrightInputOpera(String t, String a, String l, String da, String isbn, String gen, Image f, Image stand) throws MalformedURLException {
		
		boolean isall;
		if(t.isEmpty() || a.isEmpty() || l.isEmpty() || da.isEmpty() || isbn.isEmpty() || gen.isEmpty() || f.equals(stand)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore Accesso");
			alert.setHeaderText("Errore dati Input");
			alert.setContentText("I campi Titolo, Autore, Lingua, DataPub., isbn, Genere o Copertina devono essere non vuoti");
			alert.showAndWait();
			isall = false;
		}
		else isall = true;
		return isall;
		
	}
	
	public void createOperaDirectory(String tit) {
		FS.CreateOperaDirectory(tit);
	}
	
	public void InsertCopertina(File f) {
		
		FS.InsertCopertina(f, operatoup);
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Operazione Eseguita");
    	alert.setHeaderText(null);
    	alert.setContentText("Nuova opera inserita nel sistema");
    	alert.showAndWait();
	}
	
	public void Publicopera(Opera p) {
		
		operadao.PublicOpera(p);
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Operazione Eseguita");
    	alert.setHeaderText(null);
    	alert.setContentText("Opera pubblicata");
    	alert.showAndWait();
    	
	}
	
	public void setOperaToSee(Opera op) {
		Tosee = op;
	}
	
	public Opera getOperaToSee() {
		return Tosee;
	}
	
	public void setOperaToAssign(Opera ta) {
		toassign = ta;
	}
	
	public Opera getOperaToAssign() {
		return toassign;
	}
	
	public void setOpereToAddPage(Opera o) {
		op = o;
	}
	
	public Opera GetOpera() {
		return op;
	}
	
	public void InitOperaToReview(Opera p) {
		toReview = p;
	}
	
	public Opera getOperaToReview() {
		return toReview;
	}
			
}
