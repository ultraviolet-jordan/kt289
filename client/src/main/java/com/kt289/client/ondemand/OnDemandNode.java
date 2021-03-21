package com.kt289.client.ondemand;

import com.kt289.util.aggregation.CacheableNode;

public class OnDemandNode extends CacheableNode {

    public int dataType;
    public int index;
    public byte[] buffer;
    public int loopCycle;
    public boolean incomplete;

    public OnDemandNode() {
        incomplete = true;
    }
}
