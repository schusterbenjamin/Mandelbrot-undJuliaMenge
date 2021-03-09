package com.schusterbenjamin.application;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import com.schusterbenjamin.utils.ColorMap;
import com.schusterbenjamin.utils.Complexnumber;

abstract class Set
{

	GUI gui;

	double xSetOff, ySetOff;
	double moveSpeed = 0.05;
	double zoom = 1;
	static double zoomChangeFactor = 2;
	int maxIterations = 100;
	double iterationZoomFactor = 1;

	int iterationChangeOnSave = 3;

	Color[] blueorangeMap;
	Color[] testMap;
	static Color[] customMap;

	public static double moveCSpeed = 0.03;

	public Set(GUI g)
	{
		gui = g;

		createMappings();
	}

	private void createMappings()
	{
		ArrayList<Color> colorMapList = new ArrayList<Color>();

		// colorMapList.add(Color.rgb(30, 6, 40));
		// colorMapList.add(Color.rgb(66, 30, 15));
		// colorMapList.add(Color.rgb(25, 7, 26));
		colorMapList.add(Color.rgb(9, 1, 47));
		colorMapList.add(Color.rgb(4, 4, 73));
		colorMapList.add(Color.rgb(0, 7, 100));
		colorMapList.add(Color.rgb(12, 44, 138));
		colorMapList.add(Color.rgb(24, 82, 177));
		colorMapList.add(Color.rgb(57, 125, 209));
		colorMapList.add(Color.rgb(134, 181, 229));
		colorMapList.add(Color.rgb(211, 236, 248));
		colorMapList.add(Color.rgb(241, 233, 191));
		colorMapList.add(Color.rgb(248, 201, 95));
		colorMapList.add(Color.rgb(255, 170, 0));
		colorMapList.add(Color.rgb(204, 128, 0));
		colorMapList.add(Color.rgb(153, 87, 0));
		colorMapList.add(Color.rgb(106, 52, 3));

		blueorangeMap = new ColorMap(colorMapList).getColorMap();

		colorMapList.clear();

		colorMapList.add(Color.rgb(255, 255, 255));
		colorMapList.add(Color.rgb(55, 55, 55));
		colorMapList.add(Color.rgb(100, 100, 100));
		// colorMapList.add(Color.rgb(10,10,10));
		colorMapList.add(Color.rgb(255, 255, 255));

		testMap = new ColorMap(colorMapList).getColorMap();

		customMap = testMap;
	}

	public static void setCustomMap(ArrayList<Color> customColorList)
	{
		customMap = new ColorMap(customColorList).getColorMap();
	}

	static int imageWidth = 300, imageHeight = 300;

	public double getxSetOff()
	{
		return xSetOff;
	}

	public void setxSetOff(double xSetOff)
	{
		this.xSetOff = xSetOff;
	}

	public double getySetOff()
	{
		return ySetOff;
	}

	public void setySetOff(double ySetOff)
	{
		this.ySetOff = ySetOff;
	}

	public void moveXSetOff(double value)
	{
		xSetOff += value;
	}

	public void moveXSetOffLeft()
	{

		xSetOff -= moveSpeed * zoom;

	}

	public void moveXSetOffRight()
	{

		xSetOff += moveSpeed * zoom;

	}

	public void moveYSetOff(double value)
	{
		ySetOff += value;
	}

	public void moveYSetOffUp()
	{

		ySetOff -= moveSpeed * zoom;

	}

	public void moveYSetOffDown()
	{

		ySetOff += moveSpeed * zoom;

	}

	public void moveZoom(double deltaY)
	{
		if (deltaY > 0)
		{
			zoom /= zoomChangeFactor;
			if (maxIterations < 600)
				maxIterations *= iterationZoomFactor;
		}
		else
		{
			zoom *= zoomChangeFactor;
			if (maxIterations > 250)
				maxIterations /= iterationZoomFactor;
		}
	}

	public void setZoom(double zoom)
	{
		this.zoom = zoom;
	}

	public double getZoom()
	{
		return zoom;
	}

	public void setMaxIterations(int max)
	{
		maxIterations = max;
	}

	public int getMaxIterations()
	{
		return maxIterations;
	}

	public static int getImageWidth()
	{
		return imageWidth;
	}

	public static void setImageWidth(int w)
	{
		imageWidth = w;
	}

	public static int getImageHeight()
	{
		return imageHeight;
	}

	public static void setImageHeight(int h)
	{
		imageHeight = h;
	}

	public static void setImageSize(int w, int h)
	{
		imageWidth = w;
		imageHeight = h;
	}

	public void zoomToMouse(Point mouseToNode, double deltaY)
	{
		double xold = (((mouseToNode.getX()) / 100) * zoom);
		double yold = -(((mouseToNode.getY()) / 100) * zoom);

		double xnew, ynew;

		if (deltaY > 0)
		{
			moveZoom(deltaY);
			xnew = xold / zoomChangeFactor;
			ynew = yold / zoomChangeFactor;
		}
		else
		{
			moveZoom(deltaY);
			xnew = xold * zoomChangeFactor;
			ynew = yold * zoomChangeFactor;
		}

		double s = (xold - xnew);
		moveXSetOff(s);

		double d = (yold - ynew);
		moveYSetOff(d);
	}

	private Color getColorFromIterations(int iterationcount, String clr, Complexnumber zn)
	{

		Color color = null;

		int iterationcountForOldMappings = iterationcount * iterationcount * iterationcount / 5;
		if (iterationcountForOldMappings > 255)
		{
			iterationcountForOldMappings = 255;
		}
		if (iterationcountForOldMappings < 0)
		{
			iterationcountForOldMappings = 0;
		}

		switch (clr)
		{
			case "black & white":
				color = Color.rgb(255, 255, 255);
				break;
			case "gray":
				color = Color.rgb(iterationcountForOldMappings, iterationcountForOldMappings, iterationcountForOldMappings);
				break;
			case "red":
				color = Color.rgb(iterationcountForOldMappings, 0, 0);
				break;
			case "blue":
				color = Color.rgb(0, 0, iterationcountForOldMappings);
				break;
			case "green":
				color = Color.rgb(0, iterationcountForOldMappings, 0);
				break;

			case "crazy":
				int crazycount = (int) (Math.random() * 100);

				switch (crazycount % 3)
				{
					case 0:
						color = Color.rgb(iterationcountForOldMappings, 0, 0);
						break;
					case 1:
						color = Color.rgb(0, iterationcountForOldMappings, 0);
						break;
					case 2:
						color = Color.rgb(0, 0, iterationcountForOldMappings);
						break;
				}
				break;
			case "mandala":
				double x = zn.getReal();
				double y = zn.getImaginary();
				double testValue = (Math.log(Math.sqrt(x * x + y * y))) / Math.pow(2, iterationcountForOldMappings) * Math.pow(x * x, Math.abs(y)) + 1;
				color = Color.rgb(255, 255, 255); // TODO: remove this
				color = Color.rgb(0, 0, (int) (255 / testValue));
				break;
			case "crane":
				double smooth = iterationcount + 1 - Math.log(Math.log(zn.getAbsoluteValue())) / Math.log(2);
				// Yayyyy
				color = Color.hsb(0.95f + 10 * smooth, 0.6f, 1.0f);
				break;

			case "blue-orange":

				int n = iterationcount % blueorangeMap.length;
				color = blueorangeMap[n];

				break;
			case "test":

				int k = iterationcount % testMap.length;
				color = testMap[k];

				break;
			case "custom":

				int i = iterationcount % customMap.length;
				color = customMap[i];

				break;
			default:
				color = Color.rgb(255, 255, 255);
				break;
		}

		return color;

	}

	public Color calculateColorForKomplexNumbers(Complexnumber z, Complexnumber c, boolean mandel)
	{
		for (int i = 0; i <= maxIterations; i++)
		{
			z = z.square().addition(c);

			if (z.getAbsoluteValue() > 2)
			{
				if (mandel)
				{
					return getColorFromIterations(i, gui.getMandelColor(), z);
				}
				else
				{
					return getColorFromIterations(i, gui.getJuliaColor(), z);
				}
			}
		}

		return Color.BLACK;
	}

	protected void saveImage(BufferedImage imageToSave, String name)
	{

		String path = new DirectoryChooser().showDialog(null).toString();

		int i = 1;
		while (new File(path + name + "(" + i + ")" + ".jpg").exists())
		{
			i++;
		}

		File image = new File(path + name + "(" + i + ")" + ".jpg");

		try
		{
			// String fileName =
			ImageIO.write(imageToSave, "jpg", image);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
