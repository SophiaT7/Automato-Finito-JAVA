import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.*;

public class Main{
	public static void main(String[] args){
		if(args.length !=3){
			System.out.println("<arquivo_automato.aut> <arquivo_entrada.in> <arquivo_saida.out>");
			return;
		}

		String arquivoAutomato=args[0]; // vao verificar se foram pasados como argumento
		String arquivoEntrada=args[1];
		String arquivoSaida=args[2];

		try{
			String conteudoJson=new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(arquivoAutomato)));// usando a lib para ler arquivos, 
			JSONObject json=new JSONObject(conteudoJson);

			Automato automato = new Automato();
			automato.setEstadoInicial(json.getInt("initial"));

			JSONArray finais = json.getJSONArray("final");
			for(int i=0; i<finais.length();i++){
				automato.addEstadoFinal(finais.getInt(i));
			}

			JSONArray transicoes = json.getJSONArray("transitions");
			for(int i=0;i<transicoes.length(); i++){
				JSONObject t=transicoes.getJSONObject(i);
				int from = t.getInt("from");
				int to = t.getInt("to");
				String read = t.has("read") && !t.isNull("read") ? t.getString("read"):null;
				automato.addTransicao(from,read,to);
			}

			BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada));
            BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida));

			String linha;
			while ((linha=br.readLine()) != null){
				String[] partes=linha.split(";");
				if(partes.length<2) continue;

				String palavra = partes[0];
				String esperado = partes[1];
				long inicio = System.nanoTime();// tempo inicio da ação
				boolean resultado = automato.aceitaPalavra(palavra);
				long fim = System.nanoTime(); // tempo fim da ação
				double tempo = (fim-inicio)/1e6; // tempo em segundo

				bw.write(palavra + ";" + esperado + ";" + (resultado ? 1 : 0) + ";" + String.format("%.3f", tempo));
				bw.newLine();
			}

			br.close();
            bw.close();

            System.out.println("Simulação finalizada com sucesso.");
		}catch (Exception e){
			System.out.println("ERRO AO PROCESSAR ARQUIVO"+ e.getMessage());
		}
	}
}
