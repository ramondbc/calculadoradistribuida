import java.io.*;
import java.net.*;

class Server2 {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            server = new ServerSocket(1243); //cria socket do server na port 1243
            server.setReuseAddress(true);

            while (true) {

                Socket s = server.accept(); //objeto socket para receber pedidos do client

                PrintWriter out = new PrintWriter(s.getOutputStream(), true); //enviar para o client
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); //receber do client

                System.out.println("Novo Client conectado: " + s.getInetAddress().getHostAddress()); //mensagem dizendo que foi realizada uma conexao e informa qual o address do client

                try { //checa se vai acontecer algum erro ao separar a String ou converter para int
                    String[] x = in.readLine().split(" ");

                    if (x[0].charAt(0) == '+' || x[1].charAt(0) == '+') { //evitar um erro que ocorre onde +5 eh enxergado como 5
                        out.println("Operacao invalida.");
                    }
                    else {
                        float n1 = Float.parseFloat(x[0]);//transforma a string em um numeral
                        float n2 = Float.parseFloat(x[1]);
                        double resul = 0; //cria a variavel que vai retornar o resultado da operacao

                        switch (x[2]) { //switch para os casos da operacao usando o operador que esta salvo em x[2] (motivo o qual tivemos que forcar "r" no client)
                            case "%" -> resul = (double) (n1 * n2) / 100; //porcentagem
                            case "^" -> resul = (Math.pow(n1, n2)); //potencia
                            case "r" -> resul = (Math.sqrt(n2)); //raiz quadrada
                        }
                        out.println("Resultado: " + resul); //envia resultado para o client
                    }

                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {//tratar erro de formato da operacao
                    e.printStackTrace(); //mostra um log do erro pro server
                    out.println("Operacao invalida."); //envia a mensagem de erro pro client
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}