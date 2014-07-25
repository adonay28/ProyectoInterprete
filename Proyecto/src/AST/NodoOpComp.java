package AST;

public class NodoOpComp extends NodoOperador{
	NodoBase ParteIzq;
	NodoBase ParteDer;
	String operacion;
	public NodoOpComp(String tipo, NodoBase parteIzq, NodoBase parteDer) {
		super(tipo);
		ParteIzq = parteIzq;
		ParteDer = parteDer;
	}
	public NodoBase getParteIzq() {
		return ParteIzq;
	}
	public void setParteIzq(NodoBase parteIzq) {
		ParteIzq = parteIzq;
	}
	public NodoBase getParteDer() {
		return ParteDer;
	}
	public void setParteDer(NodoBase parteDer) {
		ParteDer = parteDer;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
}
