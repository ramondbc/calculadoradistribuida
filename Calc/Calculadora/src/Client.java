import java.io.*;
import java.net.*;
import java.util.*;

class Client {

    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);
        String line = null;

        while (!"bye".equalsIgnoreCase(line)) {

            String resul = null;
            System.out.print("Digite a operacao que deseja: ");
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

                        out.println(String.join(" ", x));

                        resul = in.readLine();
                        System.out.println(resul);

                        socket.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } //catch server 1
                } //if
                if (line.charAt(i) == '%' || line.charAt(i) == '^') {
                    try (Socket socket = new Socket("localhost", 1243)) {

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

                        out.println(String.join(" ", x));

                        resul = in.readLine();
                        System.out.println(resul);

                        socket.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } //catch server2.1
                } //if server2.1
                if (i == 0 && line.charAt(i) == 'r') {
                    try (Socket socket = new Socket("localhost", 1243)) {

                        //Receber do Server
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        //Mandar pro Server
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        String r = "0"+line; //Fazendo isso pra conseguir deixar o ope como x[2]
                        String[] num = r.split("r",2);

                        String[] x = new String[3];
                        x[0] = num[0];
                        x[1] = num[1];
                        x[2] = "r";

                        out.println(String.join(" ", x));

                        resul = in.readLine();
                        System.out.println(resul);

                        socket.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } //catch server2.2
                } //if server2.2
            } //for

            if (!line.equals("bye") && resul == null) {
                System.out.println("Operacao invalida.");
            } //if inv
        } //while
        sc.close();
    } //main
} //class