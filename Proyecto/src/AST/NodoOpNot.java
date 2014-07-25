package AST;

public class NodoOpNot extends NodoBase{
	NodoBase Hijounico;
	String operacion;
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

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
}
