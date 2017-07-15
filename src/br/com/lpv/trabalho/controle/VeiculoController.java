package br.com.lpv.trabalho.controle;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.dao.ClienteDAO;
import br.com.lpv.trabalho.dao.Database;
import br.com.lpv.trabalho.dao.DatabaseFactory;
import br.com.lpv.trabalho.dao.VeiculoDAO;
import br.com.lpv.trabalho.modelo.Cliente;
import br.com.lpv.trabalho.modelo.Path;
import br.com.lpv.trabalho.modelo.Usuario;
import br.com.lpv.trabalho.modelo.Veiculo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class VeiculoController implements Initializable{
	private @FXML ChoiceBox<String> choiceOpcoes;
	private @FXML ChoiceBox<Cliente> choiceClientes;
	private @FXML ChoiceBox<Veiculo> choiceVeiculos;
	private @FXML TextField modelo, marca, placa;
	private @FXML DatePicker anoFabricacao, anoModelo;
	private @FXML Button btnAlterar, btnCadastrar;
	
	private final Database database = DatabaseFactory.getDatabase(Path.POSTGRES);
	private final Connection connection = database.conectar();
	private ClienteDAO clienteDAO;
	private VeiculoDAO veiculoDAO;
	
	private Veiculo veiculo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clienteDAO = new ClienteDAO(connection);
		veiculoDAO = new VeiculoDAO(connection);
		
		Usuario u = LoginController.getUsuario();
		String opcoes[] = new String[3];
				
		if(u.getPapel().equalsIgnoreCase("R")){
			opcoes[0] = "Cadastrar"; opcoes[1] = "Alterar";
		}else
		if(u.getPapel().equalsIgnoreCase("G")){
			opcoes[0] = "Cadastrar"; opcoes[1] = "Alterar";
			opcoes[2] = "Remover"; 
		}
		
		choiceOpcoes.setValue("Selecione uma função");
		
		choiceOpcoes.setItems(FXCollections.observableArrayList(opcoes));
		choiceClientes.setItems(FXCollections.observableArrayList(clienteDAO.listar()));
		choiceVeiculos.setItems(FXCollections.observableArrayList(veiculoDAO.listar()));
		
		choiceOpcoes.setOnAction((ActionEvent e)->{
			if(choiceOpcoes.getValue().equalsIgnoreCase("Cadastrar")){
				btnAlterar.setDisable(true);
				choiceVeiculos.setDisable(true);
				btnCadastrar.setDisable(false);
				choiceClientes.setDisable(false);
				
			}
				
			
			if(choiceOpcoes.getValue().equalsIgnoreCase("Alterar")){
				choiceVeiculos.setItems(FXCollections.observableArrayList(veiculoDAO.listar()));
				btnCadastrar.setDisable(true);
				btnAlterar.setDisable(false);
				choiceVeiculos.setDisable(false);

			}
		}); // setOnAction
		
		choiceVeiculos.setOnAction((ActionEvent e)->{
			veiculo = choiceVeiculos.getValue();
			
			if(veiculo == null) return;
			
			choiceClientes.setValue(veiculo.getCliente());
			choiceClientes.setDisable(false);
			marca.setText(veiculo.getMarca());
			modelo.setText(veiculo.getModelo());
			placa.setText(veiculo.getPlaca());
			anoFabricacao.setValue(veiculo.getAnoFabricacao());
			anoModelo.setValue(veiculo.getAnoModelo());
			
		}); // setOnAction
		
		//Path.placa(placa);
	} // initialize
	
	@FXML 
	private void handleCadastrar(){
		veiculo = new Veiculo();
		
		try {
			veiculo.setAnoFabricacao(anoFabricacao.getValue());
			veiculo.setAnoModelo(anoModelo.getValue());
			veiculo.setCliente(choiceClientes.getValue());
			veiculo.setMarca(marca.getText());
			veiculo.setModelo(modelo.getText());
			veiculo.setPlaca(placa.getText());
			
			if(veiculoDAO.inserir(veiculo))
				Path.exibirMsgSucesso("Oficina", "Cadastrar", "Veiculo cadastrado com sucesso!");
			
			Path.limparCampos(marca, placa, modelo);
			anoFabricacao.setValue(null);
			anoModelo.setValue(null);
			choiceClientes.setValue(null);
			
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Cadastrar", "Erro ao cadastrar cliente!");
		}
	}

	@FXML 
	private void handleAlterar(){

		try {
			veiculo.setAnoFabricacao(anoFabricacao.getValue());
			veiculo.setAnoModelo(anoModelo.getValue());
			veiculo.setCliente(choiceClientes.getValue());
			veiculo.setMarca(marca.getText());
			veiculo.setModelo(modelo.getText());
			veiculo.setPlaca(placa.getText());
			
			if(veiculoDAO.alterar(veiculo))
				Path.exibirMsgSucesso("Oficina", "Alterar", "Veiculo alterado com sucesso!");
			
			Path.limparCampos(marca, placa, modelo);
			
		} catch (Exception e) {
			Path.exibirMsgErro("Oficina", "Alterar", "Erro ao alterar cliente!");
		}
	}
	
}
