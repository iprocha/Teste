package view;

import java.awt.BorderLayout;
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
import controller.Dispatcher;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.SpinnerNumberModel;

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
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SetUpWindow frame = new SetUpWindow();
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
	public SetUpWindow() {
		setTitle("Processer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Servidor:");
		lblNewLabel.setBounds(5, 5, 122, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nome do Banco:");
		lblNewLabel_1.setBounds(5, 33, 122, 14);
		contentPane.add(lblNewLabel_1);
		
		textFieldServidor = new JTextField();
		textFieldServidor.setBounds(137, 2, 122, 20);
		contentPane.add(textFieldServidor);
		textFieldServidor.setColumns(10);
		
		
		textFieldNomeBanco = new JTextField();
		textFieldNomeBanco.setBounds(137, 30, 122, 20);
		contentPane.add(textFieldNomeBanco);
		textFieldNomeBanco.setColumns(10);
		
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(137, 61, 122, 20);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Usu\u00E1rio:");
		lblNewLabel_2.setBounds(5, 64, 122, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Senha:");
		lblNewLabel_3.setBounds(5, 95, 122, 14);
		contentPane.add(lblNewLabel_3);
		
		passwordFieldSenha = new JPasswordField();
		passwordFieldSenha.setBounds(137, 92, 122, 20);
		contentPane.add(passwordFieldSenha);
		
		
		
		JLabel lblNmeroDeGrupos = new JLabel("N\u00FAmero de Grupos (K):");
		lblNmeroDeGrupos.setBounds(5, 261, 141, 14);
		contentPane.add(lblNmeroDeGrupos);
		
		spinnerGrupos = new JSpinner();
		spinnerGrupos.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerGrupos.setBounds(156, 255, 47, 20);
		contentPane.add(spinnerGrupos);
		
		JLabel lblNewLabel_4 = new JLabel("Dom\u00EDnio:");
		lblNewLabel_4.setBounds(5, 187, 102, 14);
		contentPane.add(lblNewLabel_4);
		
		textFieldDominio = new JTextField();
		textFieldDominio.setBounds(117, 184, 86, 20);
		contentPane.add(textFieldDominio);
		textFieldDominio.setColumns(10);
		
		//Handler que cuida do botao de validar o dominio
		JButton btnNewButton = new JButton("Validar Dom\u00EDnio");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int numResearchers = ConnectionFactory.validateDomain(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()),textFieldDominio.getText());
				if (numResearchers > 0 ){
					labelDominioResponse.setText("Existe(m) " + numResearchers + " pesquisador(es) no domínio escolhido.");
				}
				else{
					labelDominioResponse.setText("Domínio inexistente.");
				}
			}
		});
		btnNewButton.setBounds(5, 212, 141, 23);
		contentPane.add(btnNewButton);
		
		//Handler que cuida do botao de executar
		JButton btnNewButton_1 = new JButton("Executar");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Dispatcher.execute(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()),textFieldDominio.getText(),(Integer)spinnerGrupos.getValue());
				
				
			}
		});
		btnNewButton_1.setBounds(156, 296, 89, 23);
		contentPane.add(btnNewButton_1);
		
		labelTestaConexao = new JLabel("New label");
		labelTestaConexao.setBounds(157, 127, 309, 14);
		contentPane.add(labelTestaConexao);
		
		//Handler que cuida do botao de testar conexao
		JButton btnTestarConexao = new JButton("Testar Conex\u00E3o");
		btnTestarConexao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(ConnectionFactory.testConnection(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()))){
					labelTestaConexao.setText("Conectou-se ao banco de dados com sucesso!");
				}
				else{
					labelTestaConexao.setText("Conexão não estabelecida!");
				}
			}
		});
		btnTestarConexao.setBounds(5, 123, 141, 23);
		contentPane.add(btnTestarConexao);
		
		labelDominioResponse = new JLabel("New label");
		labelDominioResponse.setBounds(250, 261, 271, 14);
		contentPane.add(labelDominioResponse);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(230, 187, 291, 14);
		contentPane.add(lblNewLabel_5);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textFieldServidor, textFieldNomeBanco, textFieldUsuario, passwordFieldSenha, lblNewLabel, btnTestarConexao, textFieldDominio, btnNewButton, spinnerGrupos, btnNewButton_1, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNmeroDeGrupos, lblNewLabel_4, labelTestaConexao, labelDominioResponse}));
	}
}
