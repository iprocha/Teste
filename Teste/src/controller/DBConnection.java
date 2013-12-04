package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBConnection {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  private String server = "localhost";
  private String user = "desenvolvimento";
  private String schema = "im_lattes";
  private String domain = "teste";
  private String password = "123456";
  

private boolean success = false;
  String sql = null;
  String ddl = null;
  String dataBase = "jdbc:mysql://" + server + "/" + schema + "?"
          + "user=" + user + "&password=" + password;
  public void readDataBase() throws Exception {
    try {
    	
    	

System.out.println("O BANCO EH:"+ dataBase);
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection(dataBase);
      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
//      success = statement
//          .execute("CREATE TABLE funcionou(`id_pesquisador` INT(11) NOT NULL,"
//        		  +"`id_tipo_producao_cientifica` VARCHAR(1) NOT NULL)");
      
//      ddl = "CREATE  TABLE "+ schema + "."+ domain +"_tab01 ("
//    		  +"`id_pesquisador` INT(11) NOT NULL,"
//    		  +"`id_tipo_producao_cientifica` VARCHAR(1) NOT NULL,"
//    		  +"`id_producao_cientifica` INT(11) NOT NULL,"
//    		  +"`titulo` MEDIUMTEXT,"
//    		  +"PRIMARY KEY (`id_pesquisador`, `id_tipo_producao_cientifica`, `id_producao_cientifica`));";
//    		  
//    	sql = "INSERT " + schema + "." + domain + "_tab01 " 
//    		  +"SELECT B.PESQUISADOR_ID AS id_pesquisador, B.ContributionType AS id_tipo_producao_cientifica, B.ID AS id_producao_cientifica, TITULO AS titulo "  
//    		  +"FROM im_lattes.pesquisador A, im_lattes.basecontribution B "
//    		  +"WHERE A.INFO = '" + domain + "' AND A.ID = B.PESQUISADOR_ID "
//    		  +"ORDER BY id_pesquisador;";

//    		INSERT im_lattes.iae_tab01
//    		SELECT B.PESQUISADOR_ID AS id_pesquisador, B.ContributionType AS id_tipo_producao_cientifica, B.ID AS id_producao_cientifica, TITULO AS titulo  
//    		FROM im_lattes.pesquisador A, im_lattes.basecontribution B
//    		WHERE A.INFO = "iae" AND A.ID = B.PESQUISADOR_ID
//    		ORDER BY id_pesquisador"

//      System.out.println(sql);
      
      
  //Inicializa a classe SQLs que prepara os SQLs de todas as tabelas do caso1.    
  SQLs caso1 = new SQLs(schema,domain);
  
  	//Inicia a execução dos SQLs e DDls da tab01
  	caso1.createSQL01();
	statement.executeUpdate("DROP TABLE IF EXISTS im_lattes.iae_tab01;");
	//success = statement.execute(caso1.DDL_TAB01);
	success = statement.execute(caso1.SQL_TAB01);
	
	//Inicia a execução dos SQLs e DDls da tab03
	caso1.createSQL03();
	System.out.println(caso1.SQL_TAB03);
	statement.executeUpdate(caso1.SQL_TAB03);
	

      
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
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

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }
  /**
   * @return the server
   */
  public String getServer() {
  	return server;
  }


  /**
   * @param server the server to set
   */
  public void setServer(String server) {
  	this.server = server;
  }


  /**
   * @return the user
   */
  public String getUser() {
  	return user;
  }


  /**
   * @param user the user to set
   */
  public void setUser(String user) {
  	this.user = user;
  }


  /**
   * @return the schema
   */
  public String getSchema() {
  	return schema;
  }


  /**
   * @param schema the schema to set
   */
  public void setSchema(String schema) {
  	this.schema = schema;
  }


  /**
   * @return the domain
   */
  public String getDomain() {
  	return domain;
  }


  /**
   * @param domain the domain to set
   */
  public void setDomain(String domain) {
  	this.domain = domain;
  }
  /**
   * @return the password
   */
  public String getPassword() {
  	return password;
  }


  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
  	this.password = password;
  }


}