import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.LinkedHashMap;

public class Parser {
    public static void main(String[] args) throws FileNotFoundException {
       String s = parseJSON("input.json");
       System.out.println(s);
    }

    // parseJson takes a json file name and returns a string of unique file extensions and its count
    static String parseJSON(String jsonFile) throws FileNotFoundException{
        //loading in the schema file
        File schemaFile = new File("Data-schema.json");
        JSONTokener schemaData = new JSONTokener(new FileInputStream(schemaFile));

        //loading in the json file
        JSONObject jsonSchema = new JSONObject(schemaData);
        File jsonData = new File(jsonFile);
        JSONTokener jsonDataFile = new JSONTokener(new FileInputStream(jsonData));
        JSONObject jsonObject;

        //Creating a Schema which will be used to validate incomming data against the schema that we predefined in Data-schema.json
        Schema schemaValidator = SchemaLoader.load(jsonSchema);

        ArrayList<JSONObject> files = new ArrayList<JSONObject>();
        LinkedHashMap<String, Set<String>> file_extensions = new LinkedHashMap<String, Set<String>>(); //used to map unique file extensions to all of the unique files with the same extension



        while(jsonDataFile.skipTo('{') != 0 ){
            jsonObject = new JSONObject(jsonDataFile);

            try{
                schemaValidator.validate(jsonObject); //checking if the current data is valid based on the predefined schema
                files.add(jsonObject);

                formatFolderStructure(jsonObject);

                String nm = jsonObject.getString("nm");
                String ph = jsonObject.getString("ph");

                if(!validateFile(nm, ph)){
                    System.out.println("mismatch between file: " + nm + " and file path: " + ph);
                    continue;
                }

                String[] current_file = nm.split("\\.");
                String file_extension = current_file[1];


                // Constructing a the HashMap of file extensions and unique file
                Set<String> s;
                if(!file_extensions.containsKey(file_extension)){
                    s = new HashSet<String>();
                }
                else{
                    s = file_extensions.get(file_extension);
                }

                s.add(ph); // HashSet does not allow duplicate entries so similar files will not be counted twice
                file_extensions.put(file_extension, s);

            }
            catch (ValidationException e){
                // prints the exception message whenever invalid data is caught by the schema validator
                System.out.println(e);
            }
        }

        //constructing a string to return
        String response = "";
        for(String key: file_extensions.keySet()){
            int size = file_extensions.get(key).size();
            response = response + key + ": " + size +"\n";
        }
        return response.trim();
    }


    //validateFile takes in two strings, the file name (nm) and the file path (ph) and make sure that the files are consistent
    //The file at the end of the path should have the same name as the included file (nm)
    static Boolean validateFile(String nm, String ph){
        String[] folder_structure = ph.split("/");
        String file_name = folder_structure[folder_structure.length-1];

        return nm.equals(file_name);

    }

    // formatFolderStucture takes a JSONObject and formats its file path attribute to ensure all Objects are in the same format
    // this method make sure the the file path leads and ends with forwards slashes and that the file name is included in the path
    static void formatFolderStructure(JSONObject jsonObject){
        String ph = jsonObject.getString("ph");
        String[] folder_structure = ph.split("/");
        String last_folder = folder_structure[folder_structure.length-1];

        if(ph.charAt(0) != '/'){
            ph = "/" + ph;
        }
        if(ph.charAt(ph.length()-1) != '/'){
            ph = ph + "/";
        }

        if(last_folder.matches("^[\\w\\s-]+\\.[A-Za-z]+$")){
            jsonObject.put("ph", ph);

        }
        else {
            ph = ph + jsonObject.get("nm") + "/";
            jsonObject.put("ph", ph);
        }
    }


}