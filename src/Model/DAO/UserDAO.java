package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

import java.util.List;

import Model.VO.User;

public interface UserDAO {
	
	public boolean CorrectLogInput(String us,String pass);
	
	public void registraUtente(String n, String c, String em, String us, String pass);
	
	public boolean VerificaUser(String us);
	
	public User getUser(String us);
	
	public void AddCollabrequest(String user);
	
	public List<User> getRequest();
	
	public void changeStatoReq(String user);
	
	public List<User> getTrabscribelist();

}
