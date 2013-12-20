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
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSpinner;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.ConnectionFactory;

import org.eclipse.wb.swing.FocusTraversalOnArray;



import java.awt.Component;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JSeparator;

public class SetUpWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldServidor;
	private JTextField textFieldNomeBanco;
	private JTextField textFieldUsuario;
	private JPasswordField passwordFieldSenha;
	private JTextField textFieldDominio;
	private JLabel labelTestaConexao;
	private JLabel labelDominioResponse;
	private JSpinner spinnerGrupos;
	private int numResearchers;
	private JSpinner spinnerPruneBelow;
	private JSpinner spinnerPruneAbove;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetUpWindow frame = new SetUpWindow();
					frame.setVisible(true);
					
					/*
					 * Teste para checar a memoria da JVM.
					 * Ainda em desenvolvimento...
					 * 
					 */
//					NumberFormat format = NumberFormat.getInstance();
//					Runtime runtime = Runtime.getRuntime();
//					StringBuilder sb = new StringBuilder();
//					long maxMemory = runtime.maxMemory();
//				    long allocatedMemory = runtime.totalMemory();
//				    long freeMemory = runtime.freeMemory();
//
//				    sb.append("free memory: " + format.format(freeMemory / 1024) + "\n");
//				    sb.append("allocated memory: " + format.format(allocatedMemory / 1024)+ "\n");
//				    sb.append("max memory: " + format.format(maxMemory / 1024) + "\n" );
//				    sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "\n");
//					System.out.println(sb);
//					
//					MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
//					MemoryUsage heap = memBean.getHeapMemoryUsage();
//					MemoryUsage nonheap = memBean.getNonHeapMemoryUsage();
//					//System.out.println(memBean.toString());
//					System.out.println(heap.toString());
//					System.out.println(nonheap.toString());
//					
		
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SetUpWindow() {
		setTitle("Processer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 502, 507);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Servidor:");
		lblNewLabel.setBounds(10, 44, 122, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nome do Banco:");
		lblNewLabel_1.setBounds(10, 72, 122, 14);
		contentPane.add(lblNewLabel_1);
		
		textFieldServidor = new JTextField();
		textFieldServidor.setText("localhost");
		textFieldServidor.setBounds(142, 41, 122, 20);
		contentPane.add(textFieldServidor);
		textFieldServidor.setColumns(10);
		
		
		textFieldNomeBanco = new JTextField();
		textFieldNomeBanco.setBounds(142, 69, 122, 20);
		contentPane.add(textFieldNomeBanco);
		textFieldNomeBanco.setColumns(10);
		
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(142, 100, 122, 20);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Usu\u00E1rio:");
		lblNewLabel_2.setBounds(10, 103, 122, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Senha:");
		lblNewLabel_3.setBounds(10, 134, 122, 14);
		contentPane.add(lblNewLabel_3);
		
		passwordFieldSenha = new JPasswordField();
		passwordFieldSenha.setBounds(142, 131, 122, 20);
		contentPane.add(passwordFieldSenha);
		
		
		
		JLabel lblNmeroDeGrupos = new JLabel("N\u00FAmero de Grupos (K):");
		lblNmeroDeGrupos.setBounds(10, 300, 141, 14);
		contentPane.add(lblNmeroDeGrupos);
		
		spinnerGrupos = new JSpinner();
		spinnerGrupos.setModel(new SpinnerNumberModel(new Integer(10), new Integer(2), null, new Integer(1)));
		spinnerGrupos.setBounds(161, 294, 47, 20);
		contentPane.add(spinnerGrupos);
		
		JLabel lblNewLabel_4 = new JLabel("Dom\u00EDnio:");
		lblNewLabel_4.setBounds(10, 226, 102, 14);
		contentPane.add(lblNewLabel_4);
		
		textFieldDominio = new JTextField();
		textFieldDominio.setBounds(122, 223, 86, 20);
		contentPane.add(textFieldDominio);
		textFieldDominio.setColumns(10);
		
		//Handler que cuida do botao de validar o dominio
		JButton btnNewButton = new JButton("Validar Dom\u00EDnio");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
				numResearchers = ConnectionFactory.validateDomain(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()),textFieldDominio.getText());
				if (numResearchers > 0 ){
					labelDominioResponse.setForeground(Color.black);
					labelDominioResponse.setText(numResearchers + " Pesquisador(es)");
				}
				else{
					labelDominioResponse.setForeground(Color.red);
					labelDominioResponse.setText("Domínio inexistente.");
				}
				}catch(SQLException sqle){
					labelDominioResponse.setForeground(Color.red);
					labelDominioResponse.setText(sqle.toString());
				}catch(Exception e){
					labelDominioResponse.setForeground(Color.red);
					labelDominioResponse.setText(e.toString());
				}finally{
					
				}
			}
		});
		btnNewButton.setBounds(10, 251, 141, 23);
		contentPane.add(btnNewButton);
		
		//Handler que cuida do botao de proximo
		JButton btnNewButton_1 = new JButton("Pr\u00F3ximo");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				//Valida o dominio para pegar o numero de pesquisadores e passar para a tela de Status do processamento
				try{
					numResearchers = ConnectionFactory.validateDomain(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()),textFieldDominio.getText());
					if (numResearchers > 0 ){
						labelDominioResponse.setForeground(Color.black);
						labelDominioResponse.setText(numResearchers + " Pesquisador(es)");
					}
					else{
						labelDominioResponse.setForeground(Color.red);
						labelDominioResponse.setText("Domínio inexistente.");
					}
					}catch(SQLException sqle){
						labelDominioResponse.setForeground(Color.red);
						labelDominioResponse.setText(sqle.toString());
					}catch(Exception e){
						labelDominioResponse.setForeground(Color.red);
						labelDominioResponse.setText(e.toString());
					}finally{
						
					}
				
				//Chama a tela de status do processamento (ProcessWindow) passando os devidos parametros.
				ProcessWindow frame = new ProcessWindow(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()),textFieldDominio.getText(),(Integer)spinnerGrupos.getValue(),numResearchers,(Integer)spinnerPruneBelow.getValue(),(Integer)spinnerPruneAbove.getValue());
				frame.setVisible(true);
				
				
			}
		});
		btnNewButton_1.setBounds(218, 435, 89, 23);
		contentPane.add(btnNewButton_1);
		
		labelTestaConexao = new JLabel("");
		labelTestaConexao.setBounds(162, 166, 383, 14);
		contentPane.add(labelTestaConexao);
		
		//Handler que cuida do botao de testar conexao
		JButton btnTestarConexao = new JButton("Testar Conex\u00E3o");
		btnTestarConexao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					if(ConnectionFactory.testConnection(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()))){
						labelTestaConexao.setForeground(Color.GREEN);
						labelTestaConexao.setText("Conectou-se ao banco de dados com sucesso!");
					}
				}catch(SQLException sqle){
					labelTestaConexao.setForeground(Color.red);
					labelTestaConexao.setText(sqle.toString());
				}catch(Exception e){
					labelTestaConexao.setForeground(Color.red);
					labelTestaConexao.setText(e.toString());
				}
				
			}
		});
		btnTestarConexao.setBounds(10, 162, 141, 23);
		contentPane.add(btnTestarConexao);
		
		labelDominioResponse = new JLabel("");
		labelDominioResponse.setBounds(218, 226, 327, 14);
		contentPane.add(labelDominioResponse);
		
		JLabel lblConfiguraoDoProcessemento = new JLabel("Configura\u00E7\u00E3o");
		lblConfiguraoDoProcessemento.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblConfiguraoDoProcessemento.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguraoDoProcessemento.setBounds(10, 0, 535, 25);
		contentPane.add(lblConfiguraoDoProcessemento);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 196, 466, 2);
		contentPane.add(separator);
		
		JLabel lblLabelPruneBelow = new JLabel("Corte Inferior:");
		lblLabelPruneBelow.setBounds(10, 330, 122, 14);
		contentPane.add(lblLabelPruneBelow);
		
		JLabel lblLabePruneAbove = new JLabel("Corte Superior:");
		lblLabePruneAbove.setBounds(10, 362, 122, 14);
		contentPane.add(lblLabePruneAbove);
		
		spinnerPruneBelow = new JSpinner();
		spinnerPruneBelow.setModel(new SpinnerNumberModel(new Integer(5), new Integer(1), null, new Integer(1)));
		spinnerPruneBelow.setBounds(161, 327, 47, 20);
		contentPane.add(spinnerPruneBelow);
		
		spinnerPruneAbove = new JSpinner();
		spinnerPruneAbove.setModel(new SpinnerNumberModel(new Integer(5), new Integer(1), null, new Integer(1)));
		spinnerPruneAbove.setBounds(161, 359, 47, 20);
		contentPane.add(spinnerPruneAbove);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textFieldServidor, textFieldNomeBanco, textFieldUsuario, passwordFieldSenha, lblNewLabel, btnTestarConexao, textFieldDominio, btnNewButton, spinnerGrupos, btnNewButton_1, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNmeroDeGrupos, lblNewLabel_4, labelTestaConexao, labelDominioResponse}));
	}
}
