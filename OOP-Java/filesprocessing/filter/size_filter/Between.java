package filesprocessing.filter.size_filter;
import filesprocessing.filter.*;

/**
 * this class represents a between filter.
 * it filtered file its size is between the argument we get from user
 * @author Roy Ackerman
 */

public class Between extends SizeFilter implements Filter{

	private Double myArgument1;
	private Double myArgument2;

	//..............constructor..........//
	public Between(Double argument1, Double argument2, boolean isNoSuffix, String sourceDir){
		super(isNoSuffix, sourceDir);
		this.myArgument1 = argument1;
		this.myArgument2 = argument2;
	}

	/**
	 * determine weather file passed the filter,
	 * means its size is between myArgument1 & myArgument2
	 * @param fileSizeInKilobytes as the file size in KB.
	 * @return true if passed, otherwise false
	 */
	public boolean isPassedFilter(double fileSizeInKilobytes) {
		boolean buggerThanArg1 = fileSizeInKilobytes >= this.myArgument1;
		boolean smallerThanArg2 = fileSizeInKilobytes <= this.myArgument2;

		return buggerThanArg1 && smallerThanArg2;
	}

}
