# CiscoCodingExercise

## Running the program
1. Clone the project to a directory
2. Open the project in IntelliJ and navigate to the "Parser.java" file (src/main/java/Parser.java)
3. Build the program and run it
4. You can test different inputs by changing the input.json file

## Testing
- I provided 5 different JSON test files along with the expected outputs in "test_inputs" folder (src/test/test_inputs) 
- To run the tests
    1. Navigate to "ParserTest.java" (src/test/java/ParserTest.java)
    2. Build and run the program
- When running the test you may notice a bunch of exception being thrown. This is because the test cases includes many invalid entries. When the program sees an invalid entry it throws and exceptions and prints a reason (incorrect file format, incorrect data type, etc). Once the exception is thrown it continues to scan for more **valid** entries

## Test files
- I provided 5 different json test files because each file focuses on different areas where bugs may occur
    - for example, test2.json return txt: 6 despite all of the filenames being the same "assignment1.txt". This is because all of the files are located in different places according to the path attribute so they are considered different files. 
    - test3.json is expected to return only txt: 1 despite having numerous entries. This because the program is recognizing all of the entries as being the same file (same name and path)
    - test4.json tests to make sure that my program is able to recognize valid and invalid entries. Running test4.json should return an empty string since all the entries are invalid


## Assumptions
- Criterias for valid inputs  
    - needs all of the JSON attributes listed in the instructions (ts, pt, si, uu, bg, sha, nm, ph, dp)
    - ts, pt and dp must be integers while the rest are strings
    - dp can only be the integers 1, 2 and 3
    - the file name (nm) must contain a valid file extension
    - file path (ph) can include the file name at the end but it **must** match the nm value
        - for example the following is valid: **ph**: Documents/folder/test.txt &nbsp; **nm**: test.txt
            - ph does not need to inclue the filename **ph**: Documents/folder/ &nbsp; **nm**: test.txt (this will be considered the same file by the program)
        - the following is **not** valid: **ph** Documents/folder/file.txt &nbsp; **nm**: test.txt (no way to know which file to count)

- Output order does not matter
    - My program is currently returning the file extention along with the count in the order that the data is comming in
        - for example if the files coming in are test1.txt and test2.pdf the output would be txt: 1 &nbsp; pdf: 1

- files with the same name but different path can be considered the same 

