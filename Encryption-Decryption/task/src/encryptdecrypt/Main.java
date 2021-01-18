package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        try {
            // process input arguments
            CommandParser parser = new CommandParser();
            parser.read(args);

            // decode-encode process
            CipherMachine cipherMachine = getCipherMachine(parser.getKey(), parser.getAlgo());
            String result = cipherMachine.run(parser.getMode(), parser.getData());

            // output
            displayResult(result, parser.getOut());

        } catch (NumberFormatException e) {
            System.out.println("Error: key must be an integer");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid operation: [select: enc or dec]");
        } catch (IOException e) {
            System.out.println("Error: File not found");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: argument value not found");
        }
    }

    private static CipherMachine getCipherMachine(int key, Algo algo) {
        if (algo == Algo.SHIFT) {
            return new ShiftCipherMachine(key);
        } else if (algo == Algo.UNICODE) {
            return new UnicodeCipherMachine(key);
        } else {
            throw new IllegalArgumentException("Unknown algorithm");
        }
    }

    private static void displayResult(String result, String outputFilePath) throws IOException{
        if (outputFilePath != null) {
            File outFile = new File(outputFilePath);
            FileWriter writer = new FileWriter(outFile);
            writer.write(result + "\n");
            writer.close();
        } else {
            System.out.println(result);
        }
    }
}

