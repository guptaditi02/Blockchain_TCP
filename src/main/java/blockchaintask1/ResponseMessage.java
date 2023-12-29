// Author - Aditi Gupta (argupta@andrew.cmu.edu)
// Taken from https://github.com/CMU-Heinz-95702/Project3 Prerequisites
// Project 3 Task 1

package blockchaintask1;

import com.google.gson.Gson;

/**
 * Represents a response message in the blockchain system.
 * The class provides methods for serialization and deserialization using JSON.
 */
public class ResponseMessage {

    private String status;
    private String message;
    private String data;

    /**
     * Constructs a ResponseMessage with the specified status, message, and data.
     *
     * @param status  The status of the response.
     * @param message A brief message describing the response.
     * @param data    The actual data or content of the response.
     */
    public ResponseMessage(String status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Gets the status of the response.
     *
     * @return The status of the response.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the response.
     *
     * @param status The status to be set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the message of the response.
     *
     * @return The message of the response.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the response.
     *
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the data/content of the response.
     *
     * @return The data of the response.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data/content of the response.
     *
     * @param data The data to be set.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Deserializes a JSON string into a ResponseMessage object.
     *
     * @param json The JSON string representing a ResponseMessage.
     * @return The ResponseMessage object.
     */
    public static ResponseMessage fromJson(String json) {
        return new Gson().fromJson(json, ResponseMessage.class);
    }

    /**
     * Serializes the ResponseMessage object into a JSON string.
     *
     * @return The JSON string representation of the ResponseMessage.
     */
    public String toJson() {
        return new Gson().toJson(this);
    }
}
