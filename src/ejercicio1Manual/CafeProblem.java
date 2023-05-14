package ejercicio1Manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import _datos.DatosCafes;
import us.lsi.common.List2;

public record CafeProblem(Integer index, List<Integer> remaining) {

	public static CafeProblem of(Integer i, List<Integer> rest) {
		return new CafeProblem(i, rest);
	}

	public static CafeProblem initial() {
		return of(0, DatosCafes.tipos);
	}

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

	public CafeProblem neighbor(Integer a) {
		Integer n_indice = this.index + 1;
		List<Integer> n_remaining = List2.copy(this.remaining);
		List<Integer> remainingNuevo = List2.empty();

		for (int j = 0; j < DatosCafes.getNumTipos(); j++) {
			Double kilosTomados = a * DatosCafes.getKgTipoVariedad(j, index);

			remainingNuevo.add(n_remaining.get(j) - kilosTomados.intValue());
		}
		return of(n_indice, remainingNuevo);
	}

	public Double heuristic() {
		List<Double> res = List2.empty();

		for (int i = 0; i < DatosCafes.getNumTipos(); i++) {
			Double re = 0.;
			if (remaining().get(i) > 0) {

				Integer max = IntStream.range(index(), DatosCafes.getNumTipos())
						// .map(i -> DatosCafes.getKgTipo(i))
						.filter(e -> DatosCafes.getKgTipo(e) <= remaining().get(e)).max().orElse(0);
				if (max > 0) {
					Integer r = remaining().get(i) / max;
					re += remaining().get(i) % max == 0 ? r : r + 1;
				} else {
					re += 100;
				}
			} else if (remaining().get(i) < 0) {
				re += 100;
			}
			res.add(re);
		}
		return res.stream().max(Comparator.naturalOrder()).get();
	}
}
