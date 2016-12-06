package JUtilidadesVariasLib.Sonido;

import java.io.*; //Aquí se encuentran las herramientas necesarias para obtener el archivo. En este caso será un archivo del disco duro.
import javax.sound.sampled.*; //Nos provee de los métodos necesarios para reproducir el sonido.

/**
 * Permite reproducir archivos wave y au.
 */
public class SonidoWaveAU implements Sonido{

    /**
     * Objeto que permite lanzar y parar audio.
     */
    private Clip interfaz;
    
    /**
     * Permite obtener información información extra del dataline
     */
    private DataLine.Info info;
    
    /**
     * Indica si se ha producido un error.
     */
    private boolean error=false;

    /**
     * Crea un sonido a partir del archivo en la ruta dada.
     * @param rutaSonido Ruta del archivo de audio
     */
    public SonidoWaveAU(String rutaSonido) {
        /**
         * Archivo con el audio.
         */
        File archivo;

        /**
         * Es un flujo de audio. Nos proporciona información de la duración del
         * sonido. Es un flujo adaptado a las necesidades de un stream de audio.
         */
        AudioInputStream flujoAudio;
        try {
            //Instanciamos los objetos necesarios para la reproducción del sonido.
            archivo = new File(rutaSonido);

            //La clase AudioSystem actúa como punto de entrada a los recursos del sistema de muestreo de audio. 
            //Esta clase permite consultar y acceder a los mezcladores instalados en el sistema.
            flujoAudio = AudioSystem.getAudioInputStream(archivo);
            
            //Data line nos da  funcionalindad a la clase.
            info = new DataLine.Info(Clip.class, flujoAudio.getFormat(), ((int) flujoAudio.getFrameLength()));

            //Clip no es mas que una interfaz que nos permite cargar los datos antes de reproducirlos. 
            //Evitando que la transmisiòn de datos se realice en tiempo real.
            interfaz = (Clip) AudioSystem.getLine(info);

            //Similar al open() para abrir un archivo o un flujo.
            interfaz.open(flujoAudio);

            //Excepciones que se lanzarÃ¡n cuando no se encuentre el archivo, o no pueda reproducirlo.
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            error=true;
        }
    }

    /**
     * Inicia la reproducción del sonido
     */
    @Override
    public void reproducir() {
        //Esta es la línea importante. Lanza la reproducción del sonido. 
        //Así mismo, el método stop nos permitirá parar la reproducción del sonido.
        interfaz.start();
    }

    /**
     * Para la reproducción del sonido
     */
    @Override
    public void parar() {
        interfaz.stop();
    }

    /**
     * La próxima vez que se reproduzca comenzará desde esta posición.     *
     * @param posicionEnMilisegundos Posición a la que se desea saltar.
     */
    @Override
    public void saltar(long posicionEnMilisegundos) {
        interfaz.setMicrosecondPosition(posicionEnMilisegundos*1000);
    }

    /**
     * Permite activar o desactivar la reproducción continua de un sonido.
     * @param bucle 
     */
    @Override
    public void modoBucle(boolean bucle) {
        if (bucle) {
            //Reproducción en bucle. Si lo activamos repetirá el sonido una y otra vez. 
            //En este caso desde el principio.
            interfaz.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            interfaz.loop(0);
        }
    }

    /**
     * Indica el número de veces que se ha de repetir un sonido
     * @param numeroDeVeces Número de veces para repetir el sonido.
     */
    @Override
    public void modoBucle(int numeroDeVeces) {
        //Reproducción en bucle. Si lo activamos repetirá el sonido una y otra vez. 
        //En este caso desde el principio.
        interfaz.loop(numeroDeVeces);
    }
    
    /**
     * Indica si se ha producido un error al cargar el archivo.
     * @return True si hay error.
     */
    @Override
    public boolean hayError(){
        return error;
    }
    
    /**
     * Devuelve la duración del archivo en milisegundos.
     * @return Duración total en milisegundos.
     */
    @Override
    public long duracion(){
        return interfaz.getMicrosecondLength()/1000;
    }
    
    /**
     * Devuelve la posición actual de la reproducción.
     * @return Posición en milisegundos.
     */
    @Override
    public long posicionActual(){
        return interfaz.getMicrosecondPosition()/1000;
    }
}
