package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Owner;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Owner object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface OwnerEvent {

	Owner getOwner();

}
