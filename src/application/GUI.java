package application;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Group {

	Stage stage;
	Scene scene;
	int screenWidth, screenHeight;

	ArrayList<Node> allNodes;

	Button easterEggButton;

	Mandelbrotmenge mandelbrotMenge;
	Text mandelbrotName;
	Button mandelSave;
	Button mandelRender;
	Button mandelReset;
	ComboBox<String> mandelColor;
	ImageView mandelImageView;
	boolean mandelIsFullscreen = false;

	Juliamenge juliaMenge;
	Text juliaName;
	Button juliaSave;
	Button juliaRender;
	Button juliaReset;
	ComboBox<String> juliaColor;
	TextField juliaRealPartOfNumber;
	TextField juliaImaginaryPartOfNumber;
	ImageView juliaImageView;
	boolean juliaIsFullscreen = false;

	CheckBox rotate;
	boolean rotatebool = false;
	Slider rotateSpeed;
	Slider rotateRadius;

	public GUI(Scene scene, Stage stage) {

		this.stage = stage;
		this.scene = scene;

		allNodes = new ArrayList<Node>();

		createGUI();

		scene.widthProperty().addListener((obs, oldVal, newVal) -> {
			setGUIonSpot();
			renderBoth();
		});

		scene.heightProperty().addListener((obs, oldVal, newVal) -> {
			setGUIonSpot();
			renderBoth();
		});

		Dimension screenSizeFromAWT = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSizeFromAWT.getWidth();
		screenHeight = (int) screenSizeFromAWT.getHeight();

	}

	private void setGUIonSpot() {

		double sceneWidth = scene.getWidth();
		double sceneHeight = scene.getHeight();

		
//Mandelbrotstuff
		mandelbrotName.setTranslateX(sceneWidth * 0.05);
		mandelbrotName.setTranslateY(sceneHeight * 0.1);

		mandelSave.setTranslateX(sceneWidth * 0.3);
		mandelSave.setTranslateY(sceneHeight * 0.05);
		mandelSave.setPrefWidth(sceneWidth * 0.1);
		mandelSave.setPrefHeight(sceneHeight * 0.05);

		mandelRender.setTranslateX(sceneWidth * 0.4);
		mandelRender.setTranslateY(sceneHeight * 0.45);
		mandelRender.setPrefWidth(sceneWidth * 0.10);
		mandelRender.setPrefHeight(sceneHeight * 0.5);

		mandelColor.setTranslateX(sceneWidth * 0.05);
		mandelColor.setTranslateY(sceneHeight * 0.15);
		mandelColor.setPrefSize(sceneWidth * 0.2, sceneHeight * 0.1);

		mandelImageView.setTranslateX(sceneWidth * 0.05);
		mandelImageView.setTranslateY(sceneHeight * 0.45);
		
		mandelReset.setTranslateX(sceneWidth * 0.3);
		mandelReset.setTranslateY(sceneHeight * 0.95); 
		
		int size = (int)(Math.min(sceneWidth * .5, sceneHeight * .5));
		Menge.setImageSize(size, size);
		
//Julia Stuff
		juliaName.setTranslateX(sceneWidth * 0.55);
		juliaName.setTranslateY(sceneHeight * 0.1);

		juliaSave.setTranslateX(sceneWidth * 0.8);
		juliaSave.setTranslateY(sceneHeight * 0.05);
		juliaSave.setPrefSize(sceneWidth * 0.1, sceneHeight * 0.05);

		juliaRender.setTranslateX(sceneWidth * 0.9);
		juliaRender.setTranslateY(sceneHeight * 0.45);
		juliaRender.setPrefSize(sceneWidth * 0.10, sceneHeight * 0.5);

		juliaColor.setTranslateX(sceneWidth * 0.55);
		juliaColor.setTranslateY(sceneHeight * 0.15);
		juliaColor.setPrefSize(sceneWidth * 0.2, sceneHeight * 0.1);

		juliaRealPartOfNumber.setTranslateX(sceneWidth * 0.8);
		juliaRealPartOfNumber.setTranslateY(sceneHeight * 0.2);
		juliaRealPartOfNumber.setPrefSize(sceneWidth * 0.1, sceneHeight * 0.05);

		juliaImaginaryPartOfNumber.setTranslateX(sceneWidth * 0.9);
		juliaImaginaryPartOfNumber.setTranslateY(sceneHeight * 0.2);
		juliaImaginaryPartOfNumber.setPrefSize(sceneWidth * 0.1, sceneHeight * 0.05);

		juliaImageView.setTranslateX(sceneWidth * 0.55);
		juliaImageView.setTranslateY(sceneHeight * 0.45);
		
		juliaReset.setTranslateX(sceneWidth  * 0.8);
		juliaReset.setTranslateY(sceneHeight * 0.95);
		
		
		
		
//Rotieren stuff
		rotate.setText("Rotieren");
		rotate.setTranslateX(sceneWidth * 0.5);
		rotate.setTranslateY(sceneHeight * 0.3);
		rotate.setPrefSize(sceneWidth * 0.1, sceneHeight * 0.025);

		rotateSpeed.setTranslateX(sceneWidth * 0.55);
		rotateSpeed.setTranslateY(sceneHeight * 0.35);
		rotateSpeed.setPrefWidth(sceneWidth * 0.4);

		rotateRadius.setTranslateX(sceneWidth * 0.55);
		rotateRadius.setTranslateY(sceneHeight * 0.4);
		rotateRadius.setPrefWidth(sceneWidth * 0.4);

		//Easter Egg
		easterEggButton.setTranslateX(sceneWidth * 0.3);
		easterEggButton.setTranslateY(sceneHeight * 0.15);
		easterEggButton.setPrefSize(sceneWidth * 0.1, sceneHeight * 0.1);
	}

	private void createMandelAndJuliaButtons() {

		mandelSave = new Button("save");
		mandelSave.setId("btn");
		add(mandelSave);

		mandelRender = new Button("Render");
		mandelRender.setId("btn");
		add(mandelRender);
		
		mandelRender.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent arg0)
			{
				renderMandelbrot();
			}
		});

		mandelSave.setOnMouseClicked((MouseEvent event) ->
		{

			BufferedImage imageToSave = mandelbrotMenge.createBufferedImageOfMandelbrotSet();

			mandelbrotMenge.saveImage(imageToSave, "/Mandelbrot.jpg");

		});

		juliaSave = new Button("save");
		juliaSave.setId("btn");
		add(juliaSave);

		juliaRender = new Button("Render");
		juliaRender.setId("btn");
		add(juliaRender);
		
		juliaRender.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent arg0)
			{

				renderJulia();
			}
		});

		juliaSave.setOnMouseClicked((MouseEvent event) ->
		{

			BufferedImage imageToSave = juliaMenge.createBufferedImageOfJuliaSet();

			juliaMenge.saveImage(imageToSave, "/Juliamenge_c=" + juliaMenge.realOfC + "+" + juliaMenge.imaginaryOfC + "i" + ".jpg");

		});

	}

	private void createNameTexts() {

		mandelbrotName = new Text("Mandelbrotmenge");
		mandelbrotName.setId("Names");
		add(mandelbrotName);

		juliaName = new Text("Juliamenge");
		juliaName.setId("Names");
		add(juliaName);
	}

	private void createColorDropLists() {
		ObservableList<String> options = FXCollections.observableArrayList("red", "green", "blue", "crazy",
				"black & white", "test", "testTwo", "gray");
		mandelColor = new ComboBox<String>(options);
		mandelColor.setValue("white & black");
		mandelColor.setId("btn");
		add(mandelColor);

		juliaColor = new ComboBox<String>(options);
		juliaColor.setValue("white & black");
		juliaColor.setId("btn");
		add(juliaColor);
	}

	private void createAllForComplexNumber() {
		juliaRealPartOfNumber = new TextField("Real Part");
		juliaRealPartOfNumber.setText("-1");
		add(juliaRealPartOfNumber);

		juliaImaginaryPartOfNumber = new TextField("Imaginary Part");
		juliaImaginaryPartOfNumber.setText("0");
		add(juliaImaginaryPartOfNumber);
	}

	private void createImageViews() {
		mandelImageView = new ImageView();
		mandelImageView.setOnMouseClicked((MouseEvent event) -> {
			if (!rotatebool) {

				if (mandelImageView.isHover()) {

					double x = (((event.getX() - Menge.getImageWidth() / 2) / 100) * mandelbrotMenge.zoom + mandelbrotMenge.xSetOff);
					double y = -(((event.getY() - Menge.getImageHeight() / 2) / 100) * mandelbrotMenge.zoom + mandelbrotMenge.ySetOff);

					juliaRealPartOfNumber.setText(x + "");
					juliaImaginaryPartOfNumber.setText(y + "");

					renderJulia();
					// rendert Mandelbrotmenge mit rotem Punkt bei dem c für die Juliamenge
					renderMandelbrot();
				}
			}
			if (event.getClickCount() == 2) {
				if (!mandelIsFullscreen) {
					removeEverythingFromGroup();
					setNodeFullscreen(mandelImageView);
					mandelIsFullscreen = true;
				} else {
					addEverythingToGroup();
					setFromFullscreenBackSmall();
					mandelIsFullscreen = false;
				}
			}
		});
		add(mandelImageView);

		juliaImageView = new ImageView();
		juliaImageView.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 2) {
				if (!juliaIsFullscreen) {
					removeEverythingFromGroup();
					setNodeFullscreen(juliaImageView);
					juliaIsFullscreen = true;
				} else {
					addEverythingToGroup();
					setFromFullscreenBackSmall();
					juliaIsFullscreen = false;
				}
			}
		});
		add(juliaImageView);
	}

	private void createRotateCheckBoxWithSliders() {

		rotate = new CheckBox();
		add(rotate);

		rotateSpeed = new Slider();
		add(rotateSpeed);

		rotateRadius = new Slider();
		add(rotateRadius);

	}

	public void createResetButtons() {
		mandelReset = new Button("Reset");
		mandelReset.setOnMouseClicked((MouseEvent event) -> {
			mandelbrotMenge.setxSetOff(0);
			mandelbrotMenge.setySetOff(0);
			mandelbrotMenge.setZoom(1);
			renderMandelbrot();
		});
		mandelReset.setId("btn");
		add(mandelReset);

		juliaReset = new Button("Reset");
		juliaReset.setOnMouseClicked((MouseEvent event) -> {
			juliaMenge.setxSetOff(0);
			juliaMenge.setySetOff(0);
			juliaMenge.setZoom(1);
			renderJulia();
		});
		juliaReset.setId("btn");
		add(juliaReset);
	}

	boolean herrSchroettinger = false;

	private void createEasterEggButton() {
		easterEggButton = new Button();
		easterEggButton.setId("schrbtn");
		add(easterEggButton);

		easterEggButton.setOnMouseClicked((MouseEvent event) -> {

			if (!herrSchroettinger) {
				scene.getStylesheets().clear();
				scene.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());
				herrSchroettinger = true;
			} else {
				scene.getStylesheets().clear();
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				herrSchroettinger = false;
			}

		});
	}

	private void createGUI() {
		createMandelAndJuliaButtons();
		createNameTexts();
		createColorDropLists();
		createAllForComplexNumber();
		createImageViews();
		createRotateCheckBoxWithSliders();
		createResetButtons();

		createEasterEggButton();

		setGUIonSpot();
	}

	public void setNodeFullscreen(Node node) {
		stage.setFullScreen(true);
		getChildren().add(node);
		node.setTranslateX(0);
		node.setTranslateY(0);
		
		Menge.setImageSize(screenWidth, screenHeight);
		
		if (node == mandelImageView) {
			mandelbrotMenge.setMaxIterations(mandelbrotMenge.getMaxIterations() / 10);
			renderMandelbrot();
		}
		if (node == juliaImageView) {
			juliaMenge.setMaxIterations(juliaMenge.getMaxIterations() / 10);
			renderJulia();
		}
	}

	public void setFromFullscreenBackSmall() {
		stage.setFullScreen(false);
		getChildren().clear();
		addEverythingToGroup();
		setGUIonSpot();
		mandelbrotMenge.setMaxIterations(mandelbrotMenge.getMaxIterations() * 10);
		renderMandelbrot();
		juliaMenge.setMaxIterations(juliaMenge.getMaxIterations() * 10);
		renderJulia();
		
		int size = (int)(Math.min(scene.getWidth() * .5, scene.getHeight() * .5));
		Menge.setImageSize(size, size);
	}

	public void removeEverythingFromGroup() {
		getChildren().clear();
	}

	public void addEverythingToGroup() {
		for (Node node : allNodes) {
			if (!getChildren().contains(node)) {
				getChildren().add(node);
			}
		}
	}

	public void add(Node node) {
		getChildren().add(node);
		allNodes.add(node);
	}

	public void setMandelbrotImage(WritableImage image) {
		mandelImageView.setImage(image);
	}

	public void setJuliaImage(WritableImage image) {
		juliaImageView.setImage(image);
	}

	public String getJuliaColor() {
		return juliaColor.getValue();
	}

	public String getMandelColor() {
		return mandelColor.getValue();
	}

	public void setMandelbrotmengeAndJuliamenge(Mandelbrotmenge mandel, Juliamenge julia) {
		this.mandelbrotMenge = mandel;
		this.juliaMenge = julia;
	}
	
	private void renderJulia() {
		juliaMenge.renderJulia();
		waitabit();
	}
	
	private void renderMandelbrot() {
		mandelbrotMenge.renderMandelbrot();
		waitabit();
	}
	
	private void renderBoth() {
		juliaMenge.renderJulia();
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
