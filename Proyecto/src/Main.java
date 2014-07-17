


public class Main {
	public static void main(String args[]) throws Exception{
		parser analizador;
		analizador = new parser( new Yylex(System.in) );
		analizador.parse();
	}
}
