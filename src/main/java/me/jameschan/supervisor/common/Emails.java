package me.jameschan.supervisor.common;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.jetbrains.annotations.NotNull;

public final class Emails {
    public static boolean isEmail(@NotNull final String string) {
        try {
            new InternetAddress(string).validate();
        } catch (AddressException ex) {
            return false;
        }

        return true;
    }
}
