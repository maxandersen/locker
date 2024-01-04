package io.mvnpm.maven.locker.pom;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class LockerPomFileAccessor {

    public final Path file;

    private LockerPomFileAccessor(Path file) {
        this.file = file;
    }

    public static LockerPomFileAccessor fromBasedir(Path basedir, String filename) {
        return new LockerPomFileAccessor(basedir.resolve(filename));
    }

    public Reader reader() {
        try {
            return new InputStreamReader(new FileInputStream(file.toFile()), UTF_8);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Writer writer() {
        try {
            Files.createDirectories(file.getParent());
            return new OutputStreamWriter(new FileOutputStream(file.toFile()), UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public boolean exists() {
        return Files.exists(file);
    }

    public String absolutePath() {
        return file.toAbsolutePath().toString();
    }
}
