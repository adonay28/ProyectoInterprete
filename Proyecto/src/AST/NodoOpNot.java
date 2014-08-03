package AST;

import com.google.common.collect.Table;

public class NodoOpNot extends NodoBase{
	NodoBase Hijounico;
	Table<Integer, String, String> resultado;
	String nombre_tabla;
	public NodoOpNot(NodoBase hijounico) {
		super();
		Hijounico = hijounico;
	}

	public NodoBase getHijounico() {
		return Hijounico;
	}

	public void setHijounico(NodoBase hijounico) {
		Hijounico = hijounico;
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
