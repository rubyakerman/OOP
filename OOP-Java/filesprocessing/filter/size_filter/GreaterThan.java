package filesprocessing.filter.size_filter;
import filesprocessing.filter.*;

/**
 * this class represents a greater_than filter.
 * it filters file its size is greater than argument we got from user
 * @author Roy Ackerman
 */

public class GreaterThan extends SizeFilter implements Filter{

	//..............constructor..........//
	public GreaterThan(Double argument, boolean isNoSuffix, String sourceDir){
		super(argument, isNoSuffix, sourceDir);
	}

	/**
	 * determine weather file passed the filter,
	 * means its size is greater than myArgument
	 * @param fileSizeInKilobytes as the file size in KB.
	 * @return true if passed, otherwise false
	 */
	public boolean isPassedFilter(double fileSizeInKilobytes){
		return fileSizeInKilobytes > this.myArgument;
	}


}
