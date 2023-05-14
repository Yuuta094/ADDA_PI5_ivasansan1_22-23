package ejercicio3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosInvestigadores;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;


public record InvestigadoresVertex(Integer index, List<Integer> capacidades, List<List<Integer>> distribucion)
		implements VirtualVertex<InvestigadoresVertex, InvestigadoresEdge, Integer> {

	public static InvestigadoresVertex of(Integer i, List<Integer> dias, List<List<Integer>> dist) {
		return new InvestigadoresVertex(i, dias, dist);
	}

	public static InvestigadoresVertex initial() {
		return of(0, DatosInvestigadores.getCapacidadesInvestigadores(), DatosInvestigadores.getDistribucion());
	}

	public static Predicate<InvestigadoresVertex> goal() {
		return v -> v.index() == DatosInvestigadores.getIndexMaximo();
	}

	public static Predicate<InvestigadoresVertex> goalHasSolution() {
		return v -> v.index() == DatosInvestigadores.getIndexMaximo();
	}

	@Override
	public List<Integer> actions() {
		// sacamos los investigado y trabajo del indice en el qstamos
		Integer i = Math.floorDiv(index, DatosInvestigadores.getNumTrabajos());
		Integer j = index % DatosInvestigadores.getNumTrabajos();

		List<Integer> acciones = new ArrayList<>();
		if (index >= DatosInvestigadores.getNumInvestigadores() * DatosInvestigadores.getNumTrabajos()) {
			return acciones;
		} else {
			// sacamos los dias necesarios para acabar el trabajo conla especialidad k
			Integer diasNecesarios = distribucion.get(j).get(DatosInvestigadores.getEspecialidad(i));
			// saacmos las horas restantes del trabajador i
			Integer diasrestantes = capacidades.get(i);

			// si el trabajador no pde hecharle las horas al trabajo
			if (!puededHacerseTrabajo()) {
				acciones.add(0);
			} else {

				acciones.addAll(IntStream.rangeClosed(0, Math.min(diasrestantes, diasNecesarios)).boxed().toList());
				Collections.reverse(acciones);
			}
			return acciones;
		}
	}

	private Boolean puededHacerseTrabajo() {
		Integer j = index % DatosInvestigadores.getNumTrabajos();
		Boolean res;
		if (getCapacidadRestanteInv() >= getCapRestanteTrab(j)) {
			res = true;
		} else {
			res = false;
		}
		return res;
	}

	// metodos auxiliares del puededHacerseTrabajo
	private Integer getCapRestanteTrab(Integer j) {
		return distribucion.get(j).stream().reduce(0, (a, s) -> a + s);
	}

	private Integer getCapacidadRestanteInv() {
		Integer res = 0;

		for (Integer i = Math.floorDiv(index, DatosInvestigadores.getNumTrabajos()); i < DatosInvestigadores
				.getNumInvestigadores(); i++) {
			res += capacidades.get(i);
		}
		return res;
	}

	@Override
	public InvestigadoresVertex neighbor(Integer action) {
		Integer i = Math.floorDiv(index, DatosInvestigadores.getNumTrabajos());
		Integer j = index % DatosInvestigadores.getNumTrabajos();

		// sacamos las capacidades y la actualizamos
		List<Integer> caps = List2.copy(capacidades);
		Integer elem = caps.get(i);
		elem -= action;
		caps.set(i, elem);

		// sacamos las distribuciones y las actualizamos con laaccion que hay
		// seleccioanda
		List<List<Integer>> dist = List2.copy(distribucion);
		List<Integer> aux = List2.copy(dist.get(j));
		Integer elem2 = aux.get(DatosInvestigadores.investigadores.get(i).especialidad());
		elem2 -= action;

		aux.set(DatosInvestigadores.investigadores.get(i).especialidad(), elem2);
		dist.set(j, aux);

		return of(index + 1, caps, dist);
	}

	@Override
	public InvestigadoresEdge edge(Integer a) {
		return InvestigadoresEdge.of(this, neighbor(a), a);
	}

	public InvestigadoresEdge greedyEdge() {

		return edge(actions().stream().max(Comparator.naturalOrder()).orElse(0));
	}
}
