package ru.akvine.dbvisor.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.dbvisor.exceptions.ExtractContentException;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class FileUtils {
    public InputStream extractContent(MultipartFile file) {
        Asserts.isNotNull(file);

        try {
            return file.getInputStream();
        } catch (IOException exception) {
            String errorMessage = String.format(
                    "Error while extracting input stream from multipart file = [%s]. Message = [%s]",
                    file.getOriginalFilename(),
                    exception.getMessage()
            );
            throw new ExtractContentException(errorMessage);
        }
    }
}
