package controller;

import java.sql.Connection;


public class RecordStages {
	private String server = "localhost";
	private String user = "root";
	private String schema = "processer";
	private String password = "81726354";
	
 Connection nativeDBConn = new ConnectionFactory(server,user, schema,
			 password).getConnection();
 //metodo que adiciona o registro no banco de que uma nova tabela foi criada com sucesso.
 public void addStage(String dominio, String tabelaCriada){
	 
 }
 
 //Metodo que retorna a ultima tabela consistente criada em um determinado dominio.
 public String checkDomain(String dominio){
	 String lastConsistentTable = null;
	 return lastConsistentTable;
	 
 }
}
