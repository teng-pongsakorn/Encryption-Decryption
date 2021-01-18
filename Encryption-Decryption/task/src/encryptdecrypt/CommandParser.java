package encryptdecrypt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

enum Algo {
    SHIFT, UNICODE
}

enum ArgumentType {
    MODE("-mode"),
    KEY("-key"),
    DATA("-data"),
    IN("-in"),
    OUT("-out"),
    ALGO("-alg");

    private final String code;
    ArgumentType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

public class CommandParser {

    private Mode mode;
    private int key;
    private String data;
    private String in;
    private String out;
    private Algo algo;

    // constructor with default setting
    public CommandParser() {
        this.mode = Mode.ENC;
        this.key = 0;
        this.data = "";
        this.in = null;
        this.out = null;
        this.algo = Algo.SHIFT;
    }

    public void read(String[] args) throws IOException {
        readMode(args);
        readKey(args);
        readData(args);
        readIn(args);
        readOut(args);
        readAlgo(args);

        // update data if there is an input file
        updateData();
    }

    public Algo getAlgo() {
        return this.algo;
    }

    private void readAlgo(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(ArgumentType.ALGO.getCode())) {
                this.algo = Algo.valueOf(args[i + 1].toUpperCase());   // IllegalArgumentException
            }
        }
    }

    private void updateData() throws IOException {
        if (data.length() == 0 && in != null) {
            readInputFile();
        }
    }

    private void readInputFile() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(this.getIn())));
    }

    public String getData() {
        return this.data;
    }

    public String getIn() {
        return this.in;
    }

    public String getOut() {
        return this.out;
    }

    public int getKey() {
        return this.key;
    }

    public Mode getMode() {
        return this.mode;
    }

    private void readOut(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(ArgumentType.OUT.getCode())) {
                this.out = args[i + 1];                       // ArrayIndexOutOfBounds
            }
        }
    }

    private void readIn(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(ArgumentType.IN.getCode())) {
                this.in = args[i + 1];                      // ArrayIndexOutOfBounds
            }
        }
    }

    private void readData(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(ArgumentType.DATA.getCode())) {
                this.data = args[i + 1];              // ArrayIndexOutOfBounds
            }
        }
    }

    private void readKey(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(ArgumentType.KEY.getCode())) {
                this.key = Integer.parseInt(args[i + 1]);           // throw NumberFormatException
            }
        }
    }

    private void readMode(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(ArgumentType.MODE.getCode())) {
                this.mode = Mode.valueOf(args[i + 1].toUpperCase());          // throw IllegalArgumentException
            }
        }
    }
}
