package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.UserExtSource;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides UserExtSource object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface UserExtSourceEvent {

	UserExtSource getUserExtSource();

}
