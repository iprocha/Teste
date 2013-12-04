package controller;
import javax.swing.JOptionPane;

import java.io.File;

import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.io.CSVDataReader;
import com.rapidminer.operator.io.ExcelExampleSource;
import com.rapidminer.operator.io.ModelWriter;
import com.rapidminer.operator.io.RepositorySource;
import com.rapidminer.operator.learner.bayes.NaiveBayes;
import com.rapidminer.tools.OperatorService;
import com.rapidminer.tools.ParameterService;
import com.rapidminer.Process;
import com.rapidminer.operator.io.web.RSSExampleSource;
import com.rapidminer.operator.io.ExcelExampleSetWriter;
import com.rapidminer.operator.io.DatabaseDataReader;

import java.io.*;

import javax.swing.JOptionPane;


public class RPMExecution {
	private String server;
	private String schema;
	private String password;
	private String domain;
	private String user;
	private String database_url;

	public RPMExecution(String server, String schema, String password,
			String domain, String user) {
		this.server = server;
		this.schema = schema;
		this.password = password;
		this.domain = domain;
		this.user = user;
		this.database_url = "jdbc:mysql://"+server+":3306/"+schema;
	}
	
	private Operator setDBOnOperator(String operatorName, Process process){
		Operator operator = process.getOperator(operatorName);
		if (operator != null){
			operator.setParameter("database_url", database_url);
			operator.setParameter("username", user);
			operator.setParameter("password", password);
		}
		else{
			System.out.println("Operador nao econtrado!");
		}
		
		return operator;
		
	}
	
	private Process getProcess(String processPath) throws Exception{
		File configFile = null;
		Process process = null;
		try{
		configFile = new File(processPath);
		process = new Process(configFile);
		
		//Caso nao econtre um processo lanca uma mensagem de erro
		}catch(Exception e){
			System.out.println("ERRO: Nenhum processo foi encontrado!");
		}
		return process;
		
	}


	public void run(String processPath, int processNumber, int kNumber) throws Exception {
	    // MUST BE INVOKED BEFORE ANYTHING ELSE !!!
		
		
		
		ParameterService.getParameterValue(RapidMiner.PROPERTY_RAPIDMINER_INIT_PLUGINS);
		String rapidMinerHome = "/usr/local/rapidminer";
		System.setProperty("rapidminer.home", rapidMinerHome);
		RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
	    RapidMiner.init();
	    
	    Operator opReadDB;
    	Operator opWriteDB;
    	Operator opWriteDB2;
    	Operator opClustering;

//	    String rssPath = null;
//	    String excelFilePath = null;
//	    
//	    while (rssPath == null){
//			rssPath = JOptionPane.showInputDialog("Digite a URL do RSS:");
//			if (rssPath == null || rssPath.equals("")){
//				JOptionPane.showInputDialog("Por favor digite a URL do RSS desejado:");
//			}
//		}
//	    
//	    while (excelFilePath == null){
//	    	excelFilePath = JOptionPane.showInputDialog("Digite o nome do arquivo de saída:");
//			if (excelFilePath == null || excelFilePath.equals("")){
//				JOptionPane.showInputDialog("Por favor digite a o nome do arquivo de saída:");
//			}
//		}
	    
	    // create the process from the command line argument file
	    
	    
	    
	    
	    
//	    Operator opReadDB = process.getOperator("Read Database");
//	    System.out.println(opReadDB.getParameterAsString("query"));
//	    opReadDB.setParameter("database_url", database_url);
//	    opReadDB.setParameter("database_username", user);
//	    opReadDB.setParameter("database_password", password);
//	    opReadDB.setParameter("query", );
	    
	    
	    
//	    Operator opWriteDB = process.getOperator("Write Database");
//	    System.out.println(opWriteDB);
//	    opWriteDB.getParameterAsString("table_name");
//	    System.out.println(opWriteDB.getParameterAsString("table_name"));
//	    opWriteDB.setParameter("database_url", database_url);
//	    opWriteDB.setParameter("database_username", user);
//	    opWriteDB.setParameter("database_password", password);
//	    opWriteDB.setParameter("table_name", );
	    
	    
//	    Operator opWriteDB = process.getOperator("Write Database (2)");
//	    System.out.println(opWriteDB);
//	    opWriteDB.setParameter("database_url", database_url);
//	    opWriteDB.setParameter("database_username", user);
//	    opWriteDB.setParameter("database_password", password);
//	    opWriteDB.setParameter("table_name", value);
	    
	    switch(processNumber){
	    case 2:
	    	Process process2 = getProcess(processPath);
	    	opReadDB = setDBOnOperator("Read Database", process2);
	    	opReadDB.setParameter("query","Select * from "+ schema + "." + "iae_tab01" );
	    	opWriteDB = setDBOnOperator("Write Database", process2);
	    	opWriteDB.setParameter("table_name", domain+"_tab02s");
	    	process2.run();
	    	break;
	    	
	    case 4:
	    	Process process4 = getProcess(processPath);
	    	opReadDB = setDBOnOperator("Read Database", process4);
	    	opReadDB.setParameter("query","Select * from "+ schema + "." + domain + "_tab03s" );
	    	opWriteDB = setDBOnOperator("Write Database", process4);
	    	opWriteDB.setParameter("table_name", domain+"_tab04s");
	    	process4.run();
	    	break;
	    	
	    case 10:
	    	Process process10 = getProcess(processPath);
	    	opReadDB = setDBOnOperator("Read Database", process10);
	    	opReadDB.setParameter("query","Select * from "+ schema + "." + domain + "_tab03s" );
	    	opWriteDB = setDBOnOperator("Write Database", process10);
	    	opWriteDB.setParameter("table_name", domain+"_tab10s2");
	    	opWriteDB2 = setDBOnOperator("Write Database (2)", process10);
	    	opWriteDB2.setParameter("table_name",domain + "_tab10s1" );
	    	opClustering = process10.getOperator("Clustering");
	    	opClustering.setParameter("k", String.valueOf(kNumber));
	    	process10.run();
	    	
	    	break;
	    	
	    case 13:
	    	Process process13 = getProcess(processPath);
	    	opReadDB = setDBOnOperator("Read Database", process13);
	    	opReadDB.setParameter("query","Select * from "+ schema + "." + domain + "_tab03s" );
	    	opWriteDB = setDBOnOperator("Write Database", process13);
	    	opWriteDB.setParameter("table_name", domain+"_tab13s");
	    	process13.run();
	    	break;
	    }
	    
	    
//	    Operator opRSS = process.getOperator("Read RSS Feed");
//	    opRSS.setParameter("url", rssPath );
//	    
//	    Operator opExcel = process.getOperator("Write Excel");
//	    opExcel.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, excelFilePath);

	  
	    
	    // run the process on the input
	    //process.run();
	}
}