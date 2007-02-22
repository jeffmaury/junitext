package org.junitext.internal.runners;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.internal.runners.TextListener;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junitext.Category;
import org.junitext.manipulation.CategoryResolver;

public class CategoryTextListener extends TextListener {

	// TODO irrg, ugly !
	private Map<Category, Map<String, Collection<Description>>> results = new HashMap<Category, Map<String, Collection<Description>>>();

	private PrintStream fWriter;

	public CategoryTextListener(PrintStream writer) {
		this.fWriter = writer;
	}

	@Override
	public void testStarted(Description description) {
		super.testStarted(description);
		Category c = CategoryResolver.getCategory(description);
		if (c != null) {
			forceInitialize(c);
			results.get(c).get("Success").add(description);
		}
	}
	
	@Override
	public void testFailure(Failure failure) {
		super.testFailure(failure);
		Category c = CategoryResolver.getCategory(failure.getDescription());
		if (c != null) {
			forceInitialize(c);
			results.get(c).get("Failure").add(failure.getDescription());
			results.get(c).get("Success").remove(failure.getDescription());
		}
	}

	@Override
	public void testIgnored(Description description) {
		super.testIgnored(description);
		Category c = CategoryResolver.getCategory(description);
		if (c != null) {
			forceInitialize(c);
			results.get(c).get("Ignore").add(description);
			results.get(c).get("Success").remove(description);
		}
	}

	@Override
	public void testRunFinished(Result result) {
		super.testRunFinished(result);
		for (Category each : results.keySet()) {
			fWriter.println("Category: " + each.value());
			for (Description eachDesc : results.get(each).get("Success")) {
				fWriter.println("  Success " + eachDesc.getDisplayName());
			}
			for (Description eachDesc : results.get(each).get("Ignore")) {
				fWriter.println("  Ignored " + eachDesc.getDisplayName());
			}
			for (Description eachDesc : results.get(each).get("Failure")) {
				fWriter.println("  Failure " + eachDesc.getDisplayName());
			}
		}
	}

	// internal methods

	private void forceInitialize(Category c) {
		if (results.get(c) == null) {
			results.put(c, new HashMap<String, Collection<Description>>());
			results.get(c).put("Ignore", new ArrayList<Description>());
			results.get(c).put("Failure", new ArrayList<Description>());
			results.get(c).put("Success", new ArrayList<Description>());
		}
	}
}
