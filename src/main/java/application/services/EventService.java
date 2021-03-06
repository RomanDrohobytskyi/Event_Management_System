package application.services;

import application.date.DateInstances;
import application.date.TimeParser;
import application.entities.event.Event;
import application.entities.user.User;
import application.managers.UserManager;
import application.models.DayOfWeek;
import application.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final UserManager userManager = new UserManager();
    private Model model;


    public Event addNewEvent(String title, String from, String to, Date date, User user, Model model) {
        this.model = model;
        Event event = adaptEvent(title, from, to, date, user);
        if (validateEvent(event)) {
            save(event);
        }
        return event;
    }

    public boolean validateEvent(Event event) {
        if (!validateAvailableHours(event)) {
            model.addAttribute("message", "This time is already taken for another event!");
            return false;
        }
        return true;
    }

    public boolean validateAvailableHours(Event event) {
        Event first = eventRepository.getFirstByFromBeforeAndToBeforeAndDayOfWeekAndDate(
                event.getFrom(), event.getTo(), event.getDayOfWeek(), event.getDate());
        return first == null;
    }

    public Event adaptEvent(String title, String from, String to, Date date, User user) {
        LocalTime fromParsed = TimeParser.parseToLocalTime(from).orElseThrow(IllegalArgumentException::new);
        LocalTime toParsed = TimeParser.parseToLocalTime(to).orElseThrow(IllegalArgumentException::new);
        DayOfWeek dayOfWeek = getDayOfWeekByDayNumber(date.getDay());
        return buildEvent(title, fromParsed, toParsed, date, dayOfWeek, user);
    }

    public DayOfWeek getDayOfWeekByDayNumber(int dayNumber) {
        return DayOfWeek.values()[dayNumber];
    }

    public Event buildEvent(String title, LocalTime from, LocalTime to, Date date, DayOfWeek dayOfWeek, User user) {
        return Event.builder()
                .title(title)
                .from(from)
                .to(to)
                .date(date)
                .dayOfWeek(dayOfWeek)
                .creator(user)
                .creationDate(new Date())
                .build();
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Map<String, List<Event>> getSortedEventsForFullWeekFromTodayWithDay() {
        List<String> weekDaysSortedFromToday = getWeekDaysStartsFromToday();
        Map<String, List<Event>> events = fillEmptyDaysOfWeek(weekDaysSortedFromToday);

        return weekDaysSortedFromToday.stream()
                .collect(toMap(day -> day, events::get));
    }

    public List<String> getWeekDaysStartsFromToday() {
        int dayOfWeek = new Date().getDay();

        DayOfWeek[] weekDaysFromToday = Arrays.copyOfRange(DayOfWeek.values(), dayOfWeek, 7);
        DayOfWeek[] weekDaysBeforeToday = Arrays.copyOfRange(DayOfWeek.values(), 0, dayOfWeek);

        DayOfWeek[] weekDays = Stream.of(weekDaysFromToday, weekDaysBeforeToday)
                .flatMap(Stream::of)
                .toArray(DayOfWeek[]::new);

        return Stream.of(weekDays)
                .map(DayOfWeek::getDay)
                .collect(toList());
    }

    private Map<String, List<Event>> fillEmptyDaysOfWeek(List<String> weekDaysSortedFromToday) {
        Map<String, List<Event>> events = getEventsForWeekFromTodayWithDay();

        for (String day : weekDaysSortedFromToday) {
            if (!events.containsKey(day)) {
                events.put(day, Collections.emptyList());
            }
        }

        return events;
    }

    public Map<String, List<Event>> getEventsForWeekFromTodayWithDay() {
        return getEventsForWeekFromToday().stream()
                .collect(groupingBy(a -> a.getDayOfWeek().getDay()));
    }

    public List<Event> getEventsForWeekFromToday() {
        Date today = DateInstances.startOfDay(new Date());
        Date todayPlusSixDays = DateInstances.endOfDay(addDays(today, 6));
        return getEventsByDateBetweenOrderByDate(today, todayPlusSixDays)
                .orElse(Collections.emptyList());
    }

    private Date addDays(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }

    public Optional<List<Event>> getEventsByDateBetweenOrderByDate(Date from, Date to) {
        return eventRepository.getEventsByDateBetweenOrderByDate(from, to);
    }

    public void addEventsAndMenu(Model model) {
        Map<String, List<Event>> events = getSortedEventsForFullWeekFromTodayWithDay();
        List<Event> availableEvents = filterEventsAvailableToRegistration(getEventsForWeekFromToday(), userManager.getLoggedInUser());
        model.addAttribute("events", events);
        model.addAttribute("availableEvents", availableEvents);
        model.addAttribute("user", userManager.getLoggedInUser());
        model.addAttribute("isUserAdmin", userManager.isLoggedUserAdmin());
        model.addAttribute("isLoggedUserIsAdmin", userManager.isLoggedUserAdmin());
    }

    private List<Event> filterEventsAvailableToRegistration(List<Event> events, User user){
        events.removeIf(event -> user.getEvents().contains(event));
        return events;
    }

    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    public void registerUserForEvent(Event event) {
        User user = userManager.getLoggedInUser();
        user.getEvents().add(event);
        userService.saveUser(user);
    }
}
