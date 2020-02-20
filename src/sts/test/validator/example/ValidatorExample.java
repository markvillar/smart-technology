/* Copyright (c) 2015-2018 Smart Technology Solutions Limited. All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of Smart
 * Technology Solutions Limited. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Smart
 * Technology Solutions Limited.
 *
 * SMART TECHNOLOGY SOLUTIONS LIMITED MAKES NO REPRESENTATIONS OR WARRANTIES
 * ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SMART TECHNOLOGY SOLUTIONS LIMITED
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. */

package sts.test.validator.example;

import org.apache.commons.lang3.ArrayUtils;
import sts.test.validator.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Example implementation of {@link Validator}. Candidates may modify this class
 * or write their own implementation.
 */
public class ValidatorExample implements Validator {

    /* Note: the required no-argument constructor is implicitly defined if no
     * other constructors are provided */

    /**
     * Indicate if the given message is valid.
     *
     * @param message The message to check
     * @return {@code true} if the message starts with STX, ends with ETX and the
     *         correct LRC, and has correctly-escaped data; {@code false}
     *         otherwise.
     * @todo Implement this method
     */
    public boolean isValid(byte[] message) {

        int stx = 0;
        int etx = message.length-2;

        List messageList = convertToList(message);
        List dataWithETX = messageList.subList(1,  message.length-1);
        List data = messageList.subList(1,  message.length-2);

        List<Byte> dataWithPrependedDLE = addDataLinkEscapes(data);

        byte calculatedLRC = calculateLRC(dataWithETX);
        byte messageLRC = message[message.length-1];

        System.out.println(" ");

        //Check the message structure.
        if (messageList.get(stx).hashCode() != 0x02 || messageList.get(etx).hashCode() != 0x03) {
            System.out.println("Message must start with a valid STX and end with a valid ETX");
            return false;
        }

        //Compare message LRC against calculated LRC.
        if (messageLRC != calculatedLRC) {
            System.out.println("Invalid LRC");
            return false;
        }

        //Add STX
        dataWithPrependedDLE.add(0, (byte) 0x02);

        //ADD ETX
        dataWithPrependedDLE.add((byte) dataWithPrependedDLE.size(), calculatedLRC);


        System.out.print("Full message (with DLEs): ");
        //Print the full message (including DLEs)
        for (Byte item : dataWithPrependedDLE) {
            String output = String.format("0x%02x", (byte) item);
            System.out.print(output+", ");
        }

        System.out.println("");

        String lrcCalculated = String.format("0x%02x", (byte) calculatedLRC);
        String lrcFromMessage = String.format("0x%02x", (byte) messageLRC);

        System.out.println("Calculated LRC: " + lrcCalculated);
        System.out.println("Message LRC (Last Byte): " + lrcFromMessage);

        return true;
    }

    //Converts an array of bytes to a List of Bytes
    private List convertToList(byte[] message) {
        Byte[] stream = ArrayUtils.toObject(message);
        List<Byte> messageStream = Arrays.asList(stream);
        return messageStream;
    }

    //Converts a List to array of bytes
    private byte[] convertToArray(List stream) {
        Byte[] bytesArray = new Byte[stream.size()];
        stream.toArray(bytesArray);
        byte[] bytes = ArrayUtils.toPrimitive(bytesArray);
        return bytes;
    }

    //Create a new list with DLEs (Data Link Escape).
    private List addDataLinkEscapes(List<Byte> message) {

        List<Byte> list = new ArrayList<Byte>();

        for (Object item : message) {

            Byte byteItem = (Byte) item;

            //Add DLE to 0x02, 0x03 and 0x10
            if (byteItem.byteValue() == (byte) 0x02 || byteItem.byteValue() == (byte) 0x03 || byteItem.byteValue() == (byte) 0x10) {
                list.add((byte) 0x10);
            }

            list.add(byteItem);
        }

        return list;

    }

    //Calculates the LRC byte of the message
    private byte calculateLRC(List message) {

        Byte[] messageArray = (Byte[]) message.toArray(new Byte[message.size()]);

        AtomicReference<Byte> resultHolder = new AtomicReference<>((byte) 0x00);

        for (Byte item : messageArray) {
            Byte aByte = resultHolder.updateAndGet(v -> (byte) (v ^ (byte) item));
        }

        return resultHolder.get().byteValue();
    }

}
