import java.util.*;

public class Automato{
	public int estadoInicial;
	public List<Integer> estadosFinais=new ArrayList<>();
	public List<Transicao> transicoes=new ArrayList<>();

	public void setEstadoInicial(int estado){
		this.estadoInicial=estado;
	}

	public void addEstadoFinal(int estado){
		estadosFinais.add(estado);
	}

	public void addTransicao(int n, String symbol, int m){
		Transicao t=new Transicao(n,symbol,m);
		transicoes.add(t);
	}

	public List<Integer> mover(int estadoAtual, String 	symbol){
		List<Integer> proximo = new ArrayList<>();
		for(Transicao t:transicoes){
			if(t.from==estadoAtual && Objects.equals(t.read, symbol)){
				proximo.add(t.to);
			}
		}
		return proximo;
	}

	public boolean aceitaPalavra(String palavra){
		return aceitaRecursivo(Set.of(estadoInicial), palavra, 0);
	}

	private boolean aceitaRecursivo(Set<Integer> atual, String palavra, int pos ){
		if(pos==palavra.length()){
			for(int estado:atual){
				if(estadosFinais.contains(estado)) return true;
				for(int prox:mover(estado,null)){
					if(estadosFinais.contains(prox)) return true;
				}
			}
			return false;
		}


	String symbol=String.valueOf(palavra.charAt(pos));
	Set<Integer> prox = new HashSet<>();

	for(int estado:atual){
		prox.addAll(mover(estado, symbol));
		prox.addAll(mover(estado, null));// epsilon
	}

	return aceitaRecursivo(prox, palavra, pos+1);
	}
}
