package Model.DAO;

import java.util.List;

import Model.VO.Page;

public interface TranscriptionDAO {
	
	public List<Page> getAssignedPages(int id);
	
	
}
