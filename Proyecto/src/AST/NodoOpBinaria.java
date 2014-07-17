package AST;


public class NodoOpBinaria extends NodoOperacion {

	NoboBase operandoIzquierdo;
	NoboBase operandoDerecho;
	public NoboBase getOperandoIzquierdo() {
		return operandoIzquierdo;
	}
	public void setOperandoIzquierdo(NoboBase operandoIzquierdo) {
		this.operandoIzquierdo = operandoIzquierdo;
	}
	public NoboBase getOperandoDerecho() {
		return operandoDerecho;
	}
	public void setOperandoDerecho(NoboBase operandoDerecho) {
		this.operandoDerecho = operandoDerecho;
	}
	public NodoOpBinaria(String tipo, NoboBase operandoIzquierdo,
			NoboBase operandoDerecho) {
		super(tipo);
		this.operandoIzquierdo = operandoIzquierdo;
		this.operandoDerecho = operandoDerecho;
	}

	
	
}
