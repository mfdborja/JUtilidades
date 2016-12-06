package JUtilidadesVariasLib.Sonido;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.Time;

public class SonidoMp3 implements Sonido {

    /**
     * Reproductor
     */
    //private Player reproductor;
    javax.media.bean.playerbean.MediaPlayer reproductor;
    private boolean error = false;

    /**
     * Crea un sonido.
     */
    public SonidoMp3(String rutaSonido) {
        try {
            //se asigna a mediaURL el archivo de video seleccionado
            URL url = new File(rutaSonido).toURI().toURL();
            //se asigna el mp3 al reproductor
            reproductor = (javax.media.bean.playerbean.MediaPlayer)Manager.createRealizedPlayer(url);
        } catch (IOException | NoPlayerException | CannotRealizeException ex) {
            error = true;
        }
    }

    /**
     * Para la reproducción
     */
    @Override
    public void parar() {
//comprueba que el reproductor tenga un archivo
        reproductor.stop();
    }

    /**
     * Reproduce un archivo mp3, si no encuentra ninguno devuelve un string
     * vacio.
     */
    @Override
    public void reproducir() {
        //si ya se esta reproduciendo
        reproductor.start();
    }

    @Override
    public void saltar(long posicionEnMilisegundos) {
        reproductor.syncStart(new Time(posicionEnMilisegundos*1000000));
    }

    @Override
    public void modoBucle(boolean bucle) {
        reproductor.setPlaybackLoop(bucle);
    }

    @Override
    public void modoBucle(int numeroDeVeces) {
       
    }

    @Override
    public boolean hayError() {
        return error;
    }

    @Override
    public long duracion() {
        return (long)reproductor.getDuration().getSeconds()*1000;
    }

    @Override
    public long posicionActual() {
        return (long)reproductor.getMediaTime().getSeconds()*1000;
    }
}
