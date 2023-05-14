
package _datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class DatosInvestigadores {
	public static List<Investigador> investigadores;
	public static List<Trabajo> trabajos;

	// creacion del trabajo
	public record Trabajo(Integer id, Integer calidad, Map<Integer, Integer> reparto) {

		public static int contador;

		public static Trabajo of(Integer calidad, Map<Integer, Integer> reparto) {
			contador++;
			return new Trabajo(contador, calidad, reparto);
		}

		public static Trabajo create(String linea) {
			String[] trab = linea.split(";");
			Integer cal = Integer.parseInt(trab[0].trim().split("=")[1].trim());

			String[] diasTrab = trab[1].trim().split("=")[1].trim().split(",");
			Map<Integer, Integer> trabas = new HashMap<>();
			for (String s : diasTrab) {
				s = s.replace("(", "").replace(")", "").trim();

				trabas.put(Integer.parseInt(s.split(":")[0].trim()), Integer.parseInt(s.split(":")[1].trim()));
			}
			return new Trabajo(contador++, cal, trabas);
		}
	}

	// record del investigador
	public record Investigador(Integer id, Integer capacidad, Integer especialidad) {

		public static int contador;

		public static Investigador of(Integer capacidad, Integer especialidad) {
			contador++;

			return new Investigador(contador - 1, capacidad, especialidad);
		}

		public static Investigador create(String line) {
			String[] inv = line.split(";");
			Integer capac = Integer.parseInt(inv[0].trim().split("=")[1].trim());
			Integer esp = Integer.parseInt(inv[1].trim().split("=")[1].trim());
			return new Investigador(contador++, capac, esp);
		}
	}

	public static void iniDatos(String file) {

		investigadores = new ArrayList<>();
		trabajos = new ArrayList<>();

		Files2.linesFromFile(file).forEach(linea -> {
			if (linea.startsWith("I")) {
				String[] s = linea.split("=");
				Integer capacidad = Integer.parseInt(s[1].split(";")[0].trim());
				Integer especialidad = Integer.parseInt(s[2].replace(";", ""));

				Investigador i = Investigador.of(capacidad, especialidad);
				investigadores.add(i);

			} else if (linea.startsWith("T")) {
				String[] s = linea.split("=");
				Integer calidad = Integer.parseInt(s[1].split(";")[0].trim());
				Map<Integer, Integer> reparto = new HashMap<>();
				String[] comps = s[2].split(",");
				for (String comp : comps) {
					String cleanComp = comp.replace("(", "").replace(")", "");
					Integer especialidad = Integer.parseInt(cleanComp.split(":")[0]);
					Integer tiempo = Integer.parseInt(cleanComp.split(":")[1].replace(";", ""));

					reparto.put(especialidad, tiempo);
				}
				Trabajo j = Trabajo.of(calidad, reparto);
				trabajos.add(j);

			}

		});
	}

	// Métodos para el primer vértice
	public static Integer getIndexMaximo() {
		return investigadores.size() * trabajos.size();
	}

	public static List<Integer> getCapacidadesInvestigadores() {
		List<Integer> res = new ArrayList<>();
		for (int i = 0; i < getNumInvestigadores(); i++) {
			res.add(investigadores.get(i).capacidad());

		}
		return res;
	}

	public static List<List<Integer>> getDistribucion() {
		List<List<Integer>> res = new ArrayList<>();
		for (int j = 0; j < getNumTrabajos(); j++) {
			List<Integer> aux = new ArrayList<>();
			for (int e = 0; e < getNumEspecialidadesDiferentes(); e++) {
				aux.add(getDiasNecesarios(j, e));
			}
			res.add(aux);

		}
		return res;

	}

	public static int diasNecesariosTrabajo(int i) {
		return trabajos.get(i).reparto.values().stream().collect(Collectors.summingInt(Integer::intValue));
	}

	public static Integer getEspecialidad(Integer i) {
		return investigadores.get(i).especialidad();
	}

	public static Integer getNumInvestigadores() {
		return investigadores.size();
	}

	public static Integer getNumTrabajos() {
		return trabajos.size();
	}

	public static Integer getCalidadTrabajo(Integer j) {
		return trabajos.get(j).calidad();
	}

	public static Integer getDiasNecesarios(Integer j, Integer k) {
		Integer res = trabajos.get(j).reparto().get(k);

		return res;
	}

	public static Integer investigadorEspecialistaEnK(Integer i, Integer k) {
		Integer res = 0;
		if (investigadores.get(i).especialidad().equals(k)) {
			res = 1;
		}

		return res;
	}

	public static Integer getNumEspecialidadesDiferentes() {
		Set<Integer> especialidades = new HashSet<>();
		for (Investigador i : investigadores) {
			especialidades.add(i.especialidad());
		}
		return especialidades.size();

	}

	public static Set<Integer> getEspecialidadesDiferentes() {
		Set<Integer> especialidades = new HashSet<>();
		for (Investigador i : investigadores) {
			especialidades.add(i.especialidad());
		}
		return especialidades;

	}

	public static List<Integer> getEspecialidadesTrabajo(Integer j) {
		List<Integer> res = new ArrayList<>();
		Map<Integer, Integer> aux = trabajos.get(j).reparto();
		for (int i = 0; i < aux.size(); i++) {
			if (aux.get(i) > 0) {

				res.add(aux.keySet().stream().toList().get(i));
			}

		}
		return res;

	}

	public static List<Integer> getEspecialidades() {
		List<Integer> especialidades = new ArrayList<>();
		for (Investigador i : investigadores) {
			especialidades.add(i.especialidad());
		}
		return especialidades;
	}

	public static Integer getCapacidad(Integer i) {
		return investigadores.get(i).capacidad();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			iniDatos("ficheros/Ejercicio3DatosEntrada" + String.valueOf(i + 1) + ".txt");
		}
	}
}
