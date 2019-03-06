package application;

public class KomplexeZahl
{

	double real, imaginary;

	public KomplexeZahl(double r, double i)
	{
		real = r;
		imaginary = i;
	}

	public KomplexeZahl addition(KomplexeZahl a)
	{
		double r = real + a.getReal();
		double i = imaginary + a.getImaginary();
		
		return new KomplexeZahl(r, i);
	}

	public KomplexeZahl multiplication(KomplexeZahl a)
	{
		double r  = (real * a.getReal()) - (imaginary * a.getImaginary());
		double i = (real * a.getImaginary()) + (imaginary * a.getReal());

		return new KomplexeZahl(r, i);
	}

	public KomplexeZahl square()
	{
		double r = real * real - imaginary * imaginary;
		double i = 2 * real * imaginary;
		
		return new KomplexeZahl(r, i);
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
