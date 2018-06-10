package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import Controller.Controller_User;
import Model.VO.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InfoUserController implements Initializable  {
		
	private Controller_User contr;
	private User log;
		
	@FXML
	private Label namelab, userlab, mailab;
	@FXML
	private JFXButton richiesta, richiesta1, viewusers, assegnatrasc;
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		contr = new Controller_User();
		log = contr.getLogger();
		initUserInfo();
		
	}
		
	/*
     * L'utente base può fare richiesta di Collaborazione e diventare Utente Privilegiato; Viene passato l'User dell'utente al Controller_Utenza che 
     * passa l'user al DataBase che aggiorna IDRicheista dell'utente a 1 = "Richiesta Approvazione" nella tabella Richiesta;
     */
    
    @FXML 
    private void RichiestaColl() {
    	
    	contr.SendCollaboratorRequest();
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Messaggio interno");
		alert.setHeaderText(null);
    	alert.setContentText("Richiesta effettuata correttamente: "
    			+ "riceverai presto notizie");
    	alert.showAndWait();
    	
    }
	/*
     * L'Admin può visualizzare le richieste e decidere eventualmente di accettarle o di lasciarle in sospeso.
     */
    
    @FXML
    private void ViewRequest() {
    	
    	 try {
 			FXMLLoader loader = new FXMLLoader();
 			loader.setLocation(getClass().getResource("/Presentation/View/FXML/"
 					+ "CollaboratorRequest.fxml"));
 			Parent root = (Parent) loader.load();
 			Stage req = new Stage();
 		    req.setScene(new Scene (root));
 		    req.setTitle("Visualizza richieste");
 		    req.show();
 		    req.setResizable(false);
    	 } catch (Exception e) {e.printStackTrace();}
    	 
    }
    
    /*
     * L'Admin vede la lista degli utenti iscritti nel sistema: Da Implementare
     */
    
    @FXML
    private void ViewSystemUsers() {
    	
    }
    
    /*
     * Si apre la view che permette al Manager di decidere su quale opera vuole assegnare le relative pagine
     * ai trascrittori. Si apre quindi la View con la lista delle opere che hanno lo stato = "Pubblicata";
     */
    
    @FXML
    private void AssignOpera() {
    	
    	 try {
  			FXMLLoader loader = new FXMLLoader();
  			loader.setLocation(getClass().getResource("/Presentation/View/FXML/"
  					+ "AssignOpera.fxml"));
  			Parent root = (Parent) loader.load();
  			Stage req = new Stage();
  		    req.setScene(new Scene (root));
  		    req.setTitle("Assegna Opere");
  		    req.show();
  		    req.setResizable(false);
     	 } catch (Exception e) {e.printStackTrace();}
    	 
    }
    
    /*
     * Il metodo inizializza le info dell'Utente loggato. Vengono inoltre gestiti i pulsanti in base all'utenza.
     * Se l'user è un Admin allora vengono mostrati i pulsanti di "Accetta richiesta" e "Visualizza Lista Utenti"
     * Se utente base allora il pulsante per effettuare la richiesta; Se menager il pulsante per assegnare le pagine
     * ai trascrittori;
     */
    
    public void initUserInfo() {
		
		namelab.setText(log.getNome().toUpperCase() +" " + log.getCognome().toUpperCase());
        mailab.setText("Email:  " +log.getMail());
        userlab.setText("Username:  " +log.getUser());
        if (!(contr.isUtenteBase())) richiesta.setVisible(false);
        if (contr.isAdmin()) {
			richiesta1.setVisible(true);
			viewusers.setVisible(true);
		}	
		else {
			richiesta1.setVisible(false);
		    viewusers.setVisible(false);
	    }	
		if (contr.isManager()) {
			assegnatrasc.setVisible(true);
		} else {
			assegnatrasc.setVisible(false);
		}
	}

    
		
	}


	
		

