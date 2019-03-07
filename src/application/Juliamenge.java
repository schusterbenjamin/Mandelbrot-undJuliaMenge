package application;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Juliamenge extends Menge
{

	GUI gui;
	Scene scene;
	double sceneHeight, sceneWidth;

	double realOfC, imaginaryOfC;

	public Juliamenge(GUI gui)
	{
		super(gui);
		this.gui = gui;
		scene = gui.scene;
		sceneHeight = scene.getHeight();
		sceneWidth = scene.getWidth();
	}

	public void renderJulia()
	{
		new Thread()
		{
			public void run()
			{
				renderJulia(imageWidth, imageHeight);
			}
		}.start();
	}

	public void renderJulia(int width, int height)
	{

		gui.setJuliaImage(createWritableImageOfJuliaSet(width, height));

	}

	private WritableImage createWritableImageOfJuliaSet(int width, int height)
	{

		WritableImage juliaImage = new WritableImage(width, height);
		PixelWriter juliaWriter = juliaImage.getPixelWriter();

		try
		{
			realOfC = Double.parseDouble(gui.juliaRealPartOfNumber.getText());
			imaginaryOfC = Double.parseDouble(gui.juliaImaginaryPartOfNumber.getText());
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Bitte Zahlen eingeben!", "Ich kann damit nicht arbeiten!!!", JOptionPane.PLAIN_MESSAGE);
		}

		for (int i = 0; i < juliaImage.getWidth(); i++)
		{
			for (int k = 0; k < juliaImage.getHeight(); k++)
			{

				Color color = calculateColorForPoint(i, k, width, height);

				juliaWriter.setColor(i, k, color);

			}
		}

		return juliaImage;

	}

	public BufferedImage createBufferedImageOfJuliaSet()
	{

		setMaxIterations(getMaxIterations() * 2);
		setZoom(getZoom() / 10);

		int imageToSaveWidth = 3000, imageToSaveHeight = 3000;

		BufferedImage juliaImage = new BufferedImage(imageToSaveWidth, imageToSaveHeight, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < juliaImage.getWidth(); i++)
		{
			for (int k = 0; k < juliaImage.getHeight(); k++)
			{

				Color fxColor = calculateColorForPoint(i, k, imageToSaveWidth, imageToSaveHeight);
				java.awt.Color color = new java.awt.Color((float) fxColor.getRed(), (float) fxColor.getGreen(), (float) fxColor.getBlue());

				juliaImage.setRGB(i, k, color.getRGB());

			}
		}

		setMaxIterations(getMaxIterations() / 2);
		setZoom(getZoom() * 10);

		return juliaImage;

	}

	private Color calculateColorForPoint(int X, int Y, int width, int height)
	{
		double real = X;
		double imaginary = Y;
		real = (((real - (width / 2)) / 100) * zoom + xSetOff);
		imaginary = -(((imaginary - (height / 2)) / 100) * zoom + ySetOff);

		KomplexeZahl z = new KomplexeZahl(real, imaginary);

		KomplexeZahl c = new KomplexeZahl(realOfC, imaginaryOfC);

		return calculateColorForKomplexNumbers(z, c, false);
	}

}
