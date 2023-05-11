import java.io.*;
import java.net.*;
import java.util.*;

class Client {

    public static void main(String[] args)
    {


            Scanner sc = new Scanner(System.in);
            String line = null;

            while (!"sair".equalsIgnoreCase(line)) {

                line = sc.nextLine();
                line = line.replaceAll("\\s", "");

                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '+' || line.charAt(i) == '-' || line.charAt(i) == '*' || line.charAt(i) == '/') {
                        try (Socket socket = new Socket("localhost", 1234)) {

                            //Receber do Server
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            //Mandar pro Server
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                            String ope = String.valueOf(line.charAt(i));
                            String[] num = line.split("\\%s".formatted(ope),2);

                            String[] x = new String[3];
                            x[0] = num[0];
                            x[1] = num[1];
                            x[2] = ope;

                            out.println(x);
                            //out.flush(); testar autoFlush

                            int resul = in.read();
                            System.out.println(x[0] + " " + x[2] + " " + x[1] + " = " + resul);

                            socket.close();
                        }
                        catch (IOException e) {
                        e.printStackTrace();
                        }
                    //else if (line.charAt(i) == '%' || line.charAt(i) == '^' || line.charAt(i) == 'r') {

                    //}
                    }
                }
            }
            sc.close();
    }
}