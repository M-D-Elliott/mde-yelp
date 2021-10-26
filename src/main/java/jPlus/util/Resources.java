package jPlus.util;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Resources {

    public static List<String> read(String path) {
        return read(asStream(path));
    }

    public static List<String> read(InputStream inputStream) {
        final List<String> ret = new ArrayList<>();
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String text;
                while ((text = reader.readLine()) != null) ret.add(text);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        return ret;
    }

    public static InputStream asStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }

    public static File[] extractTempFiles(String path) {

        final var loader = ClassLoader.getSystemClassLoader();
        final URL url = loader.getResource(path);

        if (url != null) {
            try {
                final var files = new File(url.toURI()).listFiles();
                if (files != null) return files;
            } catch (URISyntaxException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        final String userDir = System.getProperty("user.dir") + File.separator;

        try {
            String jarPath = Resources.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();

            URI uri = URI.create("jar:file:" + jarPath);
            try (var fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                final File folder = FileUtils.genDir(userDir + path);
                Files.walk(fs.getPath(path))
                        .filter(Files::isRegularFile)
                        .forEach(p -> {
                            final File ret = new File(userDir + p);
                            final InputStream stream = loader.getResourceAsStream(p.toString().substring(1));
                            copyInputStreamToFile(stream, ret);
                        });
                final File[] ret = folder.listFiles();
                if (ret != null) return ret;
            }
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }

        return new File[0];
    }

    public static final int DEFAULT_BUFFER_SIZE = 8192;

    public static void copyInputStreamToFile(InputStream inputStream, File file) {

        if (inputStream != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
                int read;
                byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else System.err.println("Resource stream null.");
    }
}
