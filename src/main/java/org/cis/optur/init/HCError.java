package org.cis.optur.init;

public class HCError {

    private String tag;
    private String message;
    private State state;

    public HCError(String tag, String message, State state) {
        this.tag = tag;
        this.message = message;
        this.state = state;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Error{");
        sb.append("tag='").append(tag).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
