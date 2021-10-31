package oldwestbury;

import java.io.*;
import java.net.*;

public class ReaderWriter extends Thread {
  InputStream input;
  OutputStream output;

  ReaderWriter(InputStream input, OutputStream output) {
    this.input = input;
    this.output = output;
  }

  public void run() {
    try {
      for (;;) {
        int ch = input.read();
        // System.out.println((char) ch);
        if (ch == -1)
          continue;
        output.write(ch);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}