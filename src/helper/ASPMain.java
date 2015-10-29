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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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

		List<Produccion> producciones = new ArrayList<Produccion>();
		List<String> derecha = new ArrayList<String>();
		ASPHelper helper = null;
		String ladoIzq;
		String separador;

		teclado = new Scanner(System.in);
		System.out
				.println("Cualquier contenido ingresado despues de un espacio en blanco sera ignorado\nVacio se representa con: vacio");
		System.out.println();

		String entrada;
		teclado = new Scanner(System.in);

		System.out
				.println("Ingrese separador a utilizar en el lado derecho de las producciones");
		separador = teclado.next();

		System.out.println("Ingrese producciones, para terminar ingrese FIN");
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
				if (!variables[k].equals(""))
					derecha.add(variables[k]);
			}
			producciones.add(new Produccion(ladoIzq, derecha, false));
			teclado.nextLine();
			entrada = teclado.next();
		}

		// Impresion y llamada a funcion primero

		helper = new ASPHelper(producciones);
		Set<String> noTerminales = helper.getNoTerminales();
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
		ASPTable tabla = new ASPTable(primeros, siguientes, producciones);
		System.out.println(tabla.generarTablaASP());

	}
}
