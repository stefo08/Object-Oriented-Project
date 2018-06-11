package Controller;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.util.List;

import Model.DAO.DAOFactory;
import Model.DAO.UserDAO;
import Model.VO.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller_User {
	
	private DAOFactory mysqlfactory;
	private UserDAO userdao;
	private static User log;
	
	public Controller_User() {
		
		mysqlfactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		userdao = mysqlfactory.getUserDAO();
				
	}
	
	public boolean isAllRightsInput(String us, String pass) {
		boolean isall;
		if(us.isEmpty() || pass.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore Accesso");
			alert.setHeaderText("Errore dati Input");
			alert.setContentText("I campi Username e Password devono essere non vuoti");
			alert.showAndWait();
			isall = false;
		}
		else isall = true;
		return isall;
		
	}
	
	public boolean isAllRightsInputReg(String nm, String cn, String em, String us, String pass) {
		boolean isall;
		if(nm.isEmpty() || cn.isEmpty() || em.isEmpty() || us.isEmpty() || pass.isEmpty() ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Errore Dati in Input");
			alert.setHeaderText("I campi di registrazioni sono vuoti");
			alert.setContentText("Inserisci i valori e riprova");
			alert.showAndWait();
			isall = false;
		}
		else isall = true;
		return isall;
		
	}
	
	public boolean ExistUser(String us, String pass) {
		if (userdao.CorrectLogInput(us, pass)) return true;
		
		else {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Accesso Negato");
			alert.setHeaderText("Errore accesso al sistema");
			alert.setContentText("Username o password inseriti errati, riprova");
			alert.showAndWait();
			return false;			
		}
	}
	
	public boolean AlreadyUser(String us) {
		
		if (!(userdao.VerificaUser(us))) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Registrazione effettuata");
			alert.setHeaderText(null);
	    	alert.setContentText("Utente registrato correttamente nel sistema");
	    	alert.showAndWait();
		    return true; 
		    }
		else {
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("Errore registrazione");
			alert2.setHeaderText("Username già presente nel sistema");
			alert2.setContentText("Modifica user o prova a loggarti");
			alert2.showAndWait();
			return false;
		}
	}
	
	public void RegisterUser(String n, String sur, String em, String us, String pass) {
		userdao.registraUtente(n, sur, em, us, pass);
	}
	
	public boolean CanUpload() {
		if (!(log.getRuolo().equals("Uploader") || log.getRuolo().equals("Admin"))){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Accesso Negato");
			alert.setHeaderText("Errore accesso schermata");
			alert.setContentText("Non puoi accedere a questa funzionalità: "
					+ "non hai i permessi necessari");
			alert.showAndWait();
			return false;
		}
		else return true;
	}
	
	public boolean CanAddPage() {
		if ((log.getRuolo().equals("Revisore") || log.getRuolo().equals("Admin") 
				|| log.getRuolo().equals("Uploader"))) return true;
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Accesso Negato");
			alert.setHeaderText("Errore accesso schermata");
			alert.setContentText("Non puoi accedere a questa funzionalità: " + "non hai i permessi necessari.");
			alert.showAndWait();
			return false;
		}
	}
	
	public boolean CanReviewPage() {
		
		if (log.getRuolo().equals("Revisore") || log.getRuolo().equals("Admin")) 
			return true;
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Accesso Negato");
			alert.setHeaderText("Errore accesso schermata");
			alert.setContentText("Non puoi accedere a questa funzionalità: "
					+ "non hai i permessi necessari. Effettua la richiesta di collaborazione"
					+ " nella schermata profilo utente");
			alert.showAndWait();
			return false;
		}
		
	}
	
	public boolean CanTrabscribe() {
		
		if (log.getRuolo().equals("Admin") || log.getRuolo().equals("Trascrittore")) 
			return true;
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Accesso Negato");
			alert.setHeaderText("Errore accesso schermata");
			alert.setContentText("Non puoi accedere a questa funzionalità: " + "non hai i permessi necessari.");
			;
			alert.showAndWait();
			return false;
		}
		
	}
	
	public List<User> getTrabscribers(){
		return userdao.getTrabscribelist();
	}
	
	public List<User> getCollaboratorRequest(){
		return userdao.getRequest();
	}
	
	public void AcceptCollaboratorRequest(String user) {
		userdao.changeStatoReq(user);
	}
	
	public void SendCollaboratorRequest() {
		userdao.AddCollabrequest(log.getUser());
	}
	
	public boolean isUtenteBase() {
		if (log.getRuolo().equals("Utente base")) return true;
		else return false;
	}
	
	public boolean isAdmin() {
		if (log.getRuolo().equals("Admin")) return true;
		else return false;
	}
	
	public boolean isManager() {
		if (log.getRuolo().equals("Manager")) return true;
		else return false;
	}
		
	public void setLogger(String us) {
		log = userdao.getUser(us);
	}
		
	public User getLogger() {
		return log;
	}
}
