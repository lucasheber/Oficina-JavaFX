package br.com.lpv.trabalho.controle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.dao.ClienteDAO;
import br.com.lpv.trabalho.dao.Database;
import br.com.lpv.trabalho.dao.DatabaseFactory;
import br.com.lpv.trabalho.dao.OrdemServicoDAO;
import br.com.lpv.trabalho.dao.VeiculoDAO;
import br.com.lpv.trabalho.modelo.Cliente;
import br.com.lpv.trabalho.modelo.OrdemServico;
import br.com.lpv.trabalho.modelo.Path;
import br.com.lpv.trabalho.modelo.Veiculo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PagamentoController implements Initializable{
	@FXML private RadioButton radioCPF, radioVeiculo, radioOS;
	@FXML private TextField busca, valor, os;
	@FXML private TableView<OrdemServico> tabela;
	@FXML private Pagination pagination;
	@FXML private TableColumn<OrdemServico, String> columnOS, columnStatus, columnValor, columnVeiculo;
	
	private final ToggleGroup group = new ToggleGroup();
	private final Database database = DatabaseFactory.getDatabase(Path.POSTGRES);
	private final Connection connection = database.conectar();
	private ClienteDAO clienteDAO;
	private VeiculoDAO veiculoDAO;
	private OrdemServicoDAO ordemDAO;
	private int pagina, tipo = 2; 
	private List<OrdemServico> listServicos;
	
	private static OrdemServico ordemServico;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ordemDAO = new OrdemServicoDAO(connection);
		clienteDAO = new ClienteDAO(connection);
		veiculoDAO = new  VeiculoDAO(connection);
		busca.setEditable(false);
		
		listServicos = new ArrayList<>();
		
		radioCPF.setToggleGroup(group);
		radioVeiculo.setToggleGroup(group);
		radioOS.setToggleGroup(group);
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
		    	busca.clear();
		    	busca.setEditable(true);
		    	listServicos.clear();
		    	
		    	if (group.getSelectedToggle() == radioCPF) {
	                  Path.cpfField(busca);
	              	 tipo = 1;                
		        }
		            
	            if (group.getSelectedToggle() == radioOS) {
	                 //Path.numericField(busca);
	            	 tipo = 2;
	            }
	            
	            if (group.getSelectedToggle() == radioVeiculo) {
	            	//Path.placa(busca);
	            	tipo = 3;
	            }
		    }// changed
		});// selectedToggleProperty
		
		tabela.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
				-> pagar(newValue));
		
	}// initialize
	
	private void pagar(OrdemServico newValue) {
		ordemServico = newValue;
		
		if(ordemServico == null) return;
		os.setText(ordemServico.getNumeroOS());
		valor.setText(String.format("%1.2f", ordemServico.getValor()));
		
	}

	@FXML private void handleBuscar(){
		
		try{
			if(busca.getText().isEmpty() || busca == null){
				Path.exibirMsgErro("OFICINO", "Buscar", "Digite um valor");
				return;
			}
			
			listServicos.clear();
			
			if(tipo == 2) // OS
				listServicos.add(ordemDAO.buscar(Integer.parseInt(busca.getText())));
			else
				if(tipo == 1){ // CPF
					Cliente c = clienteDAO.buscar(busca.getText());
					
					if(c == null){
						Path.exibirMsgErro("OFICINO", "Buscar", "CPF não encontrado");
						return;
					}
					
					Veiculo v = veiculoDAO.buscarCliente(c.getId());
					
					
					if(v == null){
						Path.exibirMsgErro("OFICINO", "Buscar", "Cliente nao possui veículo!");
						return;
					}
					
					listServicos = ordemDAO.listar(v.getId());
				}// if
				else
					if(tipo == 3){ // Veiculo
						Veiculo v = veiculoDAO.buscar(busca.getText());
						
						
						if(v == null){
							Path.exibirMsgErro("OFICINO", "Buscar", "Placa não encontrada!");
							return;
						}
						
						listServicos = ordemDAO.listar(v.getId());
					}
			
			carregarTabela();
			
			pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
			      @Override
			   public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			        
			    pagina = newValue.intValue();
			    carregarTabela();
			  }
		
			});
		}catch(NumberFormatException e){
			Path.exibirMsgErro("OFICINO", "Buscar", "preencha os campos corretamente");
		}
	}
	
	private void carregarTabela(){
		tabela.setItems(null);

		columnVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
		columnOS.setCellValueFactory(new PropertyValueFactory<>("numeroOS"));
		columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		columnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		
		int maxPag = 3;
		
		if(listServicos.size() > maxPag){
			
			pagination.setDisable(false);
			
			int numPaginas = listServicos.size() / maxPag;
			
			if(listServicos.size() % maxPag != 0)
				numPaginas++;
			
			pagination.setPageCount(numPaginas);
			pagination.setCurrentPageIndex(pagina);
			
		}
		
		int inicio = pagina * maxPag;
		int limite = inicio + maxPag;
	
		
		if(limite > listServicos.size())
			limite  = listServicos.size();
		
		tabela.setItems(FXCollections.observableArrayList((listServicos.subList(inicio, limite))));
	}

	@FXML private void handlePagar(){
		
		try {
			if(ordemServico == null) {Path.exibirMsgErro("OFICINO", "Buscar", "Selecione um item!"); return;}
			
			if(!ordemServico.getStatus().equalsIgnoreCase("concluida")){
				Path.exibirMsgErro("OFICINA", "Pagamentos", "OS não concluida!");
				return;
			}
				
			ordemServico.setStatus("Pago");
			
			ordemDAO.alterar(ordemServico);
			
			Stage dialog = new Stage();
			Parent parent;
			
			parent = FXMLLoader.load(getClass().getResource(Path.NE));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			
			dialog.initModality(Modality.APPLICATION_MODAL); 
			dialog.showAndWait();
			tabela.setItems(null);
			
			carregarTabela();
		} catch (IOException e) {
			
		}
			
	}

	public static OrdemServico getOrdemServico() {
		return ordemServico;
	}
	
	
}
