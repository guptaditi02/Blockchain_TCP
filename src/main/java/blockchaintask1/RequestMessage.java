// Author - Aditi Gupta (argupta@andrew.cmu.edu)
// Taken from https://github.com/CMU-Heinz-95702/Project3 Prerequisites
// Project 3 Task 1

package blockchaintask1;

import com.google.gson.Gson;

/**
 * Represents a request message in the blockchain system.
 * The class provides methods for serialization and deserialization using JSON.
 */
public class RequestMessage {

    private String action;
    private String data;
    private int difficulty;

    /**
     * Constructs a RequestMessage with the specified action, data, and difficulty level.
     *
     * @param action     The action associated with the request.
     * @param data       The data content for the request.
     * @param difficulty The difficulty level associated with the request, if any.
     */
    public RequestMessage(String action, String data, int difficulty) {
        this.action = action;
        this.data = data;
        this.difficulty = difficulty;
    }

    /**
     * Gets the action associated with the request.
     *
     * @return The action of the request.
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the action associated with the request.
     *
     * @param action The action to be set.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets the data/content of the request.
     *
     * @return The data of the request.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data/content of the request.
     *
     * @param data The data to be set.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Gets the difficulty level associated with the request.
     *
     * @return The difficulty level of the request.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level associated with the request.
     *
     * @param difficulty The difficulty level to be set.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Deserializes a JSON string into a RequestMessage object.
     *
     * @param json The JSON string representing a RequestMessage.
     * @return The RequestMessage object.
     */
    public static RequestMessage fromJson(String json) {
        return new Gson().fromJson(json, RequestMessage.class);
    }

    /**
     * Serializes the RequestMessage object into a JSON string.
     *
     * @return The JSON string representation of the RequestMessage.
     */
    public String toJson() {
        return new Gson().toJson(this);
    }
}
