package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.util.List;
import java.util.TreeSet;

import Model.VO.Opera;
import Model.VO.Page;

public interface PageDAO {
	
	public boolean insertPage(int op, String path, String num, String pathtra);
	
	public TreeSet<Opera> getOperetoReview();
	
	public List<Page> getOperaPages(Opera p);
	
	public void acceptPage(int ID);
	
	public void refusePage(int ID);
	
	public List<Page> getOperaClosePage(Opera p);
	
	public void setPageTranscriber(int idp, int idus);
	

}
