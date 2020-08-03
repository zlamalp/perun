package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.UserEvent;
import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.User;

public class AttributeChangedForUser extends AuditEvent implements AttributeEvent, UserEvent {

	private Attribute attribute;
	private User user;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeChangedForUser() {
	}

	public AttributeChangedForUser(Attribute attribute, User user) {
		this.attribute = attribute;
		this.user = user;
		this.message = formatMessage("%s changed for %s.", attribute, user);
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
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
