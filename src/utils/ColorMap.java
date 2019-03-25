package utils;

import javafx.scene.paint.Color;
import java.util.ArrayList;

public class ColorMap
{
	int colorMapLength = 90;
	Color[] colorMap = new Color[colorMapLength];

	public ColorMap(ArrayList<Color> colorList)
	{

		if (colorList.size() > colorMapLength)
		{
			System.out.println("colorList shouldnt be larger than: " + colorMapLength);
			for (int i = colorMapLength; i < colorList.size(); i++)
			{
				colorList.remove(i);
			}
		}

		int[] r = new int[colorMapLength];
		int[] g = new int[colorMapLength];
		int[] b = new int[colorMapLength];

		int[] givenRedValues = getSingleValueListFromColorList(colorList, 1);
		int[] givenGreenValues = getSingleValueListFromColorList(colorList, 2);
		int[] givenBlueValues = getSingleValueListFromColorList(colorList, 3);

		map(givenRedValues, r);
		map(givenGreenValues, g);
		map(givenBlueValues, b);

		for (int k = 0; k < colorMapLength; k++)
		{
			colorMap[k] = Color.rgb(r[k], g[k], b[k]);
		}

	}

	private void map(int[] givenValues, int[] toMap)
	{
		int numberOfSteps = givenValues.length - 1;
		int numberOfIndexesPerStep = toMap.length / numberOfSteps;

		for (int i = 0; i < numberOfSteps; i++)
		{
			toMap[i * numberOfIndexesPerStep] = givenValues[i];

			int difference = givenValues[i + 1] - givenValues[i];
			int differencePerIndex = difference / numberOfIndexesPerStep;
//			System.out.println(differencePerIndex);

			for (int k = i * numberOfIndexesPerStep + 1; k < (i + 1) * numberOfIndexesPerStep; k++)
			{
				toMap[k] = toMap[k - 1] + differencePerIndex;
//				System.out.println(toMap[k]);
			}

		}

	}

	private int[] getSingleValueListFromColorList(ArrayList<Color> colorList, int whichColor)
	{
		int[] returnArray = new int[colorList.size()];

		for (int k = 0; k < returnArray.length; k++)
		{
			switch (whichColor)
			{
				case 1:
					returnArray[k] = (int) (colorList.get(k).getRed() * 255);
					break;
				case 2:
					returnArray[k] = (int) (colorList.get(k).getGreen() * 255);
					break;
				case 3:
					returnArray[k] = (int) (colorList.get(k).getBlue() * 255);
					break;
			}
		}

		return returnArray;
	}

	public Color[] getColorMap()
	{
		return colorMap;
	}

}
