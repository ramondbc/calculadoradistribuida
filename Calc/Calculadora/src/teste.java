import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class teste {

    public static void main(String[] args){

        while (true) {

            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            line = line.replaceAll("\\s", "") ;
            // String newStr= str.replaceAll("\\s", "");

            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '+' || line.charAt(i) == '-' || line.charAt(i) == '*' || line.charAt(i) == '/') {
                    //try (Socket socket = new Socket("localhost", 1234)) {

                    //Receber do Server
                    // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    //Mandar pro Server
                    // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    String ope = String.valueOf(line.charAt(i));
                    String[] num = line.split("\\%s".formatted(ope),2);

                    String[] x = new String[3];
                    x[0] = num[0];
                    x[1] = num[1];
                    x[2] = ope;
                    //out.println(num);
                    //out.flush(); testar autoFlush

                    //int resul = in.read();
                    System.out.println(x[0]+ x[2] + x[1]);

                    String result = "";

                    switch (x[2]){
                        case "+" :
                            result = Double.toString(Integer.parseInt(x[0])+Integer.parseInt(x[1]));;
                            System.out.println(result);
                            break;
                            

                    }
                }
            }

        }
    }
}
