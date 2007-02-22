package p1;

import java.util.concurrent.TimeoutException;

public class C5 {

	public static void m() throws TimeoutException {
		throw new TimeoutException();
	}

	public static void main(String[] args) {
		System.out.print("C5: ");
		try {
			m();
		} catch (TimeoutException ex) {
			// fine
		}
		System.out.println();
	}
}