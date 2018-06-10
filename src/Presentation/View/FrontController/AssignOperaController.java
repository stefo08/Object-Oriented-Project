package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Controller.Controller_Opera;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AssignOperaController implements Initializable {

	Controller_Opera controp;
	private List<Opera> opere;
	
	@FXML
	private TextField operatext, trasctext;
	@FXML
	private TableView<Opera> OperaTable;
	@FXML
	private TableColumn<Opera, String> titcol;
		
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		controp = new Controller_Opera();
		opere = controp.getCloseOpera();
		initTable();
		    			
	}
	
	/*
	 * il manager decide su quale opera vuole assegnare le pagine. L'Opera selezionata viene passata 
	 * al Controller_Opera che memorizza l'opera in modo che venga caricata nella View Successiva
	 *  e quindi in modo che vengano visualizzate le sue pagine;
	 */
	
	@FXML
	private void SelectOpera() {
		
		Opera toassign = OperaTable.getSelectionModel().getSelectedItem();
		controp.setOperaToAssign(toassign);
		
		 try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/Presentation/View/FXML/AssignPages.fxml"));
				Parent root = (Parent) loader.load();
				Stage infouts = new Stage();
			    infouts.setScene(new Scene (root));
			    infouts.setTitle("Biblioteca Digitale UNIVAQ: Home");
			    infouts.show();
			    infouts.setResizable(false);
		 } catch (Exception e) {e.printStackTrace();}
		 
	}
	
	/*
	 * Viene inizializzata la Table View con la lista delle Opere con stato = "Upload Chiuso" e che sono quindi
	 * in attesa di revisione;
	 */
	
	private void initTable() {
		ObservableList<Opera> values = FXCollections.
    			observableArrayList();
		for (Opera o : opere) {
    		values.add(o);
    	}
    	
    	titcol.setCellValueFactory(new PropertyValueFactory<Opera, String>("Titolo"));
    	OperaTable.setItems(values);
    	OperaTable.setVisible(true);
    	
	}
}

