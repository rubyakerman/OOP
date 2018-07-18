package filesprocessing.command_file;

import filesprocessing.order.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**ParseCommandFile class represents a command file and its parse methods,
 * this way we would easily & modularly analyze the file for checking its structure & geting its
 * filters & orders commands.
 * @author Roy Ackerman
 */

public class ParseCommandFile{

	public final static String FILTER = "FILTER";
	public final static String ORDER = "ORDER";
	private final static String[] NO_STRINGS = null;

	private String commandFilePath;
	private ArrayList<String> filters;
	private ArrayList<String> orders;

	private String[] commandFileStrings;

	public ParseCommandFile(String commandFilePath){
		this.commandFilePath = commandFilePath;
		this.commandFileStrings = ParseCommandFile.NO_STRINGS;
		this.filters = new ArrayList<String>();
		this.orders = new ArrayList<String>();
	}

	/**
	 * @return the filters's strings as ArrayList
	 */
	public ArrayList<String> getFilters(){
		return this.filters;
	}

	/**
	 * @return the orders's strings as ArrayList
	 */
	public ArrayList<String> getOrders(){
		return this.orders;
	}

	/**
	 * checking weather currentLine line is equals to shouldEqualsTo, in order to check
	 * the FILTER/ORDER lines of the command file
	 * @param currentLine is the string of current line
	 * @param shouldEqualsTo can be ParseCommandFile.FILTER or ParseCommandFile.ORDER
	 * @throws CommandFileProcessingException if the currentLine is different than shouldEqualsTo
	 */
	private void checkLine(String currentLine,String shouldEqualsTo)throws CommandFileProcessingException{
		if (!currentLine.equals(shouldEqualsTo)) {
			System.err.print(ParseCommandFile.errorMassage(shouldEqualsTo));
			throw new CommandFileProcessingException();
		}
	}

	/**
	 * @param missingSection the representor string of the missing section
	 * @return the error massage for missing the section missingSection
	 */
	private static String errorMassage(String missingSection){
	return "ERROR: There is no " + missingSection + " sub-section\n";
	}

	/**
	 * get bufferedReader & convert its file into an array of strings
	 * @param bufferedReader
	 * @return array of strings
	 * @throws IOException
	 */
	private String[] fileToArray(BufferedReader bufferedReader)throws IOException{
		String currentLine;

		//adding the file to ArrayList
		ArrayList<String> fileList = new ArrayList<String>();
		while ((currentLine = bufferedReader.readLine()) != null) {
			fileList.add(currentLine);
		}

		String[] fileArray = fileList.toArray(new String[fileList.size()]);
		this.commandFileStrings = fileArray;
		return fileArray;
	}

	/**
	 * read line by line by bufferedReader, checks the file sub-sections's format structure
	 * @param bufferedReader
	 * @return true if the structure is accepted, otherwise false
	 * @throws CommandFileProcessingException throws if the file format is not accepted
	 * @throws IOException throws if there is an I/O Exception during perform BufferedReader/FileReader
	 * to read the file lines
	 */
	private boolean checkFileLines(BufferedReader bufferedReader)
												throws CommandFileProcessingException,IOException{
		final int FIRST_LINE = 0;

		int numOfLine = FIRST_LINE;
		boolean shouldBeFilter = true;

		String [] fileArray = fileToArray(bufferedReader);

		for (int i = 0; i< fileArray.length; i++) {
			//checking the order of FILTER / ORDER inside the file
			boolean lineIsEven = numOfLine % 2 == 0;
			boolean hasNext = (i+1) < fileArray.length;
			if (lineIsEven){//it is FILTER / ORDER line
				if (shouldBeFilter) {
					this.checkLine(fileArray[i], ParseCommandFile.FILTER);
					//if everything is ok add the filter:
					if (hasNext)
						this.filters.add(fileArray[i+1]);
					else{
						System.err.print(ParseCommandFile.errorMassage(ParseCommandFile.FILTER));
						throw new CommandFileProcessingException();
					}
					shouldBeFilter = false;

				}else{//it should be order
					this.checkLine(fileArray[i], ParseCommandFile.ORDER);
					shouldBeFilter = true;
					if (hasNext){
						if (fileArray[i+1].equals(ParseCommandFile.FILTER)){ //empty ORDER so:
							this.orders.add(OrderFactory.DEFAULT_ORDER);
							numOfLine --;//so at the end of this iterate it will be even again
						}else//there is an order in the next line - add the order:
							this.orders.add(fileArray[i+1]);
					}else//add the last one
						this.orders.add(OrderFactory.DEFAULT_ORDER);
				}
			}
				numOfLine ++;
			isLastSectionDamaged(i, fileArray);
		}
		return true;
	}

	/**
	 * get an index of a filter in the list, and return the line it
	 * appears inside the originally command file.
	 * @param sectionNumber the section number of the filter\order.
	 * @return the line it appears inside the originally command file.
	 */
	public int numOfLine(int sectionNumber, String whatToSearchFor){
		//it will round to button as default, so:
		int sectionCounter = 0;

		//finding the line of whatToSearchFor (FILTER/ORDER)
		int line = 0;
		while(sectionCounter < sectionNumber){
			if (this.commandFileStrings[line].equals(whatToSearchFor))
				sectionCounter++;
			line++;
		}

		line += 1;
		return line;

	}

	/**
	 * checks weather the last sub-section is damaged
	 * @param i the current index in the file's lines
	 * @param fileArray an array of the file's lines
	 * @throws CommandFileProcessingException in case is damaged
	 */
	private void isLastSectionDamaged(int i, String[] fileArray)throws CommandFileProcessingException{
		final int FIRST_INDEX = 1;
		boolean indexIsValid = (i >= FIRST_INDEX);
		if (indexIsValid){
			boolean lastLine =  i == fileArray.length-1;
			boolean lineIsNotOrder = !fileArray[i].equals(ParseCommandFile.ORDER);
			boolean previousIsNotOrder = !fileArray[i-1].equals(ParseCommandFile.ORDER);

			//we are at the last of the file & the last sub-section is damaged
			if (lastLine && lineIsNotOrder && previousIsNotOrder) {
				System.err.print(ParseCommandFile.errorMassage(ParseCommandFile.ORDER));
				throw new CommandFileProcessingException();
			}
		}
	}

	/**
	 * checks weather the format structure of the command file if accepted
	 * @return true if accepted, otherwise false
	 * @throws CommandFileProcessingException if the file format is not accepted
	 * @throws IOException if there is an I/O Exception during perform BufferedReader/FileReader
	 * to read the file lines
	 */
	public boolean isFileFormatGood() throws CommandFileProcessingException, IOException{
		BufferedReader bufferedReader;
		FileReader fileReader;
		boolean isFileOk;

		fileReader = new FileReader(this.commandFilePath);
		bufferedReader = new BufferedReader(fileReader);

		isFileOk = this.checkFileLines(bufferedReader);

		bufferedReader.close();
		fileReader.close();

		return isFileOk;
	}

}




