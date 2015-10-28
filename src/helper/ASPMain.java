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
import java.util.List;
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

		Integer numProd;
		Integer numIzq;
		List<Produccion> producciones = new ArrayList<Produccion>();
		List<String> derecha = new ArrayList<String>();
		ASPHelper helper = null;
		String ladoIzq;

		teclado = new Scanner(System.in);
		System.out.println("Vacio se representa con: " + ASPHelper.VACIO);
		System.out.println();

		System.out.println("Ingrese cantidad de producciones");

		teclado = new Scanner(System.in);
		numProd = teclado.nextInt();

		for (int i = 0; i < numProd; i++) {

			System.out.println("Ingrese el lado izquierdo de la produccion "
					+ i);
			System.out.println();
			ladoIzq = teclado.next();
			System.out
					.println("Ingrese la cantidad de variables del lado izquierdo para "
							+ ladoIzq);
			System.out.println();
			numIzq = teclado.nextInt();

			for (int j = 0; j < numIzq; j++) {
				System.out.println("Ingrese variable " + j
						+ " del lado izquierdo");
				System.out.println();

				derecha.add(teclado.next());
			}
			producciones.add(new Produccion(ladoIzq, derecha, false));
			derecha = new ArrayList<String>();
		}

		// Impresion y llamada a funcion primero

		helper = new ASPHelper(producciones);
		Set<String> noTerminales = helper.getNoTerminales();
		System.out.println("Gramatica ingresada:");
		for (Produccion prod : producciones) {
			System.out.println(prod);
		}
		for (String it : noTerminales) {
			helper = new ASPHelper(producciones);
			System.out.println("Primero de " + it + helper.getPrimero(it));
		}

	}
}
