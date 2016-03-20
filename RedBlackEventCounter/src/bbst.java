import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sayak Biswas on 3/16/2016.
 * This is the executable class file and provides access to commands needed to perform the required operations.
 * @author Sayak Biswas
 */
public class bbst {
    public static void main(String[] args) {
        long beforeTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
        if(args.length < 1) {
            System.out.println("Proper usage: java bbst test_100.txt < commands.txt > out_100.txt");
            System.exit(0);
        } else {
            ArrayList<Event> eventArrayList = null;
            RedBlackTreeEventCounter redBlackTreeEventCounter = null;
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(new FileInputStream(args[0]));
                int lineCount = 0;
                eventArrayList = new ArrayList<>();
                while (fileReader.hasNext()) {
                    String scannedLine = fileReader.nextLine();
                    Scanner scannedToken = new Scanner(scannedLine);
                    if (lineCount > 0) {
                        while (scannedToken.hasNext()) {
                            int ID = scannedToken.nextInt();
                            int count = scannedToken.nextInt();
                            Event event = new Event(ID, count);
                            eventArrayList.add(event);
                        }
                        scannedToken.close();
                    }
                    lineCount++;
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("File " + args[0] + " not found!");
                fileNotFoundException.printStackTrace();
            } finally {
                if(fileReader != null) {
                    fileReader.close();
                }
            }
            if(eventArrayList != null) {
                redBlackTreeEventCounter = new RedBlackTreeEventCounter();
                redBlackTreeEventCounter.buildEventCounter(eventArrayList);
                long afterBuildTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                System.out.println("Time taken to builds tree :: " + (afterBuildTimeMillis - beforeTimeMillis)); //TODO: Comment. Placed for performance testing.
            }

            if(redBlackTreeEventCounter != null) {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String scannedString = scanner.nextLine();
                    Scanner stringScanner = new Scanner(scannedString);
                    while (stringScanner.hasNext()) {
                        String command = stringScanner.next();
                        if("increase".equalsIgnoreCase(command)) {
                            long beforeIncreaseTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            int ID = stringScanner.nextInt();
                            int amount = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.increase(ID, amount));
                            long afterIncreaseTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            System.out.println("Time taken to increase :: " + (afterIncreaseTimeMillis - beforeIncreaseTimeMillis)); //TODO: Comment. Placed for performance testing.
                        } else if("reduce".equalsIgnoreCase(command)) {
                            long beforeReduceTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            int ID = stringScanner.nextInt();
                            int amount = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.reduce(ID, amount));
                            long afterReduceTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            System.out.println("Time taken to reduce :: " + (afterReduceTimeMillis - beforeReduceTimeMillis)); //TODO: Comment. Placed for performance testing.
                        } else if("count".equalsIgnoreCase(command)) {
                            long beforeCountTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            int ID = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.count(ID));
                            long afterCountTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            System.out.println("Time taken to count :: " + (afterCountTimeMillis - beforeCountTimeMillis)); //TODO: Comment. Placed for performance testing.
                        } else if("inrange".equalsIgnoreCase(command)) {
                            long beforeInRangeTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            int ID1 = stringScanner.nextInt();
                            int ID2 = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.inRange(ID1, ID2));
                            long afterInRangeTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            System.out.println("Time taken to find inRange :: " + (afterInRangeTimeMillis - beforeInRangeTimeMillis)); //TODO: Comment. Placed for performance testing.
                        } else if("next".equalsIgnoreCase(command)) {
                            long beforeNextTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            int ID = stringScanner.nextInt();
                            Event nextEvent = redBlackTreeEventCounter.next(ID);
                            System.out.println(nextEvent.getID() + " " + nextEvent.getCount());
                            long afterNextTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            System.out.println("Time taken to find next :: " + (afterNextTimeMillis - beforeNextTimeMillis)); //TODO: Comment. Placed for performance testing.
                        } else if("previous".equalsIgnoreCase(command)) {
                            long beforePreviousTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            int ID = stringScanner.nextInt();
                            Event previousEvent = redBlackTreeEventCounter.previous(ID);
                            System.out.println(previousEvent.getID() + " " + previousEvent.getCount());
                            long afterPreviousTimeMillis = System.currentTimeMillis(); //TODO: Comment. Placed for performance testing.
                            System.out.println("Time taken to find previous :: " + (afterPreviousTimeMillis - beforePreviousTimeMillis)); //TODO: Comment. Placed for performance testing.
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
        long afterTimeMillis = System.currentTimeMillis(); //TODO: Remove
        System.out.println("Total Time taken :: " + (afterTimeMillis - beforeTimeMillis)); //TODO: Remove
    }
}
