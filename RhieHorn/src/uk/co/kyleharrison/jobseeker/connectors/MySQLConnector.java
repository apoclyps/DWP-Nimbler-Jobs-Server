package uk.co.kyleharrison.jobseeker.connectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public class MySQLConnector {
  private Connection connection = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  private DataSource datasource;
  private String database_name = "ubiquity";
  
  public MySQLConnector(){
	  try{			
			datasource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/"+database_name);
			connection = datasource.getConnection();
		}catch (NamingException ne)	{	
			System.out.println("Naming Exception in DatabaseConnector.java");
			ne.printStackTrace();
		}catch (SQLException e)	{	
			System.out.println("SQL Exception in DatabaseConnector.java");
			e.printStackTrace();
		}
  }
  
  public boolean checkConnection(){
    Context initContext;
      
	try {
		initContext = new InitialContext();
	    Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    datasource = (DataSource)envContext.lookup("jdbc/"+database_name);		
	    connection = datasource.getConnection();
	} catch (NamingException e) {
		e.printStackTrace();
		return false;
	} catch (SQLException e) {
		e.printStackTrace();
		return false;
	}
	return true;
  }
  
  
  public void selectData() throws Exception {
    try {
  
        Context initContext  = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        datasource = (DataSource)envContext.lookup("jdbc/"+database_name);		
        connection = datasource.getConnection();
      
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from login");
            
            writeMetaData(resultSet);
            writeResultSet(resultSet);
           
        }catch(MySQLSyntaxErrorException se){
        	System.out.println("Read database Syntax Exception : "+ se.getStackTrace());
        }catch(Exception e){
        	System.out.println("Read database Error : " +e.getStackTrace());
        }

    } catch (Exception e) {
      throw e;
    } finally {
     // close();
    }

  }
  
  public void createData(String table,String [] parameter){

  	String sqlStatement ="INSERT INTO "+table+" VALUES (";
  	
  	int parameterLength = parameter.length;
  	for(int i=0;i<parameterLength;i++){
  		sqlStatement +="?";
  		if(i<=(parameterLength-2)){
  			sqlStatement +=",";
  		}else if(i==(parameterLength-1)){
  			sqlStatement +=");";
  		}
  	}
  	
  	System.out.println("SQL Statement = "+sqlStatement);
  	
      try {
		preparedStatement = this.connection
		      .prepareStatement(sqlStatement);

	      preparedStatement.setString(1, parameter[0]);
	      preparedStatement.setString(2, parameter[1]);
	      preparedStatement.setString(3, parameter[2]);
	      preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
	      preparedStatement.setDate(5, new java.sql.Date(2009, 12, 11));
	      preparedStatement.executeUpdate();

	      /*
	      preparedStatement = connection
	          .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
	      resultSet = preparedStatement.executeQuery();
	      writeResultSet(resultSet);
	      
	      */
	} catch(MySQLIntegrityConstraintViolationException ivce){
		ivce.printStackTrace();
		System.out.println("Duplicate Entry");
	}catch (SQLException e) {
		e.printStackTrace();
	} 
      
  }
  
  public int deleteData(String table,String parameter){
	  int status =-1;
	  
	  try {
    	 
    	String sqlStatement ="delete from "+table+" where "+parameter+"= ? ;";
    	
		preparedStatement = this.connection.prepareStatement(sqlStatement);
		preparedStatement.setString(1, "12345678");
		preparedStatement.executeUpdate();
		
		System.out.println("Delete:"+status);   
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return status;     
  }
  
  public int updateData(String table,String parameter, String ID , String value){
	  
	  int updateStatus=-1;
	  
	  try {

          String sql        = "UPDATE +"+table+" SET "+parameter+" = ? "
                   + " WHERE "+ID+ "= "+value+";";

          PreparedStatement pst = connection.prepareStatement(sql);
          pst.setString(1, "John");

          updateStatus = pst.executeUpdate();
          System.out.println("Updated Successfully!");

          //connection.close();

       } catch (SQLException e) {
          System.out.println("Exception 1!");
          e.printStackTrace();
       }
	  
	  return updateStatus;
  }

  private void writeMetaData(ResultSet resultSet) throws SQLException {
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
      ResultSetMetaData rsmd = resultSet.getMetaData();
      int numColumns = rsmd.getColumnCount();
      
      if (resultSet.next()) {
      	int columnIndex = 1;
      
      	while(columnIndex<=numColumns){
      		System.out.println(resultSet.getString(columnIndex));
      		columnIndex++;
      	}
      } 
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connection != null) {
    	  connection.close();
      }
    } catch (Exception e) {

    }
  }

} 