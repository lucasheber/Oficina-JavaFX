package br.com.lpv.trabalho.controle;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.modelo.OrdemServico;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class NFController implements Initializable{
	@FXML private TableView<String> tabela;
	@FXML private TableColumn<String, String> columnOS, columnCli, columnValor, columnVei, columnData;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		OrdemServico ordem = PagamentoController.getOrdemServico();
		String valor = String.format("%1.2f", ordem.getValor());
		String[] dados = {""};
		
		columnOS.setCellValueFactory(data -> new SimpleStringProperty(ordem.getNumeroOS()));
		columnCli.setCellValueFactory(data -> new SimpleStringProperty(ordem.getVeiculo().getCliente().getNome()));
		columnValor.setCellValueFactory(data -> new SimpleStringProperty(valor));
		columnVei.setCellValueFactory(data -> new SimpleStringProperty(ordem.getVeiculo().getPlaca()));
		columnData.setCellValueFactory(data -> new SimpleStringProperty(ordem.getDO()));
		
		tabela.setItems(FXCollections.observableArrayList(dados));
		
	}

	
}
