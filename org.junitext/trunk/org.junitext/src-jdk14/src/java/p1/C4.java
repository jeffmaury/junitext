package p1;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class C4 {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface MyAnnotation {
		String value();
	}

	@MyAnnotation(value = "Hello World")
	public static void m() throws Exception {
		Method m = C4.class.getMethod("m", new Class[] {});
		Annotation[] annos = m.getAnnotations();
		for (Annotation a : annos) {
			System.out.print(a.toString());
		}
		System.out.print("m");
	}

	public static void main(String... args) throws Exception {
		System.out.print("C4: ");
		m();
		System.out.println();
	}
}