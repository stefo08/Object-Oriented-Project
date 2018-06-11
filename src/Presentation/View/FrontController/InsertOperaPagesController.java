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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class InsertOperaPagesController implements Initializable {
		
	private Controller_Opera controp;
	
	@FXML
	private TableView<Opera> opereUP;
	@FXML
	private TableColumn<Opera,String> titoloUp;
	@FXML
	private TableColumn<Opera, Integer> numpageUP;
	@FXML
	private ImageView copertina;
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		controp = new Controller_Opera();
		initiOpereUploader();
		
	}
	
	/*
     *Viene mostrata la View che permette di inserire successivamente nuove pagine all'opera 
     */
    
    @FXML
    private void InsertPage() {
    	
    	Opera temp = opereUP.getSelectionModel().getSelectedItem();
    	controp.setOpereToAddPage(temp);	
    	try {
    		FXMLLoader loader = new FXMLLoader();
 			loader.setLocation(getClass().getResource("/Presentation/View/FXML/"
 					+ "InsertPages.fxml"));
 			Parent root = (Parent) loader.load();
 			Stage req = new Stage();
 			req.setScene(new Scene (root));
 		    req.setTitle("Inserisci Pagina");
 		    req.show();
 		    req.setResizable(false);
    	 } catch (Exception e) {e.printStackTrace();} 
    }
    
    /*
     * Il metodo inizializza la table view che mostra le opere inserite solo dall'Upload 
     * corrente che può così inserirvi nuove pagine o chiudere la procedura di Upload 
     * dell'Opera corrente;
     */
    
    public void initiOpereUploader() {
    	
    	ObservableList<Opera> values = FXCollections.
    			observableArrayList();
       	ArrayList<Opera> lista = (ArrayList<Opera>) controp.getOperaToAddPage();
    	for (Opera o : lista) {
    		values.add(o);
    	}
    	
    	titoloUp.setCellValueFactory(new PropertyValueFactory<Opera, String>("Titolo"));
    	numpageUP.setCellValueFactory(new PropertyValueFactory<Opera, Integer>("Paginanum"));
    	opereUP.setItems(values);
      	opereUP.setVisible(true);
    }
    
    /*
     * Quando l'Uploader decide di aver concluso l'upload delle pagine, allora 
     * chiude l'opera che può essere finalmente revisionata dai revisori degli Upload
     */
    
    @FXML
    private void CloseOperaUpload() {
    	
    	Opera ins = opereUP.getSelectionModel().getSelectedItem();
    	controp.CloseOperaUpload(ins);
    	//Ho finito di inserire pagine. Stato opera = 3;
    }
    
    /*
     * Viene mostrata la copertina dell'opera nel riquadro di destra ogni volta che 
     * questa viene selezionata;
     */
    
    @FXML
    private void SetCopertina() throws MalformedURLException {
    	
    	File f = new File(opereUP.getSelectionModel().getSelectedItem().getPathCopertina());
    	copertina.setImage(new Image(f.toURI().toURL().toExternalForm()));
    	
    }
    
    /*
     * L'Uploader decide che l'inseriemento delle pagine è completato. L'opera assume lo stato "Upload Chiuso"
     * Il revisore degli Upload può così revisionare le scansioni;
     */
    	
}
