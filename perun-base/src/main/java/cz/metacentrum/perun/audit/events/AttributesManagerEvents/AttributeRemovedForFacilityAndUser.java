package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeDefinitionEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.audit.events.UserEvent;
import cz.metacentrum.perun.core.api.AttributeDefinition;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.User;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeRemovedForFacilityAndUser extends AuditEvent implements AttributeDefinitionEvent, FacilityEvent, UserEvent {

	private AttributeDefinition attribute;
	private Facility facility;
	private User user;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeRemovedForFacilityAndUser() {
	}

	public AttributeRemovedForFacilityAndUser(AttributeDefinition attribute, Facility facility, User user) {
		this.attribute = attribute;
		this.facility = facility;
		this.user = user;
		this.message = formatMessage("%s removed for %s and %s.", attribute, facility, user);
	}

	@Override
	public AttributeDefinition getAttributeDefinition() {
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
