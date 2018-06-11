package Model.DAO;

/*
 * @author 
 * Angelo D'Alfonso, Andrea Amicosante, Stefano Ravanetti
 */

public abstract class DAOFactory {
	
	public static final int MYSQL = 0;
	public static final int ORACLE = 1;
	
	public abstract UserDAO getUserDAO();
	
	public abstract OperaDAO getOperaDAO();
	
	public abstract PageDAO getPageDAO();
	
	public abstract TranscriptionDAO getTranscriptionDAO();
	
	public static DAOFactory getDAOFactory(int database) {
		switch (database) {
		case MYSQL:
			return new MySQLDAOFactory();
		case ORACLE:
			return null;
		default:
			return null;
		}
	}

}
