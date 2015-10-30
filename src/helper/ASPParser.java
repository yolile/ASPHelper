/**
 * 
 */
package src.helper;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import src.helper.clases.Key;
import src.helper.clases.Produccion;

/**
 * @author Victor Ughelli
 *
 */
public class ASPParser {

	public static void parser(Queue<String> input, Stack<String> stack,
			Map<Key, List<Produccion>> tabla, List<Produccion> producciones) {
		String fromEntrada = input.peek();
		String fromPila = stack.peek();
		ASPHelper helper = new ASPHelper(producciones);

		while (!fromPila.equals("$")) {
			System.out.print("\nStack:" + stack + "\tInput:" + input);

			Key key = new Key(fromPila, fromEntrada);
			if (fromEntrada.equals(fromPila)) {
				fromPila = stack.pop();
				fromEntrada = input.poll();
				fromEntrada = input.peek();
			}

			else if (helper.esTerminal(fromPila)) {
				System.out.println("Error al procesar la entrada.");
				return;
			} else if (tabla.get(key) == null) {
				System.out.println("Error al procesar la entrada.");
				return;
			} else {
				if (tabla.get(key).get(0).getDerecha().equals("sinc")) {
					fromPila = stack.pop();

				} else {
					List<String> prod = tabla.get(key).get(0).getDerecha();
					System.out.print("\tAccion:" + tabla.get(key).get(0));
					// Collections.reverse(prod);

					fromPila = stack.pop();

					for (int j = prod.size() - 1; j >= 0; j--) {
						if (!prod.get(j).equals("vacio"))
							stack.push(prod.get(j));
					}
				}

			}
			fromPila = stack.peek();
		}
	}

}
