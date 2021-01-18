package encryptdecrypt;

abstract class CipherMachine {

    private int key;

    public CipherMachine(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public static int getIndex(char[] charSet, char s) {
        for (int i = 0; i < charSet.length; i++) {
            if (charSet[i] == s) {
                return i;
            }
        }
        return -1;
    }

    abstract String decipher(String message, Mode mode);

    public String run(Mode mode, String data) {
        if (mode == Mode.ENC) {
            return encode(data);
        } else {
            return decode(data);
        }
    }

    private String encode(String message) {
        return decipher(message, Mode.ENC);
    }

    private String decode(String message) {
        return decipher(message, Mode.DEC);
    }
}


class ShiftCipherMachine extends CipherMachine {

    // for shift algo
    final private char[] lowercases = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    final private char[] uppercases = "ABBCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public ShiftCipherMachine(int key) {
        super(key);
    }

    @Override
    String decipher(String message, Mode mode) {
        return decipherShiftAlgo(message, mode);
    }

    private String decipherShiftAlgo(String message, Mode mode) {
        StringBuilder builder = new StringBuilder();
        for (char s: message.toCharArray()) {
            builder.append(decipherOneCharShift(s, mode));
        }
        return builder.toString();
    }

    private char decipherOneCharShift(char s, Mode mode) {
        if (s >= 'a' && s <= 'z') {
            return lowercaseShift(s, mode);
        } else if (s >= 'A' && s <= 'Z') {
            return uppercaseShift(s, mode);
        }
        return s;
    }

    private char uppercaseShift(char s, Mode mode) {
        int origIdx = getIndex(uppercases, s);
        int charSize = uppercases.length;
        int newIdx = (origIdx + (getKey() * mode.getValue()) + charSize) % charSize;
        return uppercases[newIdx];
    }

    private char lowercaseShift(char s, Mode mode) {
        int origIdx = getIndex(lowercases, s);
        int charSize = lowercases.length;
        int newIdx = (origIdx + (getKey() * mode.getValue()) + charSize) % charSize;
        return lowercases[newIdx];
    }
}


class UnicodeCipherMachine extends CipherMachine {

    final private char firstChar = 32;
    final private char lastChar = 126;

    public UnicodeCipherMachine(int key) {
        super(key);
    }

    @Override
    String decipher(String message, Mode mode) {
        return decipherUnicodeAlgo(message, mode);
    }

    private String decipherUnicodeAlgo(String message, Mode mode) {
        StringBuilder builder = new StringBuilder();
        for (char s: message.toCharArray()) {
            builder.append(decipherOneCharUnicode(s, mode));
        }
        return builder.toString();
    }

    private char decipherOneCharUnicode(char s, Mode mode) {
        int originalCharInt = s - firstChar;
        int charSize = lastChar - firstChar + 1;
        int encodeCharInt = (originalCharInt + (getKey() * mode.getValue())) % charSize + firstChar;
        return (char) encodeCharInt;
    }
}