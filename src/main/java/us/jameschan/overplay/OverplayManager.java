package us.jameschan.overplay;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import us.jameschan.overplay.annotation.Entry;
import us.jameschan.overplay.annotation.OverplayException;
import us.jameschan.overplay.exception.BaseExceptionNotRegisteredException;
import us.jameschan.overplay.exception.IncorrectExtensionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Overplay manager.
 * @author James Chan (TypingHare)
 */
@Component
public class OverplayManager {
    private final ApplicationContext applicationContext;

    /**
     * Mapping: base exception class => base exception info instance
     */
    private final Map<Class<? extends BaseException>, BaseExceptionInfo> baseExceptionClassMap
        = new HashMap<>();

    @Autowired
    private OverplayManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public void init() {
        final Map<String, Object> exceptionConfigurationBeanMap
            = applicationContext.getBeansWithAnnotation(OverplayException.class);

        for (final Object baseExceptionObject : exceptionConfigurationBeanMap.values()) {
            if (!(baseExceptionObject instanceof BaseException)) {
                throw new IncorrectExtensionException(baseExceptionObject.getClass());
            }

            final OverplayException overplayException
                = baseExceptionObject.getClass().getAnnotation(OverplayException.class);
            final Class<? extends BaseException> baseExceptionClass
                = (Class<? extends BaseException>) baseExceptionObject.getClass();
            final BaseExceptionInfo baseExceptionInfo
                = new BaseExceptionInfo(overplayException.typeCode());
            baseExceptionClassMap.put(baseExceptionClass, baseExceptionInfo);

            // Utilizing Java reflection to read fields.
            final Field overplayManagerField = getOverplayManagerField();
            for (final Field field : baseExceptionClass.getDeclaredFields()) {
                final Entry entry = field.getAnnotation(Entry.class);
                if (entry == null) continue;

                try {
                    final Constructor<BaseException> constructor
                        = (Constructor<BaseException>) baseExceptionClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    final BaseException baseException = constructor.newInstance();

                    // Inject OverplayManager.
                    if (overplayManagerField != null) {
                        overplayManagerField.set(baseException, this);
                    }

                    // inject
                    field.set(null, baseException);

                    // register
                    baseExceptionInfo.register(
                        baseException,
                        entry.code(),
                        entry.status(),
                        entry.message(),
                        field.getName()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(String.format(
                        "Fail to create an instance of base exception class: <%s>",
                        baseExceptionClass.getName()
                    ));
                }
            }
        }
    }

    /**
     * Returns base exception info.
     * @param baseException base exception instance.
     * @return base exception info.
     */
    private BaseExceptionInfo getBaseExceptionInfo(BaseException baseException) {
        BaseExceptionInfo baseExceptionInfo = baseExceptionClassMap.get(baseException.getClass());
        if (baseExceptionInfo == null) {
            throw new BaseExceptionNotRegisteredException(baseException.getClass());
        }

        return baseExceptionInfo;
    }

    /**
     * Returns overplay manager field.
     * @return overplay manager field.
     */
    private Field getOverplayManagerField() {
        try {
            final Field overplayManagerField = BaseException.class.getDeclaredField("overplayManager");
            overplayManagerField.setAccessible(true);

            return overplayManagerField;
        } catch (NoSuchFieldException ignore) {
            return null;
        }
    }

    /**
     * Returns the entry name of a specified base exception.
     * @param baseException base exception instance.
     * @return the entry name of the base exception.
     */
    public String getEntryName(BaseException baseException) {
        return getBaseExceptionInfo(baseException).getBaseExceptionEntry(baseException).entryName();
    }

    /**
     * Returns the error code of a base exception.
     * @param baseException base exception instance.
     * @return the error code.
     */
    public String getErrorCode(BaseException baseException) {
        final BaseExceptionInfo baseExceptionInfo = getBaseExceptionInfo(baseException);
        final String typeCode = String.valueOf(baseExceptionInfo.getTypeCode());
        final String entryCode = String.valueOf(baseExceptionInfo.getBaseExceptionEntry(baseException).entryCode());
        final int errorCodeLength = 6 - typeCode.length();

        return typeCode + Strings.padStart(entryCode, errorCodeLength, '0');
    }

    /**
     * Returns the error message.
     * @param baseException base exception instance.
     * @return the error message.
     */
    public String getMessage(BaseException baseException) {
        return getBaseExceptionInfo(baseException).getBaseExceptionEntry(baseException).message();
    }

    /**
     * Returns the HTTP status code.
     * @param baseException base exception instance.
     * @return the HTTP status code.
     */
    public HttpStatus getHttpStatus(BaseException baseException) {
        return
            getBaseExceptionInfo(baseException).getBaseExceptionEntry(baseException).httpStatus();
    }
}