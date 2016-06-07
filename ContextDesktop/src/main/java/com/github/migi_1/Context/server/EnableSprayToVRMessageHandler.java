//package com.github.migi_1.Context.server;
//
//import com.github.migi_1.Context.main.Main;
//import com.github.migi_1.ContextMessages.EnableSprayToVRMessage;
//import com.github.migi_1.ContextMessages.MessageListener;
//
//public class EnableSprayToVRMessageHandler extends MessageListener<EnableSprayToVRMessage> {
//
//    private Main main;
//
//    public EnableSprayToVRMessageHandler(Main main) {
//        this.main = main;
//    }
//
//    @Override
//    public void messageReceived(Object source, EnableSprayToVRMessage message) {
//        System.out.println("ENABLE SPRAY MSG RECEIVED, SENDING TO: " + message.getPosition());
//        main.handleEnableSprayMessage(message.getPosition());
//    }
//
//    @Override
//    public Class<EnableSprayToVRMessage> getMessageClass() {
//        return EnableSprayToVRMessage.class;
//    }
//
//}
