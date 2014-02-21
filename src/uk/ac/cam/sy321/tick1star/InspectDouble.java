package uk.ac.cam.sy321.tick1star;

public class InspectDouble {
	public static void main(String[] args) throws Exception {
		double d = Double.parseDouble(args[0]);
		// return the bits which represent the floating point number
		long bits = Double.doubleToLongBits(d);
		// Sign bit located in bit 63
		// Suggested Mask 0x8000000000000000L
		// Format 1 => number is negative
		long negmask = 0x8000000000000000L;
		boolean negative = ( negmask & bits) != 0;

		// Exponent located in bits 52 - 62
		// Suggested Mask 0x7ff0000000000000L
		// format Sum( 2^n * e(n)) - 1023 (binary number with bias)
		long expmask = 0x7ff0000000000000L;
		long exponent = ((expmask & bits) >> 52) - 1023;

		// Mantissa located in bits 0 - 51
		// Mask left as an exercise for the reader
		// format 1 + Sum(2^-(n+1) * m(n) )
		long manmask = 0x000fffffffffffffL;
		long mantissabits = manmask & bits;

		double mantissa = mantissaToDecimal(mantissabits);

		System.out.println((negative ? "-" : "") + mantissa + " x 2^" + exponent);
	}
	private static double mantissaToDecimal(long mantissabits) {
		long one = 0x0010000000000000L;
		return (double)(mantissabits + one) / (double)one;
	}
}
