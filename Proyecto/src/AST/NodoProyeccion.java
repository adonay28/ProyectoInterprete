package AST;

import com.google.common.collect.Table;

public class NodoProyeccion extends NodoBase{
	NodoBase HijoIzq;
	NodoBase HijoDer;
	Table<Integer, String, String> resultado;
	public NodoProyeccion(NodoBase hijoIzq, NodoBase hijoDer) {
		super();
		HijoIzq = hijoIzq;
		HijoDer = hijoDer;
	}
	public NodoBase getHijoIzq() {
		return HijoIzq;
	}
	public void setHijoIzq(NodoBase hijoIzq) {
		HijoIzq = hijoIzq;
	}
	public NodoBase getHijoDer() {
		return HijoDer;
	}
	public void setHijoDer(NodoBase hijoDer) {
		HijoDer = hijoDer;
	}
	public Table<Integer, String, String> getResultado() {
		return resultado;
	}
	public void setResultado(Table<Integer, String, String> resultado) {
		this.resultado = resultado;
	}
	
}
