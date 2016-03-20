import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Sayak Biswas on 3/16/2016.
 * This is the executable class file and provides access to commands needed to perform the required operations.
 * @author Sayak Biswas
 */
public class bbst {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Proper usage: java bbst test_100.txt < commands.txt > out_100.txt");
            System.exit(0);
        } else {
            ArrayList<RedBlackTree.RedBlackTreeNode> eventArrayList = null;
            RedBlackTreeEventCounter redBlackTreeEventCounter = null;
            BufferedReader fileReader = null;
            try {
                fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
                int lineCount = 0;
                StringTokenizer tokenizer;
                String scannedLine;
                while((scannedLine = fileReader.readLine()) != null) {
                    tokenizer = new StringTokenizer(scannedLine);
                    if (lineCount > 0) {
                        int ID = Integer.parseInt(tokenizer.nextToken());
                        int count = Integer.parseInt(tokenizer.nextToken());
                        RedBlackTree.RedBlackTreeNode event = new RedBlackTree.RedBlackTreeNode(ID,count);
                        eventArrayList.add(event);
                    } else {
                        int size = Integer.parseInt(tokenizer.nextToken());
                        eventArrayList = new ArrayList<>(size);
                    }
                    lineCount++;
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("File " + args[0] + " not found!");
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            finally {
                if(fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            if(eventArrayList != null) {
                redBlackTreeEventCounter = new RedBlackTreeEventCounter();
                redBlackTreeEventCounter.buildEventCounter(eventArrayList);
            }

            if(redBlackTreeEventCounter != null) {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String scannedString = scanner.nextLine();
                    Scanner stringScanner = new Scanner(scannedString);
                    while (stringScanner.hasNext()) {
                        String command = stringScanner.next();
                        if("increase".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            int amount = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.increase(ID, amount));
                        } else if("reduce".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            int amount = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.reduce(ID, amount));
                        } else if("count".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.count(ID));
                        } else if("inrange".equalsIgnoreCase(command)) {
                            int ID1 = stringScanner.nextInt();
                            int ID2 = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.inRange(ID1, ID2));
                        } else if("next".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            RedBlackTree.RedBlackTreeNode nextEvent = redBlackTreeEventCounter.next(ID);
                            System.out.println(((nextEvent != null) ? nextEvent.getID() : 0) + " "
                                    + ((nextEvent != null) ? nextEvent.getCount() : 0));
                        } else if("previous".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            RedBlackTree.RedBlackTreeNode previousEvent = redBlackTreeEventCounter.previous(ID);
                            System.out.println(((previousEvent != null) ? previousEvent.getID() : 0) + " "
                                    + ((previousEvent != null) ? previousEvent.getCount() : 0));
                        } else if("quit".equalsIgnoreCase(command)) {
                            break;
                        } else {
                            System.out.println("Wrong input command!");
                        }
                    }
                    stringScanner.close();
                }
                scanner.close();
            }
        }
    }
}
