package JUtilidadesVariasLib.Sonido;

public interface Sonido {
    
    /**
     * Inicia la reproducción del sonido
     */
    public void reproducir();

    /**
     * Para la reproducción del sonido
     */
    public void parar() ;

    /**
     * La próxima vez que se reproduzca comenzará desde esta posición.     *
     * @param posicionEnMilisegundos Posición a la que se desea saltar.
     */
    public void saltar(long posicionEnMilisegundos);

    /**
     * Permite activar o desactivar la reproducción continua de un sonido.
     * @param bucle 
     */
    public void modoBucle(boolean bucle);

    /**
     * Indica el número de veces que se ha de repetir un sonido
     * @param numeroDeVeces Número de veces para repetir el sonido.
     */
    public void modoBucle(int numeroDeVeces) ;
    
    /**
     * Indica si se ha producido un error al cargar el archivo.
     * @return True si hay error.
     */
    public boolean hayError();
    
    /**
     * Devuelve la duración del archivo en milisegundos.
     * @return Duración total en milisegundos.
     */
    public long duracion();
    
    /**
     * Devuelve la posición actual de la reproducción.
     * @return Posición en milisegundos.
     */
    public long posicionActual();
}
