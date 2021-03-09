package com.schusterbenjamin.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application
{
	BorderPane root;
	Scene scene;
	Stage stage;

	GUI gui;
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			stage = new Stage();
			root = new BorderPane();
			scene = new Scene(root, 1000, 600);
			
//			stage.getIcons().add(new Image(MainJuliaMandelbrotMenge.class.getResourceAsStream("back1"))); //TODO: try something

			gui = new GUI(scene, stage);
			root.getChildren().add(gui);

			stage.setResizable(true);

			stage.setTitle("Mandelbrot- and Juliaset");
			scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String args[])
	{
		launch(args);
	}

}
