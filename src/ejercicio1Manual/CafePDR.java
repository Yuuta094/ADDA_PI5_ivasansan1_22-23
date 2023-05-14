package ejercicio1Manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import _datos.DatosCafes;
import _soluciones.SolucionCafes;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CafePDR {

	public static record Spm(Integer act, Integer benef) implements Comparable<Spm> {

		public static Spm of(Integer a, Integer ben) {
			return new Spm(a, ben);
		}

		@Override
		public int compareTo(Spm o) {
			//Auto-generated method stub
			return this.benef.compareTo(o.benef);
		}
	}

	public static Map<CafeProblem, Spm> memory;
	public static Integer mejorValor;

	public static SolucionCafes search() {
		memory = Map2.empty();
		mejorValor = Integer.MIN_VALUE;

		pdr_search(CafeProblem.initial(), 0, memory);
		return getSol();
	}

	private static Spm pdr_search(CafeProblem prob, int acumulado, Map<CafeProblem, Spm> memoria) {
		//Auto-generated method stub
		Spm res = null;

		prob.index().equals(DatosCafes.getNumVariedades());
		Boolean esTerminal = prob.index().equals(DatosCafes.getNumVariedades());
		Boolean esSolucion = true;

		if (memory.containsKey(prob)) {
			res = memory.get(prob);

		} else if (esTerminal && esSolucion) {
			res = Spm.of(null, 0);
			memory.put(prob, res);

			if (acumulado > mejorValor) {
				mejorValor = acumulado;
			}
		} else {
			List<Spm> soluciones = List2.empty();
			for (Integer action : prob.actions()) {

				CafeProblem vecino = prob.neighbor(action);
				Spm s = pdr_search(vecino, acumulado + action * DatosCafes.getMaxKgVariedad(prob.index()), memory);
				if (s != null) {
					Spm amp = Spm.of(action, s.benef() + action * DatosCafes.getMaxKgVariedad(prob.index()));
					soluciones.add(amp);
				}
			}

			res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if (res != null) {
				memory.put(prob, res);
			}
		}
		return res;
	}

	private static SolucionCafes getSol() {
		//Auto-generated method stub

		List<Integer> acciones = List2.empty();
		CafeProblem prob = CafeProblem.initial();
		Spm spm = memory.get(prob);
		while (spm != null && spm.act != null) {
			CafeProblem old = prob;
			acciones.add(spm.act);
			prob = old.neighbor(spm.act);
			spm = memory.get(prob);
		}
		return SolucionCafes.of_Range(acciones);
	}
}
