package br.com.lpv.trabalho.controle;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.dao.Database;
import br.com.lpv.trabalho.dao.DatabaseFactory;
import br.com.lpv.trabalho.dao.PecaDAO;
import br.com.lpv.trabalho.modelo.Path;
import br.com.lpv.trabalho.modelo.Peca;
import br.com.lpv.trabalho.modelo.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PecasController implements Initializable{
	@FXML private ChoiceBox<String> choiceOpcoes;
	@FXML private ChoiceBox<Peca> choicePecas;
	@FXML private TextField nome, fornecedor, tipo, marca, quantidade, fieldQtd;
	@FXML private Button btnAlterar, btnCadastrar, btnRemover, btnRetirar;
	@FXML private GridPane gridPane;
	@FXML private Label labelQtd;
	
	private final Database database = DatabaseFactory.getDatabase(Path.POSTGRES);
	private final Connection connection = database.conectar();
	private PecaDAO pecaDAO;
	
	private Peca peca;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pecaDAO = new PecaDAO(connection);
		
		Usuario u = LoginController.getUsuario();
		String opcoes[] = null;
			
		Path.numericField(fieldQtd);
		
		if(u.getPapel().equalsIgnoreCase("R")){
			gridPane.setVisible(true);
			opcoes = new String[2];
			opcoes[0] = "Cadastrar"; opcoes[1] = "Alterar";
		}else
		if(u.getPapel().equalsIgnoreCase("G")){
			gridPane.setVisible(true);
			opcoes = new String[3];
			opcoes[0] = "Cadastrar"; opcoes[1] = "Alterar";
			opcoes[2] = "Remover"; 
		}else
			if(u.getPapel().equalsIgnoreCase("M")){
				gridPane.setVisible(false);
				labelQtd.setVisible(true);
				fieldQtd.setVisible(true);
				opcoes = new String[1];
				opcoes[0] = "Retirar";	
		}
		
		choiceOpcoes.setValue("Selecione uma função");
		
		choiceOpcoes.setItems(FXCollections.observableArrayList(opcoes));
		
		choiceOpcoes.setOnAction((ActionEvent e)->{
			if(choiceOpcoes.getValue().equalsIgnoreCase("Cadastrar")){
				btnAlterar.setDisable(true);
				btnCadastrar.setDisable(false);
			
			}
				
			
			if(choiceOpcoes.getValue().equalsIgnoreCase("Alterar")){
				choicePecas.setDisable(false);
				choicePecas.setItems(FXCollections.observableArrayList(pecaDAO.listar()));
				btnCadastrar.setDisable(true);
				btnAlterar.setDisable(false);

			}
			
			if(choiceOpcoes.getValue().equalsIgnoreCase("Remover")){
				choicePecas.setDisable(false);
				choicePecas.setItems(FXCollections.observableArrayList(pecaDAO.listar()));
				btnCadastrar.setDisable(true);
				btnAlterar.setDisable(true);
				btnRemover.setDisable(false);

			}
			
			if(choiceOpcoes.getValue().equalsIgnoreCase("Retirar")){
				choicePecas.setDisable(false);
				choicePecas.setItems(FXCollections.observableArrayList(pecaDAO.listar()));
				btnCadastrar.setDisable(true);
				btnAlterar.setDisable(true);
				btnRemover.setDisable(true);
				btnRetirar.setDisable(false);

			}
		}); // setOnAction
		
		choicePecas.setOnAction((ActionEvent e)->{
			peca = choicePecas.getValue();
			
			if(peca == null) return;
			
			nome.setText(peca.getNome());
			fornecedor.setText(peca.getFornecedor());
			marca.setText(peca.getMarca());
			tipo.setText(peca.getTipo());
			quantidade.setText(String.format("%d", peca.getQuantidade()));
			
		}); // setOnAction
		
	} // initialize
	
	@FXML 
	private void handleCadastrar(){
		peca = new Peca();
		
		try {
			
			peca.setFornecedor(fornecedor.getText());
			peca.setMarca(marca.getText());
			peca.setNome(nome.getText());
			peca.setQuantidade(Integer.parseInt(quantidade.getText()));
			peca.setTipo(tipo.getText());
			
			if(pecaDAO.inserir(peca))
				Path.exibirMsgSucesso("Oficina", "Cadastrar", "Peca cadastrada!");
			else
				Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar peca!");
			
			Path.limparCampos(fornecedor, nome, marca, quantidade, tipo);
			
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar peca!");
		}
	}
	
	@FXML 
	private void handleRemover(){

		try {
			if(peca == null){
				Path.exibirMsgErro("Oficina", "Remover", "Selecione uma peça!");
				return;
			}
			
			if(!Path.exibirMsgConfirmar("OFICIONA", "Remover", "Confirmar remoção?"))
				return;
			
			if(pecaDAO.remover(peca))
				Path.exibirMsgSucesso("Oficina", "Cadastrar", "Peca removida!");
			else
				Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao remover peca!");
			
			Path.limparCampos(fornecedor, nome, marca, quantidade, tipo);
			choicePecas.setItems(FXCollections.observableArrayList(pecaDAO.listar()));
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar peca!");
		}
	}
	
	@FXML 
	private void handleRetirar(){

		try {
			if(peca == null){
				Path.exibirMsgErro("Oficina", "Retirar", "Selecione uma peça!");
				return;
			}
			
			int totalPecas = peca.getQuantidade() - Integer.parseInt(fieldQtd.getText());
			
			if( totalPecas < 0){
				Path.exibirMsgErro("Oficina", "Retirar", "Quantide em etoque = " + peca.getQuantidade());
				return;
			}
			
			if(!Path.exibirMsgConfirmar("OFICIONA", "Retirar", "Confirmar retirada?"))
				return;
			
			peca.setQuantidade(totalPecas);
			
			if(pecaDAO.alterar(peca))
				Path.exibirMsgSucesso("Oficina", "Cadastrar", "Peca removida!");
			else
				Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao remover peca!");
			
			Path.limparCampos(fieldQtd);
			choicePecas.setItems(FXCollections.observableArrayList(pecaDAO.listar()));
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar peca!");
		}
	}

	@FXML 
	private void handleAlterar(){

		try {
			
			peca.setFornecedor(fornecedor.getText());
			peca.setMarca(marca.getText());
			peca.setNome(nome.getText());
			peca.setQuantidade(Integer.parseInt(quantidade.getText()));
			peca.setTipo(tipo.getText());
			
			if(pecaDAO.alterar(peca))
				Path.exibirMsgSucesso("Oficina", "Cadastrar", "Peca alterada com sucesso!");
			
			Path.limparCampos(fornecedor, nome, marca, quantidade, tipo);
			choicePecas.setItems(FXCollections.observableArrayList(pecaDAO.listar()));
			
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Alterar", "Erro ao alterar peca!");
		}
	}
	
}
