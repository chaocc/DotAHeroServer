package com.electrotank.examples.chatlogger;

import com.electrotank.electroserver5.extensions.BaseManagedObjectFactory;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;

public class ChatLoggerFactory extends BaseManagedObjectFactory {

    private ChatLogger chatLogger;

    @Override
    public void init( EsObjectRO parameters ) {
            chatLogger = new ChatLogger(  );
            chatLogger.start();
            getApi().getLogger().debug("ChatLogger started");
    }

    @Override
    public Object acquireObject( EsObjectRO esObjectRO ) {
        return chatLogger;
    }

    @Override
    public void destroy() {
        getApi().getLogger().warn("ControllerFactory.destroy invoked");
        try {
            chatLogger.interrupt();
            chatLogger.dispose();
        } catch( Exception e ) {
            getApi().getLogger().error( "Unable to cleanly spin down", e );
        }
    }
}
