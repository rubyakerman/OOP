package filesprocessing.filter;

import filesprocessing.filter.properties_filter.*;
import filesprocessing.filter.size_filter.*;
import filesprocessing.filter.values_filter.*;

import java.util.ArrayList;

/** this class is the factory creates filters by type & parameters we got as strings
 * it checks weather the filter's name\parameters are as requested,
 * otherwise throw new FilterException
 * @author Roy Ackerman
 */

public class FilterFactory{
	private final static String GREATER_THAN = "greater_than";
	private final static String BETWEEN = "between";
	private final static String SMALLER_THAN = "smaller_than";
	private final static String FILE = "file";
	private final static String CONTAINS = "contains";
	private final static String PREFIX = "prefix";
	private final static String SUFFIX = "suffix";
	private final static String WRITABLE = "writable";
	private final static String EXECUTABLE = "executable";
	private final static String HIDDEN = "hidden";
	private final static String ALL = "all";

	private final static String NOT_SUFFIX = "#NOT";
	private final static String SEPARATOR_STRING = "#";

	private final static int FIRST_ARGUMENT = 1;
	private final static int SECOND_ARGUMENT = 2;

	private final static int FIRST_ARGUMENT_POSITION = 0;
	private final static int SECOND_ARGUMENT_POSITION = 1;

	private final static int ONE_DOUBLE = 1;
	private final static int TWO_DOUBLES = 2;
	private final static int YES_OR_NO = 3;
	private final static int STRING = 4;

	private final static int MAX_NUM_OF_ARGUMENTS = 2;

	//to contain the arguments for each filter
	private static Object[] arguments = new Object[MAX_NUM_OF_ARGUMENTS];

	/**
	 * the method create filter by string & source directory
	 * @param filterString the string represents the filter
	 * @param sourceDir the files directory
	 * @return the filter created
	 * @throws FilterException in case of error (type 1)
	 */
	public static Filter createFilters(String filterString, String sourceDir) throws FilterException{

		Filter myFilter;
			boolean isNoSuffix = checkNoSuffix(filterString);

			//remove period filter's arguments
			for (int j = 0; j<FilterFactory.arguments.length; j++)
				FilterFactory.arguments[j] = null;

			String filterName = getFilterName(filterString);
			if (filterName.equals(FilterFactory.GREATER_THAN)) {
				checkArguments(filterString, FilterFactory.ONE_DOUBLE);
				myFilter = new GreaterThan((Double)arguments
									[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix, sourceDir);

			}else if (filterName.equals(FilterFactory.BETWEEN)) {
				checkArguments(filterString, FilterFactory.TWO_DOUBLES);
				myFilter = new Between((Double) arguments[FilterFactory.FIRST_ARGUMENT_POSITION]
						, (Double)arguments[FilterFactory.SECOND_ARGUMENT_POSITION],isNoSuffix,sourceDir);

			}else if (filterName.equals(FilterFactory.SMALLER_THAN)) {
				checkArguments(filterString, FilterFactory.ONE_DOUBLE);
				myFilter = new SmallerThan((Double)arguments
									[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix, sourceDir);

			}else if (filterName.equals(FilterFactory.FILE)) {
				checkArguments(filterString, FilterFactory.STRING);
				myFilter = new File((String) arguments
								[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix, sourceDir);

			}else if (filterName.equals(FilterFactory.CONTAINS)) {
				checkArguments(filterString, FilterFactory.STRING);
				myFilter = new Contains((String) arguments
								[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix, sourceDir);

			}else if (filterName.equals(FilterFactory.PREFIX)) {
				checkArguments(filterString, FilterFactory.STRING);
				myFilter = new Prefix((String) arguments
								[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix, sourceDir);

			}else if (filterName.equals(FilterFactory.SUFFIX)) {
				checkArguments(filterString, FilterFactory.STRING);
				myFilter = new Suffix((String) arguments
								[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix, sourceDir);

			}else if (filterName.equals(FilterFactory.WRITABLE)) {
				checkArguments(filterString, FilterFactory.YES_OR_NO);
				myFilter = new Writable((String) arguments
								[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix);

			}else if (filterName.equals(FilterFactory.EXECUTABLE)) {
				checkArguments(filterString, FilterFactory.YES_OR_NO);
				myFilter = new Executable((String) arguments
								[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix);

			}else if (filterName.equals(FilterFactory.HIDDEN)) {
				checkArguments(filterString, FilterFactory.YES_OR_NO);
				myFilter = new Hidden((String) arguments
								[FilterFactory.FIRST_ARGUMENT_POSITION], isNoSuffix);

			}else if (filterName.equals(FilterFactory.ALL)) {
				checkAllFilter(filterString);
				myFilter = new All();
			}else//this is not a valid filter
				throw new FilterException();

			return myFilter;
	}

	/**
	 * @param filterFullString as the full filter string
	 * @return the sub-string of the filter's name (from the full string)
	 */
	private static String getFilterName(String filterFullString){
		char[] theStringChars = filterFullString.toCharArray();
		final int FIRST_CHAR_PLACE = 0;
		int i = FIRST_CHAR_PLACE;

		//find the filter name
		ArrayList<String> myArgument2 = new ArrayList<String>();
		while ((i < theStringChars.length) &&
				!String.valueOf(theStringChars[i]).equals(FilterFactory.SEPARATOR_STRING)){
			myArgument2.add(String.valueOf(theStringChars[i]));
			i++;
		}

		String filterName = "";
		for (String s : myArgument2)
			filterName += s;

		return filterName;
	}

	/**
	 * checks weather the filter is in a form of #NO suffix
	 * @param filterString the filter's string
	 * @return true if is, otherwise false
	 */
	private static boolean checkNoSuffix(String filterString){
		if (filterString.endsWith(FilterFactory.NOT_SUFFIX))
			return true;
		return false;
	}

	/**
	 * checks weather this filter is All filter of not
	 * @param filterString the string represents this filter (in the command file)
	 * @throws FilterException in case its not
	 */
	private static void checkAllFilter(String filterString) throws FilterException{
		if (!filterString.equals(FilterFactory.ALL) && !filterString.equals
									(FilterFactory.ALL+FilterFactory.NOT_SUFFIX))
			throw new FilterException();
	}


	/**
	 * check & update the arguments inside the arguments array
	 * @param filterString the string of the filter
	 * @param kindOfChecking as the kind of the value for the specific filter
	 * can be ONE_DOUBLE/TWO_DOUBLES/YES_OR_NO
	 * @throws FilterException
	 */
	private static void checkArguments(String filterString, int kindOfChecking) throws FilterException{

		char[] theStringChars = filterString.toCharArray();
		Double arg1, arg2;

		switch (kindOfChecking){
			case FilterFactory.ONE_DOUBLE:
				arg1 = Double.parseDouble(getArgument(theStringChars, FilterFactory.FIRST_ARGUMENT));
				arguments[FilterFactory.FIRST_ARGUMENT_POSITION] = arg1;
				arguments[FilterFactory.SECOND_ARGUMENT_POSITION] = null;
				checkOneDoubleRange(arg1);
				break;
			case FilterFactory.TWO_DOUBLES:
				arg1 = Double.parseDouble(getArgument(theStringChars, FilterFactory.FIRST_ARGUMENT));
				arg2 = Double.parseDouble(getArgument(theStringChars, FilterFactory.SECOND_ARGUMENT));
				arguments[FilterFactory.FIRST_ARGUMENT_POSITION] = arg1;
				arguments[FilterFactory.SECOND_ARGUMENT_POSITION] = arg2;
				checkTwoDoubleRange(arg1, arg2);
				break;
			case FilterFactory.YES_OR_NO:
				String myArg = getArgument(theStringChars, FilterFactory.FIRST_ARGUMENT);
				arguments[FilterFactory.FIRST_ARGUMENT_POSITION] = getArgument
									(theStringChars, FilterFactory.FIRST_ARGUMENT);
				arguments[FilterFactory.SECOND_ARGUMENT_POSITION] = null;
				checkYesOrNo(myArg);
				break;
			case FilterFactory.STRING:
				arguments[FilterFactory.FIRST_ARGUMENT_POSITION] = getArgument
									(theStringChars, FilterFactory.FIRST_ARGUMENT);
				arguments[FilterFactory.SECOND_ARGUMENT_POSITION] = null;
				break;

		}

	}

	/**
	 * checks weather arg1 (as size_filter) is in the right range (positive)
	 * @param arg1 the argument of a specific size_filter
	 * @throws FilterException in case is not
	 */
	private static void checkOneDoubleRange(Double arg1) throws FilterException{
		boolean negative = arg1 < 0;
		if (negative)
			throw new FilterException();
	}

	/**
	 * checks weather arg1,arg2 (as size_filter) are in the right range (positive)
	 * & are well fitted to the requirements of between filter
	 * @param arg1 first argument of a specific size_filter
	 * @param arg2 second argument of a specific size_filter
	 * @throws FilterException in case is not
	 */
	private static void checkTwoDoubleRange(Double arg1, Double arg2) throws FilterException{
		checkOneDoubleRange(arg1);
		checkOneDoubleRange(arg2);

		boolean illegalBetweenValue = arg1 > arg2;
		if (illegalBetweenValue)
			throw new FilterException();
	}

	/**
	 * checks weather myArgument (as argument of properties_filter) is fill the
	 * requirements (for properties_filter)
	 * @param myArgument as the argument
	 * @throws FilterException in case is not
	 */
	private static void checkYesOrNo(String myArgument) throws FilterException{
		boolean isYesOrNo = myArgument.equals(PropertiesFilter.YES_VALUE) ||
										myArgument.equals(PropertiesFilter.NO_VALUE);
		if (!isYesOrNo)
			throw new FilterException();
	}

	/**
	 * return the argument number "argumentNumber" for the string
	 * represented by the theStringChars
	 * @param theStringChars represents the string by array of chars
	 * @param argumentNumber the argument number we want to get
	 * @return the argument as string starting from the i's index
	 */
	private static String getArgument(char[] theStringChars, int argumentNumber){
		final int FIRST_CHAR = 0;

		int i = FIRST_CHAR;
		//find the separator char of the specific argument we need by argumentNumber
		for (int numOfSkipping = 1; numOfSkipping <= argumentNumber; numOfSkipping++){
			i++;
			while (!String.valueOf(theStringChars[i]).equals(FilterFactory.SEPARATOR_STRING))
				i++;
		}

		//finding argument number "argumentNumber"
		ArrayList<String> myArgument2 = new ArrayList<String>();
		int j = i + 1; //start with the next one
		while ((j < theStringChars.length) && !(String.valueOf(theStringChars[j]).equals(FilterFactory.SEPARATOR_STRING))){
			myArgument2.add(String.valueOf(theStringChars[j]));
			j++;
		}

		String arg = "";
		for (String s : myArgument2)
			arg += s;

		return arg;
	}

}




