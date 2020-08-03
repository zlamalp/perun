package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.core.api.Facility;

public class AllAttributesRemovedForFacility extends AuditEvent implements EngineIgnoreEvent, FacilityEvent {

	private Facility facility;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AllAttributesRemovedForFacility() {
	}

	public AllAttributesRemovedForFacility(Facility facility) {
		this.facility = facility;
		this.message = formatMessage("All attributes removed for %s.", facility);
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
