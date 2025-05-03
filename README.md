# Automato-Finito-JAVA
finite automaton simulator made with JAVA


> Implementação de um Simulador de Autômatos Finitos Determinísticos ou Não Determinísticos


Realiza:
Leitura do Autómato através de um arquivo ".aut"(JSON) que possui:
-Estado Inicial;
-Lista de Estados Finais
-Lista de Transições (sendo ou não vazias)


> Leitura das Palavras através do arq ".in" --> palavra;valorEsperado


> Processamento das Palavras funciona através de tratamento delas, se são aceitas ou não pelo autômato


> Arquivo de Saida ".out" que possuí --> palavra;valorEsperado;resultadoObtido;tempoExucacao

_____________________________
> Arquivos:
.aut:
initial:0, --> estado inicial
final:[2], --> estados finais (lista de inteiros)
tansitions: ["from":0, "to":0, "read": "a"] --> transições requeridas


.in:
aabab;1 --> palavra;valorEsperado
bbba;0


.out:
aabab;1;1;0.234 --> palavra;valorEsperado;valorFinal;tempoExecucao

_____________________________
>Como Executar?


>compile:
javac -cp ".:json.jar" Main.java Automato.java Transicao.java


>execute:
java -cp ".:json.jar" Main arquivo.aut entrada.in saida.out
