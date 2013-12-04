package tests;
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

import org.freehep.util.io.ASCII85InputStream;
import org.jdom2.input.*;
import org.jdom2.output.*;
import org.jdom2.*;

import java.io.*;

import javax.swing.JOptionPane;


public class TestReadFile {
	
	

	public static void main(String[] argv) throws Exception {
	    // MUST BE INVOKED BEFORE ANYTHING ELSE !!!
		File configFile = new File("C:\\Users\\igor.rocha\\Documents\\RapidMiner_Repository\\RssTest.rmp");
		//ParameterService.getParameterValue(RapidMiner.PROPERTY_RAPIDMINER_INIT_PLUGINS);
	    RapidMiner.init();

	    String rssPath = null;
	    String excelFilePath = null;
	    
	    while (rssPath == null){
			rssPath = JOptionPane.showInputDialog("Digite a URL do RSS:");
			if (rssPath == null || rssPath.equals("")){
				JOptionPane.showInputDialog("Por favor digite a URL do RSS desejado:");
			}
		}
	    
	    while (excelFilePath == null){
	    	excelFilePath = JOptionPane.showInputDialog("Digite o nome do arquivo de saída:");
			if (excelFilePath == null || excelFilePath.equals("")){
				JOptionPane.showInputDialog("Por favor digite a o nome do arquivo de saída:");
			}
		}
	    
	    // create the process from the command line argument file
	    Process process = new Process(configFile);
	    
	    Operator opRSS = process.getOperator("Read RSS Feed");
	    opRSS.setParameter("url", rssPath );
	    
	    Operator opExcel = process.getOperator("Write Excel");
	    opExcel.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, excelFilePath);

	  
	    
	    // run the process on the input
	    process.run();
	}
}
