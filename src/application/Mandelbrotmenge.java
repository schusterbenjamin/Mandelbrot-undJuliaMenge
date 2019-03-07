package application;

import java.awt.image.BufferedImage;

import javafx.scene.Scene;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Mandelbrotmenge extends Menge
{
	GUI gui;
	Scene scene;
	double sceneHeight, sceneWidth;

	public Mandelbrotmenge(GUI gui)
	{
		super(gui);
		this.gui = gui;
		scene = gui.scene;
		sceneHeight = scene.getHeight();
		sceneWidth = scene.getWidth();
	}

	public void renderMandelbrot()
	{
		new Thread()
		{
			public void run()
			{
				renderMandelbrotWithRedPoint(Double.parseDouble(gui.juliaRealPartOfNumber.getText()), Double.parseDouble(gui.juliaImaginaryPartOfNumber.getText()), imageWidth, imageHeight);
			}
		}.start();
	}

	public void renderMandelbrotWithRedPoint(double redX, double redY, int width, int height)
	{
		gui.setMandelbrotImage(createWritableImageOfMandelbrotSet(redX, redY, width, height));
	}

	private WritableImage createWritableImageOfMandelbrotSet(double redX, double redY, int width, int height)
	{

		WritableImage mandelbrotImage = new WritableImage(width, height);
		PixelWriter mandelWriter = mandelbrotImage.getPixelWriter();

		for (int i = 0; i < mandelbrotImage.getWidth(); i++)
		{
			for (int k = 0; k < mandelbrotImage.getHeight(); k++)
			{

				Color color = calculateColorForPoint(i, k, width, height);

				mandelWriter.setColor(i, k, color);

			}
		}

		int redMiddlePixelX = (int) (((redX - xSetOff) * 100) / zoom) + getImageWidth() / 2;
		int redMiddlePixelY = -(int) (((redY + ySetOff) * 100) / zoom) + getImageHeight() / 2;

		for (int i = redMiddlePixelX - 1; i <= redMiddlePixelX + 1; i++)
		{
			for (int k = redMiddlePixelY - 1; k <= redMiddlePixelY + 1; k++)
			{
				if (i < mandelbrotImage.getWidth() && k < mandelbrotImage.getHeight() && i > 0 && k > 0)
					mandelWriter.setColor(i, k, Color.rgb(255, 0, 0));
			}
		}

		return mandelbrotImage;

	}

	public BufferedImage createBufferedImageOfMandelbrotSet()
	{

		setMaxIterations(getMaxIterations() * 2);
		setZoom(getZoom() / 10);

		int imageToSaveWidth = 3000, imageToSaveHeight = 3000;

		BufferedImage mandelbrotImage = new BufferedImage(imageToSaveWidth, imageToSaveHeight, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < mandelbrotImage.getWidth(); i++)
		{
			for (int k = 0; k < mandelbrotImage.getHeight(); k++)
			{

				Color fxColor = calculateColorForPoint(i, k, imageToSaveWidth, imageToSaveHeight);
				java.awt.Color color = new java.awt.Color((float) fxColor.getRed(), (float) fxColor.getGreen(), (float) fxColor.getBlue());

				mandelbrotImage.setRGB(i, k, color.getRGB());

			}
		}

		setMaxIterations(getMaxIterations() / 2);
		setZoom(getZoom() * 10);

		return mandelbrotImage;

	}

	private Color calculateColorForPoint(int X, int Y, int width, int height)
	{
		KomplexeZahl z = new KomplexeZahl(0, 0);

		double real = X;
		double imaginary = Y;
		real = (((real - width / 2) / 100) * zoom + xSetOff);
		imaginary = (((imaginary - height / 2) / 100) * zoom + ySetOff);

		KomplexeZahl c = new KomplexeZahl(real, imaginary);

		return calculateColorForKomplexNumbers(z, c, true);
	}

}
