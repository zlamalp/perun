package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Host;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Host object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface HostEvent {

	Host getHost();

}
