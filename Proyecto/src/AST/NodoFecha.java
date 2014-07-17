package AST;

import java.util.Date;

public class NodoFecha extends NoboBase{
	Date fecha;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public NodoFecha(Date fecha) {
		super();
		this.fecha = fecha;
	}
	
}
