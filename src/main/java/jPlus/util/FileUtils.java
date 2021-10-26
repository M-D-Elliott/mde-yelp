package jPlus.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static jPlus.JPlus.log;

public final class FileUtils {

    public static List<String> read(String path) {
        List<String> lines = new ArrayList<>();
        final File file = new File(path);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lines;
    }

    public static void write(String lines, String path) {
        write(lines, path, false);
    }

    public static void write(String lines, String path, boolean append) {
        try (
                FileWriter write = new FileWriter(path, append);
                PrintWriter printLine = new PrintWriter(write)
        ) {
            printLine.printf(lines);
        } catch (IOException err) {
            log(String.format("Error writing to path %s: %s", path, err));
        }
    }

    public static File genDir(String path) {
        return FileUtils.genDir(path, false);
    }

    public static File genDir(String path, boolean emptyDirectory) {
        File directory = new File(path);

        String absolutePath = directory.getAbsolutePath();

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("FileOut failed to construct directory at " + absolutePath);
            }
        } else if (emptyDirectory) {
            FileUtils.emptyDir(directory);
        }

        return directory;
    }

    public static void emptyDir(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            FileUtils.delete(files);
        }
    }

    public static boolean delete(File... files) {
        boolean successful = true;
        for (File f : files) successful &= f.delete();
        return successful;
    }

    public static boolean deleteFolderRecursive(File folder) {
        boolean ret = true;
        folder.deleteOnExit();
        final var files = folder.listFiles();
        if (files != null)
            for (File file : files)
                ret &= deleteFolderRecursive(file);
        return ret && folder.delete();
    }

    //***************************************************************//

    public static String getFormattedPath(String path) {
        return path.contains("/")
                // Mac/Unix
                ? path + "/"
                //Dos/Windows
                : path + "\\";
    }

    public static String getSimpleName(File f) {
        return f.getName().replace('.' + getExt(f), "");
    }

    private static String getExt(File f) {
        final String[] split = f.getName().split("\\.");
        return split[split.length - 1];
    }

    //***************************************************************//

    public static boolean validatePath(String path) {
        String valid = getPathRegex();
        return validateRegex(path, valid, true);
    }

    public static boolean validateFileName(String name) {
        String valid = "([-_.A-Za-z0-9]){3,}";
        return validateRegex(name, valid, true);
    }

    private static String getPathRegex() {
        String os = System.getProperty("os.name");
        String linuxRegex = "^(/[^/ ]*)+/?$";
        String windowsRegex = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9._-]+)+\\\\?";

        if (os.equals("Linux")) {
            return linuxRegex;
        }
        return windowsRegex;
    }

    public static boolean isValidFilePath(String path) {
        final File f = new File(path);
        try {
            return StringUtils.hasContent(f.getCanonicalPath());
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isValidFilePathAndExists(String path) {
        final File f = new File(path);
        try {
            return StringUtils.hasContent(f.getCanonicalPath()) && f.exists();
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean validateRegex(final String text, final String regex, boolean positiveValidation) {
        return Pattern.compile(regex).matcher(text).matches() == positiveValidation;
    }
}
