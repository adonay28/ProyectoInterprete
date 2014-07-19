package AST;

public class NodoOpC extends NodoOpTabla{
	NodoBase TablaIzq;
	NodoBase TablaDer;
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
	
}
