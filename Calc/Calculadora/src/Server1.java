import java.io.*;
import java.net.*;

class Server1 {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            while (true) {

                // socket object to receive incoming client
                // requests
                Socket s = server.accept();

                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected " + s.getInetAddress().getHostAddress());

                String[] x = in.readLine().split(" ");

                int n1 = Integer.parseInt(x[0]);
                int n2 = Integer.parseInt(x[1]);
                float resul = 0;

                switch (x[2]) {
                    case "+":
                        resul = n1 + n2;
                        break;

                    case "-":
                        resul = n1 - n2;
                        break;

                    case "*":
                        resul = n1 * n2;
                        break;

                    case "/":
                        if (n2 == 0) {
                            out.println("Nao se pode dividir por 0.");
                            continue;
                        }
                        resul = (float) n1 / n2;
                        break;

                    default:
                        out.println("Erro.");
                        continue;
                }

                out.println("Resultado: " + resul);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {server.close();}
                catch (IOException e) {e.printStackTrace();}
            }
        }
    }
}