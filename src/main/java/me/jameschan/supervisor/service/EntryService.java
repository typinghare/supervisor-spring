package me.jameschan.supervisor.service;

import me.jameschan.supervisor.model.Entry;
import me.jameschan.supervisor.repository.EntryRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class EntryService {
    final EntryRepository entryRepository;

    public EntryService(@NotNull final EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public @NotNull List<Entry> getEntriesByUserId(final long userId) {
        return entryRepository.getEntriesByUserId(userId);
    }
}
