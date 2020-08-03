package cz.metacentrum.perun.audit.events.FacilityManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.core.api.Facility;

public class FacilityDeleted extends AuditEvent implements FacilityEvent {

	private Facility facility;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public FacilityDeleted() {
	}

	public FacilityDeleted(Facility facility) {
		this.facility = facility;
		this.message = formatMessage("%s deleted.", facility);
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
