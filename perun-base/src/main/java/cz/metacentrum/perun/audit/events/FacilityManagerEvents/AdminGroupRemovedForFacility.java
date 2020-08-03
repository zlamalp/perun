package cz.metacentrum.perun.audit.events.FacilityManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.Group;

public class AdminGroupRemovedForFacility extends AuditEvent implements EngineIgnoreEvent, FacilityEvent, GroupEvent {

	private Group group;
	private Facility facility;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AdminGroupRemovedForFacility() {
	}

	public AdminGroupRemovedForFacility(Group group, Facility facility) {
		this.group = group;
		this.facility = facility;
		this.message = formatMessage("%s was removed from admins of %s.", group, facility);
	}

	@Override
	public Group getGroup() {
		return group;
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
