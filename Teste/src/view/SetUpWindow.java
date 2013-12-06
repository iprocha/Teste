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
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetUpWindow frame = new SetUpWindow();
					frame.setVisible(true);
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
		setBounds(100, 100, 571, 424);
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
		textFieldNomeBanco.setText("im_lattes");
		textFieldNomeBanco.setBounds(142, 69, 122, 20);
		contentPane.add(textFieldNomeBanco);
		textFieldNomeBanco.setColumns(10);
		
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setText("desenvolvimento");
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
		passwordFieldSenha.setText("123456");
		passwordFieldSenha.setBounds(142, 131, 122, 20);
		contentPane.add(passwordFieldSenha);
		
		
		
		JLabel lblNmeroDeGrupos = new JLabel("N\u00FAmero de Grupos (K):");
		lblNmeroDeGrupos.setBounds(10, 300, 141, 14);
		contentPane.add(lblNmeroDeGrupos);
		
		spinnerGrupos = new JSpinner();
		spinnerGrupos.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
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
				int numResearchers = ConnectionFactory.validateDomain(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()),textFieldDominio.getText());
				if (numResearchers > 0 ){
					labelDominioResponse.setText("Existe(m) " + numResearchers + " pesquisador(es) no domínio escolhido.");
				}
				else{
					labelDominioResponse.setText("Domínio inexistente.");
				}
			}
		});
		btnNewButton.setBounds(10, 251, 141, 23);
		contentPane.add(btnNewButton);
		
		//Handler que cuida do botao de executar
		JButton btnNewButton_1 = new JButton("Pr\u00F3ximo");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				ProcessWindow frame = new ProcessWindow(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()),textFieldDominio.getText(),(Integer)spinnerGrupos.getValue());
				frame.setVisible(true);
				//Dispatcher.executeWindow(textFieldServidor.getText(),textFieldNomeBanco.getText(),textFieldUsuario.getText(),String.valueOf(passwordFieldSenha.getPassword()),textFieldDominio.getText(),(Integer)spinnerGrupos.getValue());
			
				
			}
		});
		btnNewButton_1.setBounds(309, 340, 89, 23);
		contentPane.add(btnNewButton_1);
		
		labelTestaConexao = new JLabel("");
		labelTestaConexao.setBounds(162, 166, 309, 14);
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
		btnTestarConexao.setBounds(10, 162, 141, 23);
		contentPane.add(btnTestarConexao);
		
		labelDominioResponse = new JLabel("");
		labelDominioResponse.setBounds(243, 226, 271, 14);
		contentPane.add(labelDominioResponse);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(335, 80, 136, 20);
		contentPane.add(comboBox);
		
		JButton btnSalvarConexo = new JButton("Salvar Conex\u00E3o");
		btnSalvarConexo.setBounds(335, 50, 133, 23);
		contentPane.add(btnSalvarConexo);
		
		JLabel lblConfiguraoDoProcessemento = new JLabel("Configura\u00E7\u00E3o");
		lblConfiguraoDoProcessemento.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblConfiguraoDoProcessemento.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguraoDoProcessemento.setBounds(10, 0, 535, 25);
		contentPane.add(lblConfiguraoDoProcessemento);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 196, 466, 2);
		contentPane.add(separator);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textFieldServidor, textFieldNomeBanco, textFieldUsuario, passwordFieldSenha, lblNewLabel, btnTestarConexao, textFieldDominio, btnNewButton, spinnerGrupos, btnNewButton_1, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNmeroDeGrupos, lblNewLabel_4, labelTestaConexao, labelDominioResponse}));
	}
}
