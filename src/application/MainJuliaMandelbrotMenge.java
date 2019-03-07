package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainJuliaMandelbrotMenge extends Application
{
	BorderPane root;
	Scene scene;
	Stage stage;

	GUI gui;
	Mandelbrotmenge mandelbrotMenge;
	Juliamenge juliaMenge;

	boolean mousePressed;
	boolean rotate = false;
	double circle = 0;

	double rotateValue = 0.3;

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

			renderMandelbrot();
			renderJulia();

			setKeyListener();
			setScrollListener();
			setMouseListener();
			setRotateTimeline();

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

	private void setRotateTimeline()
	{

		Timeline rotateTimer = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent arg0)
			{

				if (gui.rotate.isSelected())
				{
					rotate = true;
//					juliaMenge.setMaxIterations(juliaMenge.getMaxIterations() / 10);
//					mandelbrotMenge.setMaxIterations(mandelbrotMenge.getMaxIterations() / 10);
				}
				else
				{
					rotate = false;
					if (!gui.mandelIsFullscreen && !gui.juliaIsFullscreen)
					{
//						juliaMenge.setMaxIterations(juliaMenge.getMaxIterations() * 10);
//						mandelbrotMenge.setMaxIterations(mandelbrotMenge.getMaxIterations() * 10);
					}
				}

				if (rotate)
				{
					circle += (Math.PI / (101 - (gui.rotateSpeed.getValue())));

					if (circle > 2 * Math.PI)
					{
						circle = (-2 * Math.PI);
					}

					double rotatex = Math.cos(circle) * ((double) (gui.rotateRadius.getValue()) / 100);
					double rotatey = -Math.sin(circle) * ((double) (gui.rotateRadius.getValue()) / 100);

					gui.juliaRealPartOfNumber.setText("" + rotatex);
					gui.juliaImaginaryPartOfNumber.setText("" + rotatey);

					// if (gui.rotateRadius.getValue() == 0 || gui.rotateRadius.getValue() ==
					// gui.rotateRadius.getMax())
					// {
					// rotateValue *= -1;
					// }
					//
					// gui.rotateRadius.setValue(gui.rotateRadius.getValue() - rotateValue);

					renderJulia();

					// rendert Mandelbrotmenge mit rotem Punkt bei dem c für die Juliamenge
					renderMandelbrot();
				}

			}

		}));
		rotateTimer.setCycleCount(Timeline.INDEFINITE);
		rotateTimer.play();
	}

	private void setMouseListener()
	{

		// der Rest is in der GUI Klasse, jaa ich weiss is unübersichtlich....
		gui.mandelImageView.setOnMouseDragged((MouseEvent event) ->
		{
			getMousePosAndRenderBoth(event);

		});

	}

	private void getMousePosAndRenderBoth(MouseEvent event)
	{
		if (!rotate)
		{

			if (gui.mandelImageView.isHover())
			{

				double x = (((event.getX() - Menge.getImageWidth() / 2) / 100) * mandelbrotMenge.zoom + mandelbrotMenge.xSetOff);
				double y = -(((event.getY() - Menge.getImageHeight() / 2) / 100) * mandelbrotMenge.zoom + mandelbrotMenge.ySetOff);

				gui.juliaRealPartOfNumber.setText(x + "");
				gui.juliaImaginaryPartOfNumber.setText(y + "");

				renderJulia();
				renderMandelbrot();
			}
		}
	}

	private void setScrollListener()
	{

		scene.setOnScroll((ScrollEvent event) ->
		{

			if (gui.mandelImageView.isHover())
			{

				mandelbrotMenge.moveZoom(event.getDeltaY());
				renderMandelbrot();
			}

			if (gui.juliaImageView.isHover())
			{

				juliaMenge.moveZoom(event.getDeltaY());
				renderJulia();
			}
		});

	}

	private void setKeyListener()
	{

		scene.setOnKeyPressed(new EventHandler<KeyEvent>()
		{

			@Override
			public void handle(KeyEvent e)
			{

				if (e.getCode() == KeyCode.ESCAPE)
				{
					gui.setFromFullscreenBackSmall();
				}

				if (gui.mandelImageView.isHover())
				{

					if (e.getCode() == KeyCode.A)
					{
						mandelbrotMenge.moveXSetOffLeft();
					}

					if (e.getCode() == KeyCode.D)
					{
						mandelbrotMenge.moveXSetOffRight();
					}

					if (e.getCode() == KeyCode.W)
					{
						mandelbrotMenge.moveXSetOffUp();
					}

					if (e.getCode() == KeyCode.S)
					{
						mandelbrotMenge.moveXSetOffDown();
					}

					// rendert Mandelbrotmenge mit rotem Punkt bei dem c für die Juliamenge
					renderMandelbrot();
				}

				if (gui.juliaImageView.isHover())
				{

					if (e.getCode() == KeyCode.A)
					{
						juliaMenge.moveXSetOffLeft();
					}

					if (e.getCode() == KeyCode.D)
					{
						juliaMenge.moveXSetOffRight();
					}

					if (e.getCode() == KeyCode.W)
					{
						juliaMenge.moveXSetOffUp();
					}

					if (e.getCode() == KeyCode.S)
					{
						juliaMenge.moveXSetOffDown();
					}

					renderJulia();
				}

			}

		});
		;

	}

	public static void main(String[] args)
	{
		launch(args);
	}
	
	private void renderJulia() {
		juliaMenge.renderJulia();
		waitabit();
	}
	
	private void renderMandelbrot() {
		mandelbrotMenge.renderMandelbrot();
		waitabit();
	}
	
	private void waitabit() {
		try
		{
			Thread.sleep(150);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	

}
