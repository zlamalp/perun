package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeDefinitionEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.core.api.AttributeDefinition;

public class AttributeDeleted extends AuditEvent implements AttributeDefinitionEvent {

	private AttributeDefinition attributeDefinition;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeDeleted() {
	}

	public AttributeDeleted(AttributeDefinition attribute) {
		attributeDefinition = attribute;
		this.message = formatMessage("%s deleted.", attributeDefinition);
	}

	@Override
	public AttributeDefinition getAttributeDefinition() {
		return attributeDefinition;
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
