package filesprocessing.filter.properties_filter;
import filesprocessing.filter.*;
/**
 * this class is a Filter class that filters files has executable permissions.
 * @author Roy Ackerman
 */

public class Executable extends PropertiesFilter implements Filter{

	//..............constructor..........//
	public Executable(String argument, boolean isNoSuffix){
		super(argument, isNoSuffix);
	}

	/**
	 * determine weather file has executable permissions
	 * @param file as the file to be filtered
	 * @return true if passed, otherwise false
	 */
	public boolean isPassedFilter(java.io.File file){
		if (this.myArgument.equals(Writable.YES_VALUE))
			return file.canExecute();
		else//myArgument is NO so:
			return !file.canExecute();
	}
}