package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.util.List;

import Model.VO.Opera;

public interface OperaDAO {
	
	 public List<Opera> getOpera(String titolo);
	 
	 public void InsertOP(String t, String aut, String gen, String ISBN, int ap, String ln, int id, String path);
	 
	 public List<Opera> getOperetoaddPage(int idUp);
	 
	 public int getNumberPage(String t);
	 
	 public void CloseUpload(Opera p);
	 
	 public List<Opera> getCloseOpera();
	 
	 public void PublicOpera(Opera p);
		 	 
}
