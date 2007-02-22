package p1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

public class C3 {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface MyAnnotation {
		String value();
	}

	@MyAnnotation(value = "Hello World")
	public static void m() {
		System.out.print("m");
	}

	public static void main(String... args) {
		Collection<String> words = new ArrayList<String>();
		System.out.print("C3: ");
		m();
		System.out.println();
	}

}