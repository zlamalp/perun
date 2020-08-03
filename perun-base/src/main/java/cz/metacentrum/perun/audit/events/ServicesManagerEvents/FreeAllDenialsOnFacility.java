package cz.metacentrum.perun.audit.events.ServicesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.core.api.Facility;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class FreeAllDenialsOnFacility extends AuditEvent implements FacilityEvent {

	private Facility facility;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public FreeAllDenialsOnFacility() {
	}

	public FreeAllDenialsOnFacility(Facility facility) {
		this.facility = facility;
		this.message = formatMessage("free all denials: on %s.", facility);
	}

	@Override
	public Facility getFacility() {
		return facility;
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
