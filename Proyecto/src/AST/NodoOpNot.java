package AST;

public class NodoOpNot extends NodoBase{
	NodoBase Hijounico;

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
	
}
