package com.NumberConverter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(400);
			primaryStage.setMinHeight(530);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if (args.length == 3) {
			String number = args[0];
			String a = args[1];
			String b = args[2];
			System.out.println(Converter.fromXtoY(number, Integer.parseInt(a), Integer.parseInt(b)));
			System.exit(0);
		} else {
			launch(args);
		}

		
	}
}
