package coms309;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class WelcomeController {
    // data store
    private final List<Todo> todos = new CopyOnWriteArrayList<>();
    private long seq = 0;

    // creates some data for to-do list
    public WelcomeController() {
        todos.add(new Todo(++seq, "Buy textbook", "COM S 311", LocalDate.now().plusDays(1), Status.OPEN));
        todos.add(new Todo(++seq, "Get package", "Pickup at MWL", LocalDate.now().plusDays(3), Status.OPEN));
        todos.add(new Todo(++seq, "Meet buyer for chair", "Caribou at MU", LocalDate.now().minusDays(1), Status.DONE));
    }

    //Make sure the server is up and running
    @GetMapping("/")
    public String root() {
        return "To-Do List is running.";
    }

    // organize by date
    @GetMapping("/todos/by-date")
    public Map<LocalDate, List<Todo>> byDate(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);
        Map<LocalDate, List<Todo>> map = new TreeMap<>();
        for (Todo t : todos) {
            if (fromDate != null && t.dueDate.isBefore(fromDate)) continue;
            if (toDate != null && t.dueDate.isAfter(toDate)) continue;
            map.computeIfAbsent(t.dueDate, d -> new ArrayList<>()).add(t);
        }
        return map;
    }
    // ability to create to-dos
    @PostMapping("/todos")
    public ResponseEntity<Todo> create(@RequestBody @Valid CreateTodo req) {
        Todo t = new Todo(
                ++seq, req.title, req.description, req.dueDate,
                req.status == null ? Status.OPEN : req.status);
        todos.add(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }
    // list. ability to sort in order, on a date, from a date
    @GetMapping("/todos")
    public List<Todo> list(
            @RequestParam(required = false) String on,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        LocalDate onDate = parseDate(on);
        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);
        //filter into new list
        List<Todo> out = new ArrayList<>();
        for (Todo t : todos) {
            if (onDate != null && !onDate.equals(t.dueDate)) continue;
            if (fromDate != null && t.dueDate.isBefore(fromDate)) continue;
            if (toDate != null && t.dueDate.isAfter(toDate)) continue;
            out.add(t);
        }

        out.sort(Comparator.comparing(a -> a.dueDate));
        if ("desc".equalsIgnoreCase(sort)) {
            Collections.reverse(out);
        }
        return out;
    }

    // Read a to-do list item
    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getOne(@PathVariable long id) {
        Todo t = find(id);
        return (t == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(t);
    }
    // feature UPDATE
    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> update(@PathVariable long id, @RequestBody @Valid UpdateTodo req) {
        Todo t = find(id);
        if (t == null) return ResponseEntity.notFound().build();
        t.title = req.title;
        t.description = req.description;
        t.dueDate = req.dueDate;
        if (req.status != null) t.status = req.status;
        return ResponseEntity.ok(t);
    }
    // Delete feature
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Iterator<Todo> it = todos.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
    // Helper method to find
    private Todo find(long id) {
        for (Todo t : todos) if (t.id == id) return t;
        return null;
    }
    // helper method to look through the dates
    private LocalDate parseDate(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return LocalDate.parse(raw);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public enum Status {OPEN, IN_PROGRESS, DONE}
    //JSON format
    public static class CreateTodo {
        @NotBlank
        @Size(max = 120)
        public String title;
        @Size(max = 1000)
        public String description;
        @NotNull
        public LocalDate dueDate;
        public Status status;
    }
    //JSON format
    public static class UpdateTodo {
        @NotBlank
        @Size(max = 120)
        public String title;
        @Size(max = 1000)
        public String description;
        @NotNull
        public LocalDate dueDate;
        public Status status;
    }
    // store and return model
    public static class Todo {
        public long id;
        public String title;
        public String description;
        public LocalDate dueDate;
        public Status status;

        public Todo() {
        }

        public Todo(long id, String title, String description, LocalDate dueDate, Status status) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.status = status;
        }
    }
}
