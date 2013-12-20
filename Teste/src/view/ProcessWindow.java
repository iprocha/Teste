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

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import pivot.Pivot;
import controller.ConnectionFactory;
import controller.RPMExecution;
import controller.SQLs;

import javax.swing.JProgressBar;

import oracle.sql.DATE;

import java.util.Date;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JSeparator;
public class ProcessWindow extends JFrame {

	private JPanel contentPane;
	public JLabel lblNewLabel_0;
	public JLabel lblNewLabel_1;
	public JLabel lblNewLabel_2;
	public JLabel lblNewLabel_3;
	public JLabel lblNewLabel_4;
	public JLabel lblNewLabel_5;
	public JLabel lblNewLabel_6;
	public JLabel lblNewLabel_7;
	public JLabel lblNewLabel_8;
	public JLabel lblNewLabel_9;
	public JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_13;
	private JLabel lblNewLabel_14;
	
	private JLabel lblNewLabelTS_1;
	private JLabel lblNewLabelTS_2;
	private JLabel lblNewLabelTS_3;
	private JLabel lblNewLabelTS_4;
	private JLabel lblNewLabelTS_5;
	private JLabel lblNewLabelTS_6;
	private JLabel lblNewLabelTS_7;
	private JLabel lblNewLabelTS_8;
	private JLabel lblNewLabelTS_9;
	private JLabel lblNewLabelTS_10;
	private JLabel lblNewLabelTS_11;
	private JLabel lblNewLabelTS_12;
	private JLabel lblNewLabelTS_13;
	
	private JLabel lblLabelDominio;
	private JLabel lblLabelNumPesquisadores;
	
	private String server; 
	private String schema; 
	private String user;
	private String password; 
	private String domain; 
	private int kNumber;
	private int numResearchers;
	private JProgressBar progressBar;
	private JLabel lblNewLabel_12;
	private JButton btnNewButton;
	private int pruneBelow;
	private int pruneAbove;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProcessWindow frame = new ProcessWindow();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ProcessWindow(String serverArg, String schemaArg, String userArg, String passwordArg, String domainArg, int kNumberArg, int numResearchersArg, int pruneBelowArg, int pruneAboveArg) {
		setTitle("Processer");
		
		this.server = serverArg;
		this.schema = schemaArg;
		this.user = userArg;
		this.password = passwordArg;
		this.domain = domainArg;
		this.kNumber = kNumberArg;
		this.numResearchers = numResearchersArg;
		this.pruneAbove = pruneAboveArg;
		this.pruneBelow = pruneBelowArg;
		
		setBounds(100, 100, 629, 564);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel_0 = new JLabel("Status do Processamento");
		lblNewLabel_0.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_0.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_0.setBounds(10, 11, 583, 14);
		contentPane.add(lblNewLabel_0);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(10, 102, 176, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(10, 127, 176, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(10, 152, 176, 14);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(10, 177, 176, 14);
		contentPane.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(10, 202, 176, 14);
		contentPane.add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setBounds(10, 227, 176, 14);
		contentPane.add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(10, 252, 176, 14);
		contentPane.add(lblNewLabel_7);
		
		lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setBounds(10, 277, 176, 14);
		contentPane.add(lblNewLabel_8);
		
		lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setBounds(10, 302, 176, 14);
		contentPane.add(lblNewLabel_9);
		
		lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setBounds(10, 327, 176, 14);
		contentPane.add(lblNewLabel_10);
		
		
		btnNewButton = new JButton("Executar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNewButton.setEnabled(false);
				
				SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
					   @Override
					   protected Boolean doInBackground() throws Exception {
						   
						   
						   
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
									  	publish(1);
									  	caso1.createSQL01();			
										statement.execute(caso1.SQL_TAB01);
										System.out.println("INFO: Tabela 01 criada. ");
										publish(2);
										
									
									  
										//Roda o processo rmp tab02
										System.out.println("INFO: Tabela 02 ");
										publish(3);
									  	processNumber = 2;
										rpmPath = "public/tab02s.rmp";
										rmpProcess.run(rpmPath,processNumber,kNumber, pruneAbove, pruneBelow);
										System.out.println("INFO: Tabela 02 criada. ");
										publish(4);
										
										//Inicia a execucao dos SQLs e DDls da tab03
										System.out.println("INFO: Tabela 03 ");
										publish(5);
										caso1.createSQL03();
										statement.executeUpdate(caso1.SQL_TAB03);
										System.out.println("INFO: Tabela 03 criada. ");
										publish(6);

										
										
										//Inicia o processo do RapidMiner da tabela 04
										System.out.println("INFO: Tabela 04 ");
										publish(7);
										rpmPath = "public/tab04s.rmp";
										processNumber = 4;
										rmpProcess.run(rpmPath, processNumber, kNumber, pruneAbove, pruneBelow);
										System.out.println("INFO: Tabela 04 criada. ");
										publish(8);
									
										//Inicia o execucao do Script SQL das tabelas de 05 a 09
										System.out.println("INFO: Tabelas 05 a 09 ");
										publish(9);
										caso1.createSQL05a09();
										statement.executeUpdate(caso1.SQL_TAB05A09);
										System.out.println("INFO: Tabelas 05 a 09 criadas. ");
										publish(10);
										statement.close();
										
										statement = con.createStatement();
										//Inicia o processo RapidMiner da tabela 10.01 e tabela 10.02
										System.out.println("INFO: Tabelas 10.01 e 10.02 ");
										publish(11);
										rpmPath = "public/tab10s.rmp";
										processNumber = 10;
										rmpProcess.run(rpmPath, processNumber, kNumber, pruneAbove, pruneBelow);
										System.out.println("INFO: Tabelas 10.01 e 10.02 criadas. ");
										publish(12);
									
										//Inicia o execucao do Script SQL das tabelas 10.03 a 12
										System.out.println("INFO: Tabelas 10.03 a 12 ");
										publish(13);
										caso1.createSQL10_03a12();
										statement.executeUpdate(caso1.SQL_TAB10_03A12);
										System.out.println("INFO: Tabelas 10.03 a 12 criadas. ");
										publish(14);
										
										
										//Inica o processo RapidMiner da tabela 13
										System.out.println("INFO: Tabela 13 ");
										publish(15);
										rpmPath = "public/tab13s.rmp";
										processNumber = 13;
										rmpProcess.run(rpmPath, processNumber, kNumber, pruneAbove, pruneBelow);
										System.out.println("INFO: Tabela 13 criada. ");
										publish(16);
										
									  	//Executar o o Pivot na tabela 13 pra gera a tabela 14
										System.out.println("INFO: Tabela 14 ");
										publish(17);
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
									    publish(18);
									  
										
										//Inicia o execucao do Script SQL da tabela 15
									    System.out.println("INFO: Tabela 15 ");
									    publish(19);
										caso1.createSQL14a15();
										statement.executeUpdate(caso1.SQL_TAB14A15);
									    System.out.println("INFO: Tabela 15 criada. ");
									    publish(20);
										
									    //Executa o Pivot novamente usando a tab10s3 como entrada e a tab16s como saida
									    System.out.println("INFO: Tabela 16 ");
									    publish(21);
									    pivotArguments[0] = domain+"_tab10s3";
									    pivotArguments[1] = domain+"_tab16s";
									    Pivot.main(pivotArguments);
									    System.out.println("INFO: Tabela 16 criada. ");
									    publish(22);
									    
									  //Inicia o processo RapidMiner que gera a tabela gephi_nodes
									    System.out.println("INFO: Tabela gephi_nodes ");
										publish(23);
										rpmPath = "public/gephi_nodes_s.rmp";
										processNumber = 14;
										rmpProcess.run(rpmPath, processNumber, kNumber, pruneAbove, pruneBelow);
										System.out.println("INFO: Tabela gephi_nodes criada. ");
										publish(24);
									    
										 //Inicia o processo RapidMiner que gera a tabela gephi_edges
									    System.out.println("INFO: Tabela gephi_edges ");
										publish(25);
										rpmPath = "public/gephi_edges_s.rmp";
										processNumber = 15;
										rmpProcess.run(rpmPath, processNumber, kNumber, pruneAbove, pruneBelow);
										System.out.println("INFO: Tabela gephi_edges criada. ");
										publish(26);
									    
									    System.out.println("INFO: Processamento finalizado com sucesso!");
									    publish(0);
									}catch(SQLException sqle){
										lblNewLabel_12.setForeground(Color.red);
										lblNewLabel_12.setText(sqle.toString());
									}catch(Exception e){
										lblNewLabel_12.setForeground(Color.red);
										lblNewLabel_12.setText(e.toString());
									}finally{
										//Fechando conexão
									    try{
											statement.close();
										    con.close();
									    }catch(SQLException sqle2){
									    	lblNewLabel_12.setForeground(Color.red);
											lblNewLabel_12.setText(sqle2.toString());
									    }
									}
					     // The type we pass to publish() is determined
					     // by the second template parameter.
					    

					    // Here we can return some object of whatever type
					    // we specified for the first template parameter.
					    // (in this case we're auto-boxing 'true').
					    return true;
					   }

					   // Can safely update the GUI from this method.
					   protected void done() {
					    
					    boolean status;
					    try {
							
					     // Retrieve the return value of doInBackground.
					     status = get();
					     //lblNewLabel_0.setText("Completed with status: " + status);
					    } catch (InterruptedException e) {
					     // This is thrown if the thread's interrupted.
					    } catch (ExecutionException e) {
					     // This is thrown if we throw an exception
					     // from doInBackground.
					    }
					   }
					  
					   @Override
					   // Can safely update the GUI from this method.
					   protected void process(List<Integer> chunks) {
						   //Todo valor do publish() cai aqui nesse metodo process()
						   //Laco que percorre todo a lista de inteiros. 
						   //Algumas vezes os valores vem agrupados, por isso a necessidade de percorrer toda a lista sempre .
						   int init = 0;
						   while(init < chunks.size()){
							   
							switchMessage(chunks.get(init));
							System.out.println(String.valueOf(chunks.get(init)));
							init++;
							
						   }
					   }
					   
					   
					  };
					  
					  worker.execute();
			}
		});
		btnNewButton.setBounds(256, 477, 89, 23);
		contentPane.add(btnNewButton);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(232, 452, 146, 14);
		contentPane.add(progressBar);
		
		lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setBounds(10, 352, 176, 14);
		contentPane.add(lblNewLabel_11);
		
		lblNewLabel_12 = new JLabel("Iniciar Processamento");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setBounds(10, 427, 583, 14);
		contentPane.add(lblNewLabel_12);
		
		lblNewLabel_13 = new JLabel("");
		lblNewLabel_13.setBounds(10, 377, 176, 14);
		contentPane.add(lblNewLabel_13);
		
		lblNewLabel_14 = new JLabel("");
		lblNewLabel_14.setBounds(10, 402, 176, 14);
		contentPane.add(lblNewLabel_14);
		
		lblNewLabelTS_1 = new JLabel("");
		lblNewLabelTS_1.setBounds(196, 102, 182, 14);
		contentPane.add(lblNewLabelTS_1);
		
		lblNewLabelTS_2 = new JLabel("");
		lblNewLabelTS_2.setBounds(196, 127, 182, 14);
		contentPane.add(lblNewLabelTS_2);
		
		lblNewLabelTS_3 = new JLabel("");
		lblNewLabelTS_3.setBounds(196, 152, 182, 14);
		contentPane.add(lblNewLabelTS_3);
		
		lblNewLabelTS_4 = new JLabel("");
		lblNewLabelTS_4.setBounds(196, 177, 182, 14);
		contentPane.add(lblNewLabelTS_4);
		
		lblNewLabelTS_5 = new JLabel("");
		lblNewLabelTS_5.setBounds(196, 202, 182, 14);
		contentPane.add(lblNewLabelTS_5);
		
		lblNewLabelTS_6 = new JLabel("");
		lblNewLabelTS_6.setBounds(196, 227, 182, 14);
		contentPane.add(lblNewLabelTS_6);
		
		lblNewLabelTS_7 = new JLabel("");
		lblNewLabelTS_7.setBounds(196, 252, 182, 14);
		contentPane.add(lblNewLabelTS_7);
		
		lblNewLabelTS_8 = new JLabel("");
		lblNewLabelTS_8.setBounds(196, 277, 182, 14);
		contentPane.add(lblNewLabelTS_8);
		
		lblNewLabelTS_9 = new JLabel("");
		lblNewLabelTS_9.setBounds(196, 302, 182, 14);
		contentPane.add(lblNewLabelTS_9);
		
		lblNewLabelTS_10 = new JLabel("");
		lblNewLabelTS_10.setBounds(196, 327, 182, 14);
		contentPane.add(lblNewLabelTS_10);
		
		lblNewLabelTS_11 = new JLabel("");
		lblNewLabelTS_11.setBounds(196, 352, 182, 14);
		contentPane.add(lblNewLabelTS_11);
		
		lblNewLabelTS_12 = new JLabel("");
		lblNewLabelTS_12.setBounds(196, 377, 182, 14);
		contentPane.add(lblNewLabelTS_12);
		
		lblNewLabelTS_13 = new JLabel("");
		lblNewLabelTS_13.setBounds(196, 401, 182, 14);
		contentPane.add(lblNewLabelTS_13);
		
		JLabel lblNewLabel = new JLabel("Dom\u00EDnio:");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 36, 176, 14);
		contentPane.add(lblNewLabel);
		
		lblLabelDominio = new JLabel("Iae");
		lblLabelDominio.setForeground(Color.BLUE);
		lblLabelDominio.setBounds(196, 36, 190, 14);
		contentPane.add(lblLabelDominio);
		
		
		JLabel lblNmeroDePesquisadores = new JLabel("N\u00FAmero de Pesquisadores:");
		lblNmeroDePesquisadores.setForeground(Color.BLACK);
		lblNmeroDePesquisadores.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNmeroDePesquisadores.setBounds(10, 61, 176, 14);
		contentPane.add(lblNmeroDePesquisadores);
		
		lblLabelNumPesquisadores = new JLabel("256");
		lblLabelNumPesquisadores.setForeground(Color.BLUE);
		lblLabelNumPesquisadores.setBounds(196, 61, 199, 14);
		contentPane.add(lblLabelNumPesquisadores);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnNewButton, lblNewLabel_0, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNewLabel_4, lblNewLabel_5, lblNewLabel_6, lblNewLabel_7, lblNewLabel_8, lblNewLabel_9, lblNewLabel_10, progressBar, lblNewLabel_11, lblNewLabel_12, lblNewLabel_13, lblNewLabel_14, lblNewLabelTS_1, lblNewLabelTS_2, lblNewLabelTS_3, lblNewLabelTS_4, lblNewLabelTS_5, lblNewLabelTS_6, lblNewLabelTS_7, lblNewLabelTS_8, lblNewLabelTS_9, lblNewLabelTS_10, lblNewLabelTS_11, lblNewLabelTS_12, lblNewLabelTS_13}));
		
		lblLabelDominio.setText(String.valueOf(domainArg));
		lblLabelNumPesquisadores.setText(String.valueOf(numResearchersArg));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 86, 546, 2);
		contentPane.add(separator);
	
	}
	
	
	//Switch que identifica qual parte do processamento está sendo executada e
	//seta as devidas mensagens que aparecerão na tela de Status do Processamento.
	private void switchMessage(int value){
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy '-' HH:mm:ss'h'");
		switch(value){
		
		case 0:
			lblNewLabel_12.setForeground(Color.green);
			lblNewLabel_12.setText("Processamento Finalizado com sucesso!");
			break;
		case 1:
			lblNewLabel_12.setText("Processando...");
			lblNewLabel_1.setForeground(Color.blue);
			lblNewLabel_1.setText("Criando Tabela 01...");
			break;
		case 2:
			lblNewLabel_1.setForeground(Color.green);
			lblNewLabel_1.setText("Tabela 01 criada!");
			lblNewLabelTS_1.setForeground(Color.black);
			lblNewLabelTS_1.setText(ft.format(date));
			progressBar.setValue(6);
			break;
		case 3:
			lblNewLabel_2.setForeground(Color.blue);
			lblNewLabel_2.setText("Criando Tabela 02...");
			break;
		case 4:
			lblNewLabel_2.setForeground(Color.green);
			lblNewLabel_2.setText("Tabela 02 criada!");
			lblNewLabelTS_2.setForeground(Color.black);
			lblNewLabelTS_2.setText(ft.format(date));
			progressBar.setValue(12);
			break;
		case 5:
			lblNewLabel_3.setForeground(Color.blue);
			lblNewLabel_3.setText("Criando Tabela 03...");
			break;
		case 6:
			lblNewLabel_3.setForeground(Color.green);
			lblNewLabel_3.setText("Tabela 03 criada!");
			lblNewLabelTS_3.setForeground(Color.black);
			lblNewLabelTS_3.setText(ft.format(date));
			progressBar.setValue(18);
			break;
		case 7:
			lblNewLabel_4.setForeground(Color.blue);
			lblNewLabel_4.setText("Criando Tabela 04...");
			break;
		case 8:
			lblNewLabel_4.setForeground(Color.green);
			lblNewLabel_4.setText("Tabela 04 criada!");
			lblNewLabelTS_4.setForeground(Color.black);
			lblNewLabelTS_4.setText(ft.format(date));
			progressBar.setValue(24);
			break;
		case 9:
			lblNewLabel_5.setForeground(Color.blue);
			lblNewLabel_5.setText("Criando Tabelas 05 a 09...");
			break;
		case 10:
			lblNewLabel_5.setForeground(Color.green);
			lblNewLabel_5.setText("Tabelas 05 a 09 criadas!");
			lblNewLabelTS_5.setForeground(Color.black);
			lblNewLabelTS_5.setText(ft.format(date));
			progressBar.setValue(54);
			break;
		case 11:
			lblNewLabel_6.setForeground(Color.blue);
			lblNewLabel_6.setText("Criando Tabelas 10.01 e 10.02...");
			break;
		case 12:
			lblNewLabel_6.setForeground(Color.green);
			lblNewLabel_6.setText("Tabelas 10.01 e 10.02 criadas!");
			lblNewLabelTS_6.setForeground(Color.black);
			lblNewLabelTS_6.setText(ft.format(date));
			progressBar.setValue(68);
			break;
		case 13:
			lblNewLabel_7.setForeground(Color.blue);
			lblNewLabel_7.setText("Criando Tabelas 10.03 a 12...");
			break;
		case 14:
			lblNewLabel_7.setForeground(Color.green);
			lblNewLabel_7.setText("Tabelas 10.03 a 12 criadas!");
			lblNewLabelTS_7.setForeground(Color.black);
			lblNewLabelTS_7.setText(ft.format(date));
			progressBar.setValue(86);
			break;
		case 15:
			lblNewLabel_8.setForeground(Color.blue);
			lblNewLabel_8.setText("Criando Tabela 13...");
			break;
		case 16:
			lblNewLabel_8.setForeground(Color.green);
			lblNewLabel_8.setText("Tabela 13 criada!");
			lblNewLabelTS_8.setForeground(Color.black);
			lblNewLabelTS_8.setText(ft.format(date));
			progressBar.setValue(92);
			break;
		case 17:
			lblNewLabel_9.setForeground(Color.blue);
			lblNewLabel_9.setText("Criando Tabela 14...");
			break;
		case 18:
			lblNewLabel_9.setForeground(Color.green);
			lblNewLabel_9.setText("Tabela 14 criada!");
			lblNewLabelTS_9.setForeground(Color.black);
			lblNewLabelTS_9.setText(ft.format(date));
			progressBar.setValue(95);
			break;
		case 19:
			lblNewLabel_10.setForeground(Color.blue);
			lblNewLabel_10.setText("Criando Tabela 15...");
			break;
		case 20:
			lblNewLabel_10.setForeground(Color.green);
			lblNewLabel_10.setText("Tabela 15 criada!");
			lblNewLabelTS_10.setForeground(Color.black);
			lblNewLabelTS_10.setText(ft.format(date));
			progressBar.setValue(97);
			break;
		case 21:
			lblNewLabel_11.setForeground(Color.blue);
			lblNewLabel_11.setText("Criando Tabela 16...");
			break;
		case 22:
			lblNewLabel_11.setForeground(Color.green);
			lblNewLabel_11.setText("Tabela 16 criada!");
			lblNewLabelTS_11.setForeground(Color.black);
			lblNewLabelTS_11.setText(ft.format(date));
			progressBar.setValue(100);
			break;
		case 23:
			lblNewLabel_13.setForeground(Color.blue);
			lblNewLabel_13.setText("Criando Tabela gephi_nodes...");
			break;
		case 24:
			lblNewLabel_13.setForeground(Color.green);
			lblNewLabel_13.setText("Tabela gephi_nodes criada!");
			lblNewLabelTS_12.setForeground(Color.black);
			lblNewLabelTS_12.setText(ft.format(date));
			progressBar.setValue(100);
			break;
		case 25:
			lblNewLabel_14.setForeground(Color.blue);
			lblNewLabel_14.setText("Criando Tabela gephi_edges...");
			break;
		case 26:
			lblNewLabel_14.setForeground(Color.green);
			lblNewLabel_14.setText("Tabela gephi_edges criada!");
			lblNewLabelTS_13.setForeground(Color.black);
			lblNewLabelTS_13.setText(ft.format(date));
			progressBar.setValue(100);
			break;
		default:
			System.out.println("entrou no default");
			break;
		
		}
	}
}
