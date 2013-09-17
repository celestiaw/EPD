/* Copyright (c) 2011 Danish Maritime Authority
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package dk.dma.epd.ship.gui.voct;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.JXDatePicker;

public class SurfaceDriftPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private SimpleDateFormat format = new SimpleDateFormat("E dd/MM/yyyy");

    private JTextField twcField;
    JTextField twcHeadingField;
    JTextField leewayField;
    JTextField leewayHeadingField;

    public double getTWCKnots() {

        return Double.parseDouble(twcField.getText());
    }

    public double getLeeway() {

        return Double.parseDouble(leewayField.getText());
    }

    public double getTWCHeading() {

        String twcField = twcHeadingField.getText();

        String value = twcField.substring(0, twcField.length() - 1);

        return Double.parseDouble(value);
    }

    public double getLeewayHeading() {

        String leewayField = leewayHeadingField.getText();

        String value = leewayField.substring(0, leewayField.length() - 1);

        return Double.parseDouble(value);
    }

    public SurfaceDriftPanel(int number) {

        setBorder(new TitledBorder(null, "Point " + (number + 1),
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        // int offset = 56 + (474 * number);
        int offset = 56 + (110 * number);

        setBounds(5, offset, 464, 99);
        setLayout(null);

        JXDatePicker surfaceDriftPicker = new JXDatePicker();
        surfaceDriftPicker.setBounds(160, 22, 105, 20);
        add(surfaceDriftPicker);

        surfaceDriftPicker.setFormats(format);

        JLabel lblsurfaceDriftTime = new JLabel("Date & Time of Surface Drift:");
        lblsurfaceDriftTime.setBounds(13, 25, 147, 14);
        add(lblsurfaceDriftTime);

        Date date3 = new Date();
        SpinnerDateModel currentTimeModel3 = new SpinnerDateModel(date3, null,
                null, Calendar.HOUR_OF_DAY);

        JSpinner surfaceDriftSpinner = new JSpinner(currentTimeModel3);

        surfaceDriftSpinner.setLocation(268, 22);
        surfaceDriftSpinner.setSize(54, 20);
        JSpinner.DateEditor dateEditorSurfaceDrift = new JSpinner.DateEditor(
                surfaceDriftSpinner, "HH:mm");
        surfaceDriftSpinner.setEditor(dateEditorSurfaceDrift);

        add(surfaceDriftSpinner);

        JLabel lblTWC = new JLabel("Total Water Current, knots:");
        lblTWC.setBounds(13, 50, 147, 14);
        add(lblTWC);

        twcField = new JTextField();
        twcField.setBounds(160, 47, 33, 20);
        add(twcField);
        twcField.setColumns(10);

        JLabel lblTwcVectorHeading = new JLabel(
                "TWC Vector, Heading or Degrees:");
        lblTwcVectorHeading.setBounds(203, 50, 173, 14);
        add(lblTwcVectorHeading);

        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "N",
                "NE", "NW", "S", "SW", "SE", "E", "W" }));
        comboBox.setBounds(372, 47, 33, 20);
        add(comboBox);

        twcHeadingField = new JTextField();
        twcHeadingField.setText("00.0°");
        twcHeadingField.setBounds(415, 47, 47, 20);
        add(twcHeadingField);
        twcHeadingField.setColumns(10);

        JLabel lblLeewayKnots = new JLabel("Leeway, knots:");
        lblLeewayKnots.setBounds(13, 78, 147, 14);
        add(lblLeewayKnots);

        leewayField = new JTextField();
        leewayField.setColumns(10);
        leewayField.setBounds(160, 75, 33, 20);
        add(leewayField);

        JLabel lblLwVectorHeading = new JLabel("LW Vector, Heading or Degrees:");
        lblLwVectorHeading.setBounds(203, 78, 173, 14);
        add(lblLwVectorHeading);

        JComboBox<String> comboBox_2 = new JComboBox<String>();
        comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] {
                "N", "NE", "NW", "S", "SW", "SE", "E", "W" }));
        comboBox_2.setBounds(372, 75, 33, 20);
        add(comboBox_2);

        leewayHeadingField = new JTextField();
        leewayHeadingField.setText("00.0°");
        leewayHeadingField.setColumns(10);
        leewayHeadingField.setBounds(415, 75, 47, 20);
        add(leewayHeadingField);

    }

}