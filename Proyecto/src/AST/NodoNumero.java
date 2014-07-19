package AST;

public class NodoNumero extends NodoBase{
	Integer numero;
	
	
	public NodoNumero(Integer numero) {
		super();
		this.numero = numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public Integer getNumero() {
		return numero;
	}
}
