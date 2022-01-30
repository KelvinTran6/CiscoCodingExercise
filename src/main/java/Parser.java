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

    static String parseJSON(String jsonFile) throws FileNotFoundException{
        //json schema
        File schemaFile = new File("Data-schema.json");
        JSONTokener schemaData = new JSONTokener(new FileInputStream(schemaFile));
        JSONObject jsonSchema = new JSONObject(schemaData);

        //json data
        File jsonData = new File(jsonFile);
        JSONTokener jsonDataFile = new JSONTokener(new FileInputStream(jsonData));
        JSONObject jsonObject;
        Schema schemaValidator = SchemaLoader.load(jsonSchema);
        ArrayList<JSONObject> files = new ArrayList<JSONObject>();
        LinkedHashMap<String, Set<String>> file_extensions = new LinkedHashMap<String, Set<String>>();



        while(jsonDataFile.skipTo('{') != 0 ){
            jsonObject = new JSONObject(jsonDataFile);

            try{
                schemaValidator.validate(jsonObject);
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



                Set<String> s;
                if(!file_extensions.containsKey(file_extension)){
                    s = new HashSet<String>();
                }
                else{
                    s = file_extensions.get(file_extension);
                }
                s.add(ph);
                file_extensions.put(file_extension, s);

            }
            catch (ValidationException e){
                System.out.println(e);
            }
        }

        String response = "";
        for(String key: file_extensions.keySet()){
            int size = file_extensions.get(key).size();
            response = response + key + ": " + size +"\n";
        }
        return response.trim();
    }


    static Boolean validateFile(String nm, String ph){
        String[] folder_structure = ph.split("/");
        String file_name = folder_structure[folder_structure.length-1];

        return nm.equals(file_name);

    }

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