package me.modmuss50.ac.compiler;

import me.modmuss50.ac.tiles.TileComputer;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Mark on 01/06/2016.
 */
public class JavaCompiler {

    private static JavaCompiler compiler = new JavaCompiler();

    public static void compileAndInject(File file, File output, TileComputer computer) {
        File bin = new File(output, file.getName().replace(".java", ".class"));
        File binJar = new File(output, "output.jar");
        if(bin.exists()){
            bin.delete();
        }
        if(binJar.exists()){
            binJar.delete();
        }

        List<String> commandargs = new ArrayList<>();
        commandargs.add(" -d " + output.getAbsolutePath());
        commandargs.add(" -1.8");
        commandargs.add(" -warn:none");
        commandargs.add(" -O -time -progress -noExit");
        commandargs.add(" " + file.getAbsolutePath());
        StringBuilder builder = new StringBuilder();
        String[] commands = new String[commandargs.size()];
        commands = commandargs.toArray(commands);
        for (String s : commands) {
            builder.append(s);
        }

        System.out.println(builder.toString());

        CompilationProgress compilationProgress = null;
        BatchCompiler.compile(builder.toString(), new PrintWriter(System.out), new PrintWriter(System.out), compilationProgress);


        try {
            zipFile(bin, binJar);
        } catch (IOException e) {
            e.printStackTrace();
        }
        compiler.addToClasspath(binJar);
        try {
            Class c = Class.forName(file.getName().replace(".java", ""));
            Object obj = c.newInstance();
            Class param[] = new Class[1];
            param[0] = TileComputer.class;
            Method method = c.getMethod("run", param);
            method.invoke(obj, computer);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    /**
     * Credit to https://github.com/Slowpoke101/FTBLaunch/blob/master/src/main/java/net/ftb/workers/AuthlibDLWorker.java
     */
    public boolean addToClasspath(File file) {
        System.out.println("Loading external library " + file.getName() + " to classpath");
        try {
            if (file.exists()) {
                addURL(file.toURI().toURL());
            } else {
                System.out.println("Error loading jar");
            }
        } catch (Throwable t) {
            if (t.getMessage() != null) {
                System.out.println(t.getMessage());
            }
            return false;
        }

        return true;
    }

    /**
     * Credit to https://github.com/Slowpoke101/FTBLaunch/blob/master/src/main/java/net/ftb/workers/AuthlibDLWorker.java
     */
    public void addURL(URL u) throws IOException {
        URLClassLoader sysloader = (URLClassLoader) this.getClass().getClassLoader();
        Class sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(sysloader, u);
        } catch (Throwable t) {
            if (t.getMessage() != null) {
                System.out.println(t.getMessage());
            }
            throw new IOException("Error, could not add URL to system classloader");
        }
    }

    public static void zipFile(File input, File output) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(output);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        ZipEntry zipEntry = new ZipEntry(input.getName());
        zipOutputStream.putNextEntry(zipEntry);

        FileInputStream fileInputStream = new FileInputStream(input);
        byte[] buf = new byte[1024];
        int bytesRead;

        while ((bytesRead = fileInputStream.read(buf)) > 0){
            zipOutputStream.write(buf, 0, bytesRead);
        }
        zipOutputStream.closeEntry();
        zipOutputStream.close();
        fileOutputStream.close();
    }


}
