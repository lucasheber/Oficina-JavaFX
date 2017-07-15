package br.com.lpv.trabalho.controle;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.dao.ClienteDAO;
import br.com.lpv.trabalho.dao.Database;
import br.com.lpv.trabalho.dao.DatabaseFactory;
import br.com.lpv.trabalho.modelo.Cliente;
import br.com.lpv.trabalho.modelo.Path;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ClienteController implements Initializable {
	private @FXML ChoiceBox<String> choiceOpcoes;
	private @FXML Button btnAlterar, btnCadastrar, btnRemover;
	private @FXML TextField nome, telefone, cpf, endereco, email;
	private @FXML ChoiceBox<Cliente> choiceClientes;
	
	private final Database database = DatabaseFactory.getDatabase(Path.POSTGRES);
	private final Connection connection = database.conectar();
	private ClienteDAO clienteDAO;
	private Cliente cliente;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		clienteDAO = new ClienteDAO(connection);
		
		String opcoes[] = {"Cadastrar", "Alterar", "Remover"};
		choiceOpcoes.setValue("Selecione uma função");
		
		choiceOpcoes.setItems(FXCollections.observableArrayList(opcoes));
		
		choiceOpcoes.setOnAction((ActionEvent e)->{
			if(choiceOpcoes.getValue().equalsIgnoreCase("Cadastrar")){
				btnAlterar.setDisable(true);
				choiceClientes.setDisable(true);
				btnCadastrar.setDisable(false);
			}
				
			
			if(choiceOpcoes.getValue().equalsIgnoreCase("Alterar")){
				choiceClientes.setItems(FXCollections.observableArrayList(clienteDAO.listar()));
				btnCadastrar.setDisable(true);
				btnAlterar.setDisable(false);
				choiceClientes.setDisable(false);
				
			}
			
			if(choiceOpcoes.getValue().equalsIgnoreCase("Remover")){
				choiceClientes.setItems(FXCollections.observableArrayList(clienteDAO.listar()));
				btnCadastrar.setDisable(true);
				btnAlterar.setDisable(true);
				btnRemover.setDisable(false);
				choiceClientes.setDisable(false);
				
			}
		});
		
		choiceClientes.setOnAction((ActionEvent e)->{
			
			
			cliente = choiceClientes.getValue();
			if(cliente == null) return;
			
			cpf.setText(cliente.getCpf());
			nome.setText(cliente.getNome());
			telefone.setText(cliente.getTelefone());
			email.setText(cliente.getEmail());
			endereco.setText(cliente.getEndereco());
			
		});
		
		
		/* Abaixo faz os mascaramentos dos campos. */	
		
		Path.foneField(telefone);
		Path.cpfField(cpf);
		
	}// initialize
	
	@FXML 
	private void handleCadastrar(){
		cliente = new Cliente();
		
		try {
			if(Path.isCPF(cpf.getText().replaceAll("[^0-9]", ""))){
				cliente.setCpf(cpf.getText());
				cliente.setEmail(email.getText());
				cliente.setEndereco(endereco.getText());
				cliente.setNome(nome.getText());
				cliente.setTelefone(telefone.getText());
				
				if(clienteDAO.inserir(cliente))
					Path.exibirMsgSucesso("Oficina", "Cadastrar", "Cliente cadastrado com sucesso!");
				
				Path.limparCampos(cpf, email, endereco, nome, telefone);
			}else{
				Path.exibirMsgErro("Oficina", "Cadastrar", "CPF inválido!");
				cpf.clear();
				cpf.setStyle("-fx-border-color: red");
			}
			
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar cliente!");
		}
	}
	
	@FXML 
	private void handleAlterar(){
		
		
		try {
			if(Path.isCPF(cpf.getText().replaceAll("[^0-9]", ""))){
				cpf.setStyle("-fx-border-color: rgba(255,255,255, 0.5)");
				cliente.setCpf(cpf.getText());
				cliente.setEmail(email.getText());
				cliente.setEndereco(endereco.getText());
				cliente.setNome(nome.getText());
				cliente.setTelefone(telefone.getText());
				
				if(clienteDAO.alterar(cliente))
					Path.exibirMsgSucesso("Oficina", "Alterar", "Cliente alterado com sucesso!");
				
				Path.limparCampos(cpf, email, endereco, nome, telefone);
				choiceClientes.setItems(FXCollections.observableArrayList(clienteDAO.listar()));
			}else{
				Path.exibirMsgErro("Oficina", "Alterar", "CPF inválido!");
				cpf.clear();
				cpf.setStyle("-fx-border-color: red");
			}
			
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Alterar", "Erro ao alterar cliente!");
		}
	} // handleAlterar
	
	@FXML 
	private void handleRemover(){
		
		try {
			if(cliente == null){Path.exibirMsgErro("Oficina", "Alterar", "Selecione um cliente!"); return;}

			if(!Path.exibirMsgConfirmar("OFICIONA", "Remover", "Confirmar remoção?"))
				return;
			
			if(clienteDAO.remover(cliente))
				Path.exibirMsgSucesso("Oficina", "Remover", "Cliente removido!");
			else
				Path.exibirMsgErro("Oficina", "Remover", "Erro ao remover cliente!");
			
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Remover", "Erro ao remover cliente!");
		}
	} // handleAlterar
	
}
