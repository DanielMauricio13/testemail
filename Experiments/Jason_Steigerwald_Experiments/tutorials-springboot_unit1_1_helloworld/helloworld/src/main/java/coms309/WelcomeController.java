package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@RestController
public class WelcomeController {

    // ===== In-memory store =====
    private final List<Todo> todos = new CopyOnWriteArrayList<>();
    private final AtomicLong seq = new AtomicLong(0);

    public WelcomeController() {
        // Seed demo data (today ± a few days)
        todos.add(new Todo(seq.incrementAndGet(), "Buy textbook", "COM S 311",
                LocalDate.now().plusDays(1), Status.OPEN));
        todos.add(new Todo(seq.incrementAndGet(), "Get package", "Pickup at MWL",
                LocalDate.now().plusDays(3), Status.OPEN));
        todos.add(new Todo(seq.incrementAndGet(), "Meet buyer for chair", "Caribou at MU",
                LocalDate.now().minusDays(1), Status.DONE));
    }

    // ===== Simple sanity/hello routes (keep tutorial spirit) =====
    @GetMapping("/")
    public String root() {
        return "To-Do List is running.";
    }

    // ===== CREATE =====
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

    // ===== LIST (READ-MANY) with date filters & sort =====
    @GetMapping("/todos")
    public List<Todo> list(
            @RequestParam(required = false) String on,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String before,
            @RequestParam(required = false) String after,
            @RequestParam(required = false, defaultValue = "asc") String sort // asc|desc by dueDate
    ) {
        LocalDate onDate = parseDate(on);
        LocalDate fromDate = parseDate(from);
        LocalDate toDate = parseDate(to);
        LocalDate beforeDate = parseDate(before);
        LocalDate afterDate = parseDate(after);

        Stream<Todo> stream = todos.stream();

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
        return stream.sorted(byDue).toList();
    }

    // ===== READ-ONE =====
    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getOne(@PathVariable long id) {
        return find(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ===== UPDATE (full replace of mutable fields) =====
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

    // ===== DELETE =====
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean removed = todos.removeIf(t -> t.id == id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ===== Helpers =====
    private Optional<Todo> find(long id) {
        return todos.stream().filter(t -> t.id == id).findFirst();
    }

    private LocalDate parseDate(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try { return LocalDate.parse(raw); }
        catch (DateTimeParseException e) { return null; } // ignore bad filters gracefully
    }

    // ===== DTOs & Model =====
    public enum Status { OPEN, IN_PROGRESS, DONE }

    // Create payload (title required; description optional; dueDate required)
    public static record CreateTodo(
            @NotBlank @Size(max = 120) String title,
            @Size(max = 1000) String description,
            @NotNull LocalDate dueDate,
            Status status
    ) {}

    // Update payload (full update; keep it simple for the lab)
    public static record UpdateTodo(
            @NotBlank @Size(max = 120) String title,
            @Size(max = 1000) String description,
            @NotNull LocalDate dueDate,
            Status status
    ) {}

    // Model
    public static class Todo {
        public long id;
        public String title;
        public String description;
        public LocalDate dueDate;
        public Status status;

        public Todo() {}
        public Todo(long id, String title, String description, LocalDate dueDate, Status status) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.status = status;
        }
    }
}
