import java.io.*;
import java.net.*;

class Server2 {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            server = new ServerSocket(1243); //cria socket do server na port 1243
            server.setReuseAddress(true);
            System.out.println("Server2 inicializado!");


            while (true) {

                Socket s = server.accept(); //objeto socket para receber pedidos do client

                System.out.println("Conexao realizada: " + s.getInetAddress().getHostAddress()); //mensagem dizendo que foi realizada uma conexao e informa qual o address do client

                ClientHandler clientSock = new ClientHandler(s);
                new Thread(clientSock).start();

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

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                try { //checa se vai acontecer algum erro ao separar a String ou converter para int

                    String[] x = in.readLine().split(" ");

                    if (x[0].charAt(0) == '+' || x[1].charAt(0) == '+') { //evitar um erro que ocorre onde +5 eh enxergado como 5
                        out.println("Operacao invalida.");
                    } else {
                        float n1 = Float.parseFloat(x[0]);//transforma a string em um numeral
                        float n2 = Float.parseFloat(x[1]);
                        double resul = 0; //cria a variavel que vai retornar o resultado da operacao

                        switch (x[2]) { //switch para os casos da operacao usando o operador que esta salvo em x[2] (motivo o qual tivemos que forcar "r" no client)
                            case "%" -> resul = (double) (n1 * n2) / 100; //porcentagem
                            case "^" -> resul = (Math.pow(n1, n2)); //potencia
                            case "r" -> resul = (Math.sqrt(n2)); //raiz quadrada
                            default -> out.println("Operacao invalida!"); //se nao entrar em nenhum caso
                        }
                        out.println("Resultado: " + resul); //envia resultado para o client
                    }

                } catch (NumberFormatException | StringIndexOutOfBoundsException | IOException e) {//tratar erro de formato da operacao
                    e.printStackTrace(); //mostra um log do erro pro server
                    out.println("Operacao invalida."); //envia a mensagem de erro pro client
                }
            } catch (IOException e) {
                e.printStackTrace(); //mostra um log do erro pro server
                System.out.println("Problema de conexao!");
            }
        }
    }
}
