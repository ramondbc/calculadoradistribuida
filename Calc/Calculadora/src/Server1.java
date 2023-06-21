import java.io.*;
import java.net.*;

class Server1 {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            server = new ServerSocket(1234); //cria socket de server na port 1234
            server.setReuseAddress(true);
            System.out.println("Server1 inicializado!");


            while (true) {

                Socket s = server.accept(); //objeto socket para receber pedidos do client

                PrintWriter out = new PrintWriter(s.getOutputStream(), true); //enviar para o client
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); //receber do client

                System.out.println("Conexao realizada: " + s.getInetAddress().getHostAddress()); //mensagem dizendo que foi realizada uma conexao e informa qual o address do client

                ClientHandler clientSock = new ClientHandler(s); //Iniciando Thread
                new Thread(clientSock).start();
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
                    String[] x = in.readLine().split(" "); //recebe a String do client, separando-a usando " " como referencia.

                    if (x[0].charAt(0) == '+' || x[1].charAt(0) == '+') { //evitar um erro que ocorre onde +5 eh enxergado como 5
                        out.println("Operacao invalida.");
                    } else {
                        float n1 = Float.parseFloat(x[0]); //transforma a string em um numeral
                        float n2 = Float.parseFloat(x[1]);
                        double resul = 0; //cria a variavel que vai retornar o resultado da operacao

                        switch (x[2]) { //switch para os casos da operacao usando o operador que esta salvo em x[2], usando Math.round para aproximacao
                            case "+" -> resul = Math.round((n1 + n2) * 100) / 100D; //soma
                            case "_" -> resul = Math.round((n1 - n2) * 100) / 100D; //subtracao
                            case "*" -> resul = (n1 * n2); //multipliccao
                            case "/" -> { //divisao, tratando o erro de divisao por 0
                                if (n2 == 0) {
                                    out.println("Nao se pode dividir por 0.");
                                    break;
                                }
                                resul = (double) n1 / n2;
                            }
                            default -> out.println("Operacao invalida!"); //se nao entrar em nenhum caso
                        }
                        out.println("Resultado: " + resul); //envia resposta pro Client
                    }
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {//tratar erro de formato da operacao
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
