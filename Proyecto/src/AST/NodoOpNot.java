package AST;

import com.google.common.collect.Table;

public class NodoOpNot extends NodoBase{
	NodoBase Hijounico;
	Table<Integer, String, String> resultado;
	public NodoOpNot(NodoBase hijounico) {
		super();
		Hijounico = hijounico;
	}

	public NodoBase getHijounico() {
		return Hijounico;
	}

	public void setHijounico(NodoBase hijounico) {
		Hijounico = hijounico;
	}

	public Table<Integer, String, String> getResultado() {
		return resultado;
	}

	public void setResultado(Table<Integer, String, String> resultado) {
		this.resultado = resultado;
	}
	
}
