/* Copyright (c) 2011 Danish Maritime Authority.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.dma.epd.ship.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import dk.dma.epd.common.prototype.gui.views.CommonBottomPanelStatusDialog;
import dk.dma.epd.common.prototype.status.ComponentStatus;
import dk.dma.epd.common.prototype.status.IStatusComponent;
import dk.dma.epd.common.prototype.status.PntStatus;
import dk.dma.epd.common.text.Formatter;
import dk.dma.epd.ship.EPDShip;

public class BottomPanelStatusDialog extends CommonBottomPanelStatusDialog {

    private static final long serialVersionUID = 1L;
    private JLabel lblPositionStatus;
    private JLabel lblLastPNTDataStatus;
    private JLabel lblSourceStatus;

    /**
     * @param statusComponents 
     * 
     */
    public BottomPanelStatusDialog() {
        super();
        
        // Creates the panels
        super.createStatusPanels(createPanels());
        
        // Start timer.
//        super.timer.start();
        
        // Set location and visible to true.
        this.setLocationRelativeTo(EPDShip.getInstance().getMainFrame());
    }

    public List<JPanel> createPanels() {
        // PNT status
        JPanel pntPanel = new JPanel();
        pntPanel.setBorder(
                new TitledBorder(null, "PNT", TitledBorder.LEADING, TitledBorder.TOP, TITLE_FONT));
        pntPanel.setSize(292, 120);
        pntPanel.setLayout(null);
        
        JLabel lblPosition = new JLabel("Position:");
        lblPosition.setFont(PLAIN_FONT);
        lblPosition.setBounds(16, 30, 55, 16);
        pntPanel.add(lblPosition, 0);
        
        this.lblPositionStatus = new JLabel("status");
        this.lblPositionStatus.setFont(PLAIN_FONT);
        this.lblPositionStatus.setBounds(121, 30, 165, 16);
        pntPanel.add(this.lblPositionStatus);
        
        JLabel lblSource = new JLabel("Source:");
        lblSource.setFont(PLAIN_FONT);
        lblSource.setBounds(16, 55, 61, 16);
        pntPanel.add(lblSource);
        
        this.lblSourceStatus = new JLabel("status");
        this.lblSourceStatus.setFont(PLAIN_FONT);
        this.lblSourceStatus.setBounds(121, 55, 165, 16);
        pntPanel.add(this.lblSourceStatus);
        
        JLabel lblLastPntData = new JLabel("Last PNT data:");
        lblLastPntData.setFont(PLAIN_FONT);
        lblLastPntData.setBounds(16, 80, 90, 16);
        pntPanel.add(lblLastPntData);
        
        this.lblLastPNTDataStatus = new JLabel("status");
        this.lblLastPNTDataStatus.setFont(PLAIN_FONT);
        this.lblLastPNTDataStatus.setBounds(121, 80, 165, 16);
        pntPanel.add(this.lblLastPNTDataStatus);
        
        List<JPanel> statusPanels = new ArrayList<JPanel>();
        statusPanels.add(pntPanel);
        return statusPanels;
    }
    
    @Override
    public void showStatus() {
        super.showStatus();
        
        for (IStatusComponent iStatusComponent : super.statusComponents) {
            ComponentStatus componentStatus = iStatusComponent.getStatus();
            
            if (componentStatus instanceof PntStatus) {
                
                PntStatus pntStatus = (PntStatus) componentStatus;
                this.lblPositionStatus.setText(pntStatus.getStatus().toString());
                
                try {
                    this.lblSourceStatus.setText(pntStatus.getPntData().getPntSource().toString());                    
                } catch (NullPointerException e) {
                    this.lblSourceStatus.setText("N/A");
                }
                
                this.lblLastPNTDataStatus.setText(
                        Formatter.formatLongDateTime(pntStatus.getPntData().getLastUpdated()));
                
                super.colorStatusLabel(lblPositionStatus);
            }
        }
    }
}
