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


package dk.dma.epd.ship.route.monalisa.se.sspa.optiroute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import dk.dma.epd.ship.route.monalisa.fi.navielektro.ns.formats.vessel_waypoint_exchange.PositionType;


/**
 * <p>Java class for DepthPointType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepthPointType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="position" type="{http://www.navielektro.fi/ns/formats/vessel-waypoint-exchange}PositionType"/>
 *         &lt;element name="depth" type="{http://www.sspa.se/optiroute}DepthType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepthPointType", propOrder = {
    "position",
    "depth"
})
public class DepthPointType {

    @XmlElement(required = true)
    protected PositionType position;
    protected float depth;

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link PositionType }
     *     
     */
    public PositionType getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionType }
     *     
     */
    public void setPosition(PositionType value) {
        this.position = value;
    }

    /**
     * Gets the value of the depth property.
     * 
     */
    public float getDepth() {
        return depth;
    }

    /**
     * Sets the value of the depth property.
     * 
     */
    public void setDepth(float value) {
        this.depth = value;
    }

}
