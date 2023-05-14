package ejercicio4;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import _datos.DatosClientes;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record ClientesVertex(Integer index, Set<Integer> pendientes, List<Integer> visitados, Double kms)
		implements VirtualVertex<ClientesVertex, ClientesEdge, Integer> {

	public static ClientesVertex of(Integer i, Set<Integer> pend, List<Integer> visitados, Double kms) {
		return new ClientesVertex(i, pend, visitados, kms);
	}

	public static ClientesVertex initial() {
		return of(0, Set2.copy(DatosClientes.getClientes()), List2.of(0), 0.);
	}

	public static Predicate<ClientesVertex> goal() {
		return v -> v.index() == 0 && v.pendientes().isEmpty();

	}

	public static Predicate<ClientesVertex> goalHasSolution() {
		return v -> v.index() == 0 && v.pendientes().isEmpty();
	}


	public Boolean existeCaminoDeVuelta(Integer accion) {
		Boolean res = true;
		List<Integer> restantes = pendientes.stream().collect(Collectors.toList());
		restantes.remove(accion);
		int i = 1;
		while (i < restantes.size()) {
			res = DatosClientes.existeArista(restantes.get(i), 0);
			if (res.equals(true)) {
				break;

			} else {
				res = false;
				i++;
			}
		}
		return res;
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();

		if (visitados.size() == DatosClientes.getNumVertices()) {
			if (DatosClientes.existeArista(index, 0)) {
				alternativas.add(0);
			}
		} else {
			for (Integer elem : pendientes) {
				if (DatosClientes.existeArista(index, elem)) {
					if (existeCaminoDeVuelta(elem)) {
						alternativas.add(elem);
					}
				}
			}
		}
		return alternativas;
	}

	@Override
	public ClientesVertex neighbor(Integer a) {
		Set<Integer> rest = Set2.copy(pendientes);
		rest.remove(a);
		List<Integer> vis = List2.copy(visitados);
		vis.add(a);
		return of(a, rest, vis, kms + DatosClientes.getPeso(index, a));
	}

	@Override
	public ClientesEdge edge(Integer a) {
		return ClientesEdge.of(this, neighbor(a), a);

	}

	// Se explica en practicas.
	public ClientesEdge greedyEdge() {
		return null;
	}
}
