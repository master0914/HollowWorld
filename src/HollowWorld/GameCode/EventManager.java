package HollowWorld.GameCode;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static List<Object> events = new ArrayList<>();

    public static void addEvent(Object event) {
        events.add(event);
    }

    public static <T> List<T> getEvents(Class<T> type) {
        List<T> result = new ArrayList<>();
        for(Object event : events) {
            if(type.isInstance(event)) {
                result.add(type.cast(event));
            }
        }
        return result;
    }

    public static void clear() {
        events.clear();
    }
}
