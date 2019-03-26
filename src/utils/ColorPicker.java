package utils;

import com.sun.javafx.scene.control.skin.CustomColorDialog;

import application.GUI;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class ColorPicker extends CustomColorDialog
{


	public ColorPicker(Window win, GUI gui, boolean last)
	{
		super(win);
		show();
		
		setOnSave(new Runnable() {

			@Override
			public void run()
			{
					gui.addColorToCustomColorList(getCustomColor(), last);
			}
			
		});
		
	}
	
	public Color getCurrentColor() {
		return getCustomColor();
	}



}
