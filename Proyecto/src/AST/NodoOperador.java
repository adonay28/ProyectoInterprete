package AST;

public class NodoOperador extends NodoBase{
	String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public NodoOperador(String tipo) {
		super();
		this.tipo = tipo;
	}
}
