package AST;

public class NodoCadena extends NodoBase{
	String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public NodoCadena(String cadena) {
		super();
		this.cadena = cadena;
	}
	
}
