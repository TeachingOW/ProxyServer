package oldwestbury;

import java.io.*;
import java.net.*;

public class ProxyServerThread extends Thread {
  private Socket client = null;
  private Socket server = null;
  public ProxyServerThread(Socket client) {
    super("ProxyServerThread");
    this.client = client;
    try {
      // http://gaia.cs.umass.edu/wireshark-labs/INTRO-wireshark-file1.html
      this.server = new Socket("google.com", 80);
    } catch (Exception e) {
    }
  }

  public void run() {
    try (OutputStream serverout = server.getOutputStream();
         BufferedReader clientin =
             new BufferedReader(new InputStreamReader(client.getInputStream()));
         BufferedReader serverin =
             new BufferedReader(new InputStreamReader(server.getInputStream()));) {
      // for (;;) {
      client.setSoTimeout(1000);
      System.out.println("  waiting");
      HttpProtocol.process(clientin, serverout, serverin);
      // }

      //  socket.close();
    }

    catch (IOException e) {
      e.printStackTrace();
    }
  }
}