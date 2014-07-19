package AST;

public class NodoComa extends NodoBase{
	NodoBase HijoIzq;
	NodoBase HijoDer;
	public NodoComa(NodoBase hijoIzq, NodoBase hijoDer) {
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
}
