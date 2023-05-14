package _datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class DatosClientes {
	
	// -----------------------------------CLIENTE-------------------------------------------------------\\

	public record Cliente(int id, Double beneficio) {

		public static Cliente of(int id, Double beneficio) {
			return new Cliente(id, beneficio);
		}

		public static Cliente ofFormat(String[] formato) {
			Integer id = Integer.valueOf(formato[0].trim());
			Double benef = Double.valueOf(formato[1].trim());
			return of(id, benef);
		}

		@Override
		public String toString() {
		return String.valueOf(this.id());
		
		}
	}
	
	// -----------------------------------CONEXCION-------------------------------------------------------\\

		public record Conexion(int id, Double distancia) {
			public static int cont;

			public static Conexion of(Double distancia) {
				Integer id = cont;
				cont++;
				return new Conexion(id, distancia);
			}

			public static Conexion ofFormat(String[] formato) {
				Double dist = Double.valueOf(formato[2].trim());
				return of(dist);
			}

			@Override
			public String toString() {
				return "id: " + this.id() + "; distancia: " + this.distancia();
			}
		}


		public static Graph<Cliente, Conexion> grafo;

		public static void iniDatos(String fichero) {
			grafo = GraphsReader.newGraph(fichero, Cliente::ofFormat, Conexion::ofFormat, Graphs2::simpleWeightedGraph);
			toConsole();
		}

		
		// ------------------------------------------------------------------------------------------\\

		public static Integer getNumVertices() {
			return grafo.vertexSet().size();
		}

		public static Set<Integer> getClientes() {
			return grafo.vertexSet().stream().map(x -> x.id()).collect(Collectors.toSet());
		}

		public static Cliente getCliente(Integer i) {
			Cliente c = null;
			List<Cliente> vertices = new ArrayList<>(grafo.vertexSet());
			for (int k = 0; k < vertices.size(); k++) {
				if (vertices.get(k).id() == i) {
					c = vertices.get(k);
				}
			}
			return c;
		}

		public static Double getBeneficio(Integer i) {
			Cliente c = getCliente(i);
			return c.beneficio();
		}


		public static Double getPeso(Integer i, Integer j) {
			Cliente c1 = getCliente(i);
			Cliente c2 = getCliente(j);
			return grafo.getEdge(c1, c2).distancia();
		}
		

		public static boolean existeArista(Integer i, Integer j) {
			Cliente c1 = getCliente(i);
			Cliente c2 = getCliente(j);
			return grafo.containsEdge(c1, c2);
		}

		// ------------------------------------------------------------------------------------------\\
		
		


		private static void toConsole() {
			System.out.println("Número de vértices: " + grafo.vertexSet().size() + "\n\tVértices: " + grafo.vertexSet()
					+ "\nNúmero de aristas: " + grafo.edgeSet().size() + "\n\tAristas: " + grafo.edgeSet());
		}

		public static void main(String[] args) {
			iniDatos("ficheros/Ejercicio4DatosEntrada1.txt");
			System.out.println(getPeso(2, 4));
		}
	}