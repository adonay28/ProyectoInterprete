package AST;

public class NodoOpLog extends NodoBase{
	String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public NodoOpLog(String tipo) {
		super();
		this.tipo = tipo;
	}
}
