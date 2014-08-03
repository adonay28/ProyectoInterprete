import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import AST.NodoBase;
import AST.NodoCadena;
import AST.NodoFecha;
import AST.NodoNumero;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Tablas {
	List<String> tablas;
	public Tablas(String carpeta) {
		// TODO Auto-generated constructor stub
		File folder = new File(carpeta);
		tablas = listFilesForFolder(folder);
	}
	
	public List<String> listFilesForFolder(final File folder) {
		List<String> tablas = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory() && !fileEntry.getName().startsWith(".")) {
	        	tablas.add(fileEntry.getName().replace(".txt", ""));
	        }
	    }
		return tablas;
	}
	
	public Table <Integer,String,String> obtener_table(String archivo){
		Table<Integer,String,String> tabla = HashBasedTable.create();
		if(tablas.contains(archivo)){
			FileInputStream fstream;
			List<String> nombre_columna = new ArrayList<String>();
			try {
				fstream = new FileInputStream("Relaciones/" + archivo + ".txt");
		        DataInputStream entrada = new DataInputStream(fstream);
		        BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
		        String linea;
		        int clinea = 0;
		        while((linea = buffer.readLine()) != null){
		        	if(clinea == 0){
		        		String[] sepcomas = linea.split(";");
		        		int c_columna = 0;
	        			int c_tipo = 0;
		        		for (int i = 0; i < sepcomas.length; i++) {
		        			String columna =  sepcomas[i];
		        			String[] separar = columna.split("\\(");
		        			for (int j = 0; j < separar.length; j++) {
		        				separar[j] = separar[j].replace(")", "");
		        				if(j % 2 == 0){
		        					tabla.put(0, "columna" + c_columna, separar[j]);
		        					nombre_columna.add(separar[j]);
		        					c_columna++;
		        				}
		        				else{
		        					tabla.put(1, "tipo" + c_tipo, separar[j]);
		        					c_tipo++;
		        				}
		        			}
		  	            }
		        	}
		        	else{
		        		String[] filas = linea.split(";");
			            for (int i = 0; i < filas.length; i++) {
			               String remplazado= filas[i].replace("\"", "");
			               tabla.put(clinea+1, nombre_columna.get(i), remplazado);
			            }
		        	}
		        	clinea++;
		        }
		        fstream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tabla;
		}
		else{
			System.out.println("Error: relacion no existe");
			System.exit(1);
		}
		return tabla;
	}
	
	public static void main(String args[]){
		Tablas t = new Tablas("Relaciones/");
		List<String> a = new ArrayList<String>();
		a.add("placa");
		System.out.println(t.obtener_table("usu_car"));
		System.out.println(t.Proyeccion(a,t.obtener_table("usu_car")));
	}
	
	Table<Integer, String, String> Proyeccion(List<String> lcol,
			Table<Integer, String, String> tabla) {
		Table<Integer, String, String> temporal = HashBasedTable.create();
		int c = 2;
		Map<String, String> map1 = tabla.row(0);
		Map<String, String> map2 = tabla.row(1);
		Set<String> set = map1.keySet();
		int y = 0;
		for (int i = 0; i < lcol.size(); i++) {
			for (Iterator<String> it = set.iterator(); it.hasNext();) {
				String columna = it.next();
				String valor = map1.get(columna);
				if(valor.equals(lcol.get(i)))
					y = i;
			}
			
			temporal.put(0, "columna" + y, lcol.get(i));
			temporal.put(1, "tipo" + y, map2.get("tipo" + y));
		}
		for (int key = 2; key < tabla.rowKeySet().size(); key++) {
			for (int i = 0; i < lcol.size(); i++) {
				Map<String, String> map = tabla.row(key);
				if (map.containsKey(lcol.get(i))) {
					String valor = map.get(lcol.get(i));
					temporal.put(c, lcol.get(i), valor);
				} else {
					System.out.println("Error: columna no existe");
					System.exit(1);
				}
			}
			c++;
		}
		return temporal;
	}
	
	Table<Integer, String, String> Seleccion(Table<Integer, String, String> tabla, String comp, String id,
			NodoBase valor) {
		Table<Integer, String, String> temporal = HashBasedTable.create();
		int fila = 0;
		for (int key = 0; key < tabla.rowKeySet().size(); key++) {
			if (key >= 2) {
				Map<String, String> map = tabla.row(key);
				String valor_tabla = map.get(id);
				if (valor instanceof NodoFecha) {
					Date fecha_tabla = new Date(valor_tabla);
					Date fecha_valor = new Date(((NodoFecha) valor).getFecha());
					if (comp.equals("EQ")) {
						if (fecha_tabla.equals(fecha_valor)) {
							Set<String> set = map.keySet();
							for (Iterator<String> it = set.iterator(); it
									.hasNext();) {
								String columna = it.next();
								temporal.put(fila, columna, map.get(columna));
								fila++;
							}
						}
					} else {
						if (comp.equals("DIFERENTE")) {
							if (fecha_tabla.after(fecha_valor)
									|| fecha_tabla.before(fecha_valor)) {
								Set<String> set = map.keySet();
								for (Iterator<String> it = set.iterator(); it
										.hasNext();) {
									String columna = it.next();
									temporal.put(fila, columna,
											map.get(columna));
								}
								fila++;
							}
						} else {
							if (comp.equals("MAYOR")) {
								if (fecha_tabla.after(fecha_valor)) {
									Set<String> set = map.keySet();
									for (Iterator<String> it = set.iterator(); it
											.hasNext();) {
										String columna = it.next();
										temporal.put(fila, columna,
												map.get(columna));
									}
									fila++;
								}
							} else {
								if (comp.equals("MENOR")) {
									if (fecha_tabla.before(fecha_valor)) {
										Set<String> set = map.keySet();
										for (Iterator<String> it = set
												.iterator(); it.hasNext();) {
											String columna = it.next();
											temporal.put(fila, columna,
													map.get(columna));
										}
										fila++;
									}
								}
							}
						}
					}
				}
				if (valor instanceof NodoNumero) {
					int num_tabla = Integer.valueOf(valor_tabla);
					int num_valor = ((NodoNumero) valor).getNumero();
					if (comp.equals("EQ")) {
						if (num_tabla == num_valor) {
							Set<String> set = map.keySet();
							for (Iterator<String> it = set.iterator(); it
									.hasNext();) {
								String columna = it.next();
								temporal.put(fila, columna, map.get(columna));
							}
							fila++;
						}
					} else {
						if (comp.equals("DIFERENTE")) {
							if (num_tabla != num_valor) {
								Set<String> set = map.keySet();
								for (Iterator<String> it = set.iterator(); it
										.hasNext();) {
									String columna = it.next();
									temporal.put(fila, columna,
											map.get(columna));
								}
								fila++;
							}
						} else {
							if (comp.equals("MAYOR")) {
								if (num_tabla > num_valor) {
									Set<String> set = map.keySet();
									for (Iterator<String> it = set.iterator(); it
											.hasNext();) {
										String columna = it.next();
										temporal.put(fila, columna,
												map.get(columna));
									}
									fila++;
								}
							} else {
								if (comp.equals("MENOR")) {
									if (num_tabla < num_valor) {
										Set<String> set = map.keySet();
										for (Iterator<String> it = set
												.iterator(); it.hasNext();) {
											String columna = it.next();
											temporal.put(fila, columna,
													map.get(columna));
										}
										fila++;
									}
								}
							}
						}
					}
				}
				if (valor instanceof NodoCadena) {
					String cadena_valor = ((NodoCadena) valor).getCadena();
					if (comp.equals("EQ")) {
						if (valor_tabla.equals(cadena_valor)) {
							Set<String> set = map.keySet();
							for (Iterator<String> it = set.iterator(); it
									.hasNext();) {
								String columna = it.next();
								temporal.put(fila, columna, map.get(columna));
							}
							fila++;
						}
					} else {
						if (comp.equals("DIFERENTE")) {
							if (!valor_tabla.equals(cadena_valor)) {
								Set<String> set = map.keySet();
								for (Iterator<String> it = set.iterator(); it
										.hasNext();) {
									String columna = it.next();
									temporal.put(fila, columna,
											map.get(columna));
								}
								fila++;
							}
						} else {
							if (comp.equals("MAYOR")) {
								if (valor_tabla.length() > cadena_valor
										.length()) {
									Set<String> set = map.keySet();
									for (Iterator<String> it = set.iterator(); it
											.hasNext();) {
										String columna = it.next();
										temporal.put(fila, columna,
												map.get(columna));
									}
									fila++;
								}
							} else {
								if (comp.equals("MENOR")) {
									if (valor_tabla.length() < cadena_valor
											.length()) {
										Set<String> set = map.keySet();
										for (Iterator<String> it = set
												.iterator(); it.hasNext();) {
											String columna = it.next();
											temporal.put(fila, columna,
													map.get(columna));
										}
										fila++;
									}
								}
							}
						}
					}
				}
			}
			else{
				Map<String, String> map = tabla.row(0);
				Map<String, String> map2 = tabla.row(1);
				Set<String> set = map.keySet();
				for (int i = 0; i < set.size(); i++) {
					temporal.put(0, "columna" + i, map.get("columna" + i));
					temporal.put(1, "tipo" + i, map2.get("tipo" + i));
				}
				fila = 2;
			}
		}
		return temporal;
	}

	Table<Integer, String, String> Union(Table<Integer, String, String> tabla1,Table<Integer, String, String> tabla2){

		Table<Integer, String, String> temporal = HashBasedTable.create();
		
		Set<String> columnas = tabla1.row(2).keySet();
		
		Object col[] = tabla1.columnKeySet().toArray();
		List<String> colI = new ArrayList<String>();
		List<String> colIO = new ArrayList<String>();
		for(Iterator<String> it = columnas.iterator(); it.hasNext();){
			String columna = it.next();
			colI.add(columna);	
		}
		colIO.addAll(Ordenar(tabla1));
		
		
		if (tabla1.columnKeySet().size()==tabla2.columnKeySet().size()) {
			int CantReg = 0;
			for (Integer keyI : tabla1.rowKeySet()) {
				if(keyI<2){
					Map<String, String> map = tabla1.row(keyI);
					Set<String> set = map.keySet();
					for(Iterator<String> it = set.iterator(); it.hasNext();){
						String columna = it.next();
						temporal.put(CantReg,columna,map.get(columna));
					}
					CantReg++;
				}
				if(keyI>=2){
					Map<String, String> map = tabla1.row(keyI);
					Set<String> set = map.keySet();
					for(Iterator<String> it = set.iterator(); it.hasNext();){
						String columna = it.next();
						temporal.put(CantReg,columna,map.get(columna));
					}
					CantReg++;
				}
			}
			for (Integer keyD : tabla2.rowKeySet()) {
				if(keyD>=2){
					Map<String, String> map = tabla2.row(keyD);
					Set<String> set = map.keySet();
					for(Iterator<String> it = set.iterator(); it.hasNext();){
						String columna = it.next();
						temporal.put(CantReg,columna,map.get(columna));
					}
					CantReg++;
				}
			}
		}
		return temporal;
		
	}

	Table<Integer, String, String> Interseccion(Table<Integer, String, String> tabla1,Table<Integer, String, String> tabla2){

		Table<Integer, String, String> temporal = HashBasedTable.create();
		List<String> colI = new ArrayList<String>();
		List<String> colIO = new ArrayList<String>();
		List<String> colD = new ArrayList<String>();
		List<String> colDO = new ArrayList<String>();
		int coniguals;
		Boolean agregar;
		Set<String> columnas = tabla1.row(2).keySet();
		Set<String> columnas2 = tabla2.row(2).keySet();
		for(Iterator<String> it = columnas.iterator(); it.hasNext();){
			String columna = it.next();
			colI.add(columna);	
		}
		colIO.addAll(Ordenar(tabla1));
		
		for(Iterator<String> it = columnas2.iterator(); it.hasNext();){
			String columna = it.next();
			colD.add(columna);	
		}
		colDO.addAll(Ordenar(tabla2));
		
		if (colIO.size()==colDO.size()) {
			int CantReg = 0;
				for (Integer keyI : tabla1.rowKeySet()){ 
					Map<String, String> map = tabla1.row(keyI);
					Set<String> set = map.keySet();
					if(keyI<2){
						for(Iterator<String> it = set.iterator(); it.hasNext();){
							String columna = it.next();
							temporal.put(keyI,columna,map.get(columna));
						}
						CantReg++;
					}
					if (keyI>=2) {				
						agregar = false;
						for (Integer keyD : tabla2.rowKeySet()) {
							
							coniguals = 0;
							if(keyD>=2){
								for (int i = 0; i < colIO.size(); i++) {
									String A =(tabla1.get(keyI,colIO.get(i)));
									String B =(tabla2.get(keyD,colDO.get(i)));
									if(A.equals(B)){
										coniguals++;
									}						
								}
								if (coniguals==colDO.size()) {
									agregar = true;
									break;
								}
							}
						}
						if (agregar) {
							for (Iterator<String> it = set.iterator(); it.hasNext();) {
								String columna = it.next();
								temporal.put(CantReg,columna,map.get(columna));
							}
							CantReg++;
						}
					}
				}
		}
		return temporal;
		
	}

	Table<Integer, String, String> Diferencia(Table<Integer, String, String> tabla1,Table<Integer, String, String> tabla2){
		Table<Integer, String, String> temporal = HashBasedTable.create();
		
		int regI = tabla1.rowKeySet().size();
		int regD = tabla2.rowKeySet().size();
		
		int coniguals;
		Object col[] = tabla1.columnKeySet().toArray();
		Object col2[] = tabla2.columnKeySet().toArray();
		
		List<String> colI = new ArrayList<String>();
		List<String> colIO = new ArrayList<String>();
		
		List<String> colD = new ArrayList<String>();
		List<String> colDO = new ArrayList<String>();
		
		Boolean agregar;
		Set<String> columnas = tabla1.row(2).keySet();
		Set<String> columnas2 = tabla2.row(2).keySet();
		
		for(Iterator<String> it = columnas.iterator(); it.hasNext();){
			String columna = it.next();
			colI.add(columna);	
		}
		colIO.addAll(Ordenar(tabla1));
		
		for(Iterator<String> it = columnas2.iterator(); it.hasNext();){
			String columna = it.next();
			colD.add(columna);	
		}
		colDO.addAll(Ordenar(tabla2));
			
		if (colIO.size()==colDO.size()) {
			int CantReg = 0;
				for (Integer keyI : tabla1.rowKeySet()) {
					Map<String, String> map = tabla1.row(keyI);
					Set<String> set = map.keySet();
					if(keyI<2){
						
						for(Iterator<String> it = set.iterator(); it.hasNext();){
							String columna = it.next();
							temporal.put(keyI,columna,map.get(columna));
						}
						CantReg++;
					}
					if(keyI>=2)
					{
					agregar = true;
					
					for (Integer keyD : tabla2.rowKeySet()) {
						coniguals = 0;
						if(keyD>=2){
							for (int i = 0; i < colIO.size(); i++) {
								String A =(tabla1.get(keyI,colIO.get(i)));
								String B =(tabla2.get(keyD,colDO.get(i)));
								if(A.equals(B)){
									coniguals++;
								}
							}
							if (coniguals==colDO.size()) {
								agregar = false;
							}
							if(!agregar){
								break;
							}
						}
					}
					if (agregar) {
						for (Iterator<String> it = set.iterator(); it.hasNext();) {
							String columna = it.next();
							temporal.put(CantReg,columna,map.get(columna));
						}
						CantReg++;
					}
				}
				}
		}else{
			System.out.println("Error: Las dimensiones no coinciden");
			System.exit(1);
		}
		return temporal;
	}

	Table<Integer, String, String> Producto(Table<Integer, String, String> tabla1,Table<Integer, String, String> tabla2){
		Table<Integer, String, String> temporal = HashBasedTable.create();
		int CantReg = 0;
		
			for (Integer keyI : tabla1.rowKeySet()) {
				Map<String, String> map = tabla1.row(keyI);
				Set<String> set = map.keySet();
				for (Integer keyD : tabla2.rowKeySet()) {
					Map<String, String> map2 = tabla2.row(keyD);
					Set<String> set2 = map2.keySet();
					if((keyI==keyD)&&(keyI<2&&keyD<2)){
						for(Iterator<String> it = set.iterator(); it.hasNext();){
							String columna = it.next();
							temporal.put(CantReg,columna,map.get(columna));
						}
						int i=0;
						if(keyD==0){
							for(Iterator<String> it = set2.iterator(); it.hasNext();){
								String columna = it.next();
								temporal.put(CantReg,"columna"+(tabla1.columnKeySet().size()/3+i),map2.get(columna));
								i++;
							}
							CantReg++;
						}
						int j=0;
						if(keyD==1){
							for(Iterator<String> it = set2.iterator(); it.hasNext();){
								String columna = it.next();
								temporal.put(CantReg,"tipo"+(tabla1.columnKeySet().size()/3+j),map2.get(columna));
								j++;
							}
							CantReg++;
						}
					}else{
						if(keyI>=2&&keyD>=2){
							for(Iterator<String> it = set.iterator(); it.hasNext();){
								String columna = it.next();
								temporal.put(CantReg,columna,map.get(columna));
							}
							for(Iterator<String> it = set2.iterator(); it.hasNext();){
								String columna = it.next();
								temporal.put(CantReg,columna,map2.get(columna));
							}
							CantReg++;
						}
					}
				}
			}
		return temporal;
	}
	public List<String> Ordenar(Table<Integer, String, String> tabla){
		List<String> ordenada = new ArrayList<String>();
		Set<String> set = tabla.row(0).keySet();
		for (int i = 0; i < set.size(); i++) {
			ordenada.add(tabla.get(0, "columna" + i));
		}
		return ordenada;
	}
	
	public void mostrar_tablas(Table<Integer, String, String> tabla) {
		System.out.println(tabla);
		Collection<String> set = tabla.row(0).values();
		int cont = 0;
		String[][] tablas = new String[tabla.rowKeySet().size()][set.size()];
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
		String columna = it.next();
		tablas[0][cont] = columna;
		for (int i = 2; i < tabla.rowKeySet().size(); i++) {
		Map<String, String> set2 = tabla.row(i);

		tablas[i - 1][cont] = set2.get(columna);

		}
		cont++;
		}
		for (int i = 0; i < tabla.rowKeySet().size()-1; i++) {
		System.out.println();
		for (int j = 0; j < set.size(); j++) {
		System.out.print(tablas[i][j] + "    ");
		}

		}
		}
}
