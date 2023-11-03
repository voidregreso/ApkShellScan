import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class MyUtil {
    public static List<String> readZipFile(String file) {
        List<String> result = new ArrayList<>();
        try (ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                if (!ze.isDirectory()) {
                    String name = ze.getName().contains("/") ? ze.getName().substring(ze.getName().lastIndexOf("/") + 1) : ze.getName();
                    result.add(name);
                    System.err.println("file - " + name + " : " + ze.getSize() + " bytes");
                }
                zin.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.add("ERROR:" + e.getMessage());
        }
        return result;
    }

    public static void readAPK(String apkUrl) {
        try (ZipFile zipFile = new ZipFile(apkUrl)) {
            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = enumeration.nextElement();
                // No processing is done with the zipEntry, so the loop is effectively a no-op.
            }
        } catch (Exception ignored) {
            // Exception is ignored
        }
    }
}
