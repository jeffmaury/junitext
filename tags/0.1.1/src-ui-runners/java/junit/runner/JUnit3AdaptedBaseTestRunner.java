package junit.runner;

public abstract class JUnit3AdaptedBaseTestRunner extends BaseTestRunner {

	/**
	 * Returns the loader to be used.
	 */
	public TestSuiteLoader getLoader() {
		if (useReloadingTestSuiteLoader())
			return new ReloadingTestSuiteLoader();
		return new StandardTestSuiteLoader();
	}

	public static boolean inMac() {
		return System.getProperty("mrj.version") != null;
	}

 	public static boolean inVAJava() {
		try {
			Class.forName("com.ibm.uvm.tools.DebugSupport");
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

}
