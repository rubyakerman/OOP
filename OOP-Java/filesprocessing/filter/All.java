package filesprocessing.filter;
/**
 * this class is type of filter, which is not filtering any file at all.
 * @author Roy Ackerman
 */

public class All implements Filter{
	public All(){}

	/**
	 * @param listOfFiles list of files to be filtered
	 * @return the filtered files after the filter all (return all of them)
	 */
	public java.io.File[] getFilteredFiles(java.io.File[] listOfFiles) {
		return listOfFiles;
	}

}
