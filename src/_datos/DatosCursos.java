package _datos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;

public class DatosCursos {
	public static List<Curso> cursos;
	public static Integer maxCentros;

	public record Curso(Integer id, List<Integer> tematicas, Double precio, Integer centro) {

		public static int cont;
		public static Curso create(String linea) {
			List<Integer> aux = new ArrayList<>();
			String[] params = linea.split(":");
			String[] tem = params[0].substring(1, params[0].length() - 1).split(",");
			for (String str : tem) {
				aux.add(Integer.parseInt(str.trim()));
			}
			return new Curso(cont++, new ArrayList<>(aux), Double.parseDouble(params[1].trim()),
					Integer.parseInt(params[2].trim()));
		}

	}

	public static void iniDatos(String fichero) {
		
		Curso.cont = 0;
		List<String> lineas = Files2.linesFromFile(fichero);
		maxCentros = Integer.parseInt(lineas.get(0).split("=")[1].trim());
		
		cursos = new ArrayList<>();
		
		for (String s : lineas.subList(1, lineas.size())) {
		cursos.add(Curso.create(s));
		}
		toConsole();
	}

//------------------------------------------------------------------------------------------\\

	public static Integer getMaxCentros() {
		return maxCentros;
	}

	public static Integer getNumCursos() {
		return cursos.size();
	}

	public static List<Integer> getTematicas() {
		Set<Integer> s = new HashSet<>();
		for (Curso c : cursos) {
			s.addAll(c.tematicas());
		}
		return new ArrayList<>(s);
	}

	public static Integer getNumTematicas() {
		return getTematicas().size();
	}

	public static List<Integer> getTematicasCurso(Integer i) {
		return cursos.get(i).tematicas();
	}

	public static Integer getNumTematicasCurso(Integer i) {
		return getTematicasCurso(i).size();
	}

	public static Integer contieneTematica(Integer i, Integer j) {
		return cursos.get(i).tematicas().contains(getTematicas().get(j)) ? 1 : 0;
	}

	public static Double getPrecioCurso(Integer i) {
		return cursos.get(i).precio();
	}

	public static Integer getCentroCurso(Integer i) {
		return cursos.get(i).centro();
	}

	public static List<Integer> getCentros() {
		Set<Integer> s = new HashSet<>();
		for (Curso c : cursos) {
			s.add(c.centro());
		}
		return new ArrayList<>(s);
	}

	public static Integer getNumCentros() {
		return getCentros().size();
	}

	public static Integer ofreceCurso(Integer i, Integer k) {
		return cursos.get(i).centro().equals(getCentros().get(k)) ? 1 : 0;
	}

//------------------------------------------------------------------------------------------\\

	public static void toConsole() {
		System.out.println("Numero maximo de centros a seleccionar: " + maxCentros + "\nCursos disponibles: " + cursos);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			System.out.println("========== DATOS DE ENTRADA " + (i + 1) + " ==========");
			String fichero = "ficheros/Ejercicio2DatosEntrada" + String.valueOf(i + 1) + ".txt";
			iniDatos(fichero);
			System.out.println("\n\n");
		}
	}

}
