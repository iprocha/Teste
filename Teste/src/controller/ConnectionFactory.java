/*
 * Data:
 * Dezembro de 2013
 * 
 * Descricao:
 * Programa de integracao de processos do Programa Rapid Miner, que compoem as etapas para a 
 * geracao de resultados de similaridades e agrupamentos entre pesquisadores em determinado dominio.
 * 
 * Creditos:
 * Processos Rapid Miner e processamentos de rotina SQL. 
 * Romualdo Alves Pereira Júnior - romualdoalves@gmail.com
 * 
 * Programa de integracao dos processos e  elaboracao de interface grafica.
 * Igor Pessoa Rocha - iprocha@gmail.com
 * 
 */

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

    //Metodo que retorna uma conexao com o banco.
	public Connection getConnection() {
        try {
            return DriverManager.getConnection(
          "jdbc:mysql://"+server+"/"+schema+"?allowMultiQueries=true", user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	//Metodo para testar conexao com o banco. Cria-se uma conexao com os valores passados e fecha-se logo em seguida.
	public static boolean testConnection(String server, String schema, String user, String password) throws SQLException, Exception{
		Connection test = null;
	
			test = DriverManager.getConnection(
          "jdbc:mysql://"+server+"/"+schema+"?allowMultiQueries=true", user, password);
			
			test.close();
       
		return true;
	}
	
	//Metodo que pesquisa a quantidade de pesquisadores de um determinado dominio.
		//O metodo retorna o numero de pesquisadores
		//Caso o dominio nao exista no banco, o valor retornado eh 0 zero.
		public static int validateDomain(String server, String schema, String user, String password, String domain)throws Exception, SQLException{
			int numResearchers = 0;
			Connection con = null;
			PreparedStatement statement = null;
			
			con = new ConnectionFactory(server, user, schema, password).getConnection();
			String sql = "Select COUNT(*) as numPesquisadores FROM im_lattes.pesquisador where pesquisador.INFO = ?";
			
			statement = con.prepareStatement(sql);
			statement.setString(1, domain);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				numResearchers = rs.getInt("numPesquisadores");
			}
			
			rs.close();
			statement.close();
			con.close();
			
			return (numResearchers);
			
		}
	
}
