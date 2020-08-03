package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Member;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Member object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface MemberEvent {

	Member getMember();

}
