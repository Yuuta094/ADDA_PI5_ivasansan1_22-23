package ejercicio4;

import _datos.DatosClientes;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record ClientesEdge(ClientesVertex source, ClientesVertex target, Integer action, Double weight)
		implements SimpleEdgeAction<ClientesVertex, Integer> {

	public static ClientesEdge of(ClientesVertex s, ClientesVertex t, Integer a) {
		//La arista debe tener peso
		return new ClientesEdge(s, t, a, DatosClientes.getBeneficio(t.index()));
	}
}