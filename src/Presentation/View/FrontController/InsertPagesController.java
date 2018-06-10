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
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class InsertPagesController implements Initializable {
	
	private Controller_Opera controp;
	private Controller_Page contrpage;
    private File pg;
    private Opera op;
	private List<Page> temp;
	private int cont = 0, numpage = 0;
	
	
	@FXML
	private ImageView operaimage, alreadyinside;
	@FXML
	private TextField numberfield;
	@FXML
	private Label paginanum, operalabel, statopag;
	
	private Image standard;
	
	/*
	 * Il metodo inizilizza il riquadro delle pagine già inserite. Viene interrogato il Controller Opera
	 * che restituisce l'opera selezionata precendetemene dall'Uploader viene poi nuovamente richiamato il
	 * Controller dell'Opera che restituisce le pagine dell'Opera corrente. Le pagine già inserite vengono 
	 * così inizializzate;
	 */
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		controp = new Controller_Opera();
		standard = operaimage.getImage();
		contrpage = new Controller_Page();
		this.op = controp.GetOpera();
		operalabel.setText("INSERISCI PAGINE OPERA : " +op.getTitolo());
		temp = contrpage.getOperaPages(op);
		numpage = op.getPaginanum();
		if(numpage != 0) {
			
		   File f = new File(temp.get(0).getPath());
		   Image first = null;
		try {
			first = new Image(f.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {e.printStackTrace();
		}
		   alreadyinside.setImage(first);
		   paginanum.setText("Pagina: " +Integer.toString(temp.get(0).getNumeropagina()));
		   statopag.setText(temp.get(0).getStato_pagina());
		}		
	}
	
	/*
	 * Apre il FileExplorer per selezionare la scansione. Passa il File Image alla classe Model.FileSystem
	 * che copia la scansione nella cartella dell'opera corrispondente. Mostra inoltre la scansione in una ImageView
	 */
	
	@FXML
	private void ExplorerScan() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleziona immagine");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop/"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File file = fileChooser.showOpenDialog(null);
		pg = file;
	    if (file != null) {
	        try {
	            String imageUrl = pg.toURI().toURL().toExternalForm();
	            Image image = new Image(imageUrl);
	            System.out.println(imageUrl);
	            operaimage.setImage(image);
	        } catch (MalformedURLException ex) {
	            			
	    }
	    }
	}
	
	/*
	 * Quando l'Uploader decide che la scanasione è corretta preme "Inserisci".
	 * La pagina viene passata al Controller_Page che passa ad un FileSystem che si occupa della gestione delle stesse in modo schematico
	 * in modo da favorire accessi successivi; Il Controller_Page passa la pagina al DB in modo da 
	 * salvare URL (path directory) in modo da recuperare le singole scansioni successivamente;
	 */
	
	@FXML
	private void InsertPage() {
		
		if (contrpage.insertPage(pg, operaimage.getImage(), standard, numberfield.getText() , op)) {
			
			operaimage.setImage(standard);
	    	numberfield.setText("");
	    	
		}
					
		}

    /*
     * Come da specifica è stato implementato un metodo che permette a L'Uploader
     * di visualizzare le pagine già inserite in precedenza; Con i metodi sottostanti
     * ci si può spostare avanti e dietro fra le pagine inserite "NextPage(); PrecPage();
     */
	
	
	@FXML
	private void GetNextPage() throws MalformedURLException {
		
		if (cont < numpage - 1) {
			cont++;
			Page p = temp.get(cont);
			File f = new File(p.getPath());
			Image im = new Image(f.toURI().toURL().toExternalForm());
			alreadyinside.setImage(im);
			paginanum.setText("Pagina: " +Integer.toString(p.getNumeropagina()));
			statopag.setText(p.getStato_pagina());
		}
		
	}
	
	@FXML
	private void GetPrecPage() throws MalformedURLException {
		
		if (cont > 0) {
			cont--;
			Page p = temp.get(cont);
			File f = new File(p.getPath());
			Image im = new Image(f.toURI().toURL().toExternalForm());
			alreadyinside.setImage(im);
			paginanum.setText("Pagina: " +Integer.toString(p.getNumeropagina()));
			statopag.setText(p.getStato_pagina());
			}
		
	}
	
	}



		
	
		
	

	
	
		



