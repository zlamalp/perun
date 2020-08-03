package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeDefinitionEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.UserExtSourceEvent;
import cz.metacentrum.perun.core.api.AttributeDefinition;
import cz.metacentrum.perun.core.api.UserExtSource;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeRemovedForUes extends AuditEvent implements AttributeDefinitionEvent, UserExtSourceEvent {

	private AttributeDefinition attribute;
	private UserExtSource ues;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeRemovedForUes() {
	}

	public AttributeRemovedForUes(AttributeDefinition attribute, UserExtSource ues) {
		this.attribute = attribute;
		this.ues = ues;
		this.message = formatMessage("%s removed for %s.", attribute, ues);
	}

	@Override
	public AttributeDefinition getAttributeDefinition() {
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
