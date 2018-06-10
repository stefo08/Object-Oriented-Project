package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import Controller.Controller_Opera;
import Model.VO.Opera;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SearchOperaController implements Initializable  {
	
	private Controller_Opera controp;
	
	@FXML 
	private JFXTextField ricercafield;
	@FXML
	private TableView<Opera> ricercaTable;
	@FXML
	private TableColumn<Opera,String>  titolocolumn, autorecolumn, titoloUp, TitoloReview, UploaderReview;
	@FXML
	private TableColumn<Opera, Integer> annocolumn;
	@FXML
	private ImageView copertinaimage;
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		controp = new Controller_Opera();
		
	}
			
	/*
     *Effettua una ricerca nel Db delle opere che hanno lo stato dell'Opera su "upload chiuso". Viene passato il controllo al Controller_Opere che
     *passa al DB il campo Stringa inserito nel TextField di ricerca che viene passato al Db che restituisce una lista di Oggetti Opera 
     *che viene poi ripassata al metodo sottostante che inserisce le Opere trovate in un TableView; 
     */
    
    @FXML
    private void SearchOP() {
    	
    	if (!(ricercafield.getText().equals(""))) {
    	ObservableList<Opera> values = FXCollections.
    			observableArrayList();
    	ArrayList<Opera> lista = (ArrayList<Opera>) controp.getOpere(ricercafield.getText());
    	for (Opera o : lista) {
    		values.add(o);
    	}
      	titolocolumn.setCellValueFactory(new PropertyValueFactory<Opera, String>("Titolo"));
      	autorecolumn.setCellValueFactory(new PropertyValueFactory<Opera, String>("Autore"));
      	annocolumn.setCellValueFactory(new PropertyValueFactory<Opera, Integer>("Annopubb"));
      	ricercaTable.setItems(values);
      	ricercaTable.setVisible(true);
      	ricercafield.setText("");
      	
    	}
    }
    
    /*
     * Ogni volta che viene selezionata un Opera dalla TableView viene mostrata 
     * la copertina nel riquadro a destra;
     */
    
    @FXML
    private void SetCopertina() throws MalformedURLException {
    	
    	Opera temp = ricercaTable.getSelectionModel().getSelectedItem();
    	File f = new File(temp.getPathCopertina());
    	copertinaimage.setImage(new Image(f.toURI().toURL().toExternalForm()));
    	
    }
    
    /*
     * L'opera selezionata per la visualizzazione viene passata al Controller_Opera che mantiene l'opera salvata.
     * l'Opera verrà successivamente recuperata dal Controller della View che permette di visualizzare le singole
     * pagine e le relative trascrizioni;
     */
    
    @FXML
    private void VisualizeOpera() {
    	
    	controp.setOperaToSee(ricercaTable.getSelectionModel().getSelectedItem());
    	
    	try {
    		AnchorPane regutente = (AnchorPane) FXMLLoader.load(getClass().getResource("/Presentation/View/FXML/ViewCompleteOpera.fxml"));
		    Stage regut = new Stage();
		    Scene scen2 = new Scene(regutente);
		    regut.setTitle("Visualizza Opera: " +ricercaTable.getSelectionModel().getSelectedItem().getTitolo());
		    regut.setScene(scen2);
		    regut.setResizable(false);
		    regut.show();
		    
		 } catch (Exception e) {e.printStackTrace();}
    	
    }
}
