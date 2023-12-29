// Author - Aditi Gupta (argupta@andrew.cmu.edu)
// Taken from https://www.andrew.cmu.edu/course/95-702/examples/javadoc/blockchaintask0/BlockChain.html
// Taken code from Project 2 Task 4 - https://github.com/CMU-Heinz-95702/Project-2-Client-Server
// Project 3 Task 1

package blockchaintask1;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class BlockChainClient {
    // Socket related attributes
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Initializes the client connection to a server.
     *
     * @param ip   The IP address of the server.
     * @param port The port number to connect to.
     * @throws IOException If a network error occurs.
     */
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Sends a request message to the server and awaits a response.
     *
     * @param msg The request message to be sent.
     * @return A ResponseMessage received from the server.
     * @throws IOException If a network error occurs.
     */

    public ResponseMessage sendMessage(RequestMessage msg) throws IOException {
        out.println(msg.toJson());
        return ResponseMessage.fromJson(in.readLine());
    }

    /**
     * Closes the connection and associated resources.
     *
     * @throws IOException If a network error occurs.
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws Exception {
        Long startTime, endTime;
        double elapsedTimeInMilliSeconds;

        // Initialize the client and connect to the server
        BlockChainClient client = new BlockChainClient();
        client.startConnection("localhost", 6666);
        Scanner scanner = new Scanner(System.in);

        // Continuous loop to get user input until exit option is chosen
        while (true) {
            // Display available options to user
            System.out.println("0. View basic blockchain status.");
            System.out.println("1. Add a transaction to the blockchain.");
            System.out.println("2. Verify the blockchain.");
            System.out.println("3. View the blockchain.");
            System.out.println("4. Corrupt the chain.");
            System.out.println("5. Hide the corruption by repairing the chain.");
            System.out.println("6. Exit.");
            System.out.println("Enter a number between 0 and 6:");
            int choice = scanner.nextInt();

            ResponseMessage response = null;

            switch (choice) {
                case 0:
                    // Request basic blockchain status
                    response = client.sendMessage(new RequestMessage("View basic blockchain status.", "", 0));
                    response.getMessage();
                    System.out.println(response.getData());
                    break;
                case 1:
                    // Add a new transaction to the blockchain
                    System.out.println("Enter difficulty > 0:");
                    int difficulty = scanner.nextInt();
                    System.out.println("Enter transaction:");
                    //After nextInt only the first word is read, so we need to use nextLine to read the entire line
                    //Code taken from https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner
                    scanner.nextLine();
                    String transaction = scanner.nextLine();
                    startTime = System.currentTimeMillis();
                    // Send the request to the server
                    client.sendMessage(new RequestMessage("Add a transaction to the blockchain.", transaction, difficulty));
                    endTime = System.currentTimeMillis();
                    elapsedTimeInMilliSeconds = (endTime - startTime);
                    System.out.println("Total execution time to add this block was " + elapsedTimeInMilliSeconds + " milliseconds");
                    break;
                case 2:
                    // Verify the blockchain
                    startTime = System.currentTimeMillis();
                    // Send the request to the server
                    response= client.sendMessage(new RequestMessage("Verify the blockchain.", "", 0));
                    endTime = System.currentTimeMillis();
                    elapsedTimeInMilliSeconds = (endTime - startTime);
                    response.getMessage();
                    System.out.println(response.getData());
                    System.out.println("Total execution time to verify the chain was " + elapsedTimeInMilliSeconds + " milliseconds");
                    break;
                case 3:
                    // View the blockchain
                    System.out.println("View the Blockchain");
                    // Send the request to the server
                    response = client.sendMessage(new RequestMessage("View the blockchain.", "", 0));
                    response.getMessage();
                    System.out.println(response.getData());
                    break;
                case 4:
                    // Corrupt the blockchain
                    System.out.println("Corrupt the Blockchain");
                    System.out.println("Enter block ID to corrupt:");
                    int blockId = scanner.nextInt();
                    System.out.println("Enter new data for the block:");
                    //After nextInt only the first word is read, so we need to use nextLine to read the entire line
                    //Code taken from https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner
                    scanner.nextLine();
                    String data = scanner.nextLine();
                    // Send the request to the server
                    client.sendMessage(new RequestMessage("Corrupt the chain.", data, blockId));
                    System.out.println("Block " + blockId + " now holds " + data);
                    break;
                case 5:
                    // Repair the blockchain
                    startTime = System.currentTimeMillis();
                    // Send the request to the server
                    client.sendMessage(new RequestMessage("Hide the corruption by repairing the chain.", "", 0));
                    endTime = System.currentTimeMillis();
                    elapsedTimeInMilliSeconds = (endTime - startTime);
                    System.out.println("Total execution time to repair the chain was " + elapsedTimeInMilliSeconds + " milliseconds");
                    break;
                case 6:
                    // Close connection and exit
                    client.stopConnection();
                    System.exit(0);
                    break;
            }

        }
    }
}
