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
package dk.dma.epd.shore.gui.settingtabs;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import dk.dma.epd.common.prototype.gui.settings.BaseSettingsPanel;
import dk.dma.epd.common.prototype.gui.settings.ISettingsListener.Type;
import dk.dma.epd.common.prototype.settings.SensorSettings.SensorConnectionType;
import dk.dma.epd.shore.EPDShore;
import dk.dma.epd.shore.settings.EPDAisSettings;
import dk.dma.epd.shore.settings.EPDSensorSettings;

public class AisSettingsPanel extends BaseSettingsPanel {

    private static final long serialVersionUID = 1L;
    
    private JTextField textFieldAisHostOrSerialPort;
    private EPDAisSettings aisSettings;
    @SuppressWarnings("rawtypes")
    private JComboBox comboBoxAisConnectionType;
    private JSpinner spinnerAisTcpOrUdpPort;
    private JCheckBox chckbxAllowSending;
    private JCheckBox chckbxStrictTimeout;
    private EPDSensorSettings sensorSettings;


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public AisSettingsPanel(){
        super("AIS Settings", EPDShore.res().getCachedImageIcon("images/settings/binocular.png"));

        setBackground(GuiStyler.backgroundColor);
        setBounds(10, 11, 493, 600);
        setLayout(null);

        JPanel aisConnection = new JPanel();
        aisConnection.setBackground(GuiStyler.backgroundColor);
        aisConnection.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, new Color(70, 70, 70)), "AIS Connection", TitledBorder.LEADING, TitledBorder.TOP, GuiStyler.defaultFont, GuiStyler.textColor));

        aisConnection.setBounds(10, 11, 473, 117);
        add(aisConnection);
        aisConnection.setLayout(null);

        JLabel lblNewLabel = new JLabel("Connection type:");
        GuiStyler.styleText(lblNewLabel);
        lblNewLabel.setBounds(10, 22, 114, 14);
        aisConnection.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Host or serial port:");
        GuiStyler.styleText(lblNewLabel_1);
        lblNewLabel_1.setBounds(10, 46, 114, 14);
        aisConnection.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("TCP Port:");
        GuiStyler.styleText(lblNewLabel_2);
        lblNewLabel_2.setBounds(10, 68, 46, 14);
        aisConnection.add(lblNewLabel_2);

        comboBoxAisConnectionType = new JComboBox();
        GuiStyler.styleDropDown(comboBoxAisConnectionType);
        comboBoxAisConnectionType.setModel(new DefaultComboBoxModel(EPDSensorSettings.SensorConnectionType.values()));
        comboBoxAisConnectionType.setBounds(134, 19, 142, 20);
        aisConnection.add(comboBoxAisConnectionType);

        textFieldAisHostOrSerialPort = new JTextField();
        GuiStyler.styleTextFields(textFieldAisHostOrSerialPort);
        textFieldAisHostOrSerialPort.setBounds(134, 43, 142, 20);
        aisConnection.add(textFieldAisHostOrSerialPort);
        textFieldAisHostOrSerialPort.setColumns(10);

        spinnerAisTcpOrUdpPort = new JSpinner();
        spinnerAisTcpOrUdpPort.setEditor(new NumberEditor(spinnerAisTcpOrUdpPort, "#"));
        GuiStyler.styleSpinner(spinnerAisTcpOrUdpPort);
        spinnerAisTcpOrUdpPort.setBounds(134, 65, 142, 20);
        aisConnection.add(spinnerAisTcpOrUdpPort);

        JPanel transponderSettings = new JPanel();

        transponderSettings.setBackground(GuiStyler.backgroundColor);
        transponderSettings.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, new Color(70, 70, 70)), "Transponder Settings", TitledBorder.LEADING, TitledBorder.TOP, GuiStyler.defaultFont, GuiStyler.textColor));
        transponderSettings.setBounds(10, 150, 472, 100);
        add(transponderSettings);
        transponderSettings.setLayout(null);

        chckbxAllowSending = new JCheckBox("Allow Sending");
        GuiStyler.styleCheckbox(chckbxAllowSending);
        chckbxAllowSending.setBounds(6, 27, 125, 23);
        transponderSettings.add(chckbxAllowSending);


        chckbxStrictTimeout = new JCheckBox("Strict timeout");
        GuiStyler.styleCheckbox(chckbxStrictTimeout);
        chckbxStrictTimeout.setBounds(6, 53, 120, 23);
        transponderSettings.add(chckbxStrictTimeout);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoadSettings() {
        aisSettings = EPDShore.getInstance().getSettings().getAisSettings();
        sensorSettings = EPDShore.getInstance().getSettings().getSensorSettings();
        
        comboBoxAisConnectionType.getModel().setSelectedItem(sensorSettings.getAisConnectionType());
        textFieldAisHostOrSerialPort.setText(sensorSettings.getAisHostOrSerialPort());
        spinnerAisTcpOrUdpPort.setValue(sensorSettings.getAisTcpOrUdpPort());

        chckbxAllowSending.setSelected(aisSettings.isAllowSending());
        chckbxStrictTimeout.setSelected(aisSettings.isStrict());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSaveSettings() {
        sensorSettings.setAisConnectionType((SensorConnectionType) comboBoxAisConnectionType.getModel().getSelectedItem());
        sensorSettings.setAisHostOrSerialPort(textFieldAisHostOrSerialPort.getText());
        sensorSettings.setAisTcpOrUdpPort((Integer) spinnerAisTcpOrUdpPort.getValue());

        aisSettings.setAllowSending(chckbxAllowSending.isSelected());
        aisSettings.setStrict(chckbxStrictTimeout.isSelected());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkSettingsChanged() {
        return 
                changed(sensorSettings.getAisConnectionType(), comboBoxAisConnectionType.getModel().getSelectedItem()) ||
                changed(sensorSettings.getAisHostOrSerialPort(), textFieldAisHostOrSerialPort.getText()) ||
                changed(sensorSettings.getAisTcpOrUdpPort(), spinnerAisTcpOrUdpPort.getValue()) ||
                
                changed(aisSettings.isAllowSending(), chckbxAllowSending.isSelected()) ||
                changed(aisSettings.isStrict(), chckbxStrictTimeout.isSelected());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fireSettingsChanged() {
        fireSettingsChanged(Type.AIS);
        fireSettingsChanged(Type.SENSOR);
    }
    
}
