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
import Controller.Controller_Page;
import Model.VO.Opera;
import Model.VO.Page;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewPageRevisionController implements Initializable {
	
	Controller_Opera contropera;
	Controller_Page contrpage;
	private List<Page> lista;
	private int cont = 0;
	private int numpage = 0;
		
	@FXML
	private ImageView operaimage;
	@FXML
	private Label numpagelab;
	
	/*
	 * Viene inizializzata la lista delle pagine con quelle dell'opera scelta 
	 * dal revisore dell'Upload; 				
	 */
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		contropera = new Controller_Opera();
		contrpage = new Controller_Page();
		InitPages();
						
	}
	
	/*
	 *Permette di scorrere in avanti le pagine della relativa opera
	 *e decidere eventualmente se accettarle o meno; 
	 */
	
	@FXML
	private void GetNextPage() throws MalformedURLException{
		if (cont < numpage - 1) {
		cont++;
		Page p = lista.get(cont);
		File f = new File(p.getPath());
		Image im = new Image(f.toURI().toURL().toExternalForm());
		operaimage.setImage(im);
		numpagelab.setText("Pagina" + p.getNumeropagina() + ": ");
		}
	}
	
	/*
	 *Permettere di scorrere indietro le pagine dell'Opera ovviamente fino a che sono disponibili 
	 */
	
	@FXML
	private void GetPrecPage() throws MalformedURLException{
		if (cont > 0) {
		cont--;
		Page p = lista.get(cont);
		File f = new File(p.getPath());
		Image im = new Image(f.toURI().toURL().toExternalForm());
		operaimage.setImage(im);
		numpagelab.setText("Pagina" + p.getNumeropagina() + ": ");
		}
	}
	
	/*
	 * Il revisore può Accettare la scansione se la reputa idonea per l'opera
	 */
	
	@FXML
	private void AcceptPage() {
		contrpage.AcceptPage(lista.get(cont));
		InitPages();
	}
	
	/*
	 * Il revisore può Rifiutare la scansione se la reputa "non" idonea per l'opera
	 */
	
	@FXML
	private void RefusePage() {
		contrpage.RefusePage(lista.get(cont));
		InitPages();
	}
	
	/*
	 * Inizializza la lista delle pagine che devono essere revisionate; quando una pagina viene
	 * rifiutata o accettata il metodo richiama initPages() in modo che vengano visualizzate
	 * solo le pagine che ancora non sono state revisionate;
	 */
	
	private void InitPages() {
		
		Opera p = contropera.getOperaToReview();
		lista = p.getPagine();
		numpage = p.getPaginanum();
		Page o = lista.get(0);
		File f = new File(o.getPath());
			Image im = null;
			try {
				im = new Image(f.toURI().toURL().toExternalForm());
			} catch (MalformedURLException e) {e.printStackTrace();
			}
			
			operaimage.setImage(im);
			numpagelab.setText("Pagina" + o.getNumeropagina() + ": ");
			
	}
}	
		
		
	
	


