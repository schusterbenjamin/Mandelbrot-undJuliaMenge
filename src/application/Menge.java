package application;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;

abstract class Menge {

	double xSetOff = 0, ySetOff = 0;
	double moveSpeed = 0.05;
	double zoom = 1;
	double zoomChangeFactor = 2;
	int maxIterations = 1000;
	
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

	public void moveXSetOffUp() {

		ySetOff -= moveSpeed * zoom;

	}

	public void moveXSetOffDown() {

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
	
	public int getImageWidth() {
		return imageWidth;
	}
	
	public static void setImageWidth(int w) {
		imageWidth = w;
	}
	
	public static int getImageHeight() {
		return imageHeight;
	}
	
	public static  void setImageHeight(int h) {
		imageHeight = h;
	}
	
	public static void setImageSize(int w, int h) {
		imageWidth = w;
		imageHeight = h;
	}

	protected Color getColorFromIterations(int iterationcount, String clr, Point zn) {

		Color color = null;

		iterationcount = iterationcount * iterationcount * iterationcount / 5;
		if (iterationcount > 255) {
			iterationcount = 255;
		}
		if(iterationcount < 0) {
			iterationcount = 0;
		}

		switch (clr) {
		case "black & white":
			color = Color.rgb(255, 255, 255);
			break;
		case "gray":
			color = Color.rgb(iterationcount, iterationcount, iterationcount);
			break;
		case "red":
			color = Color.rgb(iterationcount, 0, 0);
			break;
		case "blue":
			color = Color.rgb(0, 0, iterationcount);
			break;
		case "green":
			color = Color.rgb(0, iterationcount, 0);
			break;
		case "crazy":
			int crazycount = (int) (Math.random() * 100);

			if (crazycount % 3 == 0) {
				color = Color.rgb(iterationcount, 0, 0);
			}
			if (crazycount % 3 == 1) {
				color = Color.rgb(0, iterationcount, 0);
			}
			if (crazycount % 3 == 2) {
				color = Color.rgb(0, 0, iterationcount);
			}
			break;
		case "test":
			double x = zn.getX();
			double y = zn.getY();
			double testValue = (Math.log(Math.sqrt(x * x + y * y))) / Math.pow(2, iterationcount)
					* Math.pow(x * x, Math.abs(y)) + 1;
			color = Color.rgb(255, 255, 255); // TODO: remove this
			color = Color.rgb(0, 0, (int) (255 / testValue));	
			break;
		default:
			color = Color.rgb(255, 255, 255);
			break;
		}

		return color;

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
