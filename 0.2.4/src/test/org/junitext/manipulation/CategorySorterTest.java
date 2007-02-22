/*******************************************************************************
 * Copyright (C) 2006-2007 Jochen Hiller and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License - v 1.0
 * which accompanies this distribution, and is available at
 * http://junitext.sourceforge.net/licenses/junitext-license.html
 * 
 * Contributors:
 *     Jochen Hiller - initial API and implementation
 ******************************************************************************/
package org.junitext.manipulation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.TextListener;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junitext.Category;

/**
 * Tests for the {@link CategorySorter} for JUnit.
 */
public class CategorySorterTest {

	public static class ACategoryTest {
		@Category ("Z") @Test public void c12() {}
		@Category ("Z") @Test public void c11() {}
		@Category ("A") @Test public void c22() {}
		@Category ("A") @Test public void c21() {}
	}

	@Test public void callWithSorter() throws InitializationError {
		JUnitCore core = new JUnitCore();
		final List<Description> descs = new ArrayList<Description>(); 
		core.addListener(new TextListener() {
			@Override
			public void testFinished(Description description) throws Exception {
				descs.add(description);
				//super.testFinished(description);
			}

			@Override
			protected void printFooter(Result result) {
			}

			@Override
			protected void printHeader(long runTime) {
			}

			@Override
			public void testIgnored(Description description) {
			}

			@Override
			public void testStarted(Description description) {
			}
			
		});
		Request req = Request.aClass(ACategoryTest.class);
		Result result = core.run(req.sortWith(new CategorySorter()));
		assertEquals(4, result.getRunCount()); 
		assertEquals(0, result.getFailureCount());
		assertEquals(0, result.getIgnoreCount());
		assertEquals ("c21("+ACategoryTest.class.getName()+")", descs.get(0).getDisplayName());
		assertEquals ("c22("+ACategoryTest.class.getName()+")", descs.get(1).getDisplayName());
		assertEquals ("c11("+ACategoryTest.class.getName()+")", descs.get(2).getDisplayName());
		assertEquals ("c12("+ACategoryTest.class.getName()+")", descs.get(3).getDisplayName());
	}

	public static void main(String... args) {
		JUnitCore.main(CategorySorterTest.class.getName());
	}
}
