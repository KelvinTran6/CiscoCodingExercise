import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parseJSON() throws FileNotFoundException {
        Parser p = new Parser();
        String result1 = p.parseJSON("./src/test/test_inputs/test1.json");
        String result2 = p.parseJSON("./src/test/test_inputs/test2.json");
        String result3 = p.parseJSON("./src/test/test_inputs/test3.json");
        String result4 = p.parseJSON("./src/test/test_inputs/test4.json");
        String result5 = p.parseJSON("./src/test/test_inputs/test5.json");

        String expected1 = "";
        String expected2 = "";
        String expected3 = "";
        String expected4 = "";
        String expected5 = "";

        try {
            expected1 = new String(Files.readAllBytes(Paths.get("./src/test/test_inputs/expected1.txt")));
            expected2 = new String(Files.readAllBytes(Paths.get("./src/test/test_inputs/expected2.txt")));
            expected3 = new String(Files.readAllBytes(Paths.get("./src/test/test_inputs/expected3.txt")));
            expected5 = new String(Files.readAllBytes(Paths.get("./src/test/test_inputs/expected5.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        assertEquals(expected1 ,result1);
        assertEquals(expected2 ,result2);
        assertEquals(expected3 ,result3);
        assertEquals(expected4 ,result4);
        assertEquals(expected5 ,result5);
    }
}