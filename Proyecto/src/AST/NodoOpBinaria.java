package AST;


public class NodoOpBinaria extends NodoOperacion {

	NodoBase operandoIzquierdo;
	NodoBase operandoDerecho;
	String operacion;
	public NodoBase getOperandoIzquierdo() {
		return operandoIzquierdo;
	}
	public void setOperandoIzquierdo(NodoBase operandoIzquierdo) {
		this.operandoIzquierdo = operandoIzquierdo;
	}
	public NodoBase getOperandoDerecho() {
		return operandoDerecho;
	}
	public void setOperandoDerecho(NodoBase operandoDerecho) {
		this.operandoDerecho = operandoDerecho;
	}
	public NodoOpBinaria(String tipo, NodoBase operandoIzquierdo,
			NodoBase operandoDerecho) {
		super(tipo);
		this.operandoIzquierdo = operandoIzquierdo;
		this.operandoDerecho = operandoDerecho;
		operacion = "";
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	
	
}
