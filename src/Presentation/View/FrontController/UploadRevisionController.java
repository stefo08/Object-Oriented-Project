package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeSet;
import Controller.Controller_Opera;
import Controller.Controller_Page;
import Model.VO.Opera;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UploadRevisionController implements Initializable{
	
	private TreeSet<Opera> local;
	private Controller_Page contrpage;
	private Controller_Opera contropera;
	
	@FXML
	private TableView<Opera> ReviewOpereTabel;
	@FXML
	private TableColumn<Opera,String>TitoloReview, UploaderReview;
	@FXML
	private TableColumn<Opera, Integer> NumberPageReview;
			
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		contrpage = new Controller_Page();
		contropera = new Controller_Opera();
		Init();
						
	}
	
	/*
	 * Scelta l'opera su cui il revisore intende lavorare, l'opera viene salvata nel Controller_Opera in modo
	 * che questa venga recuperata successivamente nella View successiva per inizializzare le pagine;
	 */
	
	@FXML
	private void ViewOperePages() {
		
		try {
			Opera temp = ReviewOpereTabel.getSelectionModel().getSelectedItem();
			contropera.InitOperaToReview(temp);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Presentation/View/FXML/ViewPageRevision.fxml"));
			Parent root = (Parent) loader.load();
			Stage infouts = new Stage();
		    infouts.setScene(new Scene (root));
		    infouts.setTitle("Visualizza Pagine");
		    infouts.show();
		    infouts.setResizable(false);
		    		
		 } catch (Exception e) {e.printStackTrace();}
		
	}
	
	/*
	 * Inizializza la tableview con la lista delle opere con stato "Upload chiuso"; 
	 */
	
	private void Init() {
		
		local = new TreeSet<Opera>();
		local = contrpage.getOpereToRewiew();
		ObservableList<Opera> values = FXCollections.
    			observableArrayList();
    	for (Opera p : local) {
    	values.add(p);
    	}
    	TitoloReview.setCellValueFactory(new PropertyValueFactory<Opera, String>("Titolo"));
    	NumberPageReview.setCellValueFactory(new PropertyValueFactory<Opera, Integer>("Paginanum"));
    	UploaderReview.setCellValueFactory(new PropertyValueFactory<Opera, String>("Uploader"));
      	ReviewOpereTabel.setItems(values);
      	ReviewOpereTabel.setVisible(true);
	}
	
	/*
	 * Se l'uploader ritiene che l'opera è pronta per essere inserita allora mette lo stato su "Pubblicata". 
	 * l'opera è finalmente pronta per essere mostrata all'utente finale; 
	 */
	
	@FXML
	private void PublicOpera() {
		
		Opera topublic = ReviewOpereTabel.getSelectionModel().getSelectedItem();
		contropera.Publicopera(topublic);
		
	}
	
	

}
