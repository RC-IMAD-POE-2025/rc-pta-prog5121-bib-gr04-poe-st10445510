
package Register3;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.regex.Pattern;
import java.security.NoSuchAlgorithmException;
import java.io.FileWriter;
import java.io.IOException;

public class Message {


    private String sender;
    private String recipient;
    private String content;
       private String messageID;
    
    private static int totalMessagesSent = 0;
      private static List<Message> storedMessages = new ArrayList<>();
    private static Map<String, Message> messageRegistry = new HashMap<>();
     private static Queue<Integer> availableIDs = new LinkedList<>();
     
      static {
        
        for (int i = 1; i <= 10; i++) {
            availableIDs.offer(i);
        }
    }


    // Constructor
    public Message(String sender, String recipient, String content) {
         if (availableIDs.isEmpty()) {
            throw new IllegalStateException("Maximum of 10 messages already created.");
        }
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        
        int nextID = availableIDs.poll(); 
        this.messageID = String.valueOf(nextID);

        messageRegistry.put(this.messageID, this); 
  
        
    }
     public String createMessageHash() {
      
            return  content;
        
    }
     public String getMessageID() {
        return messageID;
    }

    public static Message getMessageByID(String id) {
        return messageRegistry.get(id);
    }

    public static boolean deleteMessageByID(String id) {
      
        Message removed = messageRegistry.remove(id);
        if (removed != null) {
            int recycledID = Integer.parseInt(id);
            availableIDs.offer(recycledID); 
            return true;
    }
         return false;
     }
  public static boolean isIDAvailable() {
        return !availableIDs.isEmpty();
    }
   
    public static boolean checkRecipientCell(String cellNumber) {
        String regex = "^\\+27\\d{9}$";
        return Pattern.matches(regex, cellNumber);
    }

   
    public static boolean checkMessageContentLength(String messageContent) {
        return messageContent != null && messageContent.length() <= 250;
    }

    
    public String SentMessage(String recipient, String content) {
        
        return "Message sent";
    }

    
    public static void sendMessage(Message msg) {
        totalMessagesSent++;
        
    }


public static void storeMessage(Message msg) {
    storedMessages.add(msg);
}

public static List<Message> getStoredMessages() {
    return storedMessages;
}

   
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
   
    @Override
   public String toString() {
        return "Message ID: " + messageID +
               "\nFrom: " + sender +
               "\nTo: " + recipient +
               "\nMessage: " + content +
               "\nHash: " + createMessageHash();
    }
} 

