package com.akash.java.array;

public class MyArray {

	public static void main(String[] args) {
		Abc arr[] = new Abc[4];
		Abc a1=new Abc();
		a1.setX(2);
		arr[0] = a1;
		arr[1] = a1;
		arr[2] = a1;
		arr[3] = a1;
		Abc arr1[]=arr.clone();
		arr1[0].setX(1);
		arr[0].getX();
	}
}

class Abc {
	private int x;

	Abc() {
		System.out.println("Abc.Abc()");
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
}