package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.HistoryResponse;

import java.util.UUID;

public abstract class HistoryResponseListener extends AbstractQueueListener<HistoryResponse> {
    private UUID requestId;

    public HistoryResponseListener(UUID requestId){
        super(HistoryResponse.class);
        this.requestId = requestId;
    }

    @Override
    public String getRoutingKeyPattern() {
        return String.format("datapoints.history.%s", requestId);
    }
}
