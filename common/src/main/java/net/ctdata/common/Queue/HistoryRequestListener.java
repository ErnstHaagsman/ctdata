package net.ctdata.common.Queue;

import net.ctdata.common.Messages.HistoryRequest;

public abstract class HistoryRequestListener extends AbstractQueueListener<HistoryRequest> {
    public HistoryRequestListener(){
        super(HistoryRequest.class);
    }

    @Override
    public String getRoutingKeyPattern() {
        return "datapoints.request.*.*";
    }
}
