package net.ctdata.common.Queue;

public class DefaultMessage implements Message {
    private String _key;
    private String _body;

    @Override
    public String getRoutingKey() {
        return _key;
    }

    @Override
    public String getBody() {
        return _body;
    }

    public DefaultMessage(String routingKey, String body){
        this._key = routingKey;
        this._body = body;
    }
}
