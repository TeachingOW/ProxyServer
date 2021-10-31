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
      // http://httpbin.org/anything
      this.server = new Socket("google.com", 80);
    } catch (Exception e) {
    }
  }

  public void run() {
    System.out.println("Client " + client.getLocalAddress() + ":" + client.getLocalPort() + "  "
        + client.getRemoteSocketAddress() + ":" + client.getPort() + "\n ");
    System.out.println("Server " + server.getLocalAddress() + ":" + server.getLocalPort() + "  "
        + server.getRemoteSocketAddress() + ":" + server.getPort() + "\n ");

    try (OutputStream serverout = server.getOutputStream();
         InputStream clientin = client.getInputStream();
         InputStream serverin = server.getInputStream();
         OutputStream clientout = client.getOutputStream();

    ) {
      for (;;) {
        HttpProtocol.process(clientin, serverout, serverin, clientout);
      }

    }

    catch (IOException e) {
      e.printStackTrace();
    }
  }
}