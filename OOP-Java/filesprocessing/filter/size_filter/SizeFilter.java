package filesprocessing.filter.size_filter;

import filesprocessing.filter.*;
import java.util.ArrayList;

/**
 * this class represents the size filters.
 * such as: greater_than,  smaller_than and between.
 * @author Roy Ackerman
 */

public abstract class SizeFilter implements Filter {

	protected Double myArgument;
	protected boolean isNoSuffix;
	protected String mySourceDir;

	//..............constructors..........//
	public SizeFilter(boolean isNoSuffix, String sourceDir){
		this.isNoSuffix = isNoSuffix;
		this.mySourceDir = sourceDir;
	}

	public SizeFilter(Double argument, boolean isNoSuffix, String sourceDir) {
		this.myArgument = argument;
		this.isNoSuffix = isNoSuffix;
		this.mySourceDir = sourceDir;
	}

	/**
	 * @param listOfFiles list of files to be filtered
	 * @return the filtered files after the filter activity defined by isPassedFilter
	 */
	public java.io.File[] getFilteredFiles(java.io.File[] listOfFiles) {
		final int KB_TO_BYTE = 1024;
		ArrayList<java.io.File> filteredFiles = new ArrayList<java.io.File>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				double fileSizeInKilobytes = listOfFiles[i].length() / KB_TO_BYTE;
				boolean isPassedFilter = isPassedFilter(fileSizeInKilobytes);

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
	 * @param fileSizeInKilobytes as the file size in KB.
	 * @return true if passed, otherwise false
	 */
	public abstract boolean isPassedFilter(double fileSizeInKilobytes);
}