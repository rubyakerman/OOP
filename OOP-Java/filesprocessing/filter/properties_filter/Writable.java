package filesprocessing.filter.properties_filter;
import filesprocessing.filter.*;
/**
 * this class is a Filter class that filters files has writable permissions.
 * @author Roy Ackerman
 */

public class Writable extends PropertiesFilter implements Filter{

	//..............constructor..........//
	public Writable(String argument, boolean isNoSuffix){
		super(argument, isNoSuffix);
	}

	/**
	 * determine weather file has writable permissions
	 * @param file as the file to be filtered
	 * @return true if passed, otherwise false
	 */
	public boolean isPassedFilter(java.io.File file){
		if (this.myArgument.equals(Writable.YES_VALUE))
			return file.canWrite();
		else//myArgument is NO so:
			return !file.canWrite();

	}
}
