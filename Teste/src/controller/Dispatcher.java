package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;






import javax.swing.SwingUtilities;

import pivot.Pivot;
import view.ProcessWindow;
import view.SetUpWindow;

public class Dispatcher {
	
	
	
	public static String checkedInput (String message){
		String variable = null;
		while (variable == null){
			variable = JOptionPane.showInputDialog("Por favor, digite o(a) "+ message+ " desejado(a):");
			if (variable == null || variable.equals("")){
				JOptionPane.showInputDialog("É preciso digitar um(a) "+message+" para prosseguir.");
			}
		}
		return variable;
	}
	
	
	//Executar o processamento todo.
	 public static boolean execute(String server, String schema, String user, String password, String domain, int kNumber) {
		
//		String server = "localhost";
//		String user = "desenvolvimento";
//		String schema = "im_lattes";
//		String password = "123456";
//		String domain = "teste";
		String port = "3306";
		Statement statement = null;
		int processNumber = 0;
		
		
		
		//Pegando conexão com o banco.
				Connection con = new ConnectionFactory(server, user, schema, password).getConnection();
				RPMExecution rmpProcess = new RPMExecution(server,schema,password, domain, user);
				
				
				
				try{
				statement = con.createStatement();
				
				String rpmPath = null;
				//Inicializa a classe SQLs que prepara os SQLs de todas as tabelas do caso1.    
				  SQLs caso1 = new SQLs(schema,domain);
				  
				  	//Inicia a execução dos SQLs e DDls da tab01
				  	System.out.println("INFO: Processo iniciado. ");
				  	System.out.println("INFO: Tabela 01 ");
				  	caso1.createSQL01();			
					statement.execute(caso1.SQL_TAB01);
					System.out.println("INFO: Tabela 01 criada. ");
				
				
				  
					//Roda o processo rmp tab02
					System.out.println("INFO: Tabela 02 ");
				  	processNumber = 2;
					rpmPath = "public/tab02s.rmp";
					rmpProcess.run(rpmPath,processNumber,kNumber);
					System.out.println("INFO: Tabela 02 criada. ");
					
					//Inicia a execucao dos SQLs e DDls da tab03
					System.out.println("INFO: Tabela 03 ");
					caso1.createSQL03();
					statement.executeUpdate(caso1.SQL_TAB03);
					System.out.println("INFO: Tabela 03 criada. ");

					
					
					//Inicia o processo do RapidMiner da tabela 04
					System.out.println("INFO: Tabela 04 ");
					rpmPath = "public/tab04s.rmp";
					processNumber = 4;
					rmpProcess.run(rpmPath, processNumber, kNumber);
					System.out.println("INFO: Tabela 04 criada. ");
				
					//Inicia o execucao do Script SQL das tabelas de 05 a 09
					System.out.println("INFO: Tabelas 05 a 09 ");
					caso1.createSQL05a09();
					statement.executeUpdate(caso1.SQL_TAB05A09);
					System.out.println("INFO: Tabelas 05 a 09 criadas. ");
					
					statement.close();
				    con.close();
				    
				    con = new ConnectionFactory(server, user, schema, password).getConnection();
				    statement = con.createStatement();
				    
					//Inicia o processo RapidMiner da tabela 10.01 e tabela 10.02
					System.out.println("INFO: Tabelas 10.01 e 10.02 ");
					rpmPath = "public/tab10s.rmp";
					processNumber = 10;
					rmpProcess.run(rpmPath, processNumber, kNumber);
					System.out.println("INFO: Tabelas 10.01 e 10.02 criadas. ");
				
					//Inicia o execucao do Script SQL das tabelas 10.03 a 12
					System.out.println("INFO: Tabelas 10.03 a 12 ");
					caso1.createSQL10_03a12();
					statement.executeUpdate(caso1.SQL_TAB10_03A12);
					System.out.println("INFO: Tabelas 10.03 a 12 criadas. ");
					
					
					//Inica o processo RapidMiner da tabela 13
					System.out.println("INFO: Tabela 13 ");
					rpmPath = "public/tab13s.rmp";
					processNumber = 13;
					rmpProcess.run(rpmPath, processNumber, kNumber);
					System.out.println("INFO: Tabela 13 criada. ");
					
				  	//Executar o o Pivot na tabela 13 pra gera a tabela 14
					System.out.println("INFO: Tabela 14 ");
				    String[] pivotArguments = new String[7];
				    pivotArguments[0] = domain+"_tab13s";
				    pivotArguments[1] = domain+"_tab14s";
				    pivotArguments[2] = server;
		    		pivotArguments[3] = port;
					pivotArguments[4] = schema;
					pivotArguments[5] = user;
					pivotArguments[6] = password;
				    								
				    Pivot.main(pivotArguments);
				    System.out.println("INFO: Tabela 14 criada. ");
				  
					
					//Inicia o execucao do Script SQL da tabela 15
				    System.out.println("INFO: Tabela 15 ");
					caso1.createSQL14a15();
					statement.executeUpdate(caso1.SQL_TAB14A15);
				    System.out.println("INFO: Tabela 15 criada. ");
					
				    //Executa o Pivot novamente usando a tab10s3 como entrada e a tab16s como saida
				    System.out.println("INFO: Tabela 16 ");
				    pivotArguments[0] = domain+"_tab10s3";
				    pivotArguments[1] = domain+"_tab16s";
				    Pivot.main(pivotArguments);
				    System.out.println("INFO: Tabela 16 criada. ");
				    
				    
				    
				    
				    System.out.println("INFO: Processamento finalizado com sucesso!");
				}catch(SQLException sqle){
					System.out.println(sqle);
				}catch(Exception e){
					System.out.println(e);
				}finally{
					//Fechando conexão
				    try{
						statement.close();
					    con.close();
				    }catch(SQLException sqle2){
				    	System.out.println(sqle2);
				    }
				}
		return true;
	}
	 
//	 public static ProcessWindow openWindow(){
//		 ProcessWindow frame = new ProcessWindow();
////			frame.setVisible(true);
//			frame.lblNewLabel_1.setText("Testando");
//			return frame;
//	 }
	
	
	public static void executeWindow(String server, String schema, String user, String password, String domain, int kNumber){
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProcessWindow frame = new ProcessWindow();
//					frame.setVisible(true);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		//openWindow();
		
		execute(server, schema, user, password, domain, kNumber);
		
		
		
		
	}


//	 public static void main(String[] args)  throws Exception {
//	        SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				SetUpWindow frame = new SetUpWindow();
//				frame.setVisible(true);
////				ProcessWindow frame2  = new ProcessWindow();
////				frame2.setVisible(true);
//			}
//		});
//	}

}
