package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Resource;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Resource object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface ResourceEvent {

	Resource getResource();

}
