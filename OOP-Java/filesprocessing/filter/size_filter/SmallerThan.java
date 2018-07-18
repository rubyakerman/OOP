package filesprocessing.filter.size_filter;
import filesprocessing.filter.*;

/**
 * this class represents a smaller_than filter.
 * it filters file its size is smaller than argument we got from user
 * @author Roy Ackerman
 */

public class SmallerThan extends SizeFilter implements Filter{

	//..............constructor..........//
	public SmallerThan(Double argument, boolean isNoSuffix, String sourceDir){
		super(argument, isNoSuffix, sourceDir);
	}

	/**
	 * determine weather file passed the filter,
	 * means its size is smaller than myArgument
	 * @param fileSizeInKilobytes as the file size in KB.
	 * @return true if passed, otherwise false
	 */
	public boolean isPassedFilter(double fileSizeInKilobytes){
		return fileSizeInKilobytes < this.myArgument;
	}

}
