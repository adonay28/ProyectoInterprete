package AST;

import com.google.common.collect.Table;

public class NodoSeleccion extends NodoBase{
	NodoBase HijoIzq;
	NodoBase HijoDer;
	Table<Integer, String, String> resultado;
	String nombre_tabla;
	public NodoSeleccion(NodoBase hijoIzq, NodoBase hijoDer) {
		super();
		HijoIzq = hijoIzq;
		HijoDer = hijoDer;
	}
	public NodoBase getHijoIzq() {
		return HijoIzq;
	}
	public void setHijoIzq(NodoBase hijoIzq) {
		HijoIzq = hijoIzq;
	}
	public NodoBase getHijoDer() {
		return HijoDer;
	}
	public void setHijoDer(NodoBase hijoDer) {
		HijoDer = hijoDer;
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
