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
package dk.dma.epd.common.prototype.voct;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * Different events for VOCT events
 */
public enum VOCTUpdateEvent {
    NEW_SAR, SAR_CANCEL, SAR_READY, SAR_DISPLAY, EFFORT_ALLOCATION_READY, EFFORT_ALLOCATION_DISPLAY, SEARCH_PATTERN_GENERATED, SAR_RECEIVED_CLOUD
    , EFFORT_ALLOCATION_SERIALIZED;
    
    public boolean is(VOCTUpdateEvent... events) {
        return EnumSet.copyOf(Arrays.asList(events)).contains(this);
    }
};
