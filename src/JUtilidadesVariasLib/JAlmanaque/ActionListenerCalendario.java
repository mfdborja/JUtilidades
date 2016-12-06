package JUtilidadesVariasLib.JAlmanaque;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

class ActionListenerCalendario implements ActionListener {

    Calendar calendarioActual;
    JLabelAlmanaque almanaque;

    ActionListenerCalendario(JLabelAlmanaque almanaque) {
        this.calendarioActual = almanaque.getFecha();
        this.almanaque=almanaque;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Calendar auxCal = Calendar.getInstance();
        auxCal.set(Calendar.DATE, 1);
        auxCal.set(Calendar.YEAR, calendarioActual.get(Calendar.YEAR));

        //Pasar mes
        if (e.getActionCommand().equals(">")) {
            auxCal.set(Calendar.MONTH, calendarioActual.get(Calendar.MONTH) + 1);

            if (calendarioActual.get(Calendar.DATE) > auxCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                calendarioActual.set(Calendar.DATE, 1);
                calendarioActual.set(Calendar.MONTH, calendarioActual.get(Calendar.MONTH) + 1);
            } else {
                calendarioActual.set(Calendar.MONTH, calendarioActual.get(Calendar.MONTH) + 1);
            }

        } else {
            //Retroceder mes
            auxCal.set(Calendar.MONTH, calendarioActual.get(Calendar.MONTH) - 1);

            if (calendarioActual.get(Calendar.DATE) > auxCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                calendarioActual.set(Calendar.DATE, 1);
                calendarioActual.set(Calendar.MONTH, calendarioActual.get(Calendar.MONTH) - 1);
            } else {
                calendarioActual.set(Calendar.MONTH, calendarioActual.get(Calendar.MONTH) - 1);
            }
        }
        
        //Actualizar visualización
        almanaque.setFecha();
        almanaque.pintarCalendario();
    }

}
