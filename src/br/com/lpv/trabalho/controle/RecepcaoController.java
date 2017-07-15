package br.com.lpv.trabalho.controle;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.lpv.trabalho.Main;
import br.com.lpv.trabalho.modelo.Path;
import br.com.lpv.trabalho.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class RecepcaoController implements Initializable{
	private @FXML ImageView img_cliente, img_logout;
	private @FXML ImageView img_pecas, img_chart;
	private @FXML ImageView img_veiculo, img_usuario;
	private @FXML ImageView img_orcamento;
	private @FXML ImageView img_pagar;
	private @FXML AnchorPane pane;
	private @FXML Label label_titulo, label_graficos, label_orcamento, label_pecas, label_veiculo, label_cliente, label_pagar, label_usuario;	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Usuario u = LoginController.getUsuario();
		
		if(u.getPapel().equalsIgnoreCase("M")){
			img_orcamento.setDisable(false);
			img_veiculo.setDisable(true);
			
			label_orcamento.setDisable(false);
			label_pecas.setDisable(false);
			label_veiculo.setDisable(true);
			label_cliente.setDisable(true);
		}
		
		
		
		if(u.getPapel().equalsIgnoreCase("R")){
			img_orcamento.setDisable(false);
			label_orcamento.setDisable(false);
		}

		if(u.getPapel().equalsIgnoreCase("G")){
			img_cliente.setDisable(false);
			img_pagar.setVisible(true);
			img_pecas.setDisable(false);
			img_veiculo.setDisable(false);
			img_orcamento.setDisable(false);
			img_chart.setDisable(false);
			img_chart.setVisible(true);
			img_usuario.setVisible(true);
			
			label_pagar.setDisable(false);
			label_pagar.setVisible(true);
			label_usuario.setVisible(true);
			label_graficos.setVisible(true);
			label_graficos.setDisable(false);
			label_orcamento.setDisable(false);
			label_pecas.setDisable(false);
			label_veiculo.setDisable(false);
			label_cliente.setDisable(false);
		}
		
		img_cliente.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			try {
				AnchorPane anchor = (AnchorPane)FXMLLoader.load(getClass().getResource(Path.CLIENTES));
				pane.getChildren().setAll(anchor);
				label_titulo.setText("Clientes - Recepção");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		img_pecas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			try {
				AnchorPane anchor = (AnchorPane)FXMLLoader.load(getClass().getResource(Path.PECAS));
				pane.getChildren().setAll(anchor);
				label_titulo.setText("Peças - Recepção");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		img_veiculo.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			try {
				AnchorPane anchor = (AnchorPane)FXMLLoader.load(getClass().getResource(Path.VEICULOS));
				pane.getChildren().setAll(anchor);
				label_titulo.setText("Veículos - Recepção");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		img_orcamento.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			try {
				AnchorPane anchor = (AnchorPane)FXMLLoader.load(getClass().getResource(Path.ORCAMENTOS));
				pane.getChildren().setAll(anchor);
				label_titulo.setText("Orçamentos - Recepção");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		img_pagar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			try {
				AnchorPane anchor = (AnchorPane)FXMLLoader.load(getClass().getResource(Path.PAGAMENTOS));
				pane.getChildren().setAll(anchor);
				label_titulo.setText("Pagamentos");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		

		img_chart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			try {
				AnchorPane anchor = (AnchorPane)FXMLLoader.load(getClass().getResource(Path.CHART));
				pane.getChildren().setAll(anchor);
				label_titulo.setText("Gráficos");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		img_usuario.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			try {
				AnchorPane anchor = (AnchorPane)FXMLLoader.load(getClass().getResource(Path.USUARIOS));
				pane.getChildren().setAll(anchor);
				label_titulo.setText("Gráficos");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
		img_logout.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			logOut();
		});
		
	}
	
	@FXML private void logOut(){
		LoginController.stage.close();
		Main.stage.show();
	}
}
