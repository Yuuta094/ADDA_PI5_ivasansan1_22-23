package _soluciones;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import _datos.DatosInvestigadores;
import _datos.DatosInvestigadores.Investigador;

public class SolucionInvestigadores {
	public static SolucionInvestigadores of_Range(List<Integer> value) {
		return new SolucionInvestigadores(value);
	}

	private Integer calidad;
	private List<Investigador> investigadores;
	private List<List<Integer>> horas;

	private SolucionInvestigadores() {
		calidad = 0;
		investigadores = new ArrayList<>();
		horas = new ArrayList<>();
	}

	private SolucionInvestigadores(List<Integer> ls) {
		Integer numInv = DatosInvestigadores.getNumInvestigadores();
		Integer numTrab = DatosInvestigadores.getNumTrabajos();
		Integer numEsp = DatosInvestigadores.getNumEspecialidades();
		calidad = 0;
		investigadores = new ArrayList<>();
		investigadores.addAll(DatosInvestigadores.investigadores);
		horas = new ArrayList<>();
// Anadimos una lista por cada investigador
		for (int i = 0; i < numInv; i++) {
			horas.add(new ArrayList<>());
		}
		for (int j = 0; j < numTrab; j++) {
			Integer jj = j * numInv;
			List<Integer> trab = ls.subList(jj, jj + numInv);
// Anadimos a la lista i, las horas del trabajador i
			for (int i = 0; i < numInv; i++) {
				horas.get(i).add(trab.get(i));
			}
			boolean realiza=true;
			for (int k = 0; k < numEsp; k++) {
				Integer suma = 0;
				for (int i = 0; i < numInv; i++) {
					suma += trab.get(i) * DatosInvestigadores.trabajadorEspecialidad(i, k);
				}
				if (suma < DatosInvestigadores.diasNecesarios(j, k)) {
					realiza = false;
					k = numEsp;
				}
			}
// Si se realiza el trabajo, se suma su calidad
			if (realiza) {
				calidad += DatosInvestigadores.getCalidad(j);
			}
		}
	}

	public static SolucionInvestigadores empty() {
		return new SolucionInvestigadores();
	}

	public String toString() {
		String s = investigadores.stream().map(i -> "INV" + (i.id() + 1) + ": " + horas.get(i.id()))
				.collect(Collectors.joining("\n", "Reparto de horas:\n", "\n"));
		return String.format("%sSuma de las calidades de los trabajos realizados: %d", s, calidad);
	}
}
