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
	
	//Muda os parâmetros do banco no operador do processo Rapid Miner.
	private Operator setDBOnOperator(String operatorName, Process process){
		Operator operator = process.getOperator(operatorName);
		if (operator != null){
			operator.setParameter("database_url", database_url);
			operator.setParameter("username", user);
			operator.setParameter("password", password);
		}
		else{
			System.out.println("Operador nao econtrado!"+  "("+operatorName+ ")");
		}
		
		return operator;
		
	}
	
	//Metodo que retorna um processo a partir do caminho de seu arquivo .rmp
	//Caso o processo não exista, lanca uma excecao.
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


	//Metodo que executa os processos .rmp
	public void run(String processPath, int processNumber, int kNumber, int pruneAbove, int pruneBelow) throws Exception {
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
    	Operator opPrune;

	    
	     // Switch que seleciona qual processo vai ser executado. 
	     //Seta os devidos parâmetros para cada processo.
	    switch(processNumber){
	    case 2:
	    	Process process2 = getProcess(processPath);
	    	opReadDB = setDBOnOperator("Read Database", process2);
	    	opReadDB.setParameter("query","Select * from "+ schema + "." + domain+ "_tab01" );
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
	    	opPrune = process10.getOperator("Process Documents from Data");
	    	opPrune.setParameter("prune_above_absolute", String.valueOf(pruneAbove));
	    	opPrune.setParameter("prune_below_absolute", String.valueOf(pruneBelow));
	    	process10.run();
	    	
	    	break;
	    	
	    case 13:
	    	Process process13 = getProcess(processPath);
	    	opReadDB = setDBOnOperator("Read Database", process13);
	    	opReadDB.setParameter("query","Select * from "+ schema + "." + domain + "_tab03s" );
	    	opWriteDB = setDBOnOperator("Write Database", process13);
	    	opWriteDB.setParameter("table_name", domain+"_tab13s");
	    	opPrune = process13.getOperator("Process Documents from Data");
	    	opPrune.setParameter("prune_above_absolute", String.valueOf(pruneAbove));
	    	opPrune.setParameter("prune_below_absolute", String.valueOf(pruneBelow));
	    	process13.run();
	    	break;
	    case 14:
	    	Process processGephiNode = getProcess(processPath);
	    	opReadDB = setDBOnOperator("Read Database", processGephiNode);
	    	opReadDB.setParameter("query","SELECT A.id_pesquisador AS id, B.NOMECOMPLETO AS label FROM "+ schema + "." + domain + "_tab03s A, "+ schema + ".pesquisador B WHERE A.id_pesquisador = B.ID;" );
	    	opWriteDB = setDBOnOperator("Write Database", processGephiNode);
	    	opWriteDB.setParameter("table_name", domain+"_gephi_nodes_s");
	    	processGephiNode.run();
	    	break;
	    case 15:
	    	Process processGephiEdges = getProcess(processPath);
	    	opReadDB = setDBOnOperator("Read Database", processGephiEdges);
	    	opReadDB.setParameter("query","SELECT id_pesquisador1 AS source, id_pesquisador2 AS target, similarity_s AS similaridade FROM "+ schema + "." + domain + "_tab08s;" );
	    	opWriteDB = setDBOnOperator("Write Database", processGephiEdges);
	    	opWriteDB.setParameter("table_name", domain+"_gephi_edges_s");
	    	processGephiEdges.run();
	    	break;
	    }
	    
	
	}
}