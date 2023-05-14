package ejercicio1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import _datos.DatosCafes;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CafeVertex(Integer index, List<Integer> remaining) implements VirtualVertex<CafeVertex, CafeEdge, Integer> {

	public static CafeVertex of(Integer i, List<Integer> rest) {
		return new CafeVertex(i, rest);
	}

	public static CafeVertex initial() {

		return of(0, DatosCafes.tipos);
	}

	public static Predicate<CafeVertex> goal() {

		return v -> v.index() == DatosCafes.getNumVariedades();
	}

	public static Predicate<CafeVertex> goalHasSolution() {
		return v -> true;
	}

//============================================================================================\\
	
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		
		//si estamos en elultimo indice(final)
		if (index == DatosCafes.getNumVariedades()) {
			return List2.empty();
		} else {
			for (int i = 0; i <= DatosCafes.getMaxKgVariedad(index) && i <= maxKgsPosibles(index, remaining); i++) {
				alternativas.add(i);
			}
		}
		return alternativas;
	}

	
	public static Integer maxKgsPosibles(Integer index, List<Integer> remaining) {
		List<Double> cantidadesDisponibles = new ArrayList<>();
		for (int j = 0; j < DatosCafes.tipos.size(); j++) {
			Double kilosTipo = DatosCafes.getKgTipoVariedad(j, index);
			if (kilosTipo > 0.) {
				cantidadesDisponibles.add(remaining.get(j) / kilosTipo);
			}
		}
		Double maxKilosPosibles = cantidadesDisponibles.stream().min(Comparator.naturalOrder()).orElse(0.);
		return maxKilosPosibles.intValue();
	}

	public CafeVertex neighbor(Integer a) {
		Integer n_indice = this.index + 1;
		List<Integer> n_remaining = List2.copy(this.remaining);
		List<Integer> remainingNuevo = List2.empty();

		for (int j = 0; j < DatosCafes.getNumTipos(); j++) {
			Double kilosTomados = a * DatosCafes.getKgTipoVariedad(j, index);

			remainingNuevo.add(n_remaining.get(j) - kilosTomados.intValue());
		}
		return of(n_indice, remainingNuevo);
	}

	@Override
	public CafeEdge edge(Integer a) {
		return CafeEdge.of(this, neighbor(a), a);
	}

	// Se explica en practicas.
	public CafeEdge greedyEdge() {
		Integer a = maxKgsPosibles(index, remaining);
		return edge(a);
	}
}