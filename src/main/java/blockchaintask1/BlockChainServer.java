// Author - Aditi Gupta (argupta@andrew.cmu.edu)
// Taken from https://www.andrew.cmu.edu/course/95-702/examples/javadoc/blockchaintask0/BlockChain.html
// Taken code from Project 2 Task 4 - https://github.com/CMU-Heinz-95702/Project-2-Client-Server
// Project 3 Task 1

package blockchaintask1;

import java.net.*;
import java.io.*;

public class BlockChainServer {

    // Server and communication attributes
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    // Blockchain attribute
    private BlockChain blockChain;

    /**
     * Starts the blockchain server on a specified port.
     *
     * @param port The port number to bind the server to.
     * @throws IOException, Exception If a network or other error occurs.
     */
    public void start(int port) throws IOException, Exception {
        System.out.println("Blockchain server running");
        serverSocket = new ServerSocket(port);
        blockChain = new BlockChain();

        // Initialize the blockchain with a genesis block
        Block genesisBlock = new Block(0, blockChain.getTime(), "Genesis", 2);
        blockChain.addBlock(genesisBlock);
        blockChain.computeHashesPerSecond();

        // Continuously listen for client connections
        while (true) {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            // Process messages from the client
            while ((inputLine = in.readLine()) != null) {
                System.out.println("We have a visitor");
                RequestMessage request = RequestMessage.fromJson(inputLine);
                System.out.println("JSON request message from the client: " + inputLine);
                ResponseMessage response = processRequest(request);
                System.out.println("JSON response message to the client: " + response.toJson());
                System.out.println( response.getStatus());
                out.println(response.toJson());
                System.out.println();
            }

            // Close the client connection
            in.close();
            out.close();
            clientSocket.close();
        }
    }

    /**
     * Processes the client's request and produces a response.
     *
     * @param request The client's request.
     * @return The response to the client's request.
     * @throws Exception If an error occurs.
     */
    private ResponseMessage processRequest(RequestMessage request) throws Exception {
        ResponseMessage r;
        switch (request.getAction()) {
            // Handle different request actions

            case "View basic blockchain status.":
                StringBuilder basicStatus = new StringBuilder();
                basicStatus.append("Current size of chain: " + blockChain.blocks.size() + "\n");
                basicStatus.append("Difficulty of most recent block: " + blockChain.getLatestBlock().getDifficulty() + "\n");
                basicStatus.append("Total difficulty for all blocks: " + blockChain.getTotalDifficulty() + "\n");
                basicStatus.append("Approximate hashes per second on this machine: " + blockChain.getHashesPerSecond() + "\n");
                basicStatus.append("Expected total hashes required for the whole chain: " + blockChain.getTotalExpectedHashes() + "\n");
                basicStatus.append("Nonce for most recent block: " + blockChain.getLatestBlock().getNonce() + "\n");
                basicStatus.append("Chain hash: " + blockChain.getChainHash() + "\n");
                // Return the basic blockchain status
                r = new ResponseMessage("SUCCESS", "Viewed basic blockchain status.", basicStatus.toString());
                return r;

            // Add a new transaction block to the blockchain
            case "Add a transaction to the blockchain.":
                Block newBlock = new Block(blockChain.blocks.size(), blockChain.getTime(), request.getData(), request.getDifficulty());
                blockChain.addBlock(newBlock);
                // Return the new block
                r = new ResponseMessage("SUCCESS", "Added a transaction to the blockchain.", newBlock.toString());
                return r;

            // Verify the integrity of the blockchain
            case "Verify the blockchain.":
                StringBuilder verifyChain = new StringBuilder();
                verifyChain.append("Chain Verification :" + blockChain.isChainValid() + "\n");
                // Return the blockchain verification status
                r= new ResponseMessage("SUCCESS", "Verified the blockchain.", verifyChain.toString());
                return r;

            // View the entire blockchain
            case "View the blockchain.":
                // Return the blockchain
                r= new ResponseMessage("SUCCESS", "Viewed the blockchain.", blockChain.toString());
                return r;

            // Corrupt a specified block in the blockchain
            case "Corrupt the chain.":
                Block block = blockChain.getBlock(request.getDifficulty()); // using difficulty as blockID here for simplicity
                block.setData(request.getData());
                // Return the corrupted block
                r = new ResponseMessage("SUCCESS", "Corrupted the chain.", block.toString());
                return r;

            // Repair the blockchain to fix any corruptions
            case "Hide the corruption by repairing the chain.":
                blockChain.repairChain();
                // Return the repaired blockchain
                r = new ResponseMessage("SUCCESS", "Hid the corruption by repairing the chain.", "");
                return r;

            // Default case when the action is not recognized
            default:
                r = new ResponseMessage("ERROR", "Invalid action specified", "");
                System.exit(0);
                stop();
                return r;
        }
    }

    /**
     * Stops the server and releases resources.
     *
     * @throws IOException If a network error occurs.
     */
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws Exception {
        // Start the blockchain server
        BlockChainServer server = new BlockChainServer();
        server.start(6666);

    }
}
