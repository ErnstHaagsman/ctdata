package net.ctdata.common.Messages;

import java.util.UUID;

public class RaspberryMessage extends AbstractMessage {

    private UUID raspberryNode;

    /**
     * The unique ID for the raspberry node which reported this observation
     *
     * @return {UUID}
     */
    public UUID getRaspberryNode() {
        return raspberryNode;
    }

    /**
     * @see {@link RaspberryMessage#getRaspberryNode()}
     */
    public void setRaspberryNode(UUID raspberryNode) {
        this.raspberryNode = raspberryNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RaspberryMessage that = (RaspberryMessage) o;

        return !(raspberryNode != null ? !raspberryNode.equals(that.raspberryNode) : that.raspberryNode != null);

    }

    @Override
    public int hashCode() {
        return raspberryNode != null ? raspberryNode.hashCode() : 0;
    }
}
