package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.AttributeDefinition;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides AttributeDefinition object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface AttributeDefinitionEvent {

	AttributeDefinition getAttributeDefinition();

}
