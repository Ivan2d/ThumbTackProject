package net.thumbtack.school.file;
import com.google.gson.Gson;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.ColoredRectangle;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileService {
    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(array);
        }
    }

    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(new File(fileName), array);
    }

    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return fileInputStream.readAllBytes();
        }
    }

    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        int goodNumber = 0;
        byte[] result;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byteArrayOutputStream.write(array);
            result = byteArrayOutputStream.toByteArray();
        }

        byte[] newByteArray = new byte[array.length / 2];
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result)) {
            for (int i = 0; i < array.length; i++) {
                if (i % 2 == 0) {
                    newByteArray[goodNumber] = (byte) byteArrayInputStream.read();
                    goodNumber++;
                } else byteArrayInputStream.read();
            }
        }

        return newByteArray;
    }

    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        try (BufferedOutputStream byteArrayOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            byteArrayOutputStream.write(array);
        }
    }

    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFileBuffered(new File(fileName), array);
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            return bufferedInputStream.readAllBytes();
        }
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        return readByteArrayFromBinaryFileBuffered(new File(fileName));
    }

    public static void writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))) {
            dataOutputStream.writeInt(rect.getCenter().getX());
            dataOutputStream.writeInt(rect.getCenter().getY());
            dataOutputStream.writeInt(rect.getWidth());
            dataOutputStream.writeInt(rect.getHeight());
        }
    }

    public static Rectangle readRectangleFromBinaryFile(File file) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            return new Rectangle(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt());
        }
    }

    public static void writeColoredRectangleToBinaryFile(File file, ColoredRectangle rect) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))) {
            dataOutputStream.writeInt(rect.getCenter().getX());
            dataOutputStream.writeInt(rect.getCenter().getY());
            dataOutputStream.writeInt(rect.getWidth());
            dataOutputStream.writeInt(rect.getHeight());
            dataOutputStream.writeUTF(rect.getColor().toString());
        }
    }

    public static ColoredRectangle readColoredRectangleFromBinaryFile(File file) throws IOException, ColorException {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            return new ColoredRectangle(dataInputStream.readInt(),
                    dataInputStream.readInt(), dataInputStream.readInt(),
                    dataInputStream.readInt(), dataInputStream.readUTF());
        }
    }

    public static void writeRectangleArrayToBinaryFile(File file, Rectangle[] reacts) throws IOException{
        try(DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))){
            for (Rectangle rect : reacts) {
                dataOutputStream.writeInt(rect.getCenter().getX());
                dataOutputStream.writeInt(rect.getCenter().getY());
                dataOutputStream.writeInt(rect.getWidth());
                dataOutputStream.writeInt(rect.getHeight());
            }
        }
    }
    public static Rectangle[] readRectangleArrayFromBinaryFileReverse(File file) throws IOException {
        Rectangle[] rects = new Rectangle[(int) (file.length() / 16)];
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            for (int i = 0; i < rects.length; i++) {
                raf.seek((rects.length - 1 - i) * 16L);
                rects[i] = new Rectangle(raf.readInt(), raf.readInt(), raf.readInt(), raf.readInt());
            }
        }
        return rects;
    }

    public static void writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file, StandardCharsets.UTF_8))
        {
            printWriter.format("%d %d %d %d",
                    rect.getCenter().getX(),
                    rect.getCenter().getY(),
                    rect.getWidth(),
                    rect.getHeight());
        }
    }

    public static Rectangle readRectangleFromTextFileOneLine(File file) throws IOException {
        Rectangle react = null;
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNext()) {
                react = new Rectangle(scanner.nextInt(), scanner.nextInt(),
                        scanner.nextInt(), scanner.nextInt());
            }
        }
        return react;
    }

    public static void writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.format("%d\n %d\n %d\n %d", rect.getCenter().getX(),
                    rect.getCenter().getY(), rect.getWidth(),
                    rect.getHeight());
        }
    }

    public static Rectangle readRectangleFromTextFileFourLines(File file) throws IOException {
        return readRectangleFromTextFileOneLine(file);
    }

    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.format("%s %s %d", trainee.getFirstName(),
                    trainee.getLastName(), trainee.getRating());
        }
    }

    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException, TrainingException {
        Trainee trainee = null;
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNext()) {
                trainee = new Trainee(scanner.next(), scanner.next(), scanner.nextInt());
            }
        }
        return trainee;
    }

    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.format("%s\n %s\n %d", trainee.getFirstName(),
                    trainee.getLastName(), trainee.getRating());
        }
    }

    public static Trainee readTraineeFromTextFileThreeLines(File file) throws IOException, TrainingException {
        return readTraineeFromTextFileOneLine(file);
    }

    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(trainee);
        }
    }

    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException, ClassNotFoundException {
        Trainee trainee;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            trainee = (Trainee) objectInputStream.readObject();
        }
        return trainee;
    }

    public static String serializeTraineeToJsonString(Trainee trainee) {
        return new Gson().toJson(trainee);
    }

    public static Trainee deserializeTraineeFromJsonString(String json){
        return new Gson().fromJson(json, Trainee.class);
    }

    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        try (Writer fireWriter = new FileWriter(file)) {
            new Gson().toJson(trainee, fireWriter);
        }
    }

    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        try (Reader reader = new FileReader(file)) {
            return new Gson().fromJson(reader, Trainee.class);
        }
    }
}


