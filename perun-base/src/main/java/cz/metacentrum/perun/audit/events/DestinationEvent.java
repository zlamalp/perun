package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Destination;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Destination object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface DestinationEvent {

	Destination getDestination();

}
