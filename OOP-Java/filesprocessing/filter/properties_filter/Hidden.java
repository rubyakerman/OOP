package filesprocessing.filter.properties_filter;
import filesprocessing.filter.*;
/**
 *this class is a Filter class that filters hidden files.
 * @author Roy Ackerman
 */

public class Hidden extends PropertiesFilter implements Filter{

	//..............constructor..........//
	public Hidden(String argument, boolean isNoSuffix){
		super(argument, isNoSuffix);
	}

	/**
	 * determine weather file is hidden
	 * @param file as the file to be filtered
	 * @return true if passed, otherwise false
	 */
	public boolean isPassedFilter(java.io.File file){
		if (this.myArgument.equals(Writable.YES_VALUE))
			return file.isHidden();
		else//myArgument is NO so:
			return !file.isHidden();
	}
}