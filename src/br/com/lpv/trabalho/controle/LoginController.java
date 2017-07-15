package br.com.lpv.trabalho.controle;

import static br.com.lpv.trabalho.modelo.Path.ICON;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.Main;
import br.com.lpv.trabalho.dao.Database;
import br.com.lpv.trabalho.dao.DatabaseFactory;
import br.com.lpv.trabalho.dao.UsuarioDAO;
import br.com.lpv.trabalho.modelo.Path;
import br.com.lpv.trabalho.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	private @FXML TextField user;
	private @FXML PasswordField password;
	public static Stage stage;
	
	// Atributos para conectar ao banco de dados;
	private final Database database = DatabaseFactory.getDatabase(Path.POSTGRES);
	private final Connection connection = database.conectar();
	private UsuarioDAO userDAO;
	private static Usuario usuario;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usuario = new Usuario();
		userDAO = new UsuarioDAO(connection);
		stage = new Stage();
	}
	
	@FXML private void handleSair(){
		System.exit(0);
	}
	
	@FXML private void handleLogin() throws IOException{
		
		usuario = userDAO.buscar(user.getText().toLowerCase());
		
		if(usuario == null)
			Path.exibirMsgErro("Oficina", "Login", "Usuario não cadastrado no sistema.");
		else if(!usuario.getSenha().equals(password.getText()))
				Path.exibirMsgErro("Oficina", "Login", "Senha ou usuario inválido");
		else{
			
			Path.limparCampos(user, password);
			
			abrirTela(Path.RECEPCAO);
		}
			
		
	}
	
	public void abrirTela(String tela) {
		try {
			
			// Cria a nova Tela para respectivo funcionario.
			AnchorPane root = FXMLLoader.load(getClass().getResource(tela));
			Scene scene = new Scene(root);
			
			stage.setTitle(usuario.getUsuario().toUpperCase());
			stage.setScene(scene);
			stage.getIcons().add(new Image(getClass().getResourceAsStream(ICON)));
			
			// Desativa a opção de maximizar
			stage.setResizable(false);
			
			Main.stage.close();
			
			// Exibe a tela
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}// abrirTela

	public static Usuario getUsuario() {
		return usuario;
	}

}
