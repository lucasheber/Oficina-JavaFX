package br.com.lpv.trabalho.controle;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.dao.Database;
import br.com.lpv.trabalho.dao.DatabaseFactory;
import br.com.lpv.trabalho.dao.OrdemServicoDAO;
import br.com.lpv.trabalho.dao.PecaDAO;
import br.com.lpv.trabalho.dao.VeiculoDAO;
import br.com.lpv.trabalho.modelo.OrdemServico;
import br.com.lpv.trabalho.modelo.Path;
import br.com.lpv.trabalho.modelo.Peca;
import br.com.lpv.trabalho.modelo.Usuario;
import br.com.lpv.trabalho.modelo.Veiculo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class OrcamentoController implements Initializable{
	@FXML private TableView<OrdemServico> tabela;
	@FXML private TableColumn<OrdemServico, String> columnOS, columnStatus, columnData;
	@FXML private TableColumn<OrdemServico, String> columnValor, columnQtd, columnPeca;
	@FXML private TextField servico, valor, quantidade;
	@FXML private ChoiceBox<Veiculo> veiculos;
	@FXML private ChoiceBox<Peca> pecas;
	@FXML private Pagination pagination;
	@FXML private GridPane gridPane;
	@FXML private Button btnCadastrar, btnAprovar, btnConcluir;
	
	private final Database database = DatabaseFactory.getDatabase(Path.POSTGRES);
	private final Connection connection = database.conectar();
	private PecaDAO pecaDAO;
	private VeiculoDAO veiculoDAO;
	private OrdemServicoDAO ordemDAO;
	
	private List<OrdemServico> listServicos;
	private int pagina;
	private OrdemServico ordem;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Usuario u = LoginController.getUsuario();
		
		if(u.getPapel().equalsIgnoreCase("M")){
			gridPane.setVisible(true);
			btnCadastrar.setVisible(true);
		}
		
		if(u.getPapel().equalsIgnoreCase("R")){
			gridPane.setVisible(false);
			btnCadastrar.setVisible(false);
			btnAprovar.setVisible(true);
			
			tabela.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
					-> aprovarOS(newValue));
			
		}
		
		if(u.getPapel().equalsIgnoreCase("G")){
			gridPane.setVisible(false);
			btnCadastrar.setVisible(false);
			btnConcluir.setVisible(true);
			btnConcluir.setDisable(false);
			
			tabela.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
					-> aprovarOS(newValue));
			
		}	
		
		ordemDAO = new OrdemServicoDAO(connection);
		pecaDAO = new PecaDAO(connection);
		veiculoDAO = new VeiculoDAO(connection);
		
		// Ajusta ate qual numero ira aparecer
		pagination.setMaxPageIndicatorCount(5);
		
		listServicos = new ArrayList<>();
		pagina = 0;
		
		veiculos.setItems(FXCollections.observableArrayList(veiculoDAO.listar()));
		pecas.setItems(FXCollections.observableArrayList(pecaDAO.listar()));
		
		carregarTabela();
		
		pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		   public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		        
		    pagina = newValue.intValue();
		    carregarTabela();
		  }
		});
	}
	
	private void aprovarOS(OrdemServico os) {
		if(os == null)
			btnAprovar.setDisable(true);
		else{
			btnAprovar.setDisable(false);
			
			ordem = os;
			
		}
	}

	@FXML private void handleCadastrar(){
		ordem = new OrdemServico();
		try {
			ordem.setDataOrcamento(LocalDate.now());
			ordem.setDataServico(LocalDate.now());
			ordem.setServico(servico.getText());
			ordem.setStatus("Criada");
			ordem.setValor(Float.parseFloat(valor.getText().replaceAll(",", ".")));
			ordem.setVeiculo(veiculos.getValue());
			ordem.setQuantidade(Integer.parseInt(quantidade.getText().replaceAll("[^0-9]", "")));
			ordem.setPeca(pecas.getValue());
			
			if(ordemDAO.inserir(ordem))
				Path.exibirMsgSucesso("OFICINA", "Cadastra", "Orçamento feito com sucesso!");
			
			Path.limparCampos(servico, valor, quantidade);
			carregarTabela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// handleCadastrar
	
	@FXML private void handleAprovar(){
		
		try {
			if(ordem == null){
				Path.exibirMsgErro("OFICINA", "Aprovar", "Selecione um item na tabela!");
				return;
			}
			
			if(!ordem.getStatus().equalsIgnoreCase("Criada")){Path.exibirMsgErro("OFICINA", "Aprovar", "OS deve ser criada!"); return;}
				ordem.setStatus("Aprovada");
			
			if(ordemDAO.alterar(ordem))
				Path.exibirMsgSucesso("OFICINA", "Aprovar", "Orçamento aprovado com sucesso!");
			
			Path.limparCampos(servico, valor, quantidade);
			carregarTabela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// handleCadastrar
	
	@FXML private void handleConcluir(){
			
			try {
				if(ordem == null){
					Path.exibirMsgErro("OFICINA", "Concluir", "Selecione um item na tabela!");
				return;
			}
			
			if(ordem.getStatus().equalsIgnoreCase("Aprovada"))
				ordem.setStatus("Concluida");
			else{
				Path.exibirMsgErro("OFICINA", "Concluir", "OS não aprovada!");
				return;
			}
			
			if(ordemDAO.alterar(ordem))
				Path.exibirMsgSucesso("OFICINA", "Concluir", "Orçamento concluído com sucesso!");
			
			Path.limparCampos(servico, valor, quantidade);
			carregarTabela();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// handleCadastrar
	
	
	private void carregarTabela(){
		tabela.setItems(null);
		listServicos.clear();
		
		columnData.setCellValueFactory(new PropertyValueFactory<>("DO"));
		columnPeca.setCellValueFactory(new PropertyValueFactory<>("peca"));
		columnQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		columnOS.setCellValueFactory(new PropertyValueFactory<>("numeroOS"));
		columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		columnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		
		listServicos = ordemDAO.listar();
		
		if(listServicos.size() > 4){
			
			pagination.setDisable(false);
			
			int numPaginas = listServicos.size() / 4;
			
			if(listServicos.size() % 4 != 0)
				numPaginas++;
			
			pagination.setPageCount(numPaginas);
			pagination.setCurrentPageIndex(pagina);
			
		}
		
		
		
		int inicio = pagina * 4;
		int limite = inicio + 4;
	
		
		if(limite > listServicos.size())
			limite  = listServicos.size();
		
		tabela.setItems(FXCollections.observableArrayList((listServicos.subList(inicio, limite))));
	}

}
