package application;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import utils.ColorMap;
import utils.KomplexeZahl;

abstract class Menge {

	GUI gui;

	double xSetOff, ySetOff;
	double moveSpeed;
	double zoom;
	double zoomChangeFactor;
	int maxIterations;
  
	Color[] blueorangeMap;
	Color[] testMap;
	static Color[] customMap;

	int zoomSetOffChangeDivisor;


	public Menge(GUI g) {
		gui = g;

		xSetOff = 0;
		ySetOff = 0;
		moveSpeed = 0.05;
		zoom = 1;
		zoomChangeFactor = 2;
		maxIterations = 300;

		
		createMappings();
	}

	private void createMappings()
	{
		ArrayList<Color> colorMapList = new ArrayList<Color>();
		
		colorMapList.add(Color.rgb(30, 6, 40));
//		colorMapList.add(Color.rgb(66, 30, 15));
		colorMapList.add(Color.rgb(25, 7, 26));
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
	    colorMapList.add(Color.rgb(55,55,55));
	    colorMapList.add(Color.rgb(100,100,100));
//	    colorMapList.add(Color.rgb(10,10,10));
	    colorMapList.add(Color.rgb(255, 255, 255));
	    
	    testMap = new ColorMap(colorMapList).getColorMap();
	    
	    customMap = testMap;
	}
	
	public static void setCustomMap(ArrayList<Color> customColorList) {
		customMap = new ColorMap(customColorList).getColorMap();
	}

	static int imageWidth = 300, imageHeight = 300;

	public double getxSetOff() {
		return xSetOff;
	}

	public void setxSetOff(double xSetOff) {
		this.xSetOff = xSetOff;
	}

	public double getySetOff() {
		return ySetOff;
	}

	public void setySetOff(double ySetOff) {
		this.ySetOff = ySetOff;
	}

	public void moveXSetOffLeft() {

		xSetOff -= moveSpeed * zoom;

	}

	public void moveXSetOffRight() {

		xSetOff += moveSpeed * zoom;

	}

	public void moveYSetOffUp() {
		
		ySetOff -= moveSpeed * zoom;

	}

	public void moveYSetOffDown() {
		
		ySetOff += moveSpeed * zoom;

	}

	public void moveZoom(double deltaY) {
		if (deltaY > 0) {
			zoom /= zoomChangeFactor;
		} else {
			zoom *= zoomChangeFactor;
		}
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public double getZoom() {
		return zoom;
	}

	public void setMaxIterations(int max) {
		maxIterations = max;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public static int getImageWidth() {
		return imageWidth;
	}

	public static void setImageWidth(int w) {
		imageWidth = w;
	}

	public static int getImageHeight() {
		return imageHeight;
	}

	public static void setImageHeight(int h) {
		imageHeight = h;
	}

	public static void setImageSize(int w, int h) {
		imageWidth = w;
		imageHeight = h;
	}

	public void zoomToMouse(Point mouseToNode, double deltaY) {

		zoomSetOffChangeDivisor =  imageWidth / 60;
		
		int xMouseSetOff = (int) (mouseToNode.getX() / zoomSetOffChangeDivisor);
		int yMouseSetOff = (int) (mouseToNode.getY() / zoomSetOffChangeDivisor);

		if (deltaY > 0) {
			zoom /= zoomChangeFactor;

		} else {
			xMouseSetOff *= -1;
			yMouseSetOff *= -1;
		}

		for (int i = 0; i < Math.abs(xMouseSetOff); i++) {
			if (xMouseSetOff > 0) {
				moveXSetOffRight();
			} else {
				moveXSetOffLeft();
			}
		}

		for (int i = 0; i < Math.abs(yMouseSetOff); i++) {
			if (yMouseSetOff > 0) {
				moveYSetOffUp();
			} else {
				moveYSetOffDown();
			}
		}

		if (deltaY < 0) {
			zoom *= zoomChangeFactor;
		}

	}

	protected Color getColorFromIterations(int iterationcount, String clr, Point zn) {

		Color color = null;

		int iterationcountForOldMappings = iterationcount * iterationcount * iterationcount / 5;
		if (iterationcountForOldMappings > 255) {
			iterationcountForOldMappings = 255;
		}
		if (iterationcountForOldMappings < 0) {
			iterationcountForOldMappings = 0;
		}

		switch (clr) {
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

				if (crazycount % 3 == 0)
				{
					color = Color.rgb(iterationcountForOldMappings, 0, 0);
				}
				if (crazycount % 3 == 1)
				{
					color = Color.rgb(0, iterationcountForOldMappings, 0);
				}
				if (crazycount % 3 == 2)
				{
					color = Color.rgb(0, 0, iterationcountForOldMappings);
				}
				break;
			case "mandala":
				double x = zn.getX();
				double y = zn.getY();
				double testValue = (Math.log(Math.sqrt(x * x + y * y))) / Math.pow(2, iterationcountForOldMappings) * Math.pow(x * x, Math.abs(y)) + 1;
				color = Color.rgb(255, 255, 255); // TODO: remove this
				color = Color.rgb(0, 0, (int) (255 / testValue));
				break;
			case "crane":

				KomplexeZahl test = new KomplexeZahl(zn.getX(), zn.getY());

				double smooth = iterationcount + 1 - Math.log(Math.log(test.getAbsoluteValue()))/Math.log(2);
				//Yayyyy
				color = Color.hsb(0.95f + 10 * smooth ,0.6f,1.0f);
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

	public Color calculateColorForKomplexNumbers(KomplexeZahl z, KomplexeZahl c, boolean mandel) {
		for (int i = 0; i <= maxIterations; i++) {
			z = z.square().addition(c);


			if (z.getAbsoluteValue() > 2) {
				// System.out.println(z.getAbsoluteValue() - maxIterations);
				// System.out.println("divergiert");
				Point zn = new Point();
				zn.setLocation(z.getReal(), z.getImaginary());
				if (mandel) {
					return getColorFromIterations(i, gui.getMandelColor(), zn);
				} else {
					return getColorFromIterations(i, gui.getJuliaColor(), zn);
				}
			}
		}

		return Color.BLACK;
	}

	protected void saveImage(BufferedImage imageToSave, String name) {

		String pfad = new DirectoryChooser().showDialog(null).toString();
		File Mandelbrot = new File(pfad + name);

		try {
			ImageIO.write(imageToSave, "jpg", Mandelbrot);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
