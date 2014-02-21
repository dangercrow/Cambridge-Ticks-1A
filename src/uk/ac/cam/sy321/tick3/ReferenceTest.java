package uk.ac.cam.sy321.tick3;

public class ReferenceTest {
	public static void main(String[] args) {

		//create a 2-by-2 array with all values initialised to zero
		int[][] i = new int[2][2]; 
		//2D array of values with each 1D value of a different length
		int [][] j = {i[1],{1,2,3},{4,5,6,7}}; 
		//create a 3D array using two 2D array references
		int [][][] k = {i,j};
		System.out.println(k[0][1][0]++); //Expect i[1][0] ie 0, and i[1][0] set to 1
		System.out.println(++k[1][0][0]); //Expect 1+i[1][0] ie 2, and i[1][0] set to 2
		System.out.println(i[1][0]);      //Expect i[1][0] ie 2
		System.out.println(--j[0][0]);    //Expect -1+i[1][0] ie 1, and i[1][0] set to 1
	}
}

