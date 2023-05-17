# Calculadora Distribuida

> Disciplina: Sistemas Distribuidos<br>
> Faculdade de Petrolina<br>
> 8º Período<br>
> Equipe: Jessica e Ramon

## Descrições do projeto

Projeto de uma calculadora distribuida utilizando sockets em Java.

### Etapas

O projeto é composto de três etapas. Um cliente e dois servidores.

#### Cliente

- O Cliente faz a checagem para saber com qual Servidor precisará se comunicar.
- Ele envia uma String, com as informações que o usuário digitou, para o Servidor específico.
- Operações disponíveis (substitua x e y pelo valor desejado):
    1) Adição: `x+y`
    1) Subtração: `x_y`
    1) Multiplicação: `x*y`
    1) Divisão: `x/y`
    1) Potenciação: `x^y`
    1) Porcentagem: `x%y`
    1) Raiz quadrada: `rx`

#### Servidor de operações básicas

- O Servidor conecta pela port `1234` com o Cliente. E, recebe informações via Socket.
- Realiza as operações
    1) Adição
    1) Subtração
    1) Multiplicação
    1) Divisão
- Retorna o resultado em String para o Cliente

#### Servidor de operações especiais

- O Servidor conecta pela port `1243` com o Cliente. E, recebe informações via Socket.
- Realiza as operações
    1) Potenciação
    1) Porcentagem
    1) Raiz quadrada
- Retorna o resultado em String para o Cliente
