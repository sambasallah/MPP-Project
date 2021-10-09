package ui;

import java.io.Serializable;

public final class Author implements Serializable {
   private String firstName;
   private String lastName;

   public Author(String f, String l) {
      this.firstName = f;
      this.lastName = l;
   }
}
