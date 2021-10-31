package oldwestbury;

import java.io.*;

public class HttpProtocol {
  static void process(InputStream clientin, OutputStream serverout, InputStream serverin,
      OutputStream clientout) throws IOException {
    int state = 0;
    int len = 0;
    for (;;) {
      int ch = clientin.read();

      if (ch == -1)
        break;
      len++;
      boolean done = false;
      switch (state) {
        case 0:
          if (ch == '\r')
            state = 1;
          else
            state = 0;
          break;
        case 1:
          if (ch == '\n')
            state = 2;
          else
            state = 0;
          break;
        case 2:
          if (ch == '\r')
            state = 3;
          else
            state = 0;
          break;
        case 3:
          if (ch == '\n')
            done = true;

          state = 0;
          break;
      }

      System.out.print((char) ch);
      serverout.write((byte) ch);
      if (done)
        break;
    }

    System.out.println("--Done-- request" + len);

    for (;;) {
      //    System.out.print("--------------------");
      int ch = serverin.read();
      if (ch == -1)
        break;
      System.out.print((char) ch);
      clientout.write((byte) ch);
    }
    System.out.println("Done");
    return;
  }
}
