package com.justanothervitor.api_2.config.flyway;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FlywayCallback implements Callback {

    private static final Logger logger = LoggerFactory.getLogger(FlywayCallback.class);

    @Override
    public boolean supports(Event event, Context context){
        return event == Event.AFTER_MIGRATE;
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public void handle(Event event, Context context) {
        if(event == Event.AFTER_MIGRATE){
            logger.info("Migration completada com sucesso!");
            logger.info("Versão atual do schema:{}",context.getConfiguration().getTarget());
        }
    }

    @Override
    public String getCallbackName() {
        return "CustomFlywayCallback";
    }

}
