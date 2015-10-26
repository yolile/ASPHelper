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

        teclado = new Scanner(System.in);
        System.out.println("Ingrese cantidad de producciones");
        System.out.println();
        Integer numProd = teclado.nextInt();
        System.out
                .println("Cada variable debe ser de un solo caracter. Vacio se representa con: vacio");

        List<Produccion> producciones = new ArrayList<Produccion>();
        for (int i = 0; i < numProd; i++) {

            teclado = new Scanner(System.in);

            System.out.println("Ingrese la produccion de la forma A:BC");
            System.out.println();
            String aux = teclado.next();
            producciones.add(new Produccion(aux.split(":")[0],
                    aux.split(":")[1]));

        }
        ASPHelper helper;
        // Impresion y llamada a funcion primero
        for (Produccion prod : producciones) {
            helper = new ASPHelper(producciones);
            System.out.println("Primero de " + prod.getIzquierda()
                    + helper.getPrimero(prod.getIzquierda()));
        }
    }

}
