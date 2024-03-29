package AST;

import com.google.common.collect.Table;

public class NodoOpComp extends NodoOperador{
	NodoBase ParteIzq;
	NodoBase ParteDer;
	Table<Integer, String, String> resultado;
	String nombre_tabla;
	public NodoOpComp(String tipo, NodoBase parteIzq, NodoBase parteDer) {
		super(tipo);
		ParteIzq = parteIzq;
		ParteDer = parteDer;
	}
	public NodoBase getParteIzq() {
		return ParteIzq;
	}
	public void setParteIzq(NodoBase parteIzq) {
		ParteIzq = parteIzq;
	}
	public NodoBase getParteDer() {
		return ParteDer;
	}
	public void setParteDer(NodoBase parteDer) {
		ParteDer = parteDer;
	}
	public Table<Integer, String, String> getResultado() {
		return resultado;
	}
	public void setResultado(Table<Integer, String, String> resultado) {
		this.resultado = resultado;
	}
	public String getNombre_tabla() {
		return nombre_tabla;
	}
	public void setNombre_tabla(String nombre_tabla) {
		this.nombre_tabla = nombre_tabla;
	}
	
}
