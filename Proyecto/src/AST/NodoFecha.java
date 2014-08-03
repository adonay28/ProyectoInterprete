package AST;

public class NodoFecha extends NodoBase{
	String fecha;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public NodoFecha(String fecha) {
		super();
		this.fecha = fecha;
	}
	
}
