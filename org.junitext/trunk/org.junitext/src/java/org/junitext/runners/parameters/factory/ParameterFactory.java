package org.junitext.runners.parameters.factory;

import java.io.File;
import java.util.List;

/**
 * Interface for creating JavaBean test parameters from an XML File.  
 * <p>
 * Implementors of this interface can be passed as the bean factor to
 * the {@link org.junitext.runners.XMLBeanParameters} annotation.  Implementors
 * have the complete freedom of defining the XML format that is used.  This allows
 * for different XML-->Java object mappers to be used at the preference of the user.
 * </p>
 * <p>
 * <strong>NOTE:</strong>
 * It is possible to receive an invalid or <code>null</code> xml file.  It is the responibility
 * of implementors of the <code>ParameterFactory</code> to decide how to handle not found
 * or missing XML files.
 * </p>
 * @author Jim Hurne
 */
public interface ParameterFactory {
	
	/**
	 * Creates sets of test parameters from a given XML File.  In addition to the XML File
	 * <code>File</code> object, the test class is also passed.  This allows impelmentors
	 * to use properties of the test class to create the test parameters.
	 * <p>
	 * <strong>NOTE:</strong>
	 * It is possible to receive an invalid or <code>null</code> xmlFile.  It is the responibility
	 * of implementors of the <code>ParameterFactory</code> to decide how to handle not found
	 * or missing XML files.
	 * </p>
	 *
	 * @param testClass the test class 
	 * @param xmlFile the XML File to parse and generate paramters from
	 * @return a collection of sets of JavaBeans that are used as test parameters
	 * @throws Exception if there is a problem parsing the XML file
	 */
	public List<Object[]> createParameters(Class<?> testClass, File xmlFile) throws Exception;
}
