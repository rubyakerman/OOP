package filesprocessing;
import filesprocessing.filter.*;
import filesprocessing.order.*;
/**
 * this class represents a sub-section of the command file, each one consists of a specific
 * type of Filter, Order and its own warnings.
 * @author Roy Ackerman
 */

public class SubSection {

	private Filter filter;
	private Order order;
	private String mySourceDir;
	private Integer[] myWarnings;

	private static final Integer[] NO_WARNINGS = null;

	//...............constructors.................//

	/**
	 * build new sub-section
	 * @param myFilter the filter of this subsection
	 * @param myOrder the order of this subsection
	 * @param warnings is the warnings of this sub section (lines's number)
	 * @param sourceDir is the source's files directory
	 */
	public SubSection (Filter myFilter, Order myOrder, Integer[] warnings, String sourceDir){
		this.filter = myFilter;
		this.order = myOrder;
		this.mySourceDir = sourceDir;
		this.myWarnings = warnings;
	}

	/**
	 * @return the file of this sub section (filtered & ordered as required)
	 */
	public String[] getSubSectionFiles(){
		java.io.File folder = new java.io.File(this.mySourceDir);
		java.io.File[] listOfFiles = folder.listFiles();

		java.io.File[] filteredFiles = this.filter.getFilteredFiles(listOfFiles);
		java.io.File[] orderedFiles = this.order.getOrderedFiles(filteredFiles);

		//create array of the fileNames
		String[] names = new String[orderedFiles.length];
		for (int i = 0; i < orderedFiles.length; i++)
			names[i] = orderedFiles[i].getName();

		return names;
	}

	/**
	 * printing the warnings for this section
	 */
	public void printWarnings(){
		final String warningMsg = "Warning in line ";

		if (this.myWarnings != SubSection.NO_WARNINGS) {
			for (int i=0; i<this.myWarnings.length; i++)
				if (this.myWarnings[i] != null)
					System.err.println(warningMsg + this.myWarnings[i]);
		}
	}

}
