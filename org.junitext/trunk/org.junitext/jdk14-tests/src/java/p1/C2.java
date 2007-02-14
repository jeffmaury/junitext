package p1;

import java.util.ArrayList;
import java.util.Collection;

public class C2 {

	public static void m(Collection<String> strings) {
		for (String s : strings) {
			System.out.print(s);
			System.out.print(" ");
		}
	}

	public static void main(String... args) {
		Collection<String> words = new ArrayList<String>();
		System.out.print("C2: ");
		words.add("Hello");
		words.add("World");
		m(words);
		System.out.println();
	}
}