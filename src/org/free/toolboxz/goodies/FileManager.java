package org.free.toolboxz.goodies;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Classe de fonctions pour la gestion des fichiers.
 * <p>
 * 
 * @author Vincent Cephirins
 * @version 1.0, 20/01/2009
 */
public final class FileManager {

    // Inhibe le constructeur
    private FileManager() {
    }

    private static void deleteDirectory(File dir) {
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                deleteDirectory(file);
            }
            file.delete();
        }
        dir.delete();
    }

    /**
     * Supprime un répertoire et son contenu (récursif).
     * <p>
     * 
     * @param dir le répertoire à supprimer
     * @throws IOException en cas d'erreur
     */
    public static void rmdir(final File dir) throws IOException {
        if (dir.exists() && dir.isDirectory()) {
            deleteDirectory(dir);
        }
        else {
            throw new FileNotFoundException(dir.toString());
        }
    }

    /**
     * Compresse un fichier ou un répertoire (récursif).
     * <p>
     * 
     * @param zipFile Le fichier destination
     * @param parent Le répertoire racine
     * @param entry Le fichier/repertoire source (chemin relatif)
     * @throws IOException en cas d'erreur
     */
    public static void zip(final File zipFile, final File parent, final File entry) throws IOException {
        List<File> listFiles;
        File dest = new File(parent, entry.getPath());
        if (dest.isFile()) {
            listFiles = new ArrayList<File>();
            listFiles.add(entry);
        }
        else if (dest.isDirectory()) {
            listFiles = zipDir(dest.listFiles());
        }
        else {
            throw new FileNotFoundException(dest.toString() + " does not exist");
        }
        zipFiles(zipFile, parent, listFiles);
    }

    /**
     * Compresse un fichier ou un répertoire (récursif).
     * <p>
     * 
     * @param zipFile Le fichier destination
     * @param entry Le fichier/repertoire source (chemin absolu)
     * @throws IOException en cas d'erreur
     */
    public static void zip(final File zipFile, final File entry) throws IOException {
        zip(zipFile, null, entry);
    }

    /**
     * Compresse une liste de fichiers ou de répertoires (récursif).
     * <p>
     * 
     * @param zipFile Le fichier destination
     * @param dest Le répertoire racine
     * @param entries Les fichiers/repertoires sources (chemin relatif)
     * @throws IOException en cas d'erreur
     */
    public static void zip(final File zipFile, final File dest, final List<File> entries) throws IOException {
        List<File> listFiles;
        if (dest == null) {
            listFiles = entries;
        }
        else {
            listFiles = new ArrayList<File>(entries.size());
            for (File entry : entries) {
                String entryPath = entry.getPath();
                if (!entryPath.startsWith(dest.getPath())) {
                    listFiles.add(new File(dest, entry.getPath()));
                }
                else {
                    listFiles.add(entry);
                }
            }
        }
        listFiles = zipDir(listFiles.toArray(new File[0]));
        zipFiles(zipFile, dest, listFiles);
    }

    /**
     * Compresse une liste de fichiers ou de répertoires (récursif).
     * <p>
     * 
     * @param zipFile Le fichier destination
     * @param entries Les fichiers/repertoires sources (chemin absolu)
     * @throws IOException en cas d'erreur
     */
    public static void zip(final File zipFile, final List<File> entries) throws IOException {
        zip(zipFile, null, entries);
    }

    private static List<File> zipDir(final File[] entries) throws IOException {
        return zipDir(new ArrayList<File>(), entries);
    }

    private static List<File> zipDir(final List<File> listFiles, final File[] entries) throws IOException {
        for (File entry : entries) {
            if (entry.isFile()) {
                listFiles.add(entry);
            }
            else if (entry.isDirectory()) {
                zipDir(listFiles, entry.listFiles());
            }
        }
        return listFiles;
    }

    private static void zipFiles(File zipFile, File parent, List<File> entries) throws IOException {
        final int BUFFER = 4096;
        byte[] data = new byte[BUFFER];
        FileOutputStream dest;

        // Création d’un flux d’écriture vers l’archive Zip finale
        dest = new FileOutputStream(zipFile);
        // Création d’un buffer de sortie afin d’améliorer les performances d’écriture
        BufferedOutputStream buffos = new BufferedOutputStream(dest);
        // Création d’un flux d’écriture Zip vers ce fichier à travers le buffer
        ZipOutputStream out = new ZipOutputStream(buffos);

        // Spécifier la méthode de compression désirée
        out.setMethod(ZipOutputStream.DEFLATED);
        // Spécifier le taux de compression (entier positif entre 0 et 9)
        out.setLevel(Deflater.DEFAULT_COMPRESSION);

        // Lister des fichiers à compresser.
        for (File entry : entries) {
            FileInputStream fi = new FileInputStream(entry);
            BufferedInputStream buffis = new BufferedInputStream(fi, BUFFER);

            // Pour chacun d’eux, créer une entrée Zip
            String path = entry.getPath();
            if (parent != null && path.startsWith(parent.getPath())) {
                path = path.substring(parent.getPath().length() + 1);
            }

            // affecter cette entrée au flux de sortie
            out.putNextEntry(new ZipEntry(path));

            // Écriture des entrées dans le flux de sortie
            int count;
            while ((count = buffis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            // Fermeture de l'entrée courante
            out.closeEntry();

            // Fermeture des flux entrant
            buffis.close();
            fi.close();
        }
        // Fermeture des flux sortant
        out.close();
        buffos.close();
        dest.close();
    }

    /**
     * liste le contenu d'un fichier zip.
     * <p>
     * 
     * @param zipFile Le fichier zip
     * @throws IOException en cas d'erreur
     */
    public static String[] zipList(final File zipFile) throws IOException {
        // Ouverture du fichier Zip
        ZipFile zf = new ZipFile(zipFile);

        // Extraction de ses entrées
        Enumeration<? extends ZipEntry> entries = zf.entries();

        String[] listEntries = new String[zf.size()];
        // Parcours de chacune des entrées
        int index = 0;
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            listEntries[index] = entry.getName();
            index++;
        }

        zf.close();
        return listEntries;
    }

    /**
     * Décompression des fichiers ou des répertoires (récursif).
     * <p>
     * 
     * @param zipFile Le fichier destination
     * @throws IOException en cas d'erreur
     */
    public static void unzip(final File zipFile) throws IOException {
        unzipFiles(zipFile, null, null);
    }

    /**
     * title.
     * <p>
     * 
     * @param zipFile L'archive
     * @param dest le répertoire de l'archive
     * @throws IOException en cas d'erreur
     */
    public static void unzip(final File zipFile, final File dest) throws IOException {
        unzipFiles(zipFile, dest, null);
    }

    /**
     * Extraction d'un fichier de l'archive dans le répertoire courant.
     * <p>
     * 
     * @param zipFile l'archive
     * @param entry le fichier à extraire
     * @throws IOException sur une erreur
     */
    public static void unzip(final File zipFile, final String entry) throws IOException {
        List<String> entries = null;
        if (entry != null) {
            entries = new ArrayList<String>(1);
            entries.add(entry);
        }
        unzipFiles(zipFile, null, entries);
    }

    /**
     * Extraction d'un fichier de l'archive.
     * <p>
     * 
     * @param zipFile l'archive
     * @param dest le répertoire de l'archive
     * @param entry le fichier à extraire
     * @throws IOException sur une erreur
     */
    public static void unzip(final File zipFile, final File dest, final String entry) throws IOException {
        List<String> entries = null;
        if (entry != null) {
            entries = new ArrayList<String>(1);
            entries.add(entry);
        }
        unzipFiles(zipFile, dest, entries);
    }

    /**
     * Extraction d'une liste de fichiers de l'archive dans le répertoire courant.
     * <p>
     * 
     * @param zipFile L'archive
     * @param entries Les repertoires/fichiers à extraire
     * @throws IOException
     */
    public static void unzip(File zipFile, List<String> entries) throws IOException {
        unzipFiles(zipFile, null, entries);
    }

    /**
     * Extraction d'une liste de fichiers de l'archive.
     * <p>
     * 
     * @param zipFile L'archive
     * @param dest le Répertoire de l'archive
     * @param entries Les repertoires/fichiers à extraire
     * @throws IOException
     */
    public static void unzip(File zipFile, File dest, List<String> entries) throws IOException {
        unzipFiles(zipFile, dest, entries);
    }

    /**
     * Extraction d'une liste de fichiers de l'archive.
     * <p>
     * Note : Cette fonction est privée avec un nom différent pour ne pas être en conflit avec les fonctions qui ont des
     * parametres optionnels. <br>
     * 
     * @param zipFile L'archive
     * @param dest le Répertoire de l'archive
     * @param entries Les repertoires/fichiers à extraire
     * @throws IOException
     */
    private static void unzipFiles(File zipFile, File dest, List<String> entries) throws IOException {
        final int BUFFER = 4096;
        byte[] data = new byte[BUFFER];
        BufferedOutputStream bosDest;

        // Création des répertoires parent si nécessaire
        if (dest != null) dest.mkdirs();

        // Ouverture du fichier à décompresser via un buffer
        FileInputStream fis = new FileInputStream(zipFile);
        BufferedInputStream buffi = new BufferedInputStream(fis);

        // Ouverture de l’archive
        ZipInputStream zis = new ZipInputStream(buffi);

        // Parcours des entrées de l’archive
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            // Filtre de l'entrée
            if (entries != null) {
                if (entries.indexOf(entry.getName()) == -1) continue;
            }

            // Construction du path
            File filePath = new File(dest, entry.getName());

            if (entry.isDirectory()) {
                // Creation du repertoire
                filePath.mkdirs();
            }
            else {
                // Creation du repertoire
                filePath.getParentFile().mkdirs();

                // Creation du fichier de sortie à partir du nom de l’entrée
                FileOutputStream fos = new FileOutputStream(filePath);
                bosDest = new BufferedOutputStream(fos, BUFFER);

                // Écriture sur disque
                int nbRead;
                while ((nbRead = zis.read(data, 0, BUFFER)) != -1) {
                    bosDest.write(data, 0, nbRead);
                }

                // Vidage du tampon en écriture
                bosDest.flush();

                // Fermeture du flux de sortie
                bosDest.close();
                fos.close();
            }
        }
        // Fermeture de l’archive
        zis.close();
    }

    /**
     * Copie un fichier ou un r�pertoire (r�cursif).
     * <p>
     * 
     * @param from Le fichier/repertoire source
     * @param to Le fichier/repertoire cible
     * @throws IOException en cas d'erreur
     */
    public static void copy(final File from, final File to) throws IOException {
        if (from.isFile()) {
            copyFile(from, to);
        }
        else if (from.isDirectory()) {
            copyDirectory(from, to);
        }
        else {
            throw new FileNotFoundException(from.toString());
        }
    }

    private static void copy(final InputStream inStream, final OutputStream outStream, final int bufferSize)
                                                                                                            throws IOException {
        final byte[] buffer = new byte[bufferSize];
        int nbRead;
        while ((nbRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, nbRead);
        }
    }

    private static void copyDirectory(final File from, final File to) throws IOException {
        if (!to.exists()) {
            to.mkdirs();
        }
        final File[] inDir = from.listFiles();
        for (int i = 0; i < inDir.length; i++) {
            final File file = inDir[i];
            copy(file, new File(to, file.getName()));
        }
    }

    private static void copyFile(final File from, final File to) throws IOException {
        File fileTo;
        if (to.isDirectory()) {
            fileTo = new File(to, from.getName());
        }
        else fileTo = to;

        final InputStream inStream = new FileInputStream(from);
        final OutputStream outStream = new FileOutputStream(fileTo);
        if (from.length() > 0) {
            copy(inStream, outStream, (int) Math.min(from.length(), 4 * 1024));
        }
        inStream.close();
        outStream.close();
    }
}
