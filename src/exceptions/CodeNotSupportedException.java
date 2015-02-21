package exceptions;

public class CodeNotSupportedException extends RuntimeException {
    public CodeNotSupportedException (char c) {
        super(String.format("%d", (int) c));
    }
}
