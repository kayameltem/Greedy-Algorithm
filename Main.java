import java.io.*;
import java.util.ArrayList;
import com.google.gson.*;
import java.io.FileReader;
import java.util.Arrays;


public class Main {
    /**
     * Propogated {@link IOException} here
     * {@link #readFile} and {@link #writeOutput} methods should be called here
     * A {@link Scheduler} instance must be instantiated here
     */
    public static void main(String[] args) throws IOException{
        try {
            // read json input file
            Assignment[] asigt = readFile(args[0]);
            Scheduler s = new Scheduler(asigt);

            // write output to json files
            writeOutput("solution_dynamic.json", s.scheduleDynamic());
            writeOutput("solution_greedy.json", s.scheduleGreedy());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Assignment}s obtained by reading the given json file
     * @throws FileNotFoundException If the given file does not exist
     */
    private static Assignment[] readFile(String filename) throws FileNotFoundException {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            // convert JSON string to Assignment array
            Assignment[] asigt = gson.fromJson(reader, Assignment[].class);
            Arrays.sort(asigt);
            return asigt;

    }

    /**
     * @param filename  json filename to write
     * @param arrayList a list of {@link Assignment}s to write into the file
     * @throws IOException If something goes wrong with file creation
     */
    private static void writeOutput(String filename, ArrayList<Assignment> arrayList) throws IOException {


        // create Gson instance
        Gson gson = new Gson();

        // create a writer
        Writer writer = new BufferedWriter(new FileWriter(filename));

        // convert books object to JSON file
        gson.toJson(arrayList, writer);

        // close writer
        writer.close();

    }
}