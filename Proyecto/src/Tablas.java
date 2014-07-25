import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	Table<Integer, String, String> Proyeccion(List<String> lcol,Table<Integer, String, String> tabla){
		Table<Integer, String, String> temporal = HashBasedTable.create();
		int c = 0;
		for (int key = 2; key < tabla.rowKeySet().size(); key++) {
			for (int i = 0; i < lcol.size(); i++) {
				Map<String,String> map = tabla.row(key);
				if(map.containsKey(lcol.get(i))){
					String placa = map.get(lcol.get(i));
					temporal.put(c, lcol.get(i), placa);
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
}
