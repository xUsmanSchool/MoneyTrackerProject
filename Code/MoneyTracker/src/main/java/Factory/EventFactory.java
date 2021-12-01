package Factory;

import Events.*;
import HelperClass.Events;


public class EventFactory {
    public Event getEvent(Events event) {
        switch (event){
            case AIRPLANE: return new AirplaneEvent();
            case CINEMA: return new CinemaEvent();
            case RESTAURANT: return new RestaurantEvent();
            case TAXI: return new TaxiEvent();
            case THEATRE: return new TheatreEvent();
            default: return null;
        }
    }
}