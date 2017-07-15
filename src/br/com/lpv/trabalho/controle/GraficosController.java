package br.com.lpv.trabalho.controle;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.dao.Database;
import br.com.lpv.trabalho.dao.DatabaseFactory;
import br.com.lpv.trabalho.dao.OrdemServicoDAO;
import br.com.lpv.trabalho.modelo.OrdemServico;
import br.com.lpv.trabalho.modelo.Path;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

public class GraficosController implements Initializable{
	@FXML private ComboBox<String> tipo;
	@FXML private VBox vBox;
	@FXML private DatePicker inicio, fim;

	
	private List<OrdemServico> ordem;

	private final Database database = DatabaseFactory.getDatabase(Path.POSTGRES);
	private final Connection connection = database.conectar();
	private OrdemServicoDAO ordemDAO;
	private int tipoGrafico;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ordemDAO = new OrdemServicoDAO(connection);
		ordem = new ArrayList<>();
		ordem = ordemDAO.listar();
		tipoGrafico = 1;

		tipo.setItems(FXCollections.observableArrayList("Barra", "Area", "Linha"));
		tipo.setValue("Barra");

		tipo.setOnAction((ActionEvent e)->{
			if(tipo.getValue().equalsIgnoreCase("Barra")){
				tipoGrafico = 1;
				handleGerar();
			}
			else
			if(tipo.getValue().equalsIgnoreCase("Area")){
				tipoGrafico = 2;
				handleGerar();
			}
			else
			if(tipo.getValue().equalsIgnoreCase("Linha")){
				tipoGrafico = 3;
				handleGerar();
			}
		});

	}// initialize

	
	@FXML private void handleGerar(){
		
		if(inicio.getValue() == null || fim.getValue() == null){
			Path.exibirMsgErro("OFICINA", "Gráficos", "preencha todos os campos!");
			return;
		}
		
		if(inicio.getValue().isAfter(fim.getValue())){
			Path.exibirMsgErro("OFICINA", "Gráficos", "Data final não poder ser menor que a inicial!");
			return;
		}
		
		if(tipoGrafico == 1)
			gerarBarras();
		else
		if(tipoGrafico == 3)
			gerarLinhas();
		else
		if(tipoGrafico == 2)
			gerarArea();

	}// handleGerar
	
	@SuppressWarnings("unchecked")
	private void gerarBarras(){
		vBox.getChildren().clear();
		CategoryAxis x1 = new CategoryAxis();
		NumberAxis y1 = new NumberAxis();
	
		BarChart<String, Number> graficoBar = new BarChart<>(x1, y1);
	
		XYChart.Series<String, Number> serie = new XYChart.Series<>();
		 
		serie.getData().clear();
   
		for(OrdemServico os : ordem){
			//if(!os.getStatus().equalsIgnoreCase("Concluida")) continue;
			
			if((os.getDataOrcamento().isAfter(inicio.getValue()) || os.getDataOrcamento().isEqual(inicio.getValue())) && (os.getDataOrcamento().isBefore(fim.getValue()) || os.getDataOrcamento().isEqual(fim.getValue())))
					serie.getData().add(new Data<>(os.getServico(), os.getValor()));
		}
		
		graficoBar.getData().clear();

		graficoBar.getData().addAll(serie);
		vBox.getChildren().clear();
		vBox.getChildren().addAll(graficoBar);
	}
	
	@SuppressWarnings("unchecked")
	private void gerarLinhas(){
		vBox.getChildren().clear();
		CategoryAxis x1 = new CategoryAxis();
		NumberAxis y1 = new NumberAxis();
	
		LineChart<String, Number> graficoLinha = new LineChart<>(x1, y1);
	
		XYChart.Series<String, Number> serie = new XYChart.Series<>();
		 
		serie.getData().clear();
   
		for(OrdemServico os : ordem){
			//if(!os.getStatus().equalsIgnoreCase("Concluida")) continue;
			
			if((os.getDataOrcamento().isAfter(inicio.getValue()) || os.getDataOrcamento().isEqual(inicio.getValue())) && (os.getDataOrcamento().isBefore(fim.getValue()) || os.getDataOrcamento().isEqual(fim.getValue())))
					serie.getData().add(new Data<>(os.getServico(), os.getValor()));
		}
		
		graficoLinha.getData().clear();

		graficoLinha.getData().addAll(serie);
		vBox.getChildren().clear();
		vBox.getChildren().addAll(graficoLinha);
	}
	
	@SuppressWarnings("unchecked")
	private void gerarArea(){
		vBox.getChildren().clear();
		CategoryAxis x1 = new CategoryAxis();
		NumberAxis y1 = new NumberAxis();
	
		StackedAreaChart<String, Number> graficoArea = new StackedAreaChart<>(x1, y1);
	
		XYChart.Series<String, Number> serie = new XYChart.Series<>();
		 
		serie.getData().clear();
   
		for(OrdemServico os : ordem){
			//if(!os.getStatus().equalsIgnoreCase("Concluida")) continue;
			
			if((os.getDataOrcamento().isAfter(inicio.getValue()) || os.getDataOrcamento().isEqual(inicio.getValue())) && (os.getDataOrcamento().isBefore(fim.getValue()) || os.getDataOrcamento().isEqual(fim.getValue())))
					serie.getData().add(new Data<>(os.getServico(), os.getValor()));
		}
		
		graficoArea.getData().clear();

		graficoArea.getData().addAll(serie);
		vBox.getChildren().clear();
		vBox.getChildren().addAll(graficoArea);
	}
}
