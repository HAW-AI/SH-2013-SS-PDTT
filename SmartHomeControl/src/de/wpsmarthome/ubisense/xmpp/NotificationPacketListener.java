/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.wpsmarthome.ubisense.xmpp;



import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

import android.content.Intent;
import android.util.Log;

/** 
 * This class notifies the receiver of incoming notification packets asynchronously.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationPacketListener implements PacketListener {

    private static final String LOGTAG = NotificationPacketListener.class.getSimpleName();

    private final XmppManager xmppManager;

    public NotificationPacketListener(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
    }

    @Override
    public void processPacket(Packet packet) {
        Log.d(LOGTAG, "NotificationPacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());

        if (packet instanceof NotificationIQ) {
            NotificationIQ notification = (NotificationIQ) packet;

            if (notification.getChildElementXML().contains(
                    "androidpn:iq:notification")) {
                String notificationId = notification.getId();
                String notificationApiKey = notification.getApiKey();
                String notificationSource = notification.getSource();
                final String notificationMessage = notification.getMessage();
                //                String notificationTicker = notification.getTicker();
                String notificationUri = notification.getUri();

                Intent intent = new Intent(XmppConstants.ACTION_SHOW_NOTIFICATION);
                intent.putExtra(XmppConstants.NOTIFICATION_ID, notificationId);
                intent.putExtra(XmppConstants.NOTIFICATION_API_KEY,
                        notificationApiKey);
                intent
                        .putExtra(XmppConstants.NOTIFICATION_SOURCE,
                                notificationSource);
                intent.putExtra(XmppConstants.NOTIFICATION_MESSAGE,
                        notificationMessage);

                xmppManager.getContext().sendBroadcast(intent);
            }
        }
    }

}
