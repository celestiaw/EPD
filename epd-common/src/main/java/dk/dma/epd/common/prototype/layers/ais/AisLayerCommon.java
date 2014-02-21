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
package dk.dma.epd.common.prototype.layers.ais;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbn.openmap.omGraphics.OMGraphic;

import dk.dma.epd.common.graphics.ISelectableGraphic;
import dk.dma.epd.common.prototype.EPD;
import dk.dma.epd.common.prototype.ais.AisHandlerCommon;
import dk.dma.epd.common.prototype.ais.AisTarget;
import dk.dma.epd.common.prototype.ais.AtoNTarget;
import dk.dma.epd.common.prototype.ais.IAisTargetListener;
import dk.dma.epd.common.prototype.ais.SarTarget;
import dk.dma.epd.common.prototype.ais.VesselTarget;
import dk.dma.epd.common.prototype.layers.LazyLayerCommon;
import dk.dma.epd.common.prototype.settings.AisSettings;
import dk.dma.epd.common.prototype.settings.NavSettings;

/**
 * @author Janus Varmarken
 */
@SuppressWarnings("serial")
public abstract class AisLayerCommon<AISHANDLER extends AisHandlerCommon>
        extends LazyLayerCommon implements IAisTargetListener, PropertyChangeListener {

    private static final Logger LOG = LoggerFactory
            .getLogger(AisLayerCommon.class);

    /**
     * The AIS handler that provides AIS data for this layer.
     */
    protected volatile AISHANDLER aisHandler;
    
    /**
     * Maps an MMSI to the object handling its graphical representation.
     */
    private Map<Long, TargetGraphic> targets = new ConcurrentHashMap<>();

    /**
     * The graphic that is currently selected by the user.
     */
    private ISelectableGraphic selectedGraphic;

    /**
     * The application wide AIS settings.
     */
    private final AisSettings aisSettings;

    /**
     * The application wide Navigation settings.
     */
    private final NavSettings navSettings;

    public AisLayerCommon(int repaintIntervalMillis) {
        super(repaintIntervalMillis);
        // Get the settings singletons
        this.aisSettings = EPD.getInstance().getSettings().getAisSettings();
        this.navSettings = EPD.getInstance().getSettings().getNavSettings();
        // register self as listener for changes to the AIS settings
        this.aisSettings.addPropertyChangeListener(this);
        // receive left-click events for the following set of classes.
        this.registerMouseClickClasses(VesselTargetGraphic.class);
        // receive right-click events for the following set of classes.
        this.registerMapMenuClasses(VesselTargetGraphic.class, SartGraphic.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void findAndInit(Object obj) {
        super.findAndInit(obj);
        if (obj instanceof AisHandlerCommon) {
            this.aisHandler = (AISHANDLER) obj;
            this.aisHandler.addListener(this);
        }
    }

    @Override
    public void findAndUndo(Object obj) {
        if (obj == this.aisHandler) {
            // TODO this cleanup causes problems when we have more than one
            // MapWindow:
            // When a new window is added, findAndUndo is called for the already
            // present window.
            // this.aisHandler.removeListener(this);
        }
        super.findAndUndo(obj);
    }

    /**
     * Updates which AIS target this layer should display as the selected
     * target. If the provided {@code mmsi} does not have a graphical
     * representation in this layer, this method will remove the current
     * selection. Furthermore, if a {@code TargetGraphic} matching the
     * {@code mmsi} is found, this object needs to implement
     * {@code ISelectableGraphic} for the selection to be valid. If this is not
     * the case, this method will remove the current selection.
     * 
     * @param mmsi
     *            The MMSI of an AIS target that is now the selected AIS target.
     * @param repaintImmediately
     *            If this layer should repaint itself immediately.
     */
    public final void setSelectedTarget(long mmsi, boolean repaintImmediately) {
        TargetGraphic tg = this.targets.get(mmsi);
        if (tg instanceof ISelectableGraphic) {
            this.setSelectedGraphic((ISelectableGraphic) tg, repaintImmediately);
        } else {
            this.setSelectedGraphic(null, repaintImmediately);
        }
    }

    /**
     * Mark a graphic as selected. Repaint this layer if requested.
     * 
     * @param newSelection
     *            The graphic that is now the selected graphic.
     * @param repaint
     *            If this layer should repaint itself to reflect the change in
     *            selection.
     */
    protected void setSelectedGraphic(ISelectableGraphic newSelection,
            boolean repaint) {
        if (this.selectedGraphic != null) {
            // remove current selection
            this.selectedGraphic.setSelection(false);
        }
        if (newSelection != null) {
            // mark new selection
            newSelection.setSelection(true);
        }
        // keep reference to new selection
        this.selectedGraphic = newSelection;
        if (repaint) {
            this.doPrepare();
        }
    }

    /**
     * Add a graphical representation of an AIS target to this layers set of
     * graphics.
     * 
     * @param mmsi
     *            The MMSI of the AIS target that {@code tg} represents.
     * @param tg
     *            Object that manages the graphical representation of the given
     *            {@code mmsi}.
     */
    protected void addTargetGraphic(Long mmsi, TargetGraphic tg) {
        if (tg == null || mmsi == null) {
            return;
        }
        // Add the new graphic to the map of ais targets
        TargetGraphic oldVal = this.targets.put(mmsi, tg);
        synchronized (this.graphics) {
            if (oldVal != null) {
                // remove the old graphical representation of this MMSI
                this.graphics.remove(oldVal);
            }
            // add the new graphical representation of this MMSI to the list of
            // graphics to be displayed.
            this.graphics.add(tg);
        }
    }

    /**
     * Remove a graphical representation of an AIS target from this layers set
     * of graphics.
     * 
     * @param mmsi
     *            The MMSI of the AIS target that should have its graphical
     *            representation removed.
     */
    protected void removeTargetGraphic(Long mmsi) {
        TargetGraphic deleted = this.targets.remove(mmsi);
        if (deleted != null) {
            // Successfully removed a graphic object from map
            // This graphic object should also be present in the list of
            // graphics to be displayed, hence we need to remove it from the
            // graphics list.
            synchronized (this.graphics) {
                this.graphics.remove(deleted);
            }
        }
    }

    /**
     * Get the graphical representation of an AIS target with the given MMSI.
     * 
     * @param mmsi
     *            The MMSI to find the graphical representation for.
     * @return The graphical representation of the MMSI or null if there is
     *         currently no graphical representation for this MMSI.
     */
    protected TargetGraphic getTargetGraphic(Long mmsi) {
        return mmsi == null ? null : this.targets.get(mmsi);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void targetUpdated(AisTarget aisTarget) {
        // Sanity check
        if (aisTarget == null) {
            return;
        }
        long mmsi = aisTarget.getMmsi();
        TargetGraphic targetGraphic = this.getTargetGraphic(mmsi);
        float mapScale = (this.getProjection() == null) ? 0 : this
                .getProjection().getScale();

        if (aisTarget.isGone()) {
            if (targetGraphic != null) {
                // Remove target from map of graphics + graphics list
                this.removeTargetGraphic(mmsi);
            }
            return;
        }

        // Create and insert
        if (targetGraphic == null) {
            if (aisTarget instanceof VesselTarget) {
                targetGraphic = new VesselTargetGraphic(this.aisSettings.isShowNameLabels(), this);
                // TODO this causes problems in EPDShip with regards to mouse
                // clicks and mouse over as EPDShip does instanceof checks on
                // sub graphics of VesselTargetGraphic
                targetGraphic.setVague(true);
            } else if (aisTarget instanceof SarTarget) {
                targetGraphic = new SarTargetGraphic();
            } else if (aisTarget instanceof AtoNTarget) {
                targetGraphic = new AtonTargetGraphic();
            } else {
                LOG.error("Unknown target type");
                return;
            }
            // add to map of graphics + graphics list
            this.addTargetGraphic(mmsi, targetGraphic);
        }

        // Send the new location data to the graphic representing the AisTarget
        targetGraphic.update(aisTarget, this.aisSettings, this.navSettings, mapScale);
        targetGraphic.project(getProjection());
    }
    
    /**
     * Invoked when a change occurs in the {@code AisSettings} object that this {@code AisLayerCommon} is registered with.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(AisSettings.SHOW_NAME_LABELS_CHANGED.equals(evt.getPropertyName())) {
            // A change occurred in the "Show AIS name labels" setting
            this.onShowNameLabelsChanged(evt);
        }
    }
    
    /**
     * Invoked by {@link #propertyChange(PropertyChangeEvent)} when this layer receives a notification of a change to the "show AIS name labels" setting.
     * @param evt The event fired by the {@code AisSettings} that this layer observes.
     */
    protected void onShowNameLabelsChanged(PropertyChangeEvent evt) {
        for(TargetGraphic tg : this.targets.values()) {
            if(tg instanceof VesselTargetGraphic) {
                ((VesselTargetGraphic)tg).setShowNameLabel((Boolean)evt.getNewValue());
            }
        }
        // do a repaint
        this.doPrepare();
    }

    /**
     * Updates target selection if the {@code clickedGraphics} is an {@code ISelectableGraphic} or null.
     */
    @Override
    protected void handleMouseClick(OMGraphic clickedGraphics, MouseEvent evt) {
        // Should only handle left clicks.
        assert evt.getButton() == MouseEvent.BUTTON1;
        if(clickedGraphics instanceof ISelectableGraphic) {
            // Update selected graphic and do a repaint
            this.setSelectedGraphic((ISelectableGraphic) clickedGraphics, true);
        }
        else if(clickedGraphics == null) {
            // User clicked somewhere on the map with no nearby graphics
            // We need to remove the current selection and repaint
            this.setSelectedGraphic(null, true);
        }
    }
    
    /**
     * Force this AIS layer to update itself.
     */
    public abstract void forceLayerUpdate();
}