package AST;

import com.google.common.collect.Table;

public class NodoOpC extends NodoOpTabla{
	NodoBase TablaIzq;
	NodoBase TablaDer;
	Table<Integer, String, String> resultado;
	String nombre_tabla;
	public NodoBase getTablaIzq() {
		return TablaIzq;
	}
	public void setTablaIzq(NodoBase tablaIzq) {
		TablaIzq = tablaIzq;
	}
	public NodoBase getTablaDer() {
		return TablaDer;
	}
	public void setTablaDer(NodoBase tablaDer) {
		TablaDer = tablaDer;
	}
	public NodoOpC(String tipo, NodoBase tablaIzq, NodoBase tablaDer) {
		super(tipo);
		TablaIzq = tablaIzq;
		TablaDer = tablaDer;
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
