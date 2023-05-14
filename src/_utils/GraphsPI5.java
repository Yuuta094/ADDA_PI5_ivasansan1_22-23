package _utils;

import java.util.function.Predicate;

import ejercicio1.CafeEdge;
import ejercicio1.CafeVertex;
import ejercicio1.CafesHeuristic;
import ejercicio2.CursoEdge;
import ejercicio2.CursoVertex;
import ejercicio2.CursosHeuristic;
import ejercicio4.ClientesEdge;
import ejercicio4.ClientesHeuristic;
import ejercicio4.ClientesVertex;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.graphs.virtual.EGraphBuilder;
import us.lsi.path.EGraphPath.PathType;

// Clase Factoria para crear los constructores de los grafos de los ejemplos y ejercicios
public class GraphsPI5 {
	
	// Ejercicio1: Builder
	public static EGraphBuilder<CafeVertex, CafeEdge> cafesBuilder(CafeVertex v_inicial, Predicate<CafeVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				.goalHasSolution(CafeVertex.goalHasSolution())
				.heuristic(CafesHeuristic::heuristic);
	}
	
	// Ejercicio2: Builder
		public static EGraphBuilder<CursoVertex, CursoEdge> cursosBuilder(CursoVertex v_inicial, Predicate<CursoVertex> es_terminal) { 
			return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
					.goalHasSolution(CursoVertex.goalHasSolution())
					.heuristic(CursosHeuristic::heuristic);
		}
	
	// Ejercicio4: Builder
	public static EGraphBuilder<ClientesVertex, ClientesEdge> clientesBuilder(ClientesVertex v_inicial, Predicate<ClientesVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				.goalHasSolution(ClientesVertex.goalHasSolution())
				.heuristic(ClientesHeuristic::heuristic);
		}
}
