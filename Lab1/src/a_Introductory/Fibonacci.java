package a_Introductory;

public class Fibonacci {

	// FIXED: method had unnecessary '+ 1' at the end of default return equation
	public int fib(int n) {
		switch (n) {
			case 0: return 0;
			case 1: return 1;
			default: return (fib(n - 1) + fib(n - 2));
		}
	}
}
