package uk.ac.cam.sy321.tick6;

public class PackedLong {

	/*
	 * Unpack and return the nth bit from the packed number at index position;
	 * position counts from zero (representing the least significant bit)
	 * up to 63 (representing the most significant bit).
	 */
	public static boolean get(long packed, int position) {
		// set "check" to equal 1 if the "position" bit in "packed" is set to 1
		long check = (packed >> position) & 1;
		return (check == 1);
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
			/* packed = packed & ((-2l)<<position);
			 * Didn't work, though complement should have 0 at position
			 * and 1s elsewhere, ergo ANDing preserves all values except at position
			 */
			packed = (packed >> position & 1)<<position ^ packed;
			// Fix: Get bit and xor with self, 0 xor b preserves b
		}
		return packed;
	}
}