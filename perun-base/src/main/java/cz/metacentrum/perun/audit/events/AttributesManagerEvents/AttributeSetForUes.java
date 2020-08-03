package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.UserExtSourceEvent;
import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.UserExtSource;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeSetForUes extends AuditEvent implements AttributeEvent, UserExtSourceEvent {

	private Attribute attribute;
	private UserExtSource ues;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeSetForUes() {
	}

	public AttributeSetForUes(Attribute attribute, UserExtSource ues) {
		this.attribute = attribute;
		this.ues = ues;
		this.message = formatMessage("%s set for %s.", attribute, ues);
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public UserExtSource getUserExtSource() {
		return ues;
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
