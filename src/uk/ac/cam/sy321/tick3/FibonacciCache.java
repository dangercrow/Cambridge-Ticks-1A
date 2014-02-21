package uk.ac.cam.sy321.tick3;

public class FibonacciCache {
	public static long[] fib = new long[20];
	public static void store() {
		//stores Fibonacci values in fib, starting 1,1,2
		for (int i=0; i<fib.length; i++){
			fib[i] = (i<2) ? 1L : fib[i-1] + fib[i-2];
		}
	}
	public static void reset() {
		//Set fib to all 0s
		for (int i=0; i<fib.length; i++) fib[i] = 0L;
	}

	public static long get(int i) {
		//returns ith element of fib, else -1L if out of bounds
		return (0<=i && i<fib.length) ? fib[i] : -1L;
	}
	/*//Doesn't have spacing but prints expected values
	 public static void main(String[] args){
		 for (long L : fib) System.out.print(L);
		 System.out.println();
		 store();
		 for (long L : fib) System.out.print(L);
		 System.out.println();
		 int[] values = {0,1,2,3,4,5,100,-3};
		 for (int i : values) System.out.print(get(i));
		 System.out.println();
		 reset();
		 for (long L : fib) System.out.print(L);
		 System.out.println();
	 }
	 */
}
