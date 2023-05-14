package tests;

import java.util.List;

import _datos.DatosCursos;
import _soluciones.SolucionCursos;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicio2.CursoVertex;

public class TestEjercicio2 {
	public static void main(String[] args) {
		List.of(1, 2, 3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio2DatosEntrada", num_test, DatosCursos::iniDatos);

			TestsPI5.tests(CursoVertex.initial(), // Vertice Inicial
					CursoVertex.goal(), // Predicado para un vertice final
					GraphsPI5::cursosBuilder, // Referencia al Builder del grafo
					CursoVertex::greedyEdge, // Referencia a la Funcion para la arista voraz
					SolucionCursos::of); // Referencia al metodo factoria para la solucion
		});
	}

}
