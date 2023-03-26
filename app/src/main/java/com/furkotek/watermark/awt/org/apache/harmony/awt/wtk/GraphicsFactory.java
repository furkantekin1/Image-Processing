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
 * @author Pavel Dolgov, Alexey A. Petrenko, Oleg V. Khaschansky
 */
package com.furkotek.watermark.awt.org.apache.harmony.awt.wtk;

import com.furkotek.watermark.awt.net.windward.android.awt.Font;
import com.furkotek.watermark.awt.net.windward.android.awt.peer.FontPeer;
import com.furkotek.watermark.awt.org.apache.harmony.awt.gl.font.FontManager;

import java.io.IOException;



/**
 * GraphicsFactory interface defines methods for Graphics2D 
 * and font stuff instances factories.
 */
public interface GraphicsFactory {
    
    
    // Font methods
    FontManager getFontManager();
    FontPeer getFontPeer(Font font);
    Font embedFont(String fontFilePath) throws IOException;
}
