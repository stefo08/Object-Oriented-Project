package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Controller.Controller_Page;
import Controller.Controller_Transcription;
import Controller.Controller_User;
import Model.VO.Page;
import Model.VO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AssignPagesController implements Initializable {
	
	private Controller_Page contrpage;
	private Controller_User contruser;
	private Controller_Transcription contrtra;
	private List<Page> pages;
	private List<User> trascr;
	private User trascrib;
	private Page pagina;
		
	@FXML
	private TextField operatext, trasctext;
	@FXML
	private TableView<Page> pagetable;
	@FXML
	private TableView<User> trasctable;
	@FXML
	private TableColumn<Page, Integer> numpage;
	@FXML
	private TableColumn<User, String> usercol;
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		contrpage = new Controller_Page();
		contruser = new Controller_User();
		contrtra = new Controller_Transcription();
		pages = contrpage.getPagineOpereClose();
		trascr = contruser.getTrabscribers();
		initTable();
		    			
	}
	
	@FXML
	private void SetPage() {
		
		pagina = pagetable.getSelectionModel().getSelectedItem();
		operatext.setText(Integer.toString(pagina.getNumeropagina()));
				
	}
	
	@FXML
	private void setTrabscrib() {
		
		trascrib = trasctable.getSelectionModel().getSelectedItem();
		trasctext.setText(trascrib.getUser());
				
	}
	
	/*
	 * La pagina viene assegnata al Trascrittore. Il Controller_transcription modifica nel DB l'entita trascrzione
	 * con l'ID della pagina e l'ID del trascrittore; Più trascrittori possono essere assegnati alla stessa 
	 * pagina;
	 */
	
	@FXML
	private void setOperatoTrascribe() {
		
		contrtra.SetTranscriber(trascrib.getIDdb(), pagina.getID());
		
	}
	
	/*
	 * Viene recuperata dal Controller_opera l'opera selezionata in precedenza dal Manager e inizializzata la
	 * TableView con le sue pagine. Viene inoltre inizializzata un altra TableView con la lista
	 * dei trascrittori del Sistema;
	 */
	
	private void initTable() {
		
		ObservableList<Page> values = FXCollections.
    			observableArrayList();
		for (Page p : pages) {
    		values.add(p);
    	}
    	
    	numpage.setCellValueFactory(new PropertyValueFactory<Page, Integer>("Numeropagina"));
    	pagetable.setItems(values);
    	pagetable.setVisible(true);
    	    	
    	ObservableList<User> valuestra = FXCollections.
    			observableArrayList();
    	for(User u : trascr) {
    		valuestra.add(u);
    	}
    	usercol.setCellValueFactory(new PropertyValueFactory<User, String>("User"));
    	trasctable.setItems(valuestra);
    	trasctable.setVisible(true);
    	
	}
}
