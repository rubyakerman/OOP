package filesprocessing.filter;
/**Filter interface represents a filter of files
 * we can filter them by a specific filter & checks what files passed the filter
 * @author Roy Ackerman
 */

public interface Filter{

	/**
	 * @param listOfFiles list of files to be filtered
	 * @return the filtered files after the filter activity of a specific filter
	 */
	java.io.File[] getFilteredFiles(java.io.File[] listOfFiles);
}
