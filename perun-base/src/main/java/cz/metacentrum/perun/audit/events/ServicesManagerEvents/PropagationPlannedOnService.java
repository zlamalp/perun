package cz.metacentrum.perun.audit.events.ServicesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.ServiceEvent;
import cz.metacentrum.perun.core.api.Service;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class PropagationPlannedOnService extends AuditEvent implements ServiceEvent {

	private Service service;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public PropagationPlannedOnService() {
	}

	public PropagationPlannedOnService(Service service) {
		this.service = service;
		this.message = formatMessage("propagation planned: On %s.", service);
	}

	@Override
	public Service getService() {
		return service;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message;
	}
}
