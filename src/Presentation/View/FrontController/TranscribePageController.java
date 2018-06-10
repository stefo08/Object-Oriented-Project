package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import Controller.Controller_Transcription;
import Model.VO.Page;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TranscribePageController implements Initializable {
	
	private Controller_Transcription contrtra;
	private Page pagetotranscr;
		
	@FXML
	private ImageView imageview;
	@FXML
	private TextArea trascarea;
	
	/*
	 * Il metodo initialize recupera dal Controller_Transcription la pagina selezionata in precedenza nella View
	 * TranscribeAssigned. Viene inizializzata l'image view con la scansione della pagina e se già presente
	 * una trascrizione che è aggiornata all'ultima modifica fatta;
	 */
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		contrtra = new Controller_Transcription();
		pagetotranscr = contrtra.getPageToTranscribe();
		String text = contrtra.getTranscriptionText();
		trascarea.setText(text);
		File f = new File(pagetotranscr.getPath());
		Image img = null;
		try {
			img = new Image(f.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {e.printStackTrace();}
		imageview.setImage(img);
		
	}
	
	/*
	 * Il trascrittore decide che è necessaria una modifica. Sblocca così la text area e può
	 * cosi lavorare alla trascrizione dell'opera e apportare le giuste modifiche;
	 */
	
	@FXML
	private void ModTrascrition() {
		
		trascarea.setEditable(true);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Messaggio interno");
		alert.setHeaderText(null);
    	alert.setContentText("Modifiche abilitate");
    	alert.showAndWait();
		
	}
	
	/*
	 * Quando il trascrittore finisce di lavorare alla trascrizione, passa il controllo al Controller_Transcription
	 * che modifica (chiamando il fileSystem) il fil di testo relativo alla pagina in modo da mantenere sempre
	 * aggiornate le modifiche effettuate;
	 */
	
	@FXML
	private void saveTranscription() throws IOException {
		
		contrtra.SaveTranscription(pagetotranscr.getNumeropagina(), pagetotranscr.getTitoloop(), trascarea.getText(), pagetotranscr.getID());
		
	}

}
