package Presentation.View.FrontController;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Controller.Controller_Transcription;
import Controller.Controller_User;
import Model.VO.Page;
import Model.VO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TranscribeAssignedController implements Initializable {
	
	private Controller_Transcription contrtra;
	private Controller_User contrus;
	private User us;
	private List<Page> assigned;
	
	@FXML
	private TableView<Page> pagetable;
	@FXML
	private TableColumn<Page, String> titleop;
	@FXML
	private TableColumn<Page, Integer> numpage;
	
	@Override
	public void initialize(URL Location, ResourceBundle resources) {
		
		contrtra = new Controller_Transcription();
		contrus = new Controller_User();
		us = contrus.getLogger();
		assigned = contrtra.getAssignedPage(us.getIDdb());
		initlist();
		
	}
	
	/*
	 * Il revisore decide su quale delle pagine a lui assegnate, intende lavorare. Viene salvata la pagina
	 * scelta nel Controller_Transcription in modo che venga recuperata successivamente nella fase di 
	 * trascrizione;
	 */
	
	@FXML
	private void TranscribePage() {
		
		 try {
			    contrtra.setPageToTranscribe(pagetable.getSelectionModel().getSelectedItem());
			    AnchorPane transcr = (AnchorPane) FXMLLoader.load(getClass().getResource("/Presentation/View/FXML/TranscribePage.fxml"));
			    Stage regut = new Stage();
			    Scene scen2 = new Scene(transcr);
			    regut.setTitle("Trascrivi Opera");
			    regut.setScene(scen2);
			    regut.setResizable(false);
			    regut.show();
			 } catch (Exception e) {e.printStackTrace();}
		
	}
	
	/*
	 * Viene inizializzata la lista delle Pagine che sono state assegnate al revisore. 
	 */
	
	private void initlist() {
		ObservableList<Page> values = FXCollections.
    			observableArrayList();
		for (Page p : assigned) {
			values.add(p);
    	}
    	
		titleop.setCellValueFactory(new PropertyValueFactory<Page, String>("titoloop") );
    	numpage.setCellValueFactory(new PropertyValueFactory<Page, Integer>("Numeropagina"));
    	pagetable.setItems(values);
    	pagetable.setVisible(true);
    	    	
	}

}
