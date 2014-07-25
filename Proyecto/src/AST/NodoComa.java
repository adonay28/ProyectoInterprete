package AST;

import java.util.ArrayList;
import java.util.List;

public class NodoComa extends NodoBase{
	NodoBase HijoIzq;
	NodoBase HijoDer;
	List<String> atributos;
	public NodoComa(NodoBase hijoIzq, NodoBase hijoDer) {
		super();
		HijoIzq = hijoIzq;
		HijoDer = hijoDer;
		atributos = new ArrayList<String>();
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
	public List<String> getAtributos() {
		return atributos;
	}
	public void setAtributos(List<String> atributos) {
		this.atributos = atributos;
	}
	
}
