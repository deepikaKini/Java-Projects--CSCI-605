/*
 * ShingleAndRoofer.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */


/**
 * This program implements a game wherein n different roofers, represented
 * by threads, give a shingle to subsequent roofers until the shingle is
 * placed on the roof. This process is repeated for k different shingles.
 *
 * @author      Manasa Deshpande
 * @author      Deepika Kini
 */

public class ShingleAndRoofer extends Thread {
    // the identifier used to differentiate the roofers
    private int info;
    // the synchronized object
    static Object aObject = new Object();
    // the number of shingles
    static int shingle;
    // the number of roofers
    static int numOfRoofers;
    // the number of roofers the shingle has been passed to
    static int numberOfRoofersSeen;
    static ShingleAndRoofer shingleAndRoofer[];

    // constructor initializing the info field
    public ShingleAndRoofer (int info) {
        this.info = info;
    }

    public void giveShingleToRoofer(){
        /**
         * This method allows each roofer to call on the next roofer
         * to pass the shingle to, wait until it has been placed on
         * the roof. When the last roofer prepares for the next round,
         * the main is notified.
         */
        synchronized ( aObject )   {
            // passing the shingle to next roofer
            numberOfRoofersSeen++;
            if (numberOfRoofersSeen < numOfRoofers)    {
                String ps = "shingle number " + shingle + " is handed to " +
                            "next roofer ( " + numberOfRoofersSeen + ")";
                System.err.println(ps);
                shingleAndRoofer[numberOfRoofersSeen] = new ShingleAndRoofer(numberOfRoofersSeen);
                // call on next roofer
                shingleAndRoofer[numberOfRoofersSeen].start();
            }
            try {
                aObject.wait();
                numberOfRoofersSeen--;
                if(numberOfRoofersSeen == 0){
                    // notify main
                    aObject.notify();
                }
            } catch (IllegalMonitorStateException | InterruptedException e )   {
                System.err.println(info +
                        ": IllegalMonitorStateException");
            }
        }
    }
    public void placeShingleOnRoof () {
        /**
         * This method allows the last roofer to put the shingle on
         * the roof and notifies the remaining roofers waiting for
         * the next round
         */
        synchronized ( aObject )   {
            try {
                System.err.println("And the shingle is put on the roof");
                aObject.notifyAll();
            } catch ( IllegalMonitorStateException  e )    {
                System.err.println(info +
                        ": IllegalMonitorStateException");
            }
        }
    }

    public void run () {
        synchronized ( aObject )   {
            if ( info != (numOfRoofers - 1))
                // pass shingle to next roofer
                giveShingleToRoofer();
            else
                // place shingle on roof
                placeShingleOnRoof();
        }
    }

    public static void main (String args []) throws InterruptedException {
        synchronized (aObject){
            numOfRoofers = Integer.parseInt(args[0]);
            int shingles = Integer.parseInt(args[1]);
            shingleAndRoofer = new ShingleAndRoofer[numOfRoofers];
            for(int i = 0; i < shingles; i++){
                shingle = i;
                // reset the number of roofers the shingle has been passed to
                numberOfRoofersSeen = 0;
                shingleAndRoofer[0] = new ShingleAndRoofer(numberOfRoofersSeen);
                // first roofer picks up the shingle
                shingleAndRoofer[0].start();
                // wait for all roofers to pass the shingle, exit
                aObject.wait();
            }
        }
    }
}
