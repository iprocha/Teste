package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;


public class ConnectionFactory {
	private String server = "localhost";
	private String user = "desenvolvimento";
	private String schema = "im_lattes";
	private String password = "123456";
	
	
	
    public ConnectionFactory(String server, String user, String schema,
			String password) {
		this.server = server;
		this.user = user;
		this.schema = schema;
		this.password = password;
	}

	public Connection getConnection() {
        try {
            return DriverManager.getConnection(
          "jdbc:mysql://"+server+"/"+schema+"?allowMultiQueries=true", user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static boolean testConnection(String server, String schema, String user, String password){
		try {
			Connection test = DriverManager.getConnection(
          "jdbc:mysql://"+server+"/"+schema+"?allowMultiQueries=true", user, password);
			
			test.close();
        } catch (SQLException e) {
        	System.out.println(e);
        	return false;
        }
		return true;
	}
	
	//Metodo que pesquisa a quantidade de pesquisadores de um determinado dominio.
		//O metodo retorna o numero de pesquisadores
		//Caso o dominio nao exista no banco, o valor retornado eh 0 zero.
		public static int validateDomain(String server, String schema, String user, String password, String domain){
			int numResearchers = 0;
			try{
			Connection con = new ConnectionFactory(server, user, schema, password).getConnection();
			String sql = "Select COUNT(*) as numPesquisadores FROM im_lattes.pesquisador where pesquisador.INFO = ?";
			
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, domain);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				numResearchers = rs.getInt("numPesquisadores");
			}
			 }catch(SQLException e){
				 System.out.println(e);
			 }
			return (numResearchers);
			
		}
	
}
