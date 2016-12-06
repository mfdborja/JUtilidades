package JUtilidadesVariasLib.Tar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import org.xeustechnologies.jtar.*;

/**Clase que permite manejar un archivo Tar.
 *
 * @author Borja
 */
public class Tar {

    private final String rutaTar;

    /**
     * Crea la instancia para manejar el tar.
     * @param rutaTar Ruta del archivo tar.
     */
    public Tar(String rutaTar) {
        this.rutaTar = rutaTar;
    }

    /**
     * Añade un archivo al contenedor. Si no existe el contenedor lo crea.
     * @param rutaNuevoArchivo Ruta del archivo.
     * @param idArchivo Identificador del archivo en el tar
     * @return Si se ha realizado correctamente
     */
    public boolean addArchivo(String rutaNuevoArchivo, String idArchivo) {
        try {
            //Flujo al archivo de salida
            FileOutputStream flujoArchivoDestino = new FileOutputStream(rutaTar, true);

            // Flujo al tar
            TarOutputStream flujoTarSalida = new TarOutputStream(new BufferedOutputStream(flujoArchivoDestino));

            //Archivo en el tar
            File archivoParaAgregar = new File(rutaNuevoArchivo);
            flujoTarSalida.putNextEntry(new TarEntry(archivoParaAgregar, idArchivo));
            BufferedInputStream origen = new BufferedInputStream(new FileInputStream(archivoParaAgregar));
            int count;
            byte data[] = new byte[4096];
            while ((count = origen.read(data)) != -1) {
                flujoTarSalida.write(data, 0, count);
            }

            origen.close();
            flujoTarSalida.close();
            flujoArchivoDestino.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.err.println("El archivo da un error. " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Error de E/S. " + ex.getMessage());
        }
        return false;
    }

    /**
     * Devuelve un archivo.
     * @param idArchivo Identificador con el que se guardó el archivo
     * @return 
     */
    public PipedOutputStream getArchivo(String idArchivo) {
        PipedOutputStream salida = null;
        boolean continuar=true;
        
        try {
            // Create a TarInputStream
            TarInputStream flujoLectura = new TarInputStream(new BufferedInputStream(new FileInputStream(rutaTar)));
            TarEntry entradaEnElTar;
            while ((entradaEnElTar = flujoLectura.getNextEntry()) != null && continuar) {
                //Si es el archivo que buscamos
                if (entradaEnElTar.getName().equalsIgnoreCase(idArchivo)) {
                    int count;
                    byte data[] = new byte[4096];
                    salida = new java.io.PipedOutputStream();

                    //Leemos
                    while ((count = flujoLectura.read(data)) != -1) {
                        salida.write(data, 0, count);
                    }
                    
                    //Parar bucle
                    continuar=false;
                }
            }

            flujoLectura.close();
            salida.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
        return salida;
    }
}
