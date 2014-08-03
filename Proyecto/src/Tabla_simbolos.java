import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Table;


public class Tabla_simbolos {
	Map<String,Map<String,String>> tabla_simbolos;
	int cantidad_simbolos;
	public Tabla_simbolos() {
		tabla_simbolos = new HashMap<String, Map<String,String>>();
		cantidad_simbolos = 0;
	}
	
	public String agregar(Table<Integer, String, String> tabla){
		Map<String,String> temporal = new HashMap<String, String>();
		int columnas = tabla.row(0).size();
		for(int i = 0; i < columnas; i++){
			temporal.put(tabla.get(0, "columna" + i), tabla.get(1, "tipo" + i));
		}
		String nombre = "temporal" + cantidad_simbolos;
		tabla_simbolos.put( nombre, temporal);
		cantidad_simbolos++;
		return nombre;		
	}
	
	public String consultar(String nombre_tabla, String columna){
		String tipo = null;
		Map<String, String> map = tabla_simbolos.get(nombre_tabla);
		if(map.containsKey(columna)){
			tipo = map.get(columna);
			return tipo;
		}
		return tipo;
	}
	
	public int consultar_nro_columnas(String nombre_tabla){
		Map<String, String> map = tabla_simbolos.get(nombre_tabla);
		return map.size();
	}
	
	public boolean verificar_columnas_tipos(String nombre_tabla1, String nombre_tabla2){
		Map<String, String> map = tabla_simbolos.get(nombre_tabla1);
		Map<String, String> map2 = tabla_simbolos.get(nombre_tabla2);
		Set<String> set = map.keySet();
		Set<String> set2 = map2.keySet();
		boolean error = false;
		int i = 0;
		int j;
		for(Iterator<String> it = set.iterator(); it.hasNext();){
			String columna = it.next();
			j = 0;
			for(Iterator<String> it2 = set2.iterator(); it2.hasNext();){
				String columna2 = it2.next();
				if(i == j){
					if(map.get(columna).equals(map2.get(columna2))){
						//Todo bien
					}
					else{
						error = true;
					}
				}
				j++;
			}
			i++;
		}
		return error;
	}
	
}
