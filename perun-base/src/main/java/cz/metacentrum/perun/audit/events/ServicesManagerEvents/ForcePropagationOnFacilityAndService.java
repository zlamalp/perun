package cz.metacentrum.perun.audit.events.ServicesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineForceEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.audit.events.ServiceEvent;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.Service;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class ForcePropagationOnFacilityAndService extends AuditEvent implements EngineForceEvent, FacilityEvent, ServiceEvent {

	private Facility facility;
	private Service service;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public ForcePropagationOnFacilityAndService() {
	}

	public ForcePropagationOnFacilityAndService(Facility facility, Service service) {
		this.facility = facility;
		this.service = service;
		this.message = formatMessage("force propagation: On %s and %s.", facility, service);
	}

	@Override
	public Facility getFacility() {
		return facility;
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
