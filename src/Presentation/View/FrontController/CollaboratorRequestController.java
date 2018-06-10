package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Controller.Controller_User;
import Model.VO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CollaboratorRequestController implements Initializable {
		
	Controller_User contrus;
		
	@FXML
	private TableView<User> table;
	@FXML
	private TableColumn<User,String>	usercolumn;
	@FXML
    private TableColumn<User,String> statocolumn;
	@FXML
	private Label label1;
		
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		contrus = new Controller_User();
		start();
				
	}
	
	/*
	 * Viene inizializzata la TableView che mostra la lista degli utenti che hanno fatto richiesta (Stato = "richiesta
	 * effettuata) e quelli che sono già stati accettati dall'Admin (stato = "Accettata") vengono invece omessi
	 * gli utenti che sono stati rifiutati; 
	 */
	
	public void start() {
		
		ObservableList<User> values = FXCollections.
		observableArrayList();
		List<User> lista = new ArrayList<User>();
		lista = contrus.getCollaboratorRequest();
		for (User u : lista) {
			values.add(u);
		}
		usercolumn.setCellValueFactory(new PropertyValueFactory<User, String>("User"));
		statocolumn.setCellValueFactory(new PropertyValueFactory<User, String>("stato"));
		table.setItems(values);
		table.setVisible(true);
		
	}
	
	/*
	 * L'Admin accetta la richiesta dell'utente selezionato nella TableView. L'user viene
	 * passato al Controller_User che modifica lo stato della richiesta nel DB;
	 */
		
	@FXML
	public void AcceptRequest() {
		
		User user = table.getSelectionModel().getSelectedItem();
		contrus.AcceptCollaboratorRequest(user.getUser());
		System.out.println(user.getUser());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Operazione eseguita");
		alert.setHeaderText(null);
		alert.setContentText("Richiesta effettuata correttamente");
		alert.showAndWait();
		start();
		
	}
}
	
	
		


