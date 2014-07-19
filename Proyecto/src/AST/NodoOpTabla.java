package AST;

public class NodoOpTabla extends NodoBase{
	String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public NodoOpTabla(String tipo) {
		super();
		this.tipo = tipo;
	}
}
