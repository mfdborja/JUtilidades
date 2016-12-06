package JUtilidadesVariasLib.JAlmanaque;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Genera un calendario para imprimir por pantalla que permite seleccionar el
 * día.
 */
public class JLabelAlmanaque extends JPanel implements MouseListener {

    private EtiquetaCalendario etiquetaSeleccionada;
    private JTextField textoCalendario;
    private Calendar calendarioActual;
    private JPanel panelCalendario;
    private Color colorFondo;
    private boolean editable;

    final static String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    final static String[] dias = {"Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom"};

    JButton botonAdelante;
    JButton botonAtras;

    /**
     * Crea una instancia con la hora actual del sistema. El tamaño por defecto
     * es 200x200.
     */
    public JLabelAlmanaque() {
        this(Calendar.getInstance());
    }

    /**
     * Crea una instancia con la fecha dada por parámetro. El tamaño por defecto
     * es 200x200.
     *
     * @param dt
     */
    public JLabelAlmanaque(Calendar calendarioInicio) {
        //Calendario base
        this.calendarioActual = calendarioInicio;

        //Creación de la visualización
        colorFondo = UIManager.getColor("Label.background");

        //Tamaño
        setSize(200, 200);

        textoCalendario = new JTextField();
        panelCalendario = new JPanel();
        setLayout(new BorderLayout());

        botonAdelante = new JButton(">");
        botonAtras = new JButton("<");

        //Barra superior
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        tb.add(botonAtras);
        textoCalendario.setHorizontalAlignment(JTextField.CENTER);
        textoCalendario.setEditable(false);
        tb.add(textoCalendario);
        tb.add(botonAdelante);

        add(tb, BorderLayout.NORTH);

        //Pinta el calendario de inicio
        setFecha();
        add(panelCalendario, BorderLayout.CENTER);
        pintarCalendario();

        //Listener de los botones
        ActionListener listener = new ActionListenerCalendario(this);
        botonAdelante.addActionListener(listener);
        botonAtras.addActionListener(listener);

        //Hacer visible
        setVisible(true);
    }

    /**
     * Pinta el calendario.
     */
    void pintarCalendario() {
        etiquetaSeleccionada = null;
        //Borra el calendario actual
        this.remove(1);
        //Nuevo calendario
        panelCalendario = new JPanel();
        panelCalendario.setBackground(colorFondo);
        panelCalendario.setLayout(new GridLayout(7, 7));

        //Trabajo sobre una copia
        Calendar calAux = (Calendar) calendarioActual.clone();
        calAux.set(Calendar.DATE, 1);

        int primerDia = calAux.get(Calendar.DAY_OF_WEEK);

        primerDia = primerDia > 1 ? primerDia - 2 : 6;

        //Rellenar días
        for (int i = 0; i < 7; i++) {
            JLabel jlaux = new JLabel(dias[i]);
            jlaux.setHorizontalAlignment(JLabel.CENTER);
            panelCalendario.add(jlaux);
        }

        int contador = 0;
        //Rellenar días a principio de mes
        for (int i = 0; i < primerDia; i++) {
            panelCalendario.add(new JLabel());
            contador++;
        }

        int diasMes = calendarioActual.getActualMaximum(Calendar.DAY_OF_MONTH);
        EtiquetaCalendario aux;
        for (int i = 1; i <= diasMes; i++) {
            String numCadena = i + "";
            aux = new EtiquetaCalendario(numCadena);
            aux.addMouseListener(this);
            panelCalendario.add(aux);
            contador++;
            if (i == calendarioActual.get(Calendar.DATE)) {
                aux.bordeSeleccion();
                etiquetaSeleccionada = aux;
            }
        }

        //Rellenar días a final de mes
        while (contador < 42) {
            panelCalendario.add(new JLabel());
            contador++;
        }

        //Poner el nuevo calenndario
        add(panelCalendario, BorderLayout.CENTER);

        //Pintarlo
        panelCalendario.updateUI();
    }

    /**
     * Pinta la fecha en texto en el calendario.
     */
    void setFecha() {
        textoCalendario.setText(calendarioActual.get(Calendar.DATE) + " de " + meses[calendarioActual.get(Calendar.MONTH)] + " de " + calendarioActual.get(Calendar.YEAR) + "");
    }

    /**
     * Devuelve la fecha actual seleccionada
     *
     * @return Ultima fecha seleccionada.
     */
    public Calendar getFecha() {
        return calendarioActual;
    }

    /**
     * Hace que el calendario solo muestre una fecha dada. No permite
     * seleccionar si se desactiva.
     *
     * @param editable True permite editar. False no deja editar
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Modifica el tamaño del calendario.
     *
     * @param ancho Ancho en píxeles.
     * @param alto Alto en píxeles.
     */
    @Override
    public void setSize(int ancho, int alto) {
        super.setSize(ancho, alto);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (editable) {
            //Si hay una etiqueta seleccionada
            if (etiquetaSeleccionada != null) {
                etiquetaSeleccionada.bordeSeleccion(null);
            }

            //Etiqueta ahora seleccionada
            EtiquetaCalendario diaSeleccionado = (EtiquetaCalendario) e.getSource();
            diaSeleccionado.bordeSeleccion();
            etiquetaSeleccionada = diaSeleccionado;
            calendarioActual.set(Calendar.DAY_OF_MONTH, Integer.parseInt(etiquetaSeleccionada.getText()));
            //Actualizar fecha
            setFecha();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (editable) {
            EtiquetaCalendario etiqueta = (EtiquetaCalendario) e.getSource();
            etiqueta.setBorder(new LineBorder(java.awt.Color.BLACK));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (editable) {
            EtiquetaCalendario etiqueta = (EtiquetaCalendario) e.getSource();
            etiqueta.setBorder(etiqueta.borde);
        }
    }
}
