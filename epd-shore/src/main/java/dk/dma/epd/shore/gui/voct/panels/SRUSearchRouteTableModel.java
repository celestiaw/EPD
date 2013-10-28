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
package dk.dma.epd.shore.gui.voct.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.dma.epd.common.prototype.model.voct.sardata.EffortAllocationData;
import dk.dma.epd.common.text.Formatter;
import dk.dma.epd.shore.voct.SRU;
import dk.dma.epd.shore.voct.SRUManager;
import dk.dma.epd.shore.voct.VOCTManager;

/**
 * Table model for SRUManagerDialog
 */
public class SRUSearchRouteTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory
            .getLogger(SRUSearchRouteTableModel.class);

    private static final String[] COLUMN_NAMES = { "Name", "Src Ptn",
            "Dynamic", "Visible" };

    private SRUManager sruManager;
    private VOCTManager voctManager;

    private Map<SRU, JButton> buttons = new HashMap<SRU, JButton>();

    private SRUSearchPAtternButtonHandler handler;

    public SRUSearchRouteTableModel(SRUSearchPAtternButtonHandler handler,
            SRUManager sruManager, VOCTManager voctManager) {
        super();
        this.handler = handler;
        this.sruManager = sruManager;
        this.voctManager = voctManager;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public int getRowCount() {
        // return sruManager.getSRUCount();
        // return 0;
        return voctManager.getSarData().getEffortAllocationData().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SRU sru = sruManager.getSRUs().get(rowIndex);
        EffortAllocationData effortAllocationData = voctManager.getSarData()
                .getEffortAllocationData().get(rowIndex);
        switch (columnIndex) {
        case 0:
            return Formatter.formatString(sru.getName());
        case 1:
            return getCellButton(sru);
        case 2:
            return sru.isVisible();
        case 3:
            return true;
        default:
            LOG.error("Unknown column " + columnIndex);
            return new String("");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        System.out.println("Set value at, aValue: " + aValue + " rowIndex: "
                + rowIndex + " columIndex: " + columnIndex);
        SRU sru = sruManager.getSRUs().get(rowIndex);
        switch (columnIndex) {
        case 2:
            // toggle dynamic
            // sru.setVisible((Boolean)aValue);
            // sruManager.toggleSRUVisiblity(rowIndex, (Boolean)aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
            break;
        case 3:
            // toggle visibility of route

            // sru.setVisible((Boolean)aValue);
            // sruManager.toggleSRUVisiblity(rowIndex, (Boolean)aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
            break;

        default:
            break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // return (columnIndex == 2 && rowIndex !=
        // routeManager.getActiveRouteIndex());
        return columnIndex == 1 || columnIndex == 2 || columnIndex == 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) {
            return JButton.class;
        } else {
            return getValueAt(0, columnIndex).getClass();
        }

    }

    private JButton getCellButton(SRU sru) {
        JButton button = buttons.get(sru);
        if (button == null) {
            button = createButton(sru);
            buttons.put(sru, button);
        }
        return button;
    }

    private JButton createButton(final SRU sru) {
        final JButton button = new JButton("Create");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Clicked Hello?");
                handler.buttonClicked(sru);
            }
        });

        return button;
    }

    public interface SRUSearchPAtternButtonHandler {
        void buttonClicked(SRU e);
    }
}