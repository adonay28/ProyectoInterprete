package AST;

import com.google.common.collect.Table;

public class NodoOpAO extends NodoOpLog{
	NodoBase LogicaIzq;
	NodoBase LogicaDer;
	String operacion;
	Table<Integer, String, String> resultado;
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
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public Table<Integer, String, String> getResultado() {
		return resultado;
	}
	public void setResultado(Table<Integer, String, String> resultado) {
		this.resultado = resultado;
	}
	
}
