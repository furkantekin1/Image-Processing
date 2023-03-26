/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/**
 * @author Alexey A. Petrenko
 */
package com.furkotek.watermark.awt.net.windward.android.awt.image;

import com.furkotek.watermark.awt.net.windward.android.awt.RenderingHints;
import com.furkotek.watermark.awt.net.windward.android.awt.geom.Point2D;
import com.furkotek.watermark.awt.net.windward.android.awt.geom.Rectangle2D;

/**
 * RasterOp
 *
 */
public interface RasterOp {
    public WritableRaster createCompatibleDestRaster(Raster src);

    public WritableRaster filter(Raster src, WritableRaster dst);

    public Rectangle2D getBounds2D(Raster src);

    public Point2D getPoint2D(Point2D srcPpoint, Point2D dstPoint);

    public RenderingHints getRenderingHints();
}
