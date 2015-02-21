package exceptions;

public class CodeNotSupportedException extends RuntimeException {
    public CodeNotSupportedException (char c) {
        super(String.format("%c", c));
    }
}
