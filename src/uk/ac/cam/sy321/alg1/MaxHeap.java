package uk.ac.cam.sy321.alg1;

public class MaxHeap {
	private char heapName;
	private char[] heapData;
	private int length;

	public MaxHeap(char name)
	{
		this(name,"");
	}

	public MaxHeap(char name, String str)
	{
		heapData = new char[31];
		length=0;
		for (char i:str.toCharArray())
		{
			insert(i);
		}
	}

	public char getHeapName() {
		return heapName; //Otherwise it has literally *no* use from this perspective
	}

	public void insert(char x)
	{
		if (length==heapData.length) increaseLength();
		//Append the character
		heapData[length]=x;
		length++;
		int charlocation = length-1; //Initial location of x
		while (heapData[charlocation]>heapData[(charlocation-1)/2])
		{//While it doesn't satisfy max heap
			swap((charlocation-1)/2,charlocation);
			charlocation = (charlocation-1)/2; //Character moves to parents place
			if (charlocation == 0) return; //If it is at the root, stop swapping
		}
		return;
	}

	public void increaseLength()
	{
		char[] heap2 = new char[2*heapData.length-1];
		for (int i = 0; i<heapData.length; i++)
		{
			heap2[i]=heapData[i];
		}
		heapData=heap2;
	}

	public char getMax(){
		if (length==0) return '_';
		swap(0,length-1);
		length--;
		heapDown(heapData, length-1);
		return heapData[length];
	}

	private void heapDown(char[] str, int end)
	{
		int parent=0;
		while (parent<end){
			int leftChild=2*parent+1;
			int rightChild=leftChild+1;
			if (leftChild==end){ //Only Left Child exists
				if (heapData[parent] < heapData[leftChild])
				{
					swap(parent,leftChild);
					parent=leftChild;
				}
				else{break;}
			}
			else if (rightChild<=end){ //Both children exist
				if (heapData[parent] < heapData[leftChild] 
						&& heapData[rightChild] <= heapData[leftChild])
				{
					swap(parent,leftChild);
					parent=leftChild;
				}
				else if(heapData[parent] < heapData[rightChild] 
						&& heapData[leftChild] <= heapData[rightChild])
				{
					swap(parent,rightChild);
					parent=rightChild;
				}
				else{break;}
			}
			else {break;} //Boundary case e.g. 0 case
		}
	}

	private void swap(int ind1, int ind2)
	{
		char tmp = heapData[ind1];
		heapData[ind1]=heapData[ind2];
		heapData[ind2]=tmp;
	}

	public static void main(String[] args){
		char c;
		MaxHeap h = new MaxHeap('h', "CAMBRIDGEALGORITHMS");
		c = h.getMax();
		System.out.println(c); // expect T
		h.insert('Z');
		h.insert('A');
		c = h.getMax();
		System.out.println(c); // expect Z
		c = h.getMax();
		System.out.println(c); // expect S
	}
}
