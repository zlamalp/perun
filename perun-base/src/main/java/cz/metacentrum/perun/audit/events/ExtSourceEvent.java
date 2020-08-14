package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.ExtSource;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides ExtSource object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface ExtSourceEvent {

	ExtSource getExtSource();

}
