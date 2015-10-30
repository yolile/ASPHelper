/*-
 * Copyright (c)
 *
 * 2015 - Yohanna Lisnichuk
 * 2015 - Victor Ughelli
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package src.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import src.helper.clases.Key;
import src.helper.clases.Produccion;

/**
 * Clase de ejemplo de la utilizacion de {@link ASPHelper}
 * 
 * @author Yohanna Lisnichuk
 * @since 1.0
 * @version 1.0 25/10/2015
 * 
 */
public class ASPMain {

	private static Scanner teclado;

	public static void main(String[] ar) {

		while (true) {
			List<Produccion> producciones = new ArrayList<Produccion>();
			List<String> derecha = new ArrayList<String>();
			ASPHelper helper = null;
			String ladoIzq;
			String separador;
			Queue<String> input = new LinkedList<String>();
			Stack<String> stack = new Stack<String>();

			teclado = new Scanner(System.in);
			System.out
					.println("Cualquier contenido ingresado despues de un espacio en blanco sera ignorado\nVacio se representa con: vacio");
			System.out.println();

			String entrada;
			teclado = new Scanner(System.in);

			System.out
					.println("Ingrese separador a utilizar en el lado derecho de las producciones");
			separador = teclado.next();

			System.out
					.println("Ingrese producciones, para terminar ingrese FIN");
			entrada = teclado.next();

			while (!entrada.equals("FIN")) {
				if (!entrada.contains("->")) {
					System.out.println("produccion invalida");
					teclado.nextLine();
					entrada = teclado.next();
					continue;
				}

				String[] entradas = entrada.split("->", 2);

				if (entradas[1].equals("")) {
					System.out.println("falta lado derecho");
					teclado.nextLine();
					entrada = teclado.next();
					continue;
				}

				String[] variables = entradas[1].split(separador);
				ladoIzq = (entradas[0]);
				derecha = new ArrayList<String>();

				int k;
				for (k = 0; k < variables.length; k++) {
					if (!variables[k].equals("")) {
						derecha.add(variables[k]);
					}
				}
				producciones.add(new Produccion(ladoIzq, derecha, false));
				teclado.nextLine();
				entrada = teclado.next();
			}

			// Impresion y llamada a funcion primero

			helper = new ASPHelper(producciones);
			Set<String> noTerminales = helper.getNoTerminales();
			Set<String> terminales = helper.getTerminales();
			System.out.println("Gramatica ingresada:");
			for (Produccion prod : producciones) {
				System.out.println(prod);
			}
			Map<String, Set<String>> primeros = new HashMap<String, Set<String>>();
			Map<String, Set<String>> siguientes = new HashMap<String, Set<String>>();
			for (String it : noTerminales) {
				helper = new ASPHelper(producciones);
				Set<String> temp = helper.getPrimero(it);
				System.out.println("Primero de " + it + temp);
				primeros.put(it, temp);
			}
			for (String it : noTerminales) {
				helper = new ASPHelper(producciones);
				Set<String> temp = helper.getSiguiente(primeros, it);
				System.out.println("Siguiente de " + it + temp);
				siguientes.put(it, temp);
			}
			// Se genera la tabla ASP
			ASPTable tabla = new ASPTable(primeros, siguientes, producciones);
			Map<Key, List<Produccion>> tablaGen = (tabla.generarTablaASP());

			// Impresion de la tabla ASP
			System.out.println("Tabla ASP");
			System.out.format("%20s", "");
			terminales.remove("vacio");
			terminales.add("$");
			for (String term : terminales) {
				System.out.format("%20s", term);
			}
			boolean esAmbigua = false;
			for (String noTerm : noTerminales) {
				System.out.println();
				System.out.format("%20s", noTerm);
				// System.out.print(noTerm + "\t");
				for (String term : terminales) {
					Key key = new Key(noTerm, term);
					if (tablaGen.get(key) == null) {
						if (siguientes.get(noTerm).contains(term)) {
							List<Produccion> pp = new ArrayList<Produccion>();
							pp.add(new Produccion("sinc", null, false));
							tablaGen.put(key, pp);
							System.out.format("%20s", "sinc");
						} else {

							System.out.format("%20s", "error");
						}
					} else {
						for (Produccion prod : tablaGen.get(key)) {
							System.out.format("%20s", prod);
						}
						if (tablaGen.get(key).size() > 1) {
							esAmbigua = true;
						}

					}

				}

			}

			if (esAmbigua) {
				System.out.println("\nLa gramatica es ambigua!");
			} else {

				// Seccion de generar derivacion
				System.out.println();
				System.out.println("Ingrese Entrada a probar");
				teclado.nextLine();
				entrada = teclado.next();
				String[] entradas = entrada.split(separador);

				for (int k = 0; k < entradas.length; k++) {
					input.add(entradas[k]);
				}

				stack.add("$");
				stack.add(producciones.get(0).getIzquierda());

				ASPParser.parser(input, stack, tablaGen, producciones);
			}
			System.out.println();
			System.out.println("Ingrese SI si desea ingresar otra gramatica");
			teclado.nextLine();
			entrada = teclado.next();
			if (!entrada.equals("SI")) {
				break;
			}
		}

	}
}
