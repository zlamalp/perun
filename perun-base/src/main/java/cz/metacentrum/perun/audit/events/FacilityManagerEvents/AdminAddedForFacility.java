package cz.metacentrum.perun.audit.events.FacilityManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.audit.events.UserEvent;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.User;

public class AdminAddedForFacility extends AuditEvent implements EngineIgnoreEvent, UserEvent, FacilityEvent {

	private User user;
	private Facility facility;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AdminAddedForFacility() {
	}

	public AdminAddedForFacility(User user, Facility facility) {
		this.user = user;
		this.facility = facility;
		this.message = formatMessage("%s was added as admin of %s.", user, facility);
	}

	@Override
	public User getUser() {
		return user;
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
