import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayTable;
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
			               tabla.put(clinea+1, nombre_columna.get(i)+":"+(i+1), remplazado);
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
	
	Table<Integer, String, String> Proyeccion(List<String> lcol,Table<Integer, String, String> tabla){
		Table<Integer, String, String> temporal = HashBasedTable.create();
		int c = 0;
		for (int key = 2; key < tabla.rowKeySet().size(); key++) {
			for (int i = 0; i < lcol.size(); i++) {
				Map<String,String> map = tabla.row(key);
				if(map.containsKey(lcol.get(i))){
					String valor = map.get(lcol.get(i));
					temporal.put(c, lcol.get(i), valor);
				}
				else{
					System.out.println("Error: columna no existe");
					System.exit(1);
				}
			}			
			c++;
		}
		return temporal;
	}
	
	Table<Integer, String, String> Seleccion(String lcol,Table<Integer, String, String> tabla){
		Table<Integer, String, String> temporal = HashBasedTable.create();
		int c = 0;
		String[] aux = lcol.split(",");
		for (int key = 2; key < tabla.rowKeySet().size(); key++) {
			Map<String,String> map = tabla.row(key);
			if(map.containsKey(aux[0])){
				String valor = map.get(aux[0]);
				if(aux[1].equals("EQ")){
					if(valor.equals(aux[2])){
						
					}
				}
				else{
					//otras
				}
			}
			else{
				System.out.println("Error: columna no existe");
				System.exit(1);
			}
			c++;
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
		colIO.addAll(Ordenar(colI));
		
		int i;
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
		Object colIT[] = tabla1.columnKeySet().toArray();
		List<String> colD = new ArrayList<String>();
		List<String> colDO = new ArrayList<String>();
		Object colDT[] = tabla2.columnKeySet().toArray();
		int coniguals;
		int regI = tabla1.rowKeySet().size();
		int regD = tabla2.rowKeySet().size();
		Boolean agregar;
		Set<String> columnas = tabla1.row(2).keySet();
		Set<String> columnas2 = tabla2.row(2).keySet();
		for(Iterator<String> it = columnas.iterator(); it.hasNext();){
			String columna = it.next();
			colI.add(columna);	
		}
		colIO.addAll(Ordenar(colI));
		
		for(Iterator<String> it = columnas2.iterator(); it.hasNext();){
			String columna = it.next();
			colD.add(columna);	
		}
		colDO.addAll(Ordenar(colD));
		
		if (colIO.size()==colDO.size()) {
			
				for (Integer keyI : tabla1.rowKeySet()){ 
					Map<String, String> map = tabla1.row(keyI);
					Set<String> set = map.keySet();
					if(keyI<2){
						for(Iterator<String> it = set.iterator(); it.hasNext();){
							String columna = it.next();
							temporal.put(keyI,columna,map.get(columna));
						}
						
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
								temporal.put(keyI,columna,map.get(columna));
							}
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
		colIO.addAll(Ordenar(colI));
		
		for(Iterator<String> it = columnas2.iterator(); it.hasNext();){
			String columna = it.next();
			colD.add(columna);	
		}
		colDO.addAll(Ordenar(colD));
			
		if (colIO.size()==colDO.size()) {
				for (Integer keyI : tabla1.rowKeySet()) {
					Map<String, String> map = tabla1.row(keyI);
					Set<String> set = map.keySet();
					if(keyI<2){
						
						for(Iterator<String> it = set.iterator(); it.hasNext();){
							String columna = it.next();
							temporal.put(keyI,columna,map.get(columna));
						}
						
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
							temporal.put(keyI,columna,map.get(columna));
						}
					}
				}
				}
		}else{
			System.out.println("Error: Las dimensiones no coinciden");
			System.exit(1);
		}
		return temporal;
	}

	public List<String> Ordenar(List<String> nordenado){
		List<String> ordenado = new ArrayList<String>();
		String[] columnas = new String[2];
		for (int i = 0; i < nordenado.size(); i++) {
			for (int j = 0; j < nordenado.size(); j++) {
				String[] separar = nordenado.get(j).split(":");
	            columnas[0]=separar[0];
	            columnas[1]=separar[1];
	            if (Integer.parseInt(columnas[1])==(i+1)) {
					ordenado.add(columnas[0]+":"+columnas[1]);
					break;
				}
			}
		}
		
		return ordenado;
	}
	
}
