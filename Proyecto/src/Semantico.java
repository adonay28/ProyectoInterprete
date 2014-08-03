import java.util.ArrayList;
import java.util.List;

import AST.NodoCadena;
import AST.NodoComa;
import AST.NodoFecha;
import AST.NodoID;
import AST.NodoNumero;
import AST.NodoOpAO;
import AST.NodoOpBinaria;
import AST.NodoOpC;
import AST.NodoOpComp;
import AST.NodoOpNot;
import AST.NodoProyeccion;
import AST.NodoSeleccion;

import com.google.common.collect.Table;


public class Semantico {
	Tablas tablas;
	public Semantico() {
		// TODO Auto-generated constructor stub
		tablas = new Tablas("Relaciones/");
	}
	public void obtener_resultado(Object arbol){
		tablas.mostrar_tablas(obtener_resultado_nodo(recorrer_arbol(arbol,null)));
	}
	
	public Object recorrer_arbol(Object arbol, Table<Integer, String, String> tabla){
		if(arbol instanceof NodoProyeccion){
			System.out.println("NodoProyeccion");
			NodoProyeccion pro = (NodoProyeccion) arbol;
			Object hijoizq = recorrer_arbol(pro.getHijoIzq(),tabla);
			List<String> atributos = new ArrayList<String>();
			if(hijoizq instanceof NodoID) {
				System.out.println("Un solo atributo");
				NodoID nodoid_izq = (NodoID) hijoizq;
				atributos.add(nodoid_izq.getCadena());
			}
			else{
				if(hijoizq instanceof NodoComa){
					System.out.println("Varios atributos");
					NodoComa coma = (NodoComa) hijoizq;
					atributos.addAll(coma.getAtributos());
				}
				else{
					System.out.println("Error: el hijo izq de la proyeccion no es un id ni una lista de atributos");
					System.exit(1);
				}
			}
			Object hijoder = recorrer_arbol(pro.getHijoDer(),tabla);
			if(hijoder instanceof NodoID){
				System.out.println("Es un identificador a la derecha de la proyeccion");
				NodoID nodoid_der = (NodoID) hijoder;
				//Hacer operacion sobre la tabla y guardar el resultado
				//pro.setResultado("Es una proyeccion de la tabla " + nodoid_der.getCadena() + " de los atributos" + atributos);
				pro.setResultado(tablas.Proyeccion(atributos, tablas.obtener_table(nodoid_der.getCadena())));
			}
			else{
				System.out.println("Es un resultado de una expresion");
				//obtener resultado, hacer la proyeccion y guardar en resultado
				pro.setResultado(tablas.Proyeccion(atributos, obtener_resultado_nodo(hijoder)));
				
			}
			return pro;
		}
		if(arbol instanceof NodoComa){
			System.out.println("NodoComa");
			NodoComa coma = (NodoComa) arbol;
			Object hijoizq = recorrer_arbol(coma.getHijoIzq(),tabla);
			if(hijoizq instanceof NodoID){
				System.out.println("Nodoid izq");
				NodoID nodoid_izq = (NodoID) hijoizq;
				coma.getAtributos().add(nodoid_izq.getCadena());
			}
			else{
				if(hijoizq instanceof NodoComa){
					System.out.println("NodoComa izq");
					NodoComa comaizq = (NodoComa) hijoizq;
					coma.getAtributos().addAll(comaizq.getAtributos());
				}
				else{
					if(hijoizq instanceof NodoOpBinaria){
						System.out.println("NodoComa izq");
						NodoOpBinaria opbin_izq = (NodoOpBinaria) hijoizq;
						coma.getAtributos().add(opbin_izq.getOperacion());
					}
					else{
						System.out.println("Error: ninguno de los hijos izq es ID, coma o Operacionbinaria");
						System.exit(1);
					}
				}
			}
			Object hijoder = recorrer_arbol(coma.getHijoDer(),tabla);
			if(hijoder instanceof NodoID){
				System.out.println("Nodoid izq");
				NodoID nodoid_der = (NodoID) hijoder;
				coma.getAtributos().add(nodoid_der.getCadena());
			}
			else{
				if(hijoder instanceof NodoComa){
					System.out.println("NodoComa izq");
					NodoComa comader = (NodoComa) hijoder;
					coma.getAtributos().addAll(comader.getAtributos());
				}
				else{
					if(hijoder instanceof NodoOpBinaria){
						System.out.println("NodoComa izq");
						NodoOpBinaria opbin_der = (NodoOpBinaria) hijoder;
						coma.getAtributos().add(opbin_der.getOperacion());
					}
					else{
						System.out.println("Error: ninguno de los hijos der es ID, coma o Operacionbinaria");
						System.exit(1);
					}
				}
			}
			return coma;
		}
		if(arbol instanceof NodoOpBinaria){
			System.out.println("NodoOpbinaria");
			NodoOpBinaria opBinaria = (NodoOpBinaria) arbol;
			Object hijoizq = recorrer_arbol(opBinaria.getOperandoIzquierdo(),tabla);
			if(hijoizq instanceof NodoNumero){
				System.out.println("NodoNumero izq");
				NodoNumero nodonum_izq = (NodoNumero) hijoizq;
				opBinaria.setOperacion(opBinaria.getOperacion() + nodonum_izq.getNumero());
			}
			else{
				if(hijoizq instanceof NodoOpBinaria){
					System.out.println("NodoopBinaria izq");
					NodoOpBinaria opbinaria_izq = (NodoOpBinaria) hijoizq;
					opBinaria.setOperacion(opBinaria.getOperacion() + opbinaria_izq.getOperacion());
				}
				else{
					if(hijoizq instanceof NodoID){
						System.out.println("NodoID izq");
						NodoID nodoid_izq = (NodoID) hijoizq;
						opBinaria.setOperacion(opBinaria.getOperacion() + nodoid_izq.getCadena());
					}
					else{
						System.out.println("Error: parte izq de opbinaria no es un id,opbinaria,numero");
						System.exit(1);
					}
				}
			}
			opBinaria.setOperacion(opBinaria.getOperacion() + opBinaria.getTipo());
			Object hijoder = recorrer_arbol(opBinaria.getOperandoDerecho(),tabla);
			if(hijoder instanceof NodoNumero){
				System.out.println("NodoNumero der");
				NodoNumero nodonum_der = (NodoNumero) hijoder;
				opBinaria.setOperacion(opBinaria.getOperacion() + nodonum_der.getNumero());
			}
			else{
				if(hijoder instanceof NodoOpBinaria){
					System.out.println("NodoopBinaria der");
					NodoOpBinaria opbinaria_der = (NodoOpBinaria) hijoder;
					opBinaria.setOperacion(opBinaria.getOperacion() + opbinaria_der.getOperacion());
				}
				else{
					if(hijoder instanceof NodoID){
						System.out.println("NodoID der");
						NodoID nodoid_der = (NodoID) hijoder;
						opBinaria.setOperacion(opBinaria.getOperacion() + nodoid_der.getCadena());
					}
					else{
						System.out.println("Error: parte izq de opbinaria no es un id,opbinaria,numero");
						System.exit(1);
					}
				}
			}
			return opBinaria;
		}
		if (arbol instanceof NodoSeleccion) {
			System.out.println("NodoSeleccion");
			NodoSeleccion sel = (NodoSeleccion) arbol;
			Object hijoder = recorrer_arbol(sel.getHijoDer(), tabla);
			Table<Integer, String, String> tabla_der;
			if (hijoder instanceof NodoID) {
				System.out
						.println("Es un identificador a la derecha de la seleccion");
				NodoID nodoid_der = (NodoID) hijoder;
				tabla_der = tablas.obtener_table(nodoid_der.getCadena());
			} else {
				System.out.println("Es un resultado de una expresion");
				tabla_der = obtener_resultado_nodo(hijoder);
			}
			Object hijoizq = recorrer_arbol(sel.getHijoIzq(), tabla_der);
			if (hijoizq instanceof NodoOpAO) {
				System.out.println("NodoOpAO izq");
				NodoOpAO ao_izq = (NodoOpAO) hijoizq;
				sel.setResultado(ao_izq.getResultado());
			} else {
				if (hijoizq instanceof NodoOpComp) {
					System.out.println("NodoOpComp izq");
					NodoOpComp opcomp_izq = (NodoOpComp) hijoizq;
					sel.setResultado(opcomp_izq.getResultado());
				} else {
					if (hijoizq instanceof NodoOpNot) {
						System.out.println("NodoOpNot izq");
						NodoOpNot opnot_izq = (NodoOpNot) hijoizq;
						sel.setResultado(opnot_izq.getResultado());
					} else {
						System.out
								.println("Error: el hijo izq de Seleccion no es una comparacion, ANDOR, NOT");
						System.exit(1);
					}
				}
			}
			return sel;
		}
		if (arbol instanceof NodoOpAO) {
			System.out.println("NodoOpAO");
			NodoOpAO nodoAO = (NodoOpAO) arbol;
			Object hijoizq = recorrer_arbol(nodoAO.getLogicaIzq(), tabla);
			Table<Integer, String, String> tabla_der = tabla, tabla_izq = tabla;
			if (hijoizq instanceof NodoOpComp) {
				System.out.println("NodoOpComp izq");
				NodoOpComp opcomp_izq = (NodoOpComp) hijoizq;
				tabla_izq = opcomp_izq.getResultado();
			} else {
				if (hijoizq instanceof NodoOpNot) {
					System.out.println("NodoOpNot izq");
					NodoOpNot opnot_izq = (NodoOpNot) hijoizq;
					tabla_izq = opnot_izq.getResultado();
				} else {
					System.out
							.println("Error: el hijo izq de OpAND-OR no es una comparacion,NOT");
					System.exit(1);
				}
			}
			Object hijoder = recorrer_arbol(nodoAO.getLogicaDer(), tabla);
			if (hijoder instanceof NodoOpComp) {
				System.out.println("NodoOpComp der");
				NodoOpComp opcomp_der = (NodoOpComp) hijoder;
				tabla_der = opcomp_der.getResultado();
			} else {
				if (hijoder instanceof NodoOpNot) {
					System.out.println("NodoOpNot der");
					NodoOpNot opnot_der = (NodoOpNot) hijoder;
					tabla_der = opnot_der.getResultado();
				} else {
					System.out
							.println("Error: el hijo der de OpAND-OR no es una comparacion,NOT");
					System.exit(1);
				}
			}
			//Falta hacer la interseccion o la union dependiendo del tipo
			if(nodoAO.getTipo().equals("AND")){
				nodoAO.setResultado(tablas.Interseccion(tabla_izq, tabla_der));
			}
			else if(nodoAO.getTipo().equals("OR"))
				nodoAO.setResultado(tablas.Union(tabla_izq, tabla_der));
			return nodoAO;
		}
		if (arbol instanceof NodoOpComp) {
			System.out.println("NodoOpComp");
			NodoOpComp nodoopcomp = (NodoOpComp) arbol;
			String id = "";
			Object hijoizq = recorrer_arbol(nodoopcomp.getParteIzq(), tabla);
			if (hijoizq instanceof NodoID) {
				System.out.println("NodoID izq");
				NodoID nodoid_izq = (NodoID) hijoizq;
				id = nodoid_izq.getCadena();
			} else {
				System.out.println("Error: el hijo izq de la comparacion no es un identificador");
				System.exit(1);
			}
			Object hijoder = recorrer_arbol(nodoopcomp.getParteDer(), tabla);
			if (hijoder instanceof NodoFecha) {
				System.out.println("NodoFecha der");
				NodoFecha nodofecha_der = (NodoFecha) hijoder;
				nodoopcomp.setResultado(tablas.Seleccion(tabla, nodoopcomp.getTipo(), id, nodofecha_der));
			} else {
				if (hijoder instanceof NodoNumero) {
					System.out.println("NodoNumero der");
					NodoNumero nodonum_der = (NodoNumero) hijoder;
					nodoopcomp.setResultado(tablas.Seleccion(tabla, nodoopcomp.getTipo(), id, nodonum_der));
				} else {
					if (hijoder instanceof NodoCadena) {
						System.out.println("NodoCadena der");
						NodoCadena nodocade_der = (NodoCadena) hijoder;
						nodoopcomp.setResultado(tablas.Seleccion(tabla, nodoopcomp.getTipo(), id, nodocade_der));
					} else {
						System.out.println("Error: el hijo der de la comparacion no es un identificador,numero o fecha");
						System.exit(1);
					}
				}
			}
			return nodoopcomp;
		}
		if (arbol instanceof NodoOpNot) {
			System.out.println("NodoOpNot");
			NodoOpNot nodoopnot = (NodoOpNot) arbol;
			Object hijo = recorrer_arbol(nodoopnot.getHijounico(), tabla);
			if (hijo instanceof NodoOpComp) {
				System.out.println("NodoOpComp hijo unico");
				NodoOpComp nodocomp = (NodoOpComp) hijo;
				// RESTA de tabla con el resultado del nodo
				nodoopnot.setResultado(tablas.Diferencia(tabla, nodocomp.getResultado()));
				// nodoopnot.setOperacion("NOT " + nodocomp.getOperacion());
			} else {
				System.out
						.println("Error: el hijo unico de OPNOT no es una comparacion");
				System.exit(1);
			}
			return nodoopnot;
		}
		if(arbol instanceof NodoOpC){
			System.out.println("NodoOpC");
			NodoOpC nodoopc = (NodoOpC) arbol;
			Table <Integer,String,String> izq,der;
			Object hijoizq = recorrer_arbol(nodoopc.getTablaIzq(),tabla);
			if(hijoizq instanceof NodoID){
				System.out.println("Es un identificador a la izquierda de la Union");
				NodoID nodoid_izq = (NodoID) hijoizq;
				izq = tablas.obtener_table(nodoid_izq.getCadena());
			}else{
				izq=obtener_resultado_nodo(hijoizq);
				
			}
			Object hijoder = recorrer_arbol(nodoopc.getTablaDer(),tabla);
			if(hijoder instanceof NodoID){
				System.out.println("Es un identificador a la derecha de la Union");
				NodoID nodoid_der = (NodoID) hijoder;
				der = tablas.obtener_table(nodoid_der.getCadena());
			}else{
				der=obtener_resultado_nodo(hijoder);
				
			}
			if(nodoopc.getTipo().equals("UNI"))
			{
				nodoopc.setResultado(tablas.Union(izq, der));
			}
			if(nodoopc.getTipo().equals("INT"))
			{
				nodoopc.setResultado(tablas.Interseccion(izq, der));
			}
			if(nodoopc.getTipo().equals("DIF"))
			{
				nodoopc.setResultado(tablas.Diferencia(izq, der));
			}
			if(nodoopc.getTipo().equals("PROC"))
			{
				nodoopc.setResultado(tablas.Producto(izq, der));
			}
			
			return nodoopc;
		}
		if(arbol instanceof NodoID){
			System.out.println("NodoID");
			return arbol;
		}
		if(arbol instanceof NodoCadena){
			System.out.println("NodoCadena");
			return arbol;
		}
		if(arbol instanceof NodoNumero){
			System.out.println("NodoNumero");
			return arbol;
		}
		if(arbol instanceof NodoFecha){
			System.out.println("NodoFecha");
			return arbol;
		}
		return tabla;
	}
	
	public Table<Integer, String, String> obtener_resultado_nodo(Object nodo){
		if(nodo instanceof NodoProyeccion){
			NodoProyeccion pro = (NodoProyeccion) nodo;
			return pro.getResultado();
		}
		if(nodo instanceof NodoSeleccion){
			NodoSeleccion pro = (NodoSeleccion) nodo;
			return pro.getResultado();
		}
		if(nodo instanceof NodoOpC){
			NodoOpC pro = (NodoOpC) nodo;
			return pro.getResultado();
		}
		if(nodo instanceof NodoID){
			NodoID nombre_tabla = (NodoID) nodo;
			return tablas.obtener_table(nombre_tabla.getCadena());
		}
		return null;
	}

}
