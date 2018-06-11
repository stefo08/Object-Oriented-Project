package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.util.List;

import Model.VO.Page;

public interface TranscriptionDAO {
	
	public List<Page> getAssignedPages(int id);
	
	
}
