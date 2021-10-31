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
      this.server = new Socket("gaia.cs.umass.edu", 80);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void run() {
    System.out.println("Client " + client.getLocalAddress() + ":" + client.getLocalPort() + "  "
        + client.getRemoteSocketAddress() + "\n ");
    System.out.println("Server " + server.getLocalAddress() + ":" + server.getLocalPort() + "  "
        + server.getRemoteSocketAddress() + "\n ");

    try (OutputStream serverout = server.getOutputStream();
         InputStream clientin = client.getInputStream();
         InputStream serverin = server.getInputStream();
         OutputStream clientout = client.getOutputStream();

    ) {
      ReaderWriter clientserver = new ReaderWriter(clientin, serverout);
      ReaderWriter serverclient = new ReaderWriter(serverin, clientout);

      serverclient.start();
      clientserver.start();
      serverclient.join();
      clientserver.join();

    }

    catch (Exception e) {
      e.printStackTrace();
    }
  }
}