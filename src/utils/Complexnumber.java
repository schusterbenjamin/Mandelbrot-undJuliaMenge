package utils;

public class Complexnumber
{

	double real, imaginary;

	public Complexnumber(double r, double i)
	{
		real = r;
		imaginary = i;
	}

	public Complexnumber addition(Complexnumber a)
	{
		double r = real + a.getReal();
		double i = imaginary + a.getImaginary();
		
		return new Complexnumber(r, i);
	}

	public Complexnumber multiplication(Complexnumber a)
	{
		double r  = (real * a.getReal()) - (imaginary * a.getImaginary());
		double i = (real * a.getImaginary()) + (imaginary * a.getReal());

		return new Complexnumber(r, i);
	}

	public Complexnumber square()
	{
		double r = real * real - imaginary * imaginary;
		double i = 2 * real * imaginary;
		
		return new Complexnumber(r, i);
	}

	public void print()
	{
		System.out.println(real + " " + imaginary);
	}

	public double getAbsoluteValue()
	{
		return Math.sqrt(real * real + imaginary * imaginary);

	}

	public double getReal()
	{
		return real;
	}

	public double getImaginary()
	{
		return imaginary;
	}

}
