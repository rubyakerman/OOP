package filesprocessing;
import filesprocessing.filter.*;
import filesprocessing.order.*;
import filesprocessing.command_file.*;

import java.io.IOException;
import java.util.ArrayList;

/**this class is the main class, hence it runs the whole program:
 * parse the command file by ParseCommandFile
 * catch & take care of exceptions
 * create filters\orders by filter\order factory
 * create SubSections Respectively
 * take care of printing the output as required
 * @author Roy Ackerman
 */

public class DirectoryProcessor {

    final String WRONG_ARGUMENT_MASSAGE = "program should get 2 arguments";
    final int TWO_ARGUMENTS = 2;
    final int SOURCE_DIR = 0;
    final int COMMAND_FILE_PATH = 1;
    final int MAX_NUM_OF_WARNINGS = 2;
    final int FILTER_WARNINGS_PLACE = 0;
    final int ORDER_WARNINGS_PLACE = 1;

    public static void main(String[] args) {

        if (args.length != TWO_ARGUMENTS)//in case we've got anything but 2 arguments
            throw new IllegalArgumentException(WRONG_ARGUMENT_MASSAGE);

        String sourceDir = args[SOURCE_DIR];
        String commandFilePath = args[COMMAND_FILE_PATH];

        //check the command file format\structure (errors number 2)
        ParseCommandFile commandFile = new ParseCommandFile(commandFilePath);
        try {
            commandFile.isFileFormatGood();
        } catch (CommandFileProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // in case we would like to take care of this by another actions in the future
            e.printStackTrace();
        }

        //get the strings of the filters / orders
        ArrayList<String> filterStrings = commandFile.getFilters();
        ArrayList<String> orderStrings = commandFile.getOrders();

        //.....................initialise sub sections.......................//
        int numOfSections = orderStrings.size();
        SubSection[] subSections = new SubSection[numOfSections];
        Integer[][] warnings = new Integer[numOfSections + 1][MAX_NUM_OF_WARNINGS];// to obtain warnings for subsections

        String[] myFilterStrings = filterStrings.toArray(new String[filterStrings.size()]);
        Filter[] filters = new Filter[filterStrings.size()];

        String[] myOrderStrings = orderStrings.toArray(new String[orderStrings.size()]);
        Order[] orders = new Order[orderStrings.size()];

        //.......initialize filters & detect + keeping the warnings.......//
        for (int i=0; i<numOfSections; i++) {
            try {
                filters[i] = FilterFactory.createFilters(myFilterStrings[i], sourceDir);
            }catch(FilterException e){
                int sectionNumber = i + 1;
                //adding to sub-section number "sectionNumber" the warning
                // that occur in line detected by numOfLine() :
                int line = commandFile.numOfLine(sectionNumber, ParseCommandFile.FILTER);

                warnings[sectionNumber][FILTER_WARNINGS_PLACE] = line;
                filters[i] = new All();
                continue;
            }
    }

        //.....initialize orders & detect + keeping the warnings.....//
        for (int i = 0; i < numOfSections; i++) {
            try {
                orders[i] = OrderFactory.createOrders(myOrderStrings[i]);
            }catch (OrderException eOrder){
                int sectionNumber = i + 1;
                //adding to sub-section number "sectionNumber" the warning
                // that occur in line detected by numOfLine() :
                int line = commandFile.numOfLine(sectionNumber, ParseCommandFile.ORDER);
                warnings[sectionNumber][ORDER_WARNINGS_PLACE] = line;

                orders[i] = new Abs(false);
                continue;
            }
        }

        //.....initiate subSections.....//
        for (int j = 0; j < numOfSections; j++){
            int sectionNumber = j+1;
            subSections[j] = new SubSection(filters[j], orders[j], warnings[sectionNumber], sourceDir);
        }

        //.....printing by the requested order.....//
        final String DIRECTORY_SEPARATOR = "/";
        for (int i = 0; i < numOfSections; i++) {
            subSections[i].printWarnings();
            //than print files:
            for (String fileName : subSections[i].getSubSectionFiles()){
                java.io.File file = new java.io.File(sourceDir+DIRECTORY_SEPARATOR+fileName);
                if (!file.isDirectory())
                System.out.println(fileName);
            }
        }

    }
}
