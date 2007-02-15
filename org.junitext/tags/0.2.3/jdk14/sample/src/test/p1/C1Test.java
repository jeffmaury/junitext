package p1;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class C1Test {

	private C1 c1;

	@Before
	public void init() {
		c1 = new C1();
	}

	@After
	public void destroy() {
		c1 = null;
	}

	@Test
	public void x() {
		c1.setX("ABC");
		assertEquals("ABC", c1.getX());
	}

}
