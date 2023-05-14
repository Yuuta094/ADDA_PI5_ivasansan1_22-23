package _datos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import us.lsi.common.Files2;

public class DatosCafes {
	public static List<Integer> tipos;
	public static List<Variedad> variedades;

	public record Variedad(int id, Integer beneficio, List<Double> composicion) {

		public static int cont;
		public static Variedad create(String s) {
			List<Double> ls = new ArrayList<>();

			for (int j = 0; j < tipos.size(); j++) {
				ls.add(0.);
			}
			String[] variable = s.split(";");
			String[] compo = variable[1].split("=")[1].trim().split(",");
			Integer beneficio = Integer.parseInt(variable[0].split("=")[1].replace(";", "").trim());

			for (int j = 0; j < compo.length; j++) {
				String[] porcent = compo[j].replace("(C", "").replace(")", "").split(":");
				Integer tipo = Integer.parseInt(porcent[0].trim()) - 1;
				Double porcentaje = Double.parseDouble(porcent[1].trim());
				ls.set(tipo, porcentaje);
			}
			return new Variedad(cont++, beneficio, new ArrayList<>(ls));
		}
	}

	public static void iniDatos(String fichero) {
		Variedad.cont = 0;
		List<String> lineas = Files2.linesFromFile(fichero);
		int pos = lineas.indexOf("// VARIEDADES");
		List<String> tipoCafe = lineas.subList(1, pos);
		List<String> varCafe = lineas.subList(pos + 1, lineas.size());
		List<Integer> aux = new ArrayList<>();
		for (int i = 0; i < tipoCafe.size(); i++) {
			Integer valor = Integer.parseInt(tipoCafe.get(i).split("=")[1].replace(";", "").trim());
			aux.add(valor);
		}
		tipos = new ArrayList<>(aux);
		variedades = new ArrayList<>();
		for (int i = 0; i < varCafe.size(); i++) {
			variedades.add(Variedad.create(varCafe.get(i)));
		}
		toConsole();
	}

	// ------------------------------------------------------------------------------------------\\

	// Double getKgTipoVariedad(Integer i, Integer j)
	public static Integer getNumTipos() {
		return tipos.size();
	}

	public static Integer getNumVariedades() {
		return variedades.size();
	}

	public static Integer getKgTipo(Integer j) {
		return tipos.get(j);
	}

	public static Integer getBeneficioVariedad(Integer i) {
		return variedades.get(i).beneficio();
	}

	public static Double getKgTipoVariedad(Integer j, Integer i) {
		return variedades.get(i).composicion().get(j);
	}

	public static List<Variedad> getVariedades() {
		return new ArrayList<>(variedades);
	}

	public static Integer getMaxKgVariedad(Integer i) {
		List<Double> listaMax = new ArrayList<>();
		for (int j = 0; j < tipos.size(); j++) {
			listaMax.add(getKgTipo(j) / getKgTipoVariedad(j, i));
		}
		listaMax.sort(Comparator.naturalOrder());
		return listaMax.get(0).intValue();
	}
	// ------------------------------------------------------------------------------------------\\

	private static void toConsole() {
		System.out.println("Kgs disponibles de cada tipo: " + tipos + "\nVariedades disponibles: " + variedades);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			System.out.println("========== DATOS DE ENTRADA " + (i + 1) + " ==========");
			String fichero = "ficheros/Ejercicio1DatosEntrada" + String.valueOf(i + 1) + ".txt";
			iniDatos(fichero);
			System.out.println("\n\n");
		}
	}

}
