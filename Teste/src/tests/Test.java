package tests;
import java.io.File;

import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.io.CSVDataReader;
import com.rapidminer.operator.io.ModelWriter;
import com.rapidminer.operator.io.RepositorySource;
import com.rapidminer.operator.learner.bayes.NaiveBayes;
import com.rapidminer.tools.OperatorService;
import com.rapidminer.tools.ParameterService;
import com.rapidminer.Process;
import com.rapidminer.operator.io.web.RSSExampleSource;
import com.rapidminer.operator.io.ExcelExampleSetWriter;
import org.jdom2.input.*;
import org.jdom2.output.*;
import org.jdom2.*;

import javax.swing.JOptionPane;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String rapidMinerHome = "/usr/local/rapidminer";
		System.setProperty("rapidminer.home", rapidMinerHome);
		RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
		RapidMiner.init();
		
		String rssPath = null;
		
		while (rssPath == null){
			rssPath = JOptionPane.showInputDialog("Digite a URL do RSS:");
			if (rssPath == null || rssPath.equals("")){
				JOptionPane.showInputDialog("Por favor digite a URL do RSS desejado:");
			}
		}
		
		try {
			
			/* Buscando RSS */
			Operator rss = OperatorService.createOperator(RSSExampleSource.class);
			rss.setParameter("url", rssPath);
			
			/* Excel*/
			Operator excel = OperatorService.createOperator(ExcelExampleSetWriter.class);
			
			excel.setParameter(ExcelExampleSetWriter.PARAMETER_EXCEL_FILE, "test.xls");
			
			
			Process process = new Process();
			
			process.getRootOperator().getSubprocess(0).addOperator(rss);
			process.getRootOperator().getSubprocess(0).addOperator(excel);
			
			rss.getOutputPorts().getPortByName("output").connectTo(excel.getInputPorts().getPortByName("input"));
			
			
			
			
			
			
			process.run();
			

			
			
		} catch (OperatorCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}