/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.furkotek.watermark.awt.org.apache.commons.imaging.palette;

class MedianCutPalette extends SimplePalette {
    private final ColorGroup root;

    public MedianCutPalette(final ColorGroup root, final int[] palette) {
        super(palette);
        this.root = root;
    }

    @Override
    public int getPaletteIndex(final int rgb) {
        ColorGroup cg = root;

        while (cg.cut != null) {
            cg = cg.cut.getColorGroup(rgb);
        }

        return cg.paletteIndex;
    }
}
