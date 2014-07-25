import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Table;

import AST.*;


public class Semantico {
	Tablas tablas;
	public Semantico() {
		// TODO Auto-generated constructor stub
		tablas = new Tablas("Relaciones/");
	}
	public void obtener_resultado(Object arbol){
		System.out.println(obtener_resultado_nodo(recorrer_arbol(arbol)));
	}
	
	public Object recorrer_arbol(Object arbol){
		if(arbol instanceof NodoProyeccion){
			System.out.println("NodoProyeccion");
			NodoProyeccion pro = (NodoProyeccion) arbol;
			Object hijoizq = recorrer_arbol(pro.getHijoIzq());
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
			Object hijoder = recorrer_arbol(pro.getHijoDer());
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
				//pro.setResultado("Es una proyeccion del resultado " + obtener_resultado_nodo(hijoder) + " de la columna " + atributos);
				
			}
			return pro;
		}
		if(arbol instanceof NodoComa){
			System.out.println("NodoComa");
			NodoComa coma = (NodoComa) arbol;
			Object hijoizq = recorrer_arbol(coma.getHijoIzq());
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
			Object hijoder = recorrer_arbol(coma.getHijoDer());
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
			Object hijoizq = recorrer_arbol(opBinaria.getOperandoIzquierdo());
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
			Object hijoder = recorrer_arbol(opBinaria.getOperandoDerecho());
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
		if(arbol instanceof NodoSeleccion){
			System.out.println("NodoSeleccion");
			NodoSeleccion sel = (NodoSeleccion) arbol;
			String operaciones = "";
			Object hijoizq = recorrer_arbol(sel.getHijoIzq());
			if(hijoizq instanceof NodoOpAO){
				System.out.println("NodoOpAO izq");
				NodoOpAO ao_izq = (NodoOpAO) hijoizq;
				operaciones = ao_izq.getOperacion();
			}
			else{
				if(hijoizq instanceof NodoOpComp){
					System.out.println("NodoOpComp izq");
					NodoOpComp opcomp_izq = (NodoOpComp) hijoizq;
					operaciones = opcomp_izq.getOperacion();
				}
				else{
					if(hijoizq instanceof NodoOpNot){
						System.out.println("NodoOpNot izq");
						NodoOpNot opnot_izq = (NodoOpNot) hijoizq;
						operaciones = opnot_izq.getOperacion();
					}
					else{
						System.out.println("Error: el hijo izq de Seleccion no es una comparacion, ANDOR, NOT");
						System.exit(1);
					}
				}
			}
			Object hijoder = recorrer_arbol(sel.getHijoDer());
			if(hijoder instanceof NodoID){
				System.out.println("Es un identificador a la derecha de la seleccion");
				NodoID nodoid_der = (NodoID) hijoder;
				//Hacer operacion sobre la tabla y guardar el resultado
				//sel.setResultado("Es una seleccion de la tabla " + nodoid_der.getCadena() + " de las operaciones " + operaciones);
			}
			else{
				System.out.println("Es un resultado de una expresion");
				//obtener resultado, hacer la proyeccion y guardar en resultado
				//sel.setResultado("Es una seleccion del resultado " + obtener_resultado_nodo(hijoder) + " de las operaciones " + operaciones);
			}
			return sel;
		}
		if(arbol instanceof NodoOpAO){
			System.out.println("NodoOpAO");
			NodoOpAO nodoAO = (NodoOpAO) arbol;
			Object hijoizq = recorrer_arbol(nodoAO.getLogicaIzq());
			String operaciones = "";
			if(hijoizq instanceof NodoOpComp){
				System.out.println("NodoOpComp izq");
				NodoOpComp opcomp_izq = (NodoOpComp) hijoizq;
				operaciones = opcomp_izq.getOperacion();
			}
			else{
				if(hijoizq instanceof NodoOpNot){
					System.out.println("NodoOpNot izq");
					NodoOpNot opnot_izq = (NodoOpNot) hijoizq;
					operaciones = opnot_izq.getOperacion();
				}
				else{
					System.out.println("Error: el hijo izq de OpAND-OR no es una comparacion,NOT");
					System.exit(1);
				}
			}
			operaciones += " " + nodoAO.getTipo() + " ";
			Object hijoder = recorrer_arbol(nodoAO.getLogicaDer());
			if(hijoder instanceof NodoOpComp){
				System.out.println("NodoOpComp der");
				NodoOpComp opcomp_der = (NodoOpComp) hijoder;
				nodoAO.setOperacion(operaciones + opcomp_der.getOperacion());
			}
			else{
				if(hijoder instanceof NodoOpNot){
					System.out.println("NodoOpNot der");
					NodoOpNot opnot_der = (NodoOpNot) hijoder;
					nodoAO.setOperacion(operaciones + opnot_der.getOperacion());
				}
				else{
					System.out.println("Error: el hijo der de OpAND-OR no es una comparacion,NOT");
					System.exit(1);
				}
			}
			return nodoAO;
		}
		if(arbol instanceof NodoOpComp){
			System.out.println("NodoOpComp");
			NodoOpComp nodoopcomp = (NodoOpComp) arbol;
			Object hijoizq = recorrer_arbol(nodoopcomp.getParteIzq());
			if(hijoizq instanceof NodoID){
				System.out.println("NodoID izq");
				NodoID nodoid_izq = (NodoID) hijoizq;
				nodoopcomp.setOperacion("" + nodoid_izq.getCadena());
			}
			else{
				System.out.println("Error: el hijo izq de la comparacion no es un identificador");
				System.exit(1);
			}
			nodoopcomp.setOperacion(nodoopcomp.getOperacion() + nodoopcomp.getTipo());
			Object hijoder = recorrer_arbol(nodoopcomp.getParteDer());
			if(hijoder instanceof NodoID){
				System.out.println("NodoID der");
				NodoID nodoid_der = (NodoID) hijoder;
				nodoopcomp.setOperacion(nodoopcomp.getOperacion() + nodoid_der.getCadena());
			}
			else{
				if(hijoder instanceof NodoFecha){
					System.out.println("NodoFecha der");
					NodoFecha nodofecha_der = (NodoFecha) hijoder;
					nodoopcomp.setOperacion(nodoopcomp.getOperacion() + nodofecha_der.getFecha().toString());
				}
				else{
					if(hijoder instanceof NodoNumero){
						System.out.println("NodoNumero der");
						NodoNumero nodonum_der = (NodoNumero) hijoder;
						nodoopcomp.setOperacion(nodoopcomp.getOperacion() + nodonum_der.getNumero());
					}
					else{
						if(hijoder instanceof NodoCadena){
							System.out.println("NodoCadena der");
							NodoCadena nodocade_der = (NodoCadena) hijoder;
							nodoopcomp.setOperacion(nodoopcomp.getOperacion() + nodocade_der.getCadena());
						}
						else{
							System.out.println("Error: el hijo der de la comparacion no es un identificador,numero o fecha");
							System.exit(1);
						}
					}
				}
			}
			return nodoopcomp;
		}
		if(arbol instanceof NodoOpNot){
			System.out.println("NodoOpNot");
			NodoOpNot nodoopnot = (NodoOpNot) arbol;
			Object hijo = recorrer_arbol(nodoopnot.getHijounico());
			if(hijo instanceof NodoOpComp){
				System.out.println("NodoOpComp hijo unico");
				NodoOpComp nodocomp = (NodoOpComp) hijo;
				nodoopnot.setOperacion("NOT " + nodocomp.getOperacion());
			}
			else{
				System.out.println("Error: el hijo unico de OPNOT no es una comparacion");
				System.exit(1);
			}
			return nodoopnot;
		}
		if(arbol instanceof NodoOpC){
			System.out.println("NodoOpC");
			NodoOpC nodoopc = (NodoOpC) arbol;
			Object hijoizq = recorrer_arbol(nodoopc.getTablaIzq());
			Object hijoder = recorrer_arbol(nodoopc.getTablaDer());
			//nodoopc.setResultado(obtener_resultado_nodo(hijoizq) + nodoopc.getTipo() + obtener_resultado_nodo(hijoder));
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
		return null;
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