package uk.ac.cam.sy321.tick7;

public class PackedLong {

	/*
	 * Unpack and return the nth bit from the packed number at index position;
	 * position counts from zero (representing the least significant bit)
	 * up to 63 (representing the most significant bit).
	 */
	public static boolean get(long packed, int position) {
		// set "check" to equal 1 if the "position" bit in "packed" is set to 1
		long check = (packed >> position) & 1L;
		return (check == 1L);
	}
	/*
	 * Set the nth bit in the packed number to the value given
	 * and return the new packed number
	 */
	public static long set(long packed, int position, boolean value) {
		if (value) {
			packed = packed | (1L<<position);
		}
		else {
			packed = (packed >> position & 1L)<<position ^ packed;
			//Get bit and xor with self, 0 xor b preserves b
		}
		return packed;
	}
}