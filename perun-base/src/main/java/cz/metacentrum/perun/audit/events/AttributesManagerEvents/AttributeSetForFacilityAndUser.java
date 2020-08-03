package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.audit.events.UserEvent;
import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.User;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeSetForFacilityAndUser extends AuditEvent implements AttributeEvent, FacilityEvent, UserEvent {

	private Attribute attribute;
	private Facility facility;
	private User user;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeSetForFacilityAndUser() {
	}

	public AttributeSetForFacilityAndUser(Attribute attribute, Facility facility, User user) {
		this.attribute = attribute;
		this.facility = facility;
		this.user = user;
		this.message = formatMessage("%s set for %s and %s.", attribute, facility, user);
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public Facility getFacility() {
		return facility;
	}

	@Override
	public User getUser() {
		return user;
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
