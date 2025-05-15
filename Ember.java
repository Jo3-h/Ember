// Ember.java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Ember {

    static boolean hadError = false; // boolean variable to indicate whether an error has been hit in the program execution

    /* Function: main() - Entrypoint to Ember Interpreter main()
     * 
     * @param *.ember script to be executed or void
     * @return void
     * 
     * Purpose of this function is to take the commandline arguments and either run the specified Ember Script or enter and interactive session
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 1){
            System.out.printLn("Usage: Ember [script]");
            System.exit(64);
        } else if (args.length == 1){
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    /* Function: runFile() - Execution of Ember code in specified file path
     * 
     * @param path to *.ember file containing ember program
     * @return void
     * 
     * This function is called in the main function when a file argument is passed, therefore the Interpreter should execute the ember code stored in this file location
     */
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        // Indicate an error in the exit code
        if (hadError) System.exit(65);
    }

    /* Function: runPrompt() - Interactive session of the Ember Interpreter
     * 
     * @param void
     * @return void
     * 
     * This function has an infinite control loop to maintain the session until the user specifies the end of the interactive session with null input
     */
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;){
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    /* Function: run() - Execute a line of code in the Ember programming language
     * 
     * @param source 
     * @return void
     */
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        for (Token token : tokens){
            System.out.println(token);
        }
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message){
        System.err.println("[line]" + line + "] Error"+ where + ": " + message);
        hadError = true;
    }
}