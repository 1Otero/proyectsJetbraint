package geometria;

public class Triangulo {
   public static void main(String args[]){
      /*for(int o= 0; 0< 2; o++){
         // Definir el tamaño del array (ajústalo según tus necesidades)
         int ancho = 20;
         int alto = 10;

         // Crear un array vacío para representar la imagen
         char[][] imagen = new char[alto][ancho];

         // Calcular el radio máximo del círculo
         int radioMaximo = Math.min(ancho, alto) / 2;

         // Realizar la animación del círculo abriendo y cerrando
         for (int radio = radioMaximo; radio > 0; radio--) {
            // Rellenar el array para representar el círculo en cada paso
            for (int y = 0; y < alto; y++) {
               for (int x = 0; x < ancho; x++) {
                  int distanciaCentro = (x - ancho / 2) * (x - ancho / 2) + (y - alto / 2) * (y - alto / 2);
                  if (distanciaCentro <= radio * radio) {
                     imagen[y][x] = '*';
                  } else {
                     imagen[y][x] = ' ';
                  }
               }
            }

            // Imprimir la imagen por consola
            for (int y = 0; y < alto; y++) {
               for (int x = 0; x < ancho; x++) {
                  System.out.print(imagen[y][x]);
               }
               System.out.println(); // Salto de línea al final de cada fila
            }

            // Pausa para la animación (puedes ajustar la velocidad)
            try {
               Thread.sleep(100);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }

            // Limpiar la consola (esto puede variar según el entorno)
            System.out.print("\033[H\033[2J");
            System.out.flush();
      }
      }*/
      // Definir el tamaño del array (ajústalo según tus necesidades)
      int ancho = 20;
      int alto = 10;

      // Crear un array vacío para representar la imagen
      char[][] imagen = new char[alto][ancho];

      // Definir las coordenadas de los vértices del triángulo
      int[] xVertices = {ancho / 2, ancho - 1, 0};
      int[] yVertices = {0, alto - 1, alto - 1};

      // Realizar la animación del triángulo abriendo y cerrando
      for (int lado = 0; lado < ancho / 2; lado++) {
         // Rellenar el array para representar el triángulo en cada paso
         for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
               boolean dentroDelTriangulo = puntoDentroTriangulo(x, y, xVertices, yVertices);
               if (dentroDelTriangulo) {
                  imagen[y][x] = '*';
               } else {
                  imagen[y][x] = ' ';
               }
            }
         }

         // Imprimir la imagen por consola
         for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
               System.out.print(imagen[y][x]);
            }
            System.out.println(); // Salto de línea al final de cada fila
         }

         // Pausa para la animación (puedes ajustar la velocidad)
         try {
            Thread.sleep(100);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }

         // Limpiar la consola (esto puede variar según el entorno)
         System.out.print("\033[H\033[2J");
         System.out.flush();

         // Actualizar las coordenadas de los vértices del triángulo
         xVertices[0]++;
         xVertices[2]--;
         yVertices[0]++;
         yVertices[1]--;
         yVertices[2]--;
      }
   }

   // Función para verificar si un punto está dentro de un triángulo
   public static boolean puntoDentroTriangulo(int x, int y, int[] xVertices, int[] yVertices) {
      int d1 = (x - xVertices[1]) * (yVertices[0] - yVertices[1]) - (xVertices[0] - xVertices[1]) * (y - yVertices[1]);
      int d2 = (x - xVertices[2]) * (yVertices[1] - yVertices[2]) - (xVertices[1] - xVertices[2]) * (y - yVertices[2]);
      int d3 = (x - xVertices[0]) * (yVertices[2] - yVertices[0]) - (xVertices[2] - xVertices[0]) * (y - yVertices[0]);

      return (d1 >= 0 && d2 >= 0 && d3 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0);
   }
}
