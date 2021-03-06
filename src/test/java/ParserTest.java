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

        System.out.println("*******************************");
        assertEquals(expected1 ,result1);
        System.out.println("Test 1 success:");
        System.out.println(result1);
        System.out.println();

        assertEquals(expected2 ,result2);
        System.out.println("Test 2 success:");
        System.out.println(result2);
        System.out.println();

        assertEquals(expected3 ,result3);
        System.out.println("Test 3 success:");
        System.out.println(result3);
        System.out.println();

        assertEquals(expected4 ,result4);
        System.out.println("Test 4 success:");
        System.out.println("expected empty string");
        System.out.println();

        assertEquals(expected5 ,result5);
        System.out.println("Test 5 success:");
        System.out.println(result5);
        System.out.println();
        System.out.println("*******************************");
    }
}