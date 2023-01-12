import java.io.*;


public class LineR {
    public static void main (String args[]) throws IOException{

        try
        {
            int skip = 2;//char to be skipped
            LineReaderImplementation input;
            if (args.length ==1) {
                input = new LineReaderImplementation(new BufferedReader(new FileReader(args[0])));
            }
            else{
                input = new LineReaderImplementation(new BufferedReader(new InputStreamReader(System.in)));
            }
            System.out.println("=======ReadLine method=======");
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("--" + (line) + "---");
                System.out.println(input.getLineNumber());
                //input.skip(skip);
            }

            System.out.println("=======Read method without buffer=======");
            if (args.length ==1) {
                input = new LineReaderImplementation(new BufferedReader(new FileReader(args[0])));
            }
            else{
                input = new LineReaderImplementation(new BufferedReader(new InputStreamReader(System.in)));
            }

            int line1;
            while ( ( line1 = input.read() )  != -1  ) {
                System.out.println("--" + ((char)line1) + "---" );
                System.out.println("line# => " + input.getLineNumber() );
                //input.skip(skip);
            }
            input.close();

            System.out.println("=======testing skip/ mark/reset/read using buffer=======");
            if (args.length == 1) {
                input = new LineReaderImplementation(new BufferedReader(new FileReader(args[0])));
            } else {
                input = new LineReaderImplementation(new BufferedReader(new InputStreamReader(System.in)));
            }
            char[] buffer = new char[6];
            System.out.println(input.readLine());//prints 1a
            System.out.println("Read characters in buffer:" + input.read(buffer,0,6)+ "::");
            for(int i= 0; i< buffer.length; i++)
            {
                System.out.print(buffer[i]);
            }
            System.out.println("End of buffer");
            System.out.println( "line# => " + input.getLineNumber());
            System.out.println(input.skip(6));
            System.out.println("line after skipping => " + input.getLineNumber());
            input.mark(3);
            System.out.println(input.readLine());
            System.out.println("line# => " + input.getLineNumber());
            input.reset();
            System.out.println(input.readLine());
            System.out.println("line# => " + input.getLineNumber());
            System.out.println(input.readLine());
            input.close();

        }
        catch ( FileNotFoundException e)	{
            System.out.println(e.getMessage());
        }
        catch ( IOException e)	{
            System.out.println(e.getMessage());
        }
        catch ( Exception e)	{
            System.out.println("ExceptionType occurred: " +
                    e.getMessage() );
        }
    }
}
