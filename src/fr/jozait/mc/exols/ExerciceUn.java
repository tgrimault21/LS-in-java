package fr.jozait.mc.exols;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciceUn {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("ls")) {
            String currentLocation = System.getProperty("user.dir");
            if (args.length > 1) {
                argsHandler(Arrays.copyOfRange(args, 1, args.length), currentLocation);
            } else {
                displayDirectories(currentLocation, "" );
            }
        }
    }

    public static void argsHandler(String[] args, String currentLocation) {
        List<String> options = new ArrayList<>();
        List<String> paths = new ArrayList<>();
        for (String arg : args) {
            String prefix = arg.substring(0,1);

            if (prefix.equals("-")) {
                if (arg.equals("-a")) {
                    options.add("a");
                }
                if (arg.equals("-l")) {
                    options.add("l");
                }
                if (arg.equals("-la")) {
                    options.add("la");
                }
                if (arg.equals("-R")) {
                    options.add("R");
                }
            } else {
                paths.add(arg);
            }
        }

        displayDirectories(paths.size() > 0 ? paths.get(0) : currentLocation, options.size() > 0 ? options.get(0) : "");
    }

    public static void displayDirectories(String path, String option) {
        File file = new File(path);

        if (option.equals("a") || option.equals("")) {
            String[] directoriesAndFiles = file.list((current, name) -> filter(current, name, option));

            for (String directoryOrFile : directoriesAndFiles) {
                System.out.println(directoryOrFile);
            }
        }

        if (option.equals("l") || option.equals("la")) {
            File[] directoriesAndFiles = file.listFiles((current, name) -> filter(current, name, option));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (File directoryOrFile : directoriesAndFiles) {
                System.out.println(sdf.format(directoryOrFile.lastModified()) + " - " + directoryOrFile.length() + " - " + directoryOrFile.getName());
            }
        }
    }

    public static boolean filter (File current, String name, String option) {
        boolean directory = new File(current, name).isDirectory();
        boolean file1 = new File(current, name).isFile();
        boolean hidden = new File(current, name).isHidden();

        if (option.contains("a")) {
            return file1 || directory;
        } else {
            return (file1 || directory) && !hidden;
        }
    }
}
