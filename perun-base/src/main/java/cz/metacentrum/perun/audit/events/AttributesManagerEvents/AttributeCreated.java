package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeDefinitionEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.core.api.AttributeDefinition;

public class AttributeCreated extends AuditEvent implements EngineIgnoreEvent, AttributeDefinitionEvent {

	private AttributeDefinition attributeDefinition;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeCreated() {
	}

	public AttributeCreated(AttributeDefinition attribute) {
		this.attributeDefinition = attribute;
		this.message = formatMessage("%s created.", attributeDefinition);
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
