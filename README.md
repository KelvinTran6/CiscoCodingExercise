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
- Usually the program would print out the exception message for invalid data but it is commented out to make the output more clear

## Test files
- I provided 5 different json test files because each file focuses on different areas where bugs may occur
    - for example, test2.json return txt: 6 despite all of the filenames being the same "assignment1.txt". This is because all of the files are located in different places according to the path attribute so they are considered different files. 
    - test3.json is expected to return only txt: 1 despite having numerous entries. This because the program is recognizing all of the entries as being the same file (same name and path)
    - test4.json tests to make sure that my program is able to recognize valid and invalid entries. Running test4.json should return an empty string since all the entries are invalid


## Assumptions
- Criterias for valid inputs  
    - Needs all of the JSON attributes listed in the instructions (ts, pt, si, uu, bg, sha, nm, ph, dp)
    - ts, pt and dp must be integers while the rest are strings
    - DP can only be the integers 1, 2 and 3
    - The file name (nm) must contain a valid file extension
    - File path (ph) can include the file name at the end but it **must** match the nm value
        - For example the following is valid: **ph**: Documents/folder/test.txt &nbsp; **nm**: test.txt
            - ph does not need to inclue the filename **ph**: Documents/folder/ &nbsp; **nm**: test.txt &nbsp; (this will be considered the same file by the program)
        - The following is **not** valid: **ph** Documents/folder/file.txt &nbsp; **nm**: test.txt &nbsp; (no way to know which file to count)

- Output order does not matter
    - My program is currently returning the file extention along with the count in the order that the data is comming in
        - For example if the files coming in are test1.txt and test2.pdf the output would be txt: 1 &nbsp; pdf: 1

- Files with the same name but different path are considered different files
- Files with disposition of 1 (malicious) should still be counted
