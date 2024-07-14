package me.jameschan.supervisor.repository;

import me.jameschan.supervisor.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> getEntriesByUserId(Long userId);
}
