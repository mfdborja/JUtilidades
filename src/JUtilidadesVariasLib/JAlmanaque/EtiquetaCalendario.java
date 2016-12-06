package JUtilidadesVariasLib.JAlmanaque;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

class EtiquetaCalendario extends JLabel {
    LineBorder borde;
    EtiquetaCalendario(String nombre) {
        super(nombre);
        setHorizontalAlignment(JLabel.CENTER);
    }

     void bordeSeleccion() {
        bordeSeleccion(new LineBorder(java.awt.Color.BLUE));
    }

    void bordeSeleccion(LineBorder lb) {
        setBorder(lb);
    }
}
