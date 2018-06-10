package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import Controller.Controller_Opera;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class UploadOperaController implements Initializable {
	
	private Controller_Opera controp;
	private File cop;
	private Image standard;
		
	@FXML
	private JFXTextField titolofield, autorfield, linguafield, datafield, isbfield, genfield;
	@FXML
	private ImageView copertina;
		
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		controp = new Controller_Opera();
		standard = copertina.getImage();
			
	}
	
	/*
     * Permette di inserire una nuova Opera nel sistema. L'Opera inizialmente viene aggiunta nel DB con stato opera = "Attesa Pubblicazione" in modo che non sia
     * possibile trovare l'opera non ancora completa da parte di un Utente; Viene inserito il path della copertina
     * nel DB e nel FS; Le operazione di inserimento nel DB e nel FS sono gestite dal Controller_Opera che si occupa di
     * interrogare il DB e il FS e di verificare che i campi di inserimento non siano vuoti;
     */
	
	@FXML
	private void InsertOP() throws NumberFormatException, MalformedURLException {
		
    	String tit, aut, gen,ln,isb, dat, da;
    	tit = titolofield.getText(); aut = autorfield.getText(); ln = linguafield.getText(); da = datafield.getText();
    	isb = isbfield.getText(); gen = genfield.getText(); dat = datafield.getText(); 
    	if (controp.IsAllrightInputOpera(tit, aut, ln, dat , isb, gen, copertina.getImage(), standard)){ //Serve per verificare che i campi "Titolo, Autore" ecc non siano vuoti;
    		controp.InsertOpera(tit, aut, gen, isb ,Integer.parseInt(da), ln);
    	controp.createOperaDirectory(tit);
    	controp.InsertCopertina(cop);
    	titolofield.setText(""); autorfield.setText(""); linguafield.setText(""); genfield.setText(""); datafield.setText(""); 
    	isbfield.setText(""); copertina.setImage(standard);
    	
    	}
    }
	
	/*
	 * Apre un FileChooser che permette di selezionare una copertina per l'opera che
	 * si sta inserendo. La copertina verrà poi passata ad un FileSystem
	 * che memorizzerà la copertina nella directory dell'opera
	 */

	@FXML
	private void insertCopertina() {
		
  		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleziona immagine");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		System.out.println("Clicked");
		File file = fileChooser.showOpenDialog(null);
		cop = file;
		if (file != null) {
	        try {
	            String imageUrl = cop.toURI().toURL().toExternalForm();
	            Image image = new Image(imageUrl);
	            System.out.println(imageUrl);
	            copertina.setImage(image);
	            
	        } catch (MalformedURLException ex) {}
	    }       			
	    }
			
}
