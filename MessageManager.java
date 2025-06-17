
package Register3;
 import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageManager {
    List<String> sentMessages = new ArrayList<>();
    List<String> disregardedMessages = new ArrayList<>();
    List<String> storedMessages = new ArrayList<>();
    List<String> messageHashes = new ArrayList<>();
    List<String> messageIDs = new ArrayList<>();
    List<String> recipients = new ArrayList<>();

    // Test Data Setup
    public void populateTestData() {
        storeMessage("+27834557896", "Did you get the cake?", "Sent");
        storeMessage("0838884567", "Is dinner time", "Sent");
        storeMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        storeMessage("+27834484567", "Yohoooo, I am at your gate", "Disregard");
        storeMessage("+27838884567", "Ok, I am leaving without you.", "Stored");
    }

    // Store message to the right array based on flag
    public void storeMessage(String recipient, String message, String flag) {
        String id = UUID.randomUUID().toString();
        String hash = generateHash(message);
        messageIDs.add(id);
        messageHashes.add(hash);
        recipients.add(recipient);

        switch (flag.toLowerCase()) {
            case "sent" -> sentMessages.add("ID: " + id + " | To: " + recipient + " | Msg: " + message);
            case "stored" -> storedMessages.add("ID: " + id + " | To: " + recipient + " | Msg: " + message);
            case "disregard" -> disregardedMessages.add("ID: " + id + " | To: " + recipient + " | Msg: " + message);
        }
    }

    // Generate a hash of the message
    public String generateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            return Arrays.toString(digest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // Display sender & recipient of all sent messages
    public void displaySentMessages() {
        for (String msg : sentMessages) {
            System.out.println(msg);
        }
    }

    // Search by message ID
    public void searchByMessageID(String id) {
        for (String msg : sentMessages) {
            if (msg.contains(id)) {
                System.out.println("Found: " + msg);
                return;
            }
        }
        System.out.println("Message not found.");
    }

    // Display the longest sent message
    public void displayLongestSentMessage() {
        String longest = "";
        for (String msg : sentMessages) {
            String content = msg.split("Msg: ")[1];
            if (content.length() > longest.length()) {
                longest = content;
            }
        }
        System.out.println("Longest message: " + longest);
    }

    // Search all messages by recipient
    public void searchMessagesByRecipient(String recipient) {
        for (String msg : sentMessages) {
            if (msg.contains("To: " + recipient)) {
                System.out.println("Found in sent: " + msg);
            }
        }
        for (String msg : storedMessages) {
            if (msg.contains("To: " + recipient)) {
                System.out.println("Found in stored: " + msg);
            }
        }
    }

    // Delete a message using its hash
    public void deleteMessageByHash(String hash) {
        for (int i = 0; i < sentMessages.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                System.out.println("Message deleted: " + sentMessages.remove(i));
                messageHashes.remove(i);
                messageIDs.remove(i);
                recipients.remove(i);
                return;
            }
        }
        System.out.println("Hash not found.");
    }

    // Full report
    public void displayReport() {
        System.out.println("SENT MESSAGE REPORT:");
        for (int i = 0; i < sentMessages.size(); i++) {
            System.out.println("Hash: " + messageHashes.get(i));
            System.out.println("Recipient: " + recipients.get(i));
            System.out.println("Message: " + sentMessages.get(i));
            System.out.println("--------------------");
        }
    }
}
