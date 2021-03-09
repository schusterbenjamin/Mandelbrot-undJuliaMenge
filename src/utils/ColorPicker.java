package utils;

import com.sun.javafx.scene.control.CustomColorDialog;

import application.GUI;
import javafx.stage.Window;

@SuppressWarnings("restriction")
public class ColorPicker extends CustomColorDialog
{


	public ColorPicker(Window win, GUI gui)
	{
		super(win);
		show();
		
		setOnSave(new Runnable() {

			@Override
			public void run()
			{
					gui.addColorToCustomColorList(getCustomColor(), false);
					new ColorPicker(win, gui);
			}
			
		});
		
		setOnUse(new Runnable() {

			@Override
			public void run() {
				gui.addColorToCustomColorList(getCustomColor(), true);
			}
			
		});
		
	}
}
