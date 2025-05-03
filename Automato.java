import java.util.*;

public class Automato{
	public int estadoInicial;
	public List<Integer> estadosFinais=new ArrayList<>();
	public List<Transicao> transicoes=new ArrayList<>();

	public void setEstadoInicial(int estado){//define estado inicial
		this.estadoInicial=estado;
	}

	public void addEstadoFinal(int estado){//adiciona estado a lista de estado finais
		estadosFinais.add(estado);
	}

	public void addTransicao(int n, String symbol, int m){// add transicao, estado n (anterior) para o m (proximo) estado com simbolo (symbol)
		Transicao t=new Transicao(n,symbol,m);
		transicoes.add(t);
	}

	public List<Integer> mover(int estadoAtual, String 	symbol){
		List<Integer> proximo = new ArrayList<>();// proximo sera colocado na lista
		for(Transicao t:transicoes){// percorrre todas as transicoes
			if(t.from==estadoAtual && Objects.equals(t.read, symbol)){// se a transicao é igual ao estado atual
				proximo.add(t.to);//e se o simbolo e igual ao simbolo do estado atual s(ou null)
			}//add o estado destino a lista de proximo estados
		}
		return proximo;// retorna lista com estados alcancado
	}

	public boolean aceitaPalavra(String palavra){ // verifica a palavra
		return aceitaRecursivo(Set.of(estadoInicial), palavra, 0);// comeca pelo estado inicial 0
	}

	private boolean aceitaRecursivo(Set<Integer> atual, String palavra, int pos ){// recebe estado atual, palavra lida e a posicoa da palavra
		if(pos==palavra.length()){// se chagar ao fim da palavra
			for(int estado:atual){
				if(estadosFinais.contains(estado)) return true;//verifica se algum estado eh final
				for(int prox:mover(estado,null)){// ou se encontra algum estado epsilon que eh final(chama met mover)
					if(estadosFinais.contains(prox)) return true;//ent a lista de estaos finais possui o prox estado
				}
			}
			return false;
		}


	String symbol=String.valueOf(palavra.charAt(pos));// le simbolo atual
	Set<Integer> prox = new HashSet<>();//aqui cria-se um conjutno de prox estados unicos

	for(int estado:atual){// p cada estado atual
		prox.addAll(mover(estado, symbol));// prox estados possiveis com o simbolo
		prox.addAll(mover(estado, null));// considera epsilon
	}

	return aceitaRecursivo(prox, palavra, pos+1);// chama o recursivo com o novo estado que eh o prox e avanca ba palavra com pos+1
	}
}
