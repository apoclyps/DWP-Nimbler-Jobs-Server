import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Resource(name ="jdbc/jobseeker", type = javax.sql.DataSource.class)
public class Main {
	
	protected static DataSource datasource;
	protected static Connection connection;

  public static void main(String[] args) throws Exception {
   // MySQLAccess dao = new MySQLAccess();
   // dao.readDataBase();
	  
		try{			
			datasource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/jobseeker");
            connection = datasource.getConnection();
		}catch (NamingException ne)	{	
			System.out.println("Naming Exception in DatabaseConnector.java");
			ne.printStackTrace();
		}catch (SQLException e)	{	
			System.out.println("SQL Exception in DatabaseConnector.java");
			e.printStackTrace();
		}
  }
  
  public void test(){

  }
}


