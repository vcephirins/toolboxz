package org.free.toolboxz.ihm;

/*
 * GBC - A convenience class to tame the GridBagLayout Copyright (C) 2002 Cay S. Horstmann (http://horstmann.com) This
 * program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * This class simplifies the use of the GridBagConstraints class.
 */
public class GBC extends GridBagConstraints {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Default values
    private int gridwidthDefault = gridwidth;
    private int gridheightDefault = gridheight;
    private int fillDefault = fill;
    private int anchorDefault = anchor;
    private double weightxDefault = weightx;
    private double weightyDefault = weighty;
    private Insets insetsDefault = insets;
    private int ipadxDefault = ipadx;
    private int ipadyDefault = ipady;

    /**
     * Constructs a GBC with a given gridx and gridy position and all other grid bag constraint values set to the
     * default.
     * 
     * @param gridx the gridx position
     * @param gridy the gridy position
     */
    public GBC(int gridx, int gridy) {
        super();
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * Constructs a GBC with given gridx, gridy, gridwidth, gridheight and all other grid bag constraint values set to
     * the default.
     * 
     * @param gridx the gridx position
     * @param gridy the gridy position
     * @param gridwidth the cell span in x-direction
     * @param gridheight the cell span in y-direction
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        super();
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    /**
     * Constructs a GBC with given all grid bag constraint values the default.
     * 
     * @param gridx the gridx position
     * @param gridy the gridy position
     * @param gridwidth the cell span in x-direction
     * @param gridheight the cell span in y-direction
     * @param fill the fill direction
     * @param anchor the anchor value
     * @param weightx the cell weight in x-direction
     * @param weighty the cell weight in y-direction
     * @param insets distance the spacing to use in all directions
     * @param ipadx the internal padding in x-direction
     * @param ipady the internal padding in y-direction
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight, int fill, int anchor, double weightx,
               double weighty, Insets insets, int ipadx, int ipady) {
        super();
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = this.gridwidthDefault = gridwidth;
        this.gridheight = this.gridheightDefault = gridheight;
        this.fill = this.fillDefault = fill;
        this.anchor = this.anchorDefault = anchor;
        this.weightx = this.weightxDefault = weightx;
        this.weighty = this.weightyDefault = weighty;
        this.ipadx = this.ipadxDefault = ipadx;
        this.ipady = this.ipadyDefault = ipady;

        if (insets == null) insets = new Insets(0, 0, 0, 0);
        this.insets = this.insetsDefault = insets;
    }

    /**
     * Resets constraints with default values.
     * 
     * @param gridx the gridx position
     * @param gridy the gridy position
     */
    public GBC update(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidthDefault;
        this.gridheight = gridheightDefault;
        this.fill = this.fillDefault;
        this.anchor = this.anchorDefault;
        this.weightx = this.weightxDefault;
        this.weighty = this.weightyDefault;
        this.insets = this.insetsDefault;
        this.ipadx = this.ipadxDefault;
        this.ipady = this.ipadyDefault;
        return this;
    }

    /**
     * Resets constraints with default values.
     * 
     * @param gridx the gridx position
     * @param gridy the gridy position
     * @param gridwidth the cell span in x-direction
     * @param gridheight the cell span in y-direction
     */
    public GBC update(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
        this.fill = this.fillDefault;
        this.anchor = this.anchorDefault;
        this.weightx = this.weightxDefault;
        this.weighty = this.weightyDefault;
        this.insets = this.insetsDefault;
        this.ipadx = this.ipadxDefault;
        this.ipady = this.ipadyDefault;
        return this;
    }

    /**
     * Sets the anchor.
     * 
     * @param anchor the anchor value
     * @return this object for further modification
     */
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * Sets the fill direction.
     * 
     * @param fill the fill direction
     * @return this object for further modification
     */
    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    /**
     * Sets the cell weights.
     * 
     * @param weightx the cell weight in x-direction
     * @param weighty the cell weight in y-direction
     * @return this object for further modification
     */
    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    /**
     * Sets the insets of this cell.
     * 
     * @param distance the spacing to use in all directions
     * @return this object for further modification
     */
    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    /**
     * Sets the insets of this cell.
     * 
     * @param top the spacing to use on top
     * @param left the spacing to use to the left
     * @param bottom the spacing to use on the bottom
     * @param right the spacing to use to the right
     * @return this object for further modification
     */
    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    /**
     * Sets the internal padding
     * 
     * @param ipadx the internal padding in x-direction
     * @param ipady the internal padding in y-direction
     * @return this object for further modification
     */
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}
