package org.example.exception;

import java.io.*;

public class ExceptionSample {
    /*
     1. Prefer specific exceptions
     ------
     SHOULD:
        - throw specific exception
        - ez to catch, trace errors
        - Doesn't hide real real err
             public int parseNumber(String s) throws Exception
     */
    public int parseNumber(String input) throws NumberFormatException {
        return Integer.parseInt(input);
    }

    /*
     2. Catch specific exceptions first
     */
    public String readFile(String path) {
        try {
            return doRead(path);
            // Catch specific exception first
        } catch (FileNotFoundException e) {
            return "FILE_NOT_FOUND";
            // IOException must come after because it's the parent of FileNotFoundException
        } catch (IOException e) {
            return "IO_ERROR";
        }
    }
    /**
     * Can throw:
     *  - FileNotFoundException
     *  - IOException
     */
    private String doRead(String path) throws IOException {
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.readLine();
        }
        // close BufferedReader automatically after try block ends
    }

    /*
     3. Empty catch is not allowed - log instead
        - Swallows exceptions
        - No err into what problems are occurring
     */
    public int safeDivide(int a, int b) {
        try {
            return a / b;
        }
        catch (ArithmeticException e) {
            System.out.println("Divide-by-zero detected.");
            return 0;
        }
    }

    /*
     4. Try-with-resources
        -  FileInputStream is always closed
        - Without try-with-resources - need finally block
     */
    public int countBytes(String filePath) {
        File file = new File(filePath);

        try (FileInputStream in = new FileInputStream(file)) {
            return in.available();
        } catch (IOException e) {
            return -1;
        }
    }

    /*
     5. Don't return inside finally (ANTI-PATTERN)

        throws IllegalArgumentException → finally block → return 999 → hides the original exception.
     */
    public int badFinallyReturn() {
        try {
            throw new IllegalArgumentException("original exception");
        } finally {
            return 999;   // BAD — che mất exception
        }
    }

    /*
     6. Don't throw inside finally

        - IOException("original") is completely hidden
        - only receives RuntimeException("MASKED")
     */
    public void badFinallyThrow() {
        try {
            throw new IOException("original");
        } finally {
            throw new RuntimeException("MASKED");   // che mất exception gốc
        }
    }
}
