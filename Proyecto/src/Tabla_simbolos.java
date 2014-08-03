import java.util.HashMap;
import java.util.Map;

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
			temporal.put(tabla.get(0, "columna" + i), tabla.get(0, "tipo" + i));
		}
		String nombre = "temporal" + cantidad_simbolos;
		tabla_simbolos.put( nombre, temporal);
		return nombre;		
	}
	
	public String consultar(String nombre_tabla, String columna){
		String tipo = null;
		Map<String, String> map = tabla_simbolos.get(nombre_tabla);
		if(map.containsKey(columna)){
			return map.get(columna);
		}
		return tipo;
	}
}
