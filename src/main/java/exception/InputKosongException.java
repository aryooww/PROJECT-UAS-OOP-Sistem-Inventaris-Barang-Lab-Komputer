package exception;

// Custom Exception untuk memvalidasi input yang tidak boleh kosong
public class InputKosongException extends Exception {
    public InputKosongException(String message) {
        super(message);
    }
}