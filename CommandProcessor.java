/**
 * The purpose of this class is to parse a single line from the command text
 * file
 * according to the format specified in the project specs.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class CommandProcessor {

    // the database object to manipulate the
    // commands that the command processor
    // feeds to it
    private Database data;

    /**
     * The constructor for the command processor requires a database instance to
     * exist, so the only constructor takes a database class object to feed
     * commands to.
     * 
     *
     * the database object to manipulate
     */
    public CommandProcessor() {
        data = new Database();
    }


    /**
     * This method parses keywords in the line and calls methods in the
     * database as required. Each line command will be specified by one of the
     * keywords to perform the actions.
     * These actions are performed on specified objects and include insert,
     * remove,
     * regionsearch, search, and dump. If the command in the file line is not
     * one of these, an appropriate message will be written in the console. This
     * processor method is called for each line in the file. Note that the
     * methods called will themselves write to the console, this method does
     * not, only calling methods that do.
     * 
     * @param line
     *            a single line from the text file
     */
    public void processor(String line) {
        // Check if the line is empty or consists only of whitespace
// if (line.trim().isEmpty()) {
// // If the line is empty or whitespace,
// //return early without processing
// //System.out.println("Invalid line");
// return;
// }

        // converts the string of the line into an
        // array of its space (" ") delimited elements
        String[] arr = line.split("\\s{1,}");
        String command = arr[0]; // the command will be the first of these
                                 // elements
        // calls the insert function and passes the correct
        // parameters by converting the string integers into
        // their Integer equivalent, trimming the whitespace
        if (command.equals("insert")) {
            // Calls insert

            // Make sure there is enough arguments for the insert command
            if (arr.length == 6) {

                // Grabs the name of the rectangle etc
                String name = arr[1];

                // Extract the rest of the parameters
                int x = Integer.parseInt(arr[2].trim());
                int y = Integer.parseInt(arr[3].trim());
                int w = Integer.parseInt(arr[4].trim());
                int h = Integer.parseInt(arr[5].trim());

                // Check the rectangle to make sure it has valid w and h

                // Now construct the rectangle object
                Rectangle rect = new Rectangle(x, y, w, h);
                KVPair<String, Rectangle> pair = new KVPair<>(name, rect);
                data.insert(pair);

            }
            else {
                System.out.println("Incorrect number of "
                    + "arguments for insert command.");
            }

        }
        // calls the appropriate remove method based on the
        // number of white space delimited strings in the line
        else if (command.equals("remove")) {
            // checks the number of white space delimited strings in the line
            int numParam = arr.length - 1;
            if (numParam == 1) {
                // Calls remove by name

                // Grabs the name of the rectangle etc
                String name = arr[1];

                // Now remove it from the Database
                data.remove(name);

            }
            else if (numParam == 4) {
                // Calls remove by coordinate, converting string
                // integers into their Integer equivalent minus whitespace

                int x = Integer.parseInt(arr[1].trim());
                int y = Integer.parseInt(arr[2].trim());
                int w = Integer.parseInt(arr[3].trim());
                int h = Integer.parseInt(arr[4].trim());

                data.remove(x, y, w, h);

            }
            else {
                System.out.println("Incorrect number of "
                    + "arguments for remove command.");
            }

        }
        else if (command.equals("regionsearch")) {
            // calls the regionsearch method for a set of coordinates
            // the string integers in the line will be trimmed of whitespace
            // Ensure there are enough arguments for the regionsearch command
            if (arr.length == 5) {
                try {
                    int x = Integer.parseInt(arr[1].trim());
                    int y = Integer.parseInt(arr[2].trim());
                    int w = Integer.parseInt(arr[3].trim());
                    int h = Integer.parseInt(arr[4].trim());

                    // Validate the width and height
                    if (w > 0 && h > 0) {
                        // Call the database's regionsearch method
                        data.regionsearch(x, y, w, h);
                    }
                    else {
                        // If width or height are not valid, print an error
                        // message
                        System.out.println("Rectangle rejected: (" + x + ", "
                            + y + ", " + w + ", " + h + ")");
                    }
                }
                catch (NumberFormatException e) {
                    // If parsing fails, print an error message
                    System.out.println("Invalid parameters for regionsearch.");
                }
            }
            else {
                // If not enough parameters are provided, print an error message
                System.out.println("Invalid command format for regionsearch.");
            }

        }
        else if (command.equals("intersections")) {
            // calls the intersections method, no parameters to be passed
            // (see the intersections JavaDoc in the Database class for more
            // information)

            data.intersections();

        }
        else if (command.equals("search")) {
            // calls the search method for a name of object
            // Ensure there's a name to search
            if (arr.length > 1) {
                String name = arr[1];
                data.search(name);
            }
            else {
                System.out.println("Search command requires a name parameter.");
            }

        }
        else if (command.equals("dump")) {
            // calls the dump method for the database, takes no parameters
            // (see the dump() JavaDoc in the Database class for more
            // information)

            data.dump();

        }
        else {
            // the first white space delimited string in the line is not
            // one of the commands which can manipulate the database,
            // a message will be written to the console
            System.out.println("Unrecognized command.");
        }
    }

}
