package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Controller.Controller_Opera;
import Controller.Controller_Transcription;
import Model.VO.Opera;
import Model.VO.Page;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewCompleteOperaController implements Initializable {
	
	private Controller_Opera controp;
	private Controller_Transcription contra;
	private Opera inside;
	List<Page> listapagine;
	
	@FXML
	private Label pagenumber, OperaTitle;
	@FXML
	private ImageView imageview;
	@FXML
	private TextArea trascarea;
	
	private int cont = 0, numpage = 0;
	
	/*
	 * Il metodo initialize recupera dal Controller_Opera la pagina selezionata nella View Precedente e 
	 * inizializza la Image View e la Text area con la prima scansione dell'opera e la sua relativa scnasione;
	 */
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
			
		controp = new Controller_Opera();
		contra = new Controller_Transcription();
		inside = controp.getOperaToSee();
		listapagine = inside.getPagine();
		OperaTitle.setText(inside.getTitolo().toUpperCase() +":  Visualizza Opera completa");
		numpage = listapagine.size();
		try {
			Init();
		} catch (MalformedURLException e) {	e.printStackTrace();
		}
				
	}
	
	/*
	 * inizializza effettivamente ImageView e TextArea; 
	 */
	
	private void Init() throws MalformedURLException {
		
		File file = new File(listapagine.get(0).getPath());
		Image img = new Image(file.toURI().toURL().toExternalForm());
		imageview.setImage(img);
		pagenumber.setText("Pagina: " +listapagine.get(0).getNumeropagina());
		String text = contra.getTranscriptionCompleteText(listapagine.get(0));
		trascarea.setText(text);
		
	}
	
	/*
	 * I metodi showPrec() e showNext() permettono all'utente finale di scorrere avanti e dietro
	 * le pagine dell'Opera; 
	 */
	
	@FXML
	private void showPrec() throws MalformedURLException {
		
		if (cont > 0) {
			cont--;
			Page p = listapagine.get(cont);
			File f = new File(p.getPath());
			Image im = new Image(f.toURI().toURL().toExternalForm());
			imageview.setImage(im);
			pagenumber.setText("Pagina: " +Integer.toString(p.getNumeropagina()));
			trascarea.setText(contra.getTranscriptionCompleteText(p));
		}
		
	}
	
	@FXML
	private void showNext() throws MalformedURLException {
		
		if (cont < numpage - 1) {
			cont++;
			Page p = listapagine.get(cont);
			File f = new File(p.getPath());
			Image im = new Image(f.toURI().toURL().toExternalForm());
			imageview.setImage(im);
			pagenumber.setText("Pagina: " +Integer.toString(p.getNumeropagina()));
			trascarea.setText(contra.getTranscriptionCompleteText(p));
		}
		
	}
	
	

}
