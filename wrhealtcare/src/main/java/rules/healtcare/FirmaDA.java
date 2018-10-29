package rules.healtcare;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import it.cbt.wr.api.WorkSession;
import it.cbt.wr.api.service.repository.entities.Action;
import it.cbt.wr.api.service.repository.entities.Entity;
import it.cbt.wr.core.script.janino.WrRuleClassBody;

/**
 * @author Francesco Stimola
 *
 *         regola che firma una delibera da parte del DA.
 *
 *         VERSIONE 1.0
 */

public class FirmaDA implements WrRuleClassBody {

    WorkSession workSession;
    Logger logger;

    Entity entity;

    String instanceId;
    String taskId;

    void setParameters(Map parameters) {
        workSession = (WorkSession) parameters.get("workSession");
        logger = (Logger) parameters.get("logger");

        // entities = (List) parameters.get(Action.ENTITIES_PARAMETER);
        entity = (Entity) ((List) parameters.get(Action.ENTITIES_PARAMETER)).get(0);

        instanceId = (String) parameters.get("instanceId");
        taskId = (String) parameters.get("taskId");
    }

    @Override
    public Object run(Map parameters) {
        setParameters(parameters);

        HashMap mapRet = new HashMap();
        mapRet.put("id", entity.getId());
        mapRet.put("globalError", false);

        boolean wasSkippingPolicies = workSession.isSkippingPolicies();
        try {
            workSession.setSkipPolicies(true); // You should be admin, yet...

            logger.info("FirmaDA: inizio, id entity {}.", entity.getId());

            entity.setProperty("firmatoDA", Boolean.TRUE);
            entity.setProperty("firmatoDAIl", Calendar.getInstance());
            entity.setProperty("firmatarioDA", workSession.getCurrentProfile());
            entity.persist();
            workSession.save();

            entity = workSession.getEntityById(entity.getId());

            // effettuo la signal
            entity.signal(instanceId, taskId);

            logger.info("FirmaDA: fine.");

            return mapRet;
        } catch (Throwable ex) {
            logger.error("FirmaDA: errore durante l'invio a DA: " + ex.getMessage(), ex);
            mapRet.put("globalError", true);
            mapRet.put("errorMessage", "Errore durante la firma del Direttore Amministrativo, Contattare l'amministratore");
            return mapRet;
        } finally {
            workSession.setSkipPolicies(wasSkippingPolicies);
        }
    }
}
