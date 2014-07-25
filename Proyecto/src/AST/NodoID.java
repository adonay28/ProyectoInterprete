package AST;

public class NodoID extends NodoBase{
	String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

	public NodoID(String cadena) {
		super();
		this.cadena = cadena;
	}
	
}
