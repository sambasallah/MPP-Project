package business;
import java.io.Serializable;

public class BookStatusException extends Exception implements Serializable {
   private static final long serialVersionUID = 8978723266036027364L;

   public BookStatusException() {
   }

   public BookStatusException(String msg) {
      super(msg);
   }

   public BookStatusException(Throwable t) {
      super(t);
   }
}
