package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import Controller.Controller_User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RegisterUserPanelController implements Initializable {
	
	private Controller_User contrus;
	
	@FXML 
	private Label title;
	@FXML
	private JFXTextField userfield, surnfield, namefield, emailfield;
	@FXML
	private JFXPasswordField passfield;
		
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		contrus = new Controller_User();
				      	      	
	}
	
	/*
	 * Il Controller_User verifica che i campi di registrazione siano "Non_vuoti". Se cosi non fosse 
	 * allora mostra un messaggio di errore sullo schermo;
	 */
	
	@FXML
	private void registerButton(ActionEvent event) {
		
		String name = namefield.getText();
		String surn = surnfield.getText();
		String email = emailfield.getText();
		String user = userfield.getText();
		String pass = passfield.getText();
		if(!(contrus.isAllRightsInputReg(name, surn, email, user, pass))){
			namefield.setText("");
			surnfield.setText("");
			emailfield.setText("");
			userfield.setText("");
			passfield.setText("");
			
		}
		else {
			if (contrus.AlreadyUser(userfield.getText())) {
		    contrus.RegisterUser(name, surn, email, user, pass);
		    ComingBackLog();
			}
		}
		
		}
		
	/*
	 * Permette all'utente di tornare nella schermata di Log se lo ritiene opportuno; 
	 */
	
	@FXML 
	private void logBack(ActionEvent event) {
	    	
	    	System.out.println("Cliccato");
	    	ComingBackLog();
	   
	    }
	    
	public void ComingBackLog() {
	    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Presentation/View/FXML/LoginPanel.fxml"));
		    Parent root = (Parent) loader.load();
		    Stage log = new Stage();
		    log.setScene(new Scene(root));
		    log.setTitle("Biblioteca Digitiale UNIVAQ");
		    log.show();
		    Stage windows = (Stage) title.getScene().getWindow();
		    windows.hide();
	    	} catch (Exception e) {e.printStackTrace();}
	}
	    
	    	    	    	    	
}
	
	
			
	
	
	
	
		
	
	
		

