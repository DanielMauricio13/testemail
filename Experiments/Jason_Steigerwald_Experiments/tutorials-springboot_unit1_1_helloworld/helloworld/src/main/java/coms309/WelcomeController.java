package coms309;

import jakarta.validation.Valid;

import java.util.stream.Collectors;
import java.util.TreeMap;

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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@RestController
public class WelcomeController {
    private final List<Todo> todos = new CopyOnWriteArrayList<>();
    private final AtomicLong seq = new AtomicLong(0);

    public WelcomeController() {
        todos.add(new Todo(seq.incrementAndGet(), "Buy textbook", "COM S 311",
                LocalDate.now().plusDays(1), Status.OPEN));
        todos.add(new Todo(seq.incrementAndGet(), "Get package", "Pickup at MWL",
                LocalDate.now().plusDays(3), Status.OPEN));
        todos.add(new Todo(seq.incrementAndGet(), "Meet buyer for chair", "Caribou at MU",
                LocalDate.now().minusDays(1), Status.DONE));
    }

    @GetMapping("/")
    public String root() {
        return "To-Do List is running.";
    }

    @GetMapping("/todos/by-date")
    public Map<LocalDate, List<Todo>> byDate(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);
        return todos.stream()
                .filter(t -> fromDate == null || !t.dueDate.isBefore(fromDate))
                .filter(t -> toDate == null || !t.dueDate.isAfter(toDate))
                .collect(Collectors.groupingBy(t -> t.dueDate, TreeMap::new, Collectors.toList()));
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> create(@RequestBody @Valid CreateTodo req) {
        Todo t = new Todo(
                seq.incrementAndGet(),
                req.title(),
                req.description(),
                req.dueDate(),
                req.status() == null ? Status.OPEN : req.status()
        );
        todos.add(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    @GetMapping("/todos")
    public List<Todo> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String on,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String before,
            @RequestParam(required = false) String after,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LocalDate onDate = parseDate(on);
        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);
        LocalDate beforeDate = parseDate(before);
        LocalDate afterDate = parseDate(after);

        Stream<Todo> stream = todos.stream();
        if (q != null && !q.isBlank()) {
            String needle = q.toLowerCase();
            stream = stream.filter(t ->
                    (t.title != null && t.title.toLowerCase().contains(needle)) ||
                            (t.description != null && t.description.toLowerCase().contains(needle))
            );
        }

        Status st = parseStatus(status);
        if (st != null) {
            stream = stream.filter(t -> t.status == st);
        }
        if (onDate != null) {
            stream = stream.filter(t -> onDate.equals(t.dueDate));
        }
        if (fromDate != null) {
            stream = stream.filter(t -> !t.dueDate.isBefore(fromDate));
        }
        if (toDate != null) {
            stream = stream.filter(t -> !t.dueDate.isAfter(toDate));
        }
        if (beforeDate != null) {
            stream = stream.filter(t -> t.dueDate.isBefore(beforeDate));
        }
        if (afterDate != null) {
            stream = stream.filter(t -> t.dueDate.isAfter(afterDate));
        }

        Comparator<Todo> byDue = Comparator.comparing(t -> t.dueDate);
        if ("desc".equalsIgnoreCase(sort)) byDue = byDue.reversed();
        List<Todo> sorted = stream.sorted(byDue).toList();
        int total = sorted.size();
        int fromIdx = Math.min(page * size, total);
        int toIdx = Math.min(fromIdx + size, total);
        return sorted.subList(fromIdx, toIdx);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getOne(@PathVariable long id) {
        return find(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> update(@PathVariable long id, @RequestBody @Valid UpdateTodo req) {
        return find(id).map(existing -> {
            existing.title = req.title();
            existing.description = req.description();
            existing.dueDate = req.dueDate();
            existing.status = req.status() == null ? existing.status : req.status();
            return ResponseEntity.ok(existing);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean removed = todos.removeIf(t -> t.id == id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private Optional<Todo> find(long id) {
        return todos.stream().filter(t -> t.id == id).findFirst();
    }

    private LocalDate parseDate(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return LocalDate.parse(raw);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private Status parseStatus(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return Status.valueOf(raw.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return null;
        }
    }


    public enum Status {OPEN, IN_PROGRESS, DONE}

    public static record CreateTodo(
            @NotBlank @Size(max = 120) String title,
            @Size(max = 1000) String description,
            @NotNull LocalDate dueDate,
            Status status
    ) {
    }

    public static record UpdateTodo(
            @NotBlank @Size(max = 120) String title,
            @Size(max = 1000) String description,
            @NotNull LocalDate dueDate,
            Status status
    ) {
    }

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
