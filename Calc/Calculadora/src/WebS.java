import static spark.Spark.*;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WebS {
    public static void main(String[] args) {

        port(8081); //inicializando webservice na porta 8081
        System.out.println("Webservice inicializado!");

        //Spark.threadPool(999);

        get("/calc/r/:op2", (req, res) -> {
            String op = "r";
            String op1 = "0";
            String op2 = req.params("op2");
            String dados = op1 +" "+ op2 +" "+ op;
            try (Socket socket = new Socket("localhost", 1243)) {

                System.out.println("Conectado com sucesso ao Server2!");

                //Receber do Server
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //Mandar pro Server
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println(dados);
                String resul = in.readLine();
                return resul;

            } catch (IOException e) {
                return "Nao foi possivel conectar ao servidor!";
            }
        } ); //caso a parte de raiz
        get("/calc/:op1/:op/:op2", new Server()); //indentificar a qual servidor se conectara
        get("*", (req, res) -> "#CALCULADORA DISTRIBUIDA#<br><br>" + //Descricao da calculadora nos caminhos sem "operacao"
                "Operacoes Disponiveis (substitua x e y pelo valor desejado):<br>" +
                " Adicao: x/+/y <br>" +
                " Subtracao: x/_/y <br>" +
                " Multiplicacao: x/*/y <br>" +
                " Divisao: x/div/y <br>" +
                " Potenciacao: x/^/y <br>" +
                " Porcentagem: x/per/y <br>" +
                " Raiz quadrada: r/x");
    }
        private static class Server implements Route {
        @Override
        public String handle(Request req, Response res) {
            String op = req.params(":op"); //passa o parametro para um String
                if (op.equals("per")) {op = "%";}; //mudando per e div para os sinais usado no Server2, pois % e / apresentavam erros como "url"
                if (op.equals("div")) {op = "/";};
            String op1 = req.params(":op1");
            String op2 = req.params("op2");
            String dados = op1 +" "+ op2 +" "+ op; //junta as Strings em 1 so delimitadando por " "
            if (op.equals("+") || op.equals("_") || op.equals("*") || op.equals("/")) { // if para indentificar se conectara ao Server1
                try (Socket socket = new Socket("localhost", 1234)) {

                    System.out.println("Conectado com sucesso ao Server1!");//log de conexao

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    out.println(dados); //envia a String pro server
                    String resul = in.readLine(); //recebe resposta do server
                    return resul;

                } catch (IOException e) {
                    return "Nao foi possivel conectar ao servidor!";
                }

            } else if (op.equals("%") || op.equals("^")) { // if para indentificar se conectara ao Server2
                try (Socket socket = new Socket("localhost", 1243)) {

                    System.out.println("Conectado com sucesso ao Server2!");//log de conexao

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    out.println(dados); //envia a String pro server
                    String resul = in.readLine(); //recebe resposta do server
                    return resul;

                } catch (IOException e) {
                    return "Nao foi possivel conectar ao servidor!";
                }
            } else {
                return ("Operacao invalida!");//Se "op" nao for nenhum operador
            }
        }
    }
}