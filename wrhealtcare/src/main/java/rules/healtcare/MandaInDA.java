package rules.healtcare;

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
 *         regola che Invia a DA una delibera.
 *
 *         VERSIONE 1.0
 */

public class MandaInDA implements WrRuleClassBody {

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

            logger.info("MandaInDA: inizio, id entity {}.", entity.getId());

            // Imposto la variabile di transizione
            workSession.getWorkflowManager().getWorkflowInstance(instanceId).setVariable("transition", "mandaADA");

            // effettuo la signal
            entity.signal(instanceId, taskId);

            logger.info("MandaInDA: fine.");

            return mapRet;
        } catch (Throwable ex) {
            logger.error("MandaInDA: errore durante l'invio a DA: " + ex.getMessage(), ex);
            mapRet.put("globalError", true);
            mapRet.put("errorMessage", "Errore durante l'invio a DA, Contattare l'amministratore");
            return mapRet;
        } finally {
            workSession.setSkipPolicies(wasSkippingPolicies);
        }
    }
}
