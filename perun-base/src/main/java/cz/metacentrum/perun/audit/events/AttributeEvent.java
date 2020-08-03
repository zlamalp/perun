package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Attribute;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Attribute object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface AttributeEvent {

	Attribute getAttribute();

}
