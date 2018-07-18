package filesprocessing.filter.properties_filter;
import filesprocessing.filter.*;

import java.util.ArrayList;

/**
 * this class represents the properties filters.
 * such as: Writable,  Hidden and Executable.
 * @author Roy Ackerman
 */

public abstract class PropertiesFilter implements Filter{

	public final static String YES_VALUE = "YES";
	public final static String NO_VALUE = "NO";

	protected String myArgument;
	protected boolean isNoSuffix;

	//..............constructor..........//
	public PropertiesFilter(String argument, boolean isNoSuffix){
		this.myArgument = argument;
		this.isNoSuffix = isNoSuffix;
	}

	/**
	 * @param listOfFiles list of files to be filtered
	 * @return the filtered files after the filter activity defined by isPassedFilter
	 */
	public java.io.File[] getFilteredFiles(java.io.File[] listOfFiles) {
		ArrayList<java.io.File> filteredFiles = new ArrayList<java.io.File>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				boolean isPassedFilter = isPassedFilter(listOfFiles[i]);

				if (!isNoSuffix) {
					if (isPassedFilter) {
						filteredFiles.add(listOfFiles[i]);
					}
				} else {
					if (!isPassedFilter) {
						filteredFiles.add(listOfFiles[i]);
					}
				}
			}
		}
		return filteredFiles.toArray(new java.io.File[filteredFiles.size()]);
	}

	/**
	 * determine weather file passed the specific filter, depends on each filter
	 * @param file as the file to be filtered
	 * @return true if passed, otherwise false
	 */
	public abstract boolean isPassedFilter(java.io.File file);

}
