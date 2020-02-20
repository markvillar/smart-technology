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

import sts.test.validator.Validator;

public class RunExample {

    public static void main(String[] arguments) {

        Validator validator = new ValidatorExample();

        byte[] message = { 0x02, 0x10, 0x02, 0x0A, 0x10, 0x10, 0x07, 0x08, 0x03, 0x14 };

        //Message with invalid LRC
        byte[] message2 = { 0x02, 0x10, 0x02, 0x0A, 0x09, 0x10, 0x07, 0x08, 0x03, 0x14 };

        //Message with invalid ETX
        byte[] message3 = { 0x02, 0x04, 0x0A, 0x02, 0x02, 0x10, 0x08, 0x08, 0x01, 0x1F };

        //Message with invalid STX
        byte[] message4 = { 0x08, 0x10, 0x02, 0x0A, 0x09, 0x10, 0x07, 0x08, 0x03, 0x14 };

        //Message with valid STX, ETX and LRC
        byte[] message5 = { 0x02, 0x10, 0x02, 0x0A, 0x18, 0x0A, 0x07, 0x08, 0x03, 0x06 };

        System.out.println("Message 1 is valid: " + validator.isValid(message));
        System.out.println("Message 2 is valid: " + validator.isValid(message2));
        System.out.println("Message 3 is valid: " + validator.isValid(message3));
        System.out.println("Message 4 is valid: " + validator.isValid(message4));
        System.out.println("Message 5 is valid: " + validator.isValid(message5));

    }
}
