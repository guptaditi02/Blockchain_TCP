// Author - Aditi Gupta (argupta@andrew.cmu.edu)
// Taken from https://www.andrew.cmu.edu/course/95-702/examples/javadoc/blockchaintask0/BlockChain.html
// Taken code from https://www.baeldung.com/java-blockchain
// Project 3 Task 1

package blockchaintask1;

import com.google.gson.Gson;

import java.util.ArrayList;

public class BlockChain
        extends Object {
     /**
     This BlockChain has exactly three instance members -
      an ArrayList to hold Blocks and a chain hash to hold a SHA256 hash of the most recently added Block.
      It also maintains an instance variable holding the approximate number of hashes per second on this computer.
      This constructor creates an empty ArrayList for Block storage.
      This constructor sets the chain hash to the empty string and sets hashes per second to 0.
*/
     ArrayList<Block> blocks;      // To store blocks
    String chainHash;             // To store the SHA256 hash of the most recently added block
    long hashesPerSecond;         // To store the approximate number of hashes per second on this computer

    public BlockChain() {
        this.blocks = new ArrayList<>();
        this.chainHash = "";
        this.hashesPerSecond = 0;

    }

    /**
     * public java.lang.String getChainHash()
     * Returns:
     * the chain hash.
     */
    public String getChainHash(){
        return chainHash;
    }

    /**public java.sql.Timestamp getTime()
     Returns:
     the current system time
        */
    public java.sql.Timestamp getTime(){
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**public blockchaintask1.Block getLatestBlock()
     Returns:
     a reference to the most recently added Block.
     */
    public Block getLatestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    /**
     * computeHashesPerSecond
     * public void computeHashesPerSecond()
     * This method computes exactly 2 million hashes and times how long that process takes.
     * So, hashes per second is approximated as (2 million / number of seconds).
     * It is run on start up and sets the instance variable hashesPerSecond.
     * It uses a simple string - "00000000" to hash.
     */
    public void computeHashesPerSecond() throws Exception {
        Long startTime = System.currentTimeMillis();
        int numHash = 0;
        while (numHash < 2000000) {
            Hash.hash("00000000");
            numHash++;
        }
        Long endTime = System.currentTimeMillis();
        this.hashesPerSecond =  (int)(numHash / ((endTime - startTime) / 1000.0));
    }
    /**
     * public int getHashesPerSecond()
     * get hashes per second
     * Returns:
     * the instance variable approximating the number of hashes per second.
     */
    public int getHashesPerSecond(){
        return (int)hashesPerSecond;
    }

    /**
     * A new Block is being added to the BlockChain.
     * This new block's previous hash must hold the hash
     * of the most recently added block. After this call on addBlock,
     * the new block becomes the most recently added block on the BlockChain.
     * The SHA256 hash of every block must exhibit proof of work,
     * i.e., have the requisite number of leftmost 0's defined by its difficulty.
     * Suppose our new block is x. And suppose the old blockchain was
     * a <-- b <-- c <-- d then the chain after addBlock completes is
     * a <-- b <-- c <-- d <-- x. Within the block x, there is a previous hash field.
     * This previous hash field holds the hash of the block d.
     * The block d is called the parent of x.
     * The block x is the child of the block d.
     * It is important to also maintain a hash of the most recently added block in a chain hash.
     * Let's look at our two chains again. a <-- b <-- c <-- d.
     * The chain hash will hold the hash of d. After adding x,
     * we have a <-- b <-- c <-- d <-- x. The chain hash now holds the hash of x.
     * The chain hash is not defined within a block but is defined within the block chain.
     * The arrows are used to describe these hash pointers.
     * If b contains the hash of a then we write a <-- b.
     * Parameters:
     * newBlock is added to the BlockChain as the most recent block
     */
    public void addBlock(Block newBlock) throws Exception {
        newBlock.setPreviousHash(this.chainHash);
        this.blocks.add(newBlock);
        this.chainHash = newBlock.proofOfWork();
    }

    /**
     * public java.lang.String toString()
     * This method uses the toString method defined on each individual block.
     * Overrides:
     * toString in class java.lang.Object
     * Returns:
     * a String representation of the entire chain is returned.
     */

    /**
     * View the Blockchain
     * {"ds_chain" : [ {"index" : 0,"time stamp " : "2022-02-25 17:41:11.927","Tx ": "Genesis","PrevHash" : "","nonce" : 286,"difficulty": 2},
     * {"index" : 1,"time stamp " : "2022-02-25 17:42:46.053","Tx ": "Mike pays Marty 100 DSCoin","PrevHash" : "0026883909AA470264145129F134489316E6A38439240D0468D69AA9665D4993","nonce" : 165,"difficulty": 2},
     * {"index" : 2,"time stamp " : "2022-02-25 17:44:27.43","Tx ": "Marty pays Joe 50 DSCoin","PrevHash" : "000D14B83028DD36BD6330B8DAB185012F8625E9C9D1A8704E9C1189FD98D9DF","nonce" : 819,"difficulty": 2},
     * {"index" : 3,"time stamp " : "2022-02-25 17:45:22.044","Tx ": "Joe pays Andy 10 DSCoin","PrevHash" : "00B4CC539C5CC36AE2F09CC7B857A1330D2D02C00CA90D4A34ACD7E01D7225FC","nonce" : 167,"difficulty": 2}
     */
    //override toString method to give above output

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"ds_chain\" : [");

        for (int i = 0; i < blocks.size(); i++) {
            sb.append(blocks.get(i).toString());  // This will serialize each block to JSON

            if (i != blocks.size() - 1) {  // if not the last block, append a comma
                sb.append(",");
            }
            //Code from https://stackoverflow.com/questions/14534767/how-to-append-a-newline-to-stringbuilder - for reference
            sb.append("\n");
        }
        //Code from https://stackoverflow.com/questions/22682016/how-to-use-string-builder-to-append-double-quotes-and-or-between-elements
        sb.append(" ], \"chainHash\" : ").append("\"").append(this.chainHash).append("\"").append("}");
        return sb.toString();
    }

    /**
     * public blockchaintask1.Block getBlock(int i)
     * return block at position i
     * Parameters:
     * i -
     * Returns:
     * block at postion i
     */
    public Block getBlock(int i){
        return blocks.get(i);
    }

    /**
     * public int getTotalDifficulty()
     * Compute and return the total difficulty of all blocks on the chain. Each block knows its own difficulty.
     * Returns:
     * totalDifficulty
     */
    public int getTotalDifficulty(){
        int totalDifficulty = 0;
        for (Block block : blocks) {
            totalDifficulty += block.getDifficulty();
        }
        return totalDifficulty;
    }

    /**
    * public double getTotalExpectedHashes()
    * Compute and return the expected number of hashes required for the entire chain.
    * Returns:
    * totalExpectedHashes
    */
    public double getTotalExpectedHashes(){
        double totalExpectedHashes = 0;
        for (Block block : blocks) {
            //Taken code from https://bitcoin.stackexchange.com/questions/4565/calculating-average-number-of-hashes-tried-before-hitting-a-valid-block
            totalExpectedHashes += Math.pow(16, block.getDifficulty());
        }
        return totalExpectedHashes;
    }

    /**
     * public java.lang.String isChainValid()
     * If the chain only contains one block, the genesis block at position 0,
     * this routine computes the hash of the block and
     * checks that the hash has the requisite number of leftmost 0's (proof of work) as specified in the difficulty field.
     * It also checks that the chain hash is equal to this computed hash.
     * If either check fails, return an error message. Otherwise, return the string "TRUE".
     * If the chain has more blocks than one, begin checking from block one.
     * Continue checking until you have validated the entire chain.
     * The first check will involve a computation of a hash in Block 0 and a comparison with the hash pointer in Block 1.
     * If they match and if the proof of work is correct, go and visit the next block in the chain.
     * At the end, check that the chain hash is also correct.
     * Returns:
     * "TRUE" if the chain is valid, otherwise return a string with an appropriate error message
     */

    // https://www.baeldung.com/java-blockchain - for reference
    public String isChainValid() throws Exception {
        if (blocks.size() == 1) {
            Block block = blocks.get(0);
            String hash = block.calculateHash();
            if (hash.startsWith("0".repeat(block.getDifficulty()))) {
                if (hash.equals(this.chainHash)) {
                    return "TRUE";
                } else {
                    System.out.println("Improper hash on node 0");
                    return "FALSE";
                }
            } else {
                System.out.println("Does not begin with " + ("0".repeat(block.getDifficulty())));
                return "FALSE";
            }
        } else {
            for (int i = 1; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                Block prevBlock = blocks.get(i - 1);
                String hash = block.calculateHash();
                if (!hash.startsWith("0".repeat(block.getDifficulty())) || !block.getPreviousHash().equals(prevBlock.calculateHash())){
                        System.out.println("Improper hash on node " + i + " Does not begin with " + ("0".repeat(block.getDifficulty())));
                        return "FALSE";
                    }
            }
            return "TRUE";
        }
    }

    /**
     * public void repairChain()
     * This routine repairs the chain.
     * It checks the hashes of each block and ensures that any illegal hashes are recomputed.
     * After this routine is run, the chain will be valid.
     * The routine does not modify any difficulty values.
     * It computes new proof of work based on the difficulty specified in the Block.
     */
    public void repairChain() throws Exception {
        String hash = "";
        for (int i = 1; i < this.blocks.size(); i++) {
            Block currentBlock = this.blocks.get(i);
            Block prevBlock = this.blocks.get(i - 1);
            //Setting all the hashes again so that the chain is repaired
            currentBlock.setPreviousHash(prevBlock.calculateHash());

            // Getting the proof of work for the current block
            hash=currentBlock.proofOfWork();
        }
        //Assigning the proof of work of the final block to the chain hash
        this.chainHash = hash;
    }
}

