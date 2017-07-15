package br.com.lpv.trabalho.controle;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.dao.Database;
import br.com.lpv.trabalho.dao.DatabaseFactory;
import br.com.lpv.trabalho.dao.UsuarioDAO;
import br.com.lpv.trabalho.modelo.Path;
import br.com.lpv.trabalho.modelo.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UsuarioController implements Initializable {
	private @FXML ChoiceBox<String> choiceOpcoes, choicePapel;
	private @FXML Button btnAlterar, btnCadastrar, btnRemover;
	private @FXML TextField usuario;
	private @FXML PasswordField senha, confirmar;
	private @FXML ChoiceBox<Usuario> choiceUsuarios;
	
	private final Database database = DatabaseFactory.getDatabase(Path.POSTGRES);
	private final Connection connection = database.conectar();
	private UsuarioDAO usuarioDAO;
	private Usuario user, userLogado;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userLogado = LoginController.getUsuario();
		
		usuarioDAO = new UsuarioDAO(connection);
		
		String opcoes[] = {"Cadastrar", "Alterar", "Remover"};
		String papel[] = {"Gerente", "Recepcionista", "Mecânico"};
		choiceOpcoes.setValue("Selecione uma função");
		choicePapel.setValue("Selecione um papel:");
		
		choiceOpcoes.setItems(FXCollections.observableArrayList(opcoes));
		choicePapel.setItems(FXCollections.observableArrayList(papel));
		
		choiceOpcoes.setOnAction((ActionEvent e)->{
			if(choiceOpcoes.getValue().equalsIgnoreCase("Cadastrar")){
				btnAlterar.setDisable(true);
				choiceUsuarios.setDisable(true);
				btnCadastrar.setDisable(false);
			}
				
			
			if(choiceOpcoes.getValue().equalsIgnoreCase("Alterar")){
				choiceUsuarios.setItems(FXCollections.observableArrayList(usuarioDAO.listar()));
				btnCadastrar.setDisable(true);
				btnAlterar.setDisable(false);
				choiceUsuarios.setDisable(false);
				btnRemover.setDisable(true); 
				
			}
			
			if(choiceOpcoes.getValue().equalsIgnoreCase("Remover")){
				choiceUsuarios.setItems(FXCollections.observableArrayList(usuarioDAO.listar()));
				btnCadastrar.setDisable(true);
				btnAlterar.setDisable(false);
				btnRemover.setDisable(false); 
				choiceUsuarios.setDisable(false);
				
			}
		});
		
		choiceUsuarios.setOnAction((ActionEvent e)->{
			user = choiceUsuarios.getValue();
			if(user == null) return;
			
		});
		
		
	}// initialize
	
	@FXML 
	private void handleCadastrar(){
		user = new Usuario();
		
		try {
			
			if(usuario.getText().isEmpty() || senha.getText().isEmpty() || confirmar.getText().isEmpty()){
				Path.exibirMsgErro("Oficina", "Cadastrar", "Preencha todos os campos!");
				return;
			}
			
			if(!senha.getText().equals(confirmar.getText())){
				Path.exibirMsgErro("Oficina", "Cadastrar", "Senhas não conferem!");
				senha.clear(); confirmar.clear();
				return;
			}
			
			user.setSenha(senha.getText());
			user.setUsuario(usuario.getText());
			
			user.setPapel(choicePapel.getValue().substring(0, 1));
			
			if(usuarioDAO.inserir(user))
				Path.exibirMsgSucesso("Oficina", "Cadastrar", "Usuario cadastrado com sucesso!");
			
			Path.limparCampos(usuario, senha, confirmar);
			
		} catch (Exception e) {
			e.printStackTrace();
			Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar usuario!");
		}
	}
	
	@FXML 
	private void handleAlterar(){
	
		try {
			if(user == null) return;
			
			if(usuario.getText().isEmpty() || senha.getText().isEmpty() || confirmar.getText().isEmpty()){
				Path.exibirMsgErro("Oficina", "Cadastrar", "Preencha todos os campos!");
				return;
			}
			
			if(!senha.getText().equals(confirmar.getText())){
				Path.exibirMsgErro("Oficina", "Cadastrar", "Senhas não conferem!");
				senha.clear(); confirmar.clear();
				return;
			}
			
			user.setSenha(senha.getText());
			user.setUsuario(usuario.getText());
			
			user.setPapel(choicePapel.getValue().substring(0, 1));
			
			if(usuarioDAO.alterar(user))
				Path.exibirMsgSucesso("Oficina", "Cadastrar", "Usuario alterado com sucesso!");
			
			Path.limparCampos(usuario, senha, confirmar);
			choiceUsuarios.setItems(FXCollections.observableArrayList(usuarioDAO.listar()));
		} catch (Exception e) {
			//e.printStackTrace();
			Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar usuario!");
		}
	} // handleAlterar
	
	@FXML 
	private void handleRemover(){
		
		try {
			if(user == null){
				Path.exibirMsgErro("OFICINA", "Remover", "Selecione um usuario!");
				return;
			}
			
			if(user.getId() == userLogado.getId()){
				Path.exibirMsgErro("OFICINA", "Remover", "Usuario logado! Tente mais tarde.");
				return;
			}
			
			if(!Path.exibirMsgConfirmar("OFICINA", "Remover", "Confirmar remoção?"))
				return;
			
			if(usuarioDAO.remover(user))
				Path.exibirMsgSucesso("OFICINA", "Cadastrar", "Usuario removido com sucesso!");
			
			choiceUsuarios.setItems(FXCollections.observableArrayList(usuarioDAO.listar()));
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar usuario!");
		}

	} // handleRemover
	
	
}
