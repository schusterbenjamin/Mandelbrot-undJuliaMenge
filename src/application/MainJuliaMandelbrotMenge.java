package application;

import com.sun.javafx.scene.control.skin.CustomColorDialog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainJuliaMandelbrotMenge extends Application
{
	BorderPane root;
	Scene scene;
	Stage stage;

	GUI gui;
	Mandelbrotmenge mandelbrotMenge;
	Juliamenge juliaMenge;

	boolean mousePressed;

	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			 stage = new Stage();
			 root = new BorderPane();
			 scene = new Scene(root, 1000, 600);
			
			 gui = new GUI(scene, stage);
			 root.getChildren().add(gui);
			
			 mandelbrotMenge = new Mandelbrotmenge(gui);
			 juliaMenge = new Juliamenge(gui);
			
			 gui.setMandelbrotmengeAndJuliamenge(mandelbrotMenge, juliaMenge);
			
			 stage.setResizable(true);
			
			 stage.setTitle("Mandelbrot und Juliamenge");
			 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			 stage.setScene(scene);
			 stage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
