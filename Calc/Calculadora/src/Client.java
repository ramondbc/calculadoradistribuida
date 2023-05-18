import java.io.*;
import java.net.*;
import java.util.*;

class Client {

    public static void main(String[] args)
    {
        //descricao para o usuario
        System.out.println("#CALCULADORA DISTRUIDA#\n");
        System.out.println("Operecoes Disponiveis (substitua x e y pelo valor desejado): \n Adicao: x+y \n Subtracao: x_y \n Multiplicacao: x*y \n Divisao: x/y \n Potenciacao: x^y \n Porcentagem: x%y \n Raiz quadrada: rx\n");

        Scanner sc = new Scanner(System.in);
        String line = null; //String onde ficara salvo o input do usuario

        while (!"bye".equalsIgnoreCase(line)) { //"bye" eh a String para sair

            String resul = null; // resetando dado da String dentro do while

            System.out.print("Digite a operacao que deseja: ");
            line = sc.nextLine(); // le input do usuario
            line = line.replaceAll("\\s", ""); // troca todos " " por ""

            for (int i = 0; i < line.length(); i++) { // for para checar cada char da String
                if (line.charAt(i) == '+' || line.charAt(i) == '_' || line.charAt(i) == '*' || line.charAt(i) == '/') { // if para indentificar se conectara ao Server1

                    String ope = String.valueOf(line.charAt(i)); // salvando o char atual (que seria o operador) em uma String
                    i = line.length(); // atualiza i para nao precisar continuar rodando o for

                    try (Socket socket = new Socket("localhost", 1234)) { // tentando conectar com o Server1

                        //Receber do Server
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        //Mandar pro Server
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        
                        String[] num = line.split("\\%s".formatted(ope), 2); // separa a String em 2, usando o operador como referencia de separacao

                        String[] x = new String[3]; // array de String para ter as informacoes de operador e operando
                        x[0] = num[0];
                        x[1] = num[1];
                        x[2] = ope;

                        out.println(String.join(" ", x)); // transforma o array de String em uma String so e envia para o server

                        resul = in.readLine(); // recebe resposta o server
                        System.out.println(resul); // exibe resposta do sevidor

                    } catch (IOException e) { //catch para tratar erros/exceptions ao tentar conectar com o servidor
                        System.out.println("Nao foi possivel conectar ao servidor.");
                    } //catch server 1
                } //if server1
                else if (line.charAt(i) == '%' || line.charAt(i) == '^') { //if para indentificar se conectara ao Server2

                    String ope = String.valueOf(line.charAt(i)); // salvando o char atual (que seria o operador) em uma String
                    i = line.length(); // atualiza i para nao precisar continuar rodando o for

                    try (Socket socket = new Socket("localhost", 1243)) { //tenta conectar com o server2

                        //Receber do Server
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        //Mandar pro Server
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        String[] num = line.split("\\%s".formatted(ope), 2); //divide a String em 2, usando o operador como referencia

                        String[] x = new String[3]; //array de String para ter as informacoes de operador e operando
                        x[0] = num[0];
                        x[1] = num[1];
                        x[2] = ope;

                        out.println(String.join(" ", x)); //transforma o array de String em uma String so e envia para o server

                        resul = in.readLine(); //recebe a resposta do server
                        System.out.println(resul);//exibe a resposta do server

                    } catch (IOException e) {//catch para tratar erros/exceptions ao tentar conectar com o servidor
                        System.out.println("Nao foi possivel conectar ao servidor.");
                    } //catch server2.1
                } //if server2.1
                else if (i == 0 && line.charAt(i) == 'r') {//if separado para checar operacao de raiz que so envia 1 numero para o server2
                   
                    i = line.length(); // atualiza i para nao precisar continuar rodando o for

                    try (Socket socket = new Socket("localhost", 1243)) {//tenta conectar com o server2

                        //Receber do Server
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        //Mandar pro Server
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        String r = "0" + line; //Fazendo isso pra conseguir deixar o ope como x[2]
                        String[] num = r.split("r", 2);//separa a String em 2, usando o "r" como referencia

                        String[] x = new String[3]; //array de String para ter as informacoes de operador e operando r
                        x[0] = num[0];
                        x[1] = num[1];
                        x[2] = "r"; //r eh o operador da raiz quadrada

                        out.println(String.join(" ", x)); //transforma o array de String em uma String so e envia para o server

                        resul = in.readLine(); //recebe a resposta do server
                        System.out.println(resul); //exibe a resposta do server

                    } catch (IOException e) {//catch para tratar erros/exceptions ao tentar conectar com o servidor
                        System.out.println("Nao foi possivel conectar ao servidor.");
                    } //catch server2.2
                } //if server2.2
                else if (i == line.length() - 1 && !line.equals("bye")){ //se chegar no ultimo char sem entrar em algum if anterior, eh uma operacao invalida
                    System.out.println("Operacao invalida.");
                }
            } //for
        } //while
        sc.close();
    } //main
} //class
