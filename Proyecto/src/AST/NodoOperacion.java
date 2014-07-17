package AST;

public class NodoOperacion extends NoboBase {
	
	String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public NodoOperacion(String tipo) {
		super();
		this.tipo = tipo;
	}
	
	

}
