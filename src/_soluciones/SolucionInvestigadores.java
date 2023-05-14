package _soluciones;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import _datos.DatosInvestigadores;
import _datos.DatosInvestigadores.Investigador;
import ejercicio3.InvestigadoresEdge;
import ejercicio3.InvestigadoresVertex;

public class SolucionInvestigadores implements Comparable<SolucionInvestigadores> {
	public static SolucionInvestigadores of_Range(List<Integer> ls) {
		return new SolucionInvestigadores(ls);
	}

// Ahora en la PI5
	public static SolucionInvestigadores of(GraphPath<InvestigadoresVertex, InvestigadoresEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionInvestigadores res = of_Range(ls);
		res.path = ls;
		return res;
	}

	private Integer calidad;
	private List<Investigador> investigadores;
	private List<List<Integer>> horas;

	private SolucionInvestigadores() {
		calidad = 0;
		investigadores = new ArrayList<>();
		horas = new ArrayList<>();
	}

	// Ahora en la PI5
	private List<Integer> path;

	private SolucionInvestigadores(List<Integer> ls) {
		Integer numInvestigadors = DatosInvestigadores.getNumInvestigadores();
		Integer numTrabajos = DatosInvestigadores.getNumTrabajos();

		calidad = 0;
		investigadores = new ArrayList<>();
		investigadores.addAll(DatosInvestigadores.investigadores);
		horas = new ArrayList<>();
		for (int i = 0; i < numInvestigadors; i++) {
			horas.add(new ArrayList<>());
		}
		for (int j = 0; j < numInvestigadors; j++) {

			Integer res = j * numTrabajos;
			List<Integer> trab = ls.subList(res, res + numTrabajos);
			horas.get(j).addAll(trab);

		}
		int horasDedicadasAlTrabajo = 0;
		for (int i = 0; i < numTrabajos; i++) {
			for (int k = 0; k < horas.size(); k++) {
				horasDedicadasAlTrabajo += horas.get(k).get(i);
			}
			if (horasDedicadasAlTrabajo != DatosInvestigadores.diasNecesariosTrabajo(i)) {

				for (int k = 0; k < horas.size(); k++) {
					horas.get(k).set(i, 0);
				}
			}
			if (horasDedicadasAlTrabajo == DatosInvestigadores.diasNecesariosTrabajo(i)) {
				calidad += DatosInvestigadores.getCalidadTrabajo(i);
			}
			horasDedicadasAlTrabajo = 0;
		}

	}

	public static SolucionInvestigadores empty() {
		return new SolucionInvestigadores();
	}

	public String toString() {
		String s = investigadores.stream().map(i -> "INV" + (i.id() + 1) + ": " + horas.get(i.id()))
				.collect(Collectors.joining("\n", "dias trabajados por cada investigador en cada trabajo:\n", "\n"));
		return path == null ? s
				: String.format("%s SUMA DE LAS CALIDADES DE LOS TRABAJOS REALIZADOS= %d", s, calidad, path);
	}

	@Override
	public int compareTo(SolucionInvestigadores o) {
		//Auto-generated method stub
		return 0;
	}

}