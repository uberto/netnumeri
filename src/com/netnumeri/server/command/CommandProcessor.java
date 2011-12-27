package com.netnumeri.server.command;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.netnumeri.server.dao.NumheroUtils;
import com.netnumeri.shared.datacargo.CommandRequest;
import com.netnumeri.shared.datacargo.CommandResponse;
import com.netnumeri.shared.enumer.ApplicationCommandEnum;
import com.netnumeri.shared.finance.finpojo.User;
import com.netnumeri.shared.pojoc.BusinessAccount;
import com.netnumeri.shared.utils.ClientWarningException;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.util.logging.Logger;

//import com.numhero.client.model.pojoc.BusinessAccount;
//import com.numhero.client.model.pojoc.User;
//import com.numhero.server.NumheroUtils;
//import com.numhero.shared.datacargo.CommandRequest;
//import com.numhero.shared.datacargo.CommandResponse;
//import com.numhero.shared.exception.ClientWarningException;
//import com.numhero.shared.service.ApplicationCommandEnum;

@Singleton
public class CommandProcessor implements ICommandProcessor {
    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(CommandProcessor.class.getName());

    @Inject
    private PersistenceManagerFactory pmf;

    @Override
    public <REQUEST extends CommandRequest,
            RESPONSE extends CommandResponse> CommandResponse process(User user, BusinessAccount ba, REQUEST request) throws ClientWarningException {
        log.fine("processing request " + request.getClass().getSimpleName());
        ApplicationCommandEnum commandEnum = request.getCommand();
        ModelCommand<REQUEST, RESPONSE> command = getCommand(commandEnum);
        log.fine("processing command " + command.getClass().getSimpleName());

        RESPONSE response = null;
        command.setUser(user);
        command.setBusinessAccount(ba);

        checkGrantsOnUser(command);
        PersistenceManager pm = pmf.getPersistenceManagerProxy();
        try {
            response = command.execute(request);
        } catch (Exception e) {
            log.severe("process " + e.toString());
            e.printStackTrace();
            throw new ClientWarningException(e);
        } finally {
            pm.close();
        }
        return response;
    }

    private <REQUEST extends CommandRequest, RESPONSE extends
            CommandResponse> void checkGrantsOnUser(ModelCommand<REQUEST, RESPONSE>
                                                            commandBean) {
        User user = commandBean.getUser();
        //   TODO if (!user.getBAList.contains(baId)) throw new ValidationException
        //   TODO if (!user.getCurrentBARole().isGrantsOk(commandBean)) throw new ValidationException
    }

    @SuppressWarnings("unchecked")
    private <REQUEST extends CommandRequest, RESPONSE extends CommandResponse> ModelCommand<REQUEST, RESPONSE> getCommand(ApplicationCommandEnum commandEnum) {
        try {
            // TODO use guice instead of Class.forName
            log.fine("\n\n\n************************ " + this.getClass().getPackage().getName() + '.' + commandEnum.name());
            Class<ModelCommand<REQUEST, RESPONSE>> clazz = (Class<ModelCommand<REQUEST, RESPONSE>>) Class.forName(this.getClass().getPackage().getName() + '.' + commandEnum.name());
            ModelCommand<REQUEST, RESPONSE> commandBean = NumheroUtils.injector.getInstance(clazz);
            return commandBean;
        } catch (Exception ex) {
            log.severe("Could not find bean with name " + commandEnum.name() + ex.toString());
            throw new IllegalArgumentException("No commandEnum with the name " + commandEnum.name() + " was found.");
        }
    }
}
