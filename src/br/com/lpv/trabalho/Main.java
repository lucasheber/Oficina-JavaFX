package br.com.lpv.trabalho;
	

import static br.com.lpv.trabalho.modelo.Path.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			AnchorPane root = new AnchorPane();
			root = FXMLLoader.load(getClass().getResource(LOGIN));
		
			Scene scene = new Scene(root);
		
			stage.setScene(scene);
			stage.getIcons().add(new Image(getClass().getResourceAsStream(ICON)));
			stage.setResizable(false);
			

			scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
			    if (t.getCode() == KeyCode.ESCAPE) {
			    	stage.close();
			    }
			    
			});
			
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

