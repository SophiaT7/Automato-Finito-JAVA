import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.*;

public class Main{
	public static void main(String[] args){
		if(args.length !=3){//aceita 3 arg -aut, in e out
			System.out.println("<arquivo_automato.aut> <arquivo_entrada.in> <arquivo_saida.out>");
			return;
		}

		String arquivoAutomato=args[0]; // vao verificar se foram pasados como argumento
		String arquivoEntrada=args[1];
		String arquivoSaida=args[2];

		try{
			String conteudoJson=new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(arquivoAutomato)));// usando a lib para ler arquivos,
			JSONObject json=new JSONObject(conteudoJson);//string em um json p ser usado
			// essa parte acima vai fazer com que o java leia os arq
			Automato automato = new Automato();//instacia automato
			automato.setEstadoInicial(json.getInt("initial"));//define estado inicial lido do json

			JSONArray finais = json.getJSONArray("final");// le lista final do json
			for(int i=0; i<finais.length();i++){
				automato.addEstadoFinal(finais.getInt(i));// add estados finais ao automato
			}

			JSONArray transicoes = json.getJSONArray("transitions");//array qye tem as transicoes
			for(int i=0;i<transicoes.length(); i++){//le o tamanho todo  de transicoes(perceorre)
				JSONObject t=transicoes.getJSONObject(i);//acessa o n objeto do json e armaz na var "t"
				int from = t.getInt("from");//le o estado d origem
				int to = t.getInt("to");// o destino
				String read = t.has("read") && !t.isNull("read") ? t.getString("read"):null;// e o simbolo, se caso for ausente eh um epsilon
				automato.addTransicao(from,read,to);// add a transicao ao automato
			}

			BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada));// le arq .in
            BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida));// grava resultado no arw .out

			String linha;
			while ((linha=br.readLine()) != null){// processando cada linha do arq .in que contem conteudo
				String[] partes=linha.split(";");// cada linha deve conter palavra ; esperado
				if(partes.length<2) continue;

				String palavra = partes[0];
				String esperado = partes[1];
				long inicio = System.nanoTime();// tempo inicio da ação
				boolean resultado = automato.aceitaPalavra(palavra);// true (1) se palavra foi aceita
				long fim = System.nanoTime(); // tempo fim da ação
				double tempo = (fim-inicio)/1e6; // tempo em segundo

				bw.write(palavra + ";" + esperado + ";" + (resultado ? 1 : 0) + ";" + String.format("%.3f", tempo));
				bw.newLine();
			}

			br.close();//fecha os arq de leitura e escrita p o json .out
            bw.close();

            System.out.println("Simulação finalizada com sucesso.");
		}catch (Exception e){// caso algo de errado mostra mensagem de erro e que lugar foi o erro
			System.out.println("ERRO AO PROCESSAR ARQUIVO"+ e.getMessage());
		}
	}
}
