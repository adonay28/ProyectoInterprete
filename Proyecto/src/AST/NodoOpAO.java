package AST;

public class NodoOpAO extends NodoOpLog{
	NodoBase LogicaIzq;
	NodoBase LogicaDer;
	public NodoOpAO(String tipo, NodoBase logicaIzq, NodoBase logicaDer) {
		super(tipo);
		LogicaIzq = logicaIzq;
		LogicaDer = logicaDer;
	}
	public NodoBase getLogicaIzq() {
		return LogicaIzq;
	}
	public void setLogicaIzq(NodoBase logicaIzq) {
		LogicaIzq = logicaIzq;
	}
	public NodoBase getLogicaDer() {
		return LogicaDer;
	}
	public void setLogicaDer(NodoBase logicaDer) {
		LogicaDer = logicaDer;
	}
	
}
