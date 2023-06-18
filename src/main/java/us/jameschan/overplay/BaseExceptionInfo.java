package us.jameschan.overplay;


import org.springframework.http.HttpStatus;
import us.jameschan.overplay.exception.BaseExceptionEntryNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class BaseExceptionInfo {
    /**
     * Exception type code.
     */
    private final int typeCode;

    /**
     * Mapping: base exception instance => base exception entry
     */
    private final Map<BaseException, BaseExceptionEntry> baseExceptionBaseExceptionEntryMap = new HashMap<>();

    public BaseExceptionInfo(int typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * Returns the type code.
     * @return the type code.
     */
    public int getTypeCode() {
        return typeCode;
    }

    /**
     * Register a base exception entry.
     * @param baseException base exception instance
     * @param entryCode     entry code
     * @param httpStatus    http status
     * @param message       error message
     * @param entryName     entry name
     */
    public void register(
        BaseException baseException,
        int entryCode,
        HttpStatus httpStatus,
        String message,
        String entryName
    ) {
        final var entry = new BaseExceptionEntry(entryCode, httpStatus, message, entryName);
        baseExceptionBaseExceptionEntryMap.put(baseException, entry);
    }

    /**
     * Returns the base exception entry.
     * @param baseException base exception instance.
     * @return the base exception entry.
     */
    public BaseExceptionEntry getBaseExceptionEntry(BaseException baseException) {
        final var entry = baseExceptionBaseExceptionEntryMap.get(baseException);

        if (entry == null) {
            throw new BaseExceptionEntryNotFoundException();
        }

        return entry;
    }
}
