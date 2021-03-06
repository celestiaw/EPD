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
package dk.dma.epd.shore.gui.utils;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import dk.dma.epd.common.graphics.Resources;
import dk.dma.epd.shore.EPDShore;

public class StaticImages {

    Cursor dragCursorMouseClicked;
    Cursor dragCursor;
    Cursor navCursorMouseClicked;
    Cursor navCursor;
    ImageIcon highlightIcon;
    ImageIcon vesselWhite;
    ImageIcon vesselBlue;
    ImageIcon vesselLightgreen;
    ImageIcon vesselCyan;
    ImageIcon vesselRed;
    ImageIcon vesselWhite0;
    ImageIcon vesselBrown;
    ImageIcon vesselMagenta;
    ImageIcon vesselLightgray;
    public StaticImages(){


//      //Get the default toolkit
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      Resources toolbarRes = EPDShore.res().folder("images/toolbar");

      //Load an image for the cursor

      Image image = toolkit.getImage(toolbarRes.getResource("drag_mouse.png"));
      dragCursor = toolkit.createCustomCursor(image, new Point(0,0), "Drag");
      Image image2 = toolkit.getImage(toolbarRes.getResource("drag_on_mouse.png"));
      dragCursorMouseClicked = toolkit.createCustomCursor(image2, new Point(0,0), "Drag_on_mouse");

      Image image3 = toolkit.getImage(toolbarRes.getResource("zoom_mouse.png"));
      navCursor = toolkit.createCustomCursor(image3, new Point(0,0), "Zoom");


      Image image4 = toolkit.getImage(toolbarRes.getResource("zoom_on_mouse.png"));
      navCursorMouseClicked = toolkit.createCustomCursor(image4, new Point(0,0), "Zoom_on_mouse");

      highlightIcon = EPDShore.res().getCachedImageIcon("images/ais/highlight.png");

      Resources vesselRes = EPDShore.res().folder("images/vesselIcons");
      vesselWhite = vesselRes.getCachedImageIcon("white1_90.png");
      vesselBlue = vesselRes.getCachedImageIcon("blue1_90.png");
      vesselLightgreen = vesselRes.getCachedImageIcon("lightgreen1_90.png");
      vesselCyan = vesselRes.getCachedImageIcon("cyan1_90.png");
      vesselRed = vesselRes.getCachedImageIcon("red1_90.png");
      vesselWhite0 = vesselRes.getCachedImageIcon("white0.png");
      vesselBrown = vesselRes.getCachedImageIcon("brown1_90.png");
      vesselMagenta = vesselRes.getCachedImageIcon("magenta1_90.png");
      vesselLightgray = vesselRes.getCachedImageIcon("lightgray1_90.png");

    }

    public Cursor getDragCursorMouseClicked() {
        return dragCursorMouseClicked;
    }

    public Cursor getDragCursor() {
        return dragCursor;
    }

    public Cursor getNavCursorMouseClicked() {
        return navCursorMouseClicked;
    }

    public Cursor getNavCursor() {
        return navCursor;
    }

    public ImageIcon getHighlightIcon(){
        return highlightIcon;
    }

    public ImageIcon getVesselWhite() {
        return vesselWhite;
    }

    public ImageIcon getVesselBlue() {
        return vesselBlue;
    }

    public ImageIcon getVesselLightgreen() {
        return vesselLightgreen;
    }

    public ImageIcon getVesselCyan() {
        return vesselCyan;
    }

    public ImageIcon getVesselRed() {
        return vesselRed;
    }

    public ImageIcon getVesselWhite0() {
        return vesselWhite0;
    }

    public ImageIcon getVesselBrown() {
        return vesselBrown;
    }

    public ImageIcon getVesselMagenta() {
        return vesselMagenta;
    }

    public ImageIcon getVesselLightgray() {
        return vesselLightgray;
    }


}
