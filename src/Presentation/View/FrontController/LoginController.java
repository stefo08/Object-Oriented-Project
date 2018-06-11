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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
		
	private Controller_User contr;
			
	@FXML 
	private Label userLab;
	@FXML
	private JFXTextField userfield;
	@FXML
	private JFXPasswordField passfield;
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
				
		contr = new Controller_User();
	}
	
	/*
	 * Quando il punsante di Login viene premuto nella View il controller_User
	 * verifica che i campi User e Pass non siano vuoti (Mostra messaggio di errore) 
	 * e se cosi non è allora interroga il DB e verifica l'esistenza dell'utente
	 * nel sistema
	 */
	
	@FXML
	private void loginButtonClick(ActionEvent event) {
		
		try {
			if(contr.isAllRightsInput(userfield.getText().trim(), passfield.getText())) {
			if (contr.ExistUser(userfield.getText(), passfield.getText())){
				contr.setLogger(userfield.getText());
				openHomeView();	
			}
			else {
				
				userfield.setText("");
				passfield.setText("");
				
			}
		    }
			} catch (Exception e) {e.printStackTrace();}
		
	}
	
	/*
	 * Se l'utente chiede di iscriversi mostra la View di registazione passando il controllo al Controller della View 
	 * RegisterUSPanel che si occuperà del controllo dei dati in Input e della registrazione nel Database sempre
	 * attraverso il Controller_User;
	 */
	
	@FXML
	private void registerUser(ActionEvent event) {
		
		 try {
			    Stage windows = (Stage) userLab.getScene().getWindow();
			    windows.close();
			    AnchorPane regutente = (AnchorPane) FXMLLoader.load(getClass().getResource("/Presentation/View/FXML/RegisterUserPanel.fxml"));
			    Stage regut = new Stage();
			    Scene scen2 = new Scene(regutente);
			    regut.setTitle("Registra Utente");
			    regut.setScene(scen2);
			    regut.setResizable(false);
			    regut.show();
			 } catch (Exception e) {e.printStackTrace();}
		 
	}
	
	
	/*
	 * Verificata l'utenza nel DB la View di Login viene chiusa e viene aperta la View Home del Programma e viene passato al Controller della view
	 * L'utente che ha effettuato l'accesso in modo da poter gestire in seguito i dati, le opere, e i documenti dell'utente; vedi (HomeController.initUser);
	 */
	
	private void openHomeView() {
		
		 try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Presentation/View/FXML/Home.fxml"));
			Parent root = (Parent) loader.load();
			Stage infouts = new Stage();
		    infouts.setScene(new Scene (root));
		    infouts.setTitle("Biblioteca Digitale UNIVAQ: Home");
		    infouts.show();
		    infouts.setResizable(false);
		    Stage windows = (Stage) userLab.getScene().getWindow();
		    windows.close();
		 } catch (Exception e) {e.printStackTrace();}
		 
	}
			
	}
	
		

