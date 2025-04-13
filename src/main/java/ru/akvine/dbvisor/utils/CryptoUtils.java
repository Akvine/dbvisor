package ru.akvine.dbvisor.utils;

import jakarta.annotation.Nullable;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class CryptoUtils {

    @Nullable
    public String hash(String value) {
        return StringUtils.isNotBlank(value) ? DigestUtils.sha256Hex(value.getBytes()) : null;
    }
}
