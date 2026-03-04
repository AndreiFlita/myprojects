package DataModel;

import java.io.Serial;

public non-sealed class SimpleTask extends Task {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int startHour;
    private final int endHour;

    public SimpleTask(String status, String name, int startHour, int endHour) {
        super(name, status);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public int estimateDuration() {
        if (endHour >= startHour) {
            return endHour - startHour;
        } else {
            return (24 - startHour) + endHour;
        }
    }
}
