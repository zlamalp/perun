package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Vo;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Vo object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface VoEvent {

	Vo getVo();

}
