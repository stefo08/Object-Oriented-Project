package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Controller.Controller_User;
import Model.VO.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController implements Initializable {

	private Controller_User contr;
	private User log;

	@FXML
	private Label ruolo, nomeus, Welcome;
	@FXML
	private AnchorPane homeview;

	/*
	 * Il metodo Initialize viene sollevato quando la View viene chiamata e caricata
	 * dal Loader. La Home View presenta al suo interno piu viste che vengono
	 * visualizzate solo quando vengono premuti determinati bottoni. La Home
	 * principale viene caricata con il metoto sottostante;
	 */

	@Override
	public void initialize(URL Location, ResourceBundle resources) {

		contr = new Controller_User();
		log = contr.getLogger();
		ruolo.setText("Ruolo:  " + log.getRuolo());
		nomeus.setText(log.getUser());
		Welcome.setText(log.getNome().toUpperCase() + ", BENVENUTO NEL PORTALE");

	}

	/*
	 * L'utente fà richiesta di voler effettuare una Ricerca di Opere nel sistema.
	 * Viene visualizzata la schermatat di richiesta;
	 */

	@FXML
	private void RicercaOP() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Presentation/View/FXML/SearchOpera.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		homeview.getChildren().setAll(pane);

	}

	/*
	 * Mosta le Info Dell'utente come Nome, Cognome ecc. Inoltre se
	 * l'user è "Utente Base" allora viene mostrato il pulsante per effettuare la
	 * richiesta di collaborazione, se "Admin" il pulsante per visualizzare le
	 * richieste e eventualmente accettarle e la lista degli User;
	 * (Il controllo del ruolo viene effettuato dalla View InfoUser che si interfaccia con il Controller_User)
	 */

	@FXML
	private void InfoUT() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Presentation/View/FXML/" + "InfoUser.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		homeview.getChildren().setAll(pane);
		
	}

	/*
	 * Differenza del primo metodo però viene effettuato un controllo
	 * sul ruolo dell'utente che chiede di effettuare la richiesta. Se ruolo é
	 * diverso da "Admin" o "Upploader" allora viene bloccato l'accesso alla
	 * schermata di Upload e viene mostrato un messaggio di errore;
	 * Viene chiamato il Controller_User (contr) che si occupa della verifica;
	 */

	@FXML
	public void UploadOpera() throws IOException {
		
		if (contr.CanUpload()) {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Presentation/View/FXML/" + "UploadOpera.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			homeview.getChildren().setAll(pane);
			
		}

	}

	/*
	 * Serve per aprire la View per l'inserimento delle pagine nell'Opera creata
	 * dall'Uploader corrente; Ogni Uploader può visualizzare solo e soltanto le
	 * proprie opere e aggiungere ad esse nuove pagine;
	 */

	@FXML
	private void getOpereUploaderPage() throws IOException {
		
		if ((contr.CanAddPage())) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Presentation/View/FXML/" + "InsertOperaPages.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		homeview.getChildren().setAll(pane);
		}
		
	}
	
	/*
	 * Mosta la view per Revisionare le pagine che sono state caricate. Il revisore visualizza le opere che hanno 
	 * lo stato "Upload chiuso". Nella View UploadRevison il revisore può accettare o meno le pagine che verranno
	 * poi mostrate o non nella ricerca finale e dai trascrittori;
	 */

	@FXML
	public void getOperetoReview() throws IOException {

		if (contr.CanReviewPage()) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Presentation/View/FXML/" + "UploadRevision.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			homeview.getChildren().setAll(pane);
		}
		
		}
	
	/*
	 * In base al trascrittore che fa richiesta, vengono visualizzate solo le pagine che gli sono state
	 * assegnate dal manager. Uno o più trascrittori possono lavorare sulla stessa pagina
	 */

	@FXML
	private void OpenOperaTrabscrive() throws IOException {

		if (contr.CanTrabscribe()) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Presentation/View/FXML/" + "TranscribeAssigned.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			homeview.getChildren().setAll(pane);
			
		}

	}

	/*
	 * Effettua Log-out dal sistema;
	 */

	@FXML
	private void Logout() {

		try {
			Stage windows = (Stage) nomeus.getScene().getWindow();
			windows.close();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Presentation/View/FXML/" + "LoginPanel.fxml"));
			Parent root = (Parent) loader.load();
			Stage infouts = new Stage();
			infouts.setScene(new Scene(root));
			infouts.setTitle("Biblioteca Digitale UNIVAQ");
			infouts.show();
			infouts.setResizable(false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
