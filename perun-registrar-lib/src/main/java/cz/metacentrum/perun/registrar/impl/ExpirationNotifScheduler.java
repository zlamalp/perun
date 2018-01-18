package cz.metacentrum.perun.registrar.impl;

import cz.metacentrum.perun.core.api.ExtSourcesManager;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Member;
import cz.metacentrum.perun.core.api.PerunClient;
import cz.metacentrum.perun.core.api.PerunPrincipal;
import cz.metacentrum.perun.core.api.PerunSession;
import cz.metacentrum.perun.core.api.Status;
import cz.metacentrum.perun.core.api.User;
import cz.metacentrum.perun.core.api.Vo;
import cz.metacentrum.perun.core.api.exceptions.AttributeNotExistsException;
import cz.metacentrum.perun.core.api.exceptions.ExtendMembershipException;
import cz.metacentrum.perun.core.api.exceptions.InternalErrorException;
import cz.metacentrum.perun.core.api.exceptions.MemberNotValidYetException;
import cz.metacentrum.perun.core.api.exceptions.WrongAttributeAssignmentException;
import cz.metacentrum.perun.core.api.exceptions.WrongAttributeValueException;
import cz.metacentrum.perun.core.api.exceptions.WrongReferenceAttributeValueException;
import cz.metacentrum.perun.core.bl.PerunBl;
import cz.metacentrum.perun.core.impl.Synchronizer;
import cz.metacentrum.perun.registrar.MailManager;
import cz.metacentrum.perun.registrar.model.Application;
import cz.metacentrum.perun.registrar.model.ApplicationMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcPerunTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Check current membership expiration and notify about future/past expirations.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public class ExpirationNotifScheduler {

	private final static Logger log = LoggerFactory.getLogger(Synchronizer.class);
	private PerunSession sess;

	private PerunBl perun;
	private MailManager mailManager;
	private JdbcPerunTemplate jdbc;

	public MailManager getMailManager() {
		return mailManager;
	}

	@Autowired
	public void setMailManager(MailManager mailManager) {
		this.mailManager = mailManager;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new JdbcPerunTemplate(dataSource);
	}

	public PerunBl getPerun() {
		return perun;
	}

	@Autowired
	public void setPerun(PerunBl perun) {
		this.perun = perun;
	}

	// Only members with following statuses will be notified
	private final static List<Status> allowedStatuses = new ArrayList<Status>();

	static{
		allowedStatuses.add(Status.VALID);
		allowedStatuses.add(Status.SUSPENDED);
	}

	public ExpirationNotifScheduler() {
	}

	/**
	 * Constructor for unit tests
	 *
	 * @param perun
	 * @throws Exception
	 */
	public ExpirationNotifScheduler(PerunBl perun) throws Exception {
		this.perun = perun;
		initialize();
	}

	public void initialize() throws InternalErrorException {
		String synchronizerPrincipal = "perunSynchronizer";
		this.sess = perun.getPerunSession(
				new PerunPrincipal(synchronizerPrincipal, ExtSourcesManager.EXTSOURCE_NAME_INTERNAL, ExtSourcesManager.EXTSOURCE_INTERNAL),
				new PerunClient());
	}

	/**
	 * Perform check on members status and switch it between VALID and EXPIRED (if necessary).
	 * Switching is based on current date and their value of membership expiration.
	 *
	 * Method also log audit messages, that membership will expired in X days or expired X days ago.
	 *
	 * Method is triggered by Spring scheduler (at midnight everyday).
	 */
	public void checkMembersState() {
		if (perun.isPerunReadOnly()) {
			log.debug("This instance is just read only so skip checking members states.");
			return;
		}

		try {

			log.debug("Processing checkMemberState() on (to be) expired members.");

			// get all available VOs
			List<Vo> vos = perun.getVosManagerBl().getVos(sess);
			Map<Integer, Vo> vosMap = new HashMap<Integer, Vo>();
			for (Vo vo : vos) {
				vosMap.put(vo.getId(), vo);
			}

			Calendar monthBefore = Calendar.getInstance();
			monthBefore.add(Calendar.MONTH, 1);

			// log message for all members which will expire in a month

			List<Member> expireInAMonth = perun.getSearcherBl().getMembersByExpiration(sess, "=", monthBefore);
			for (Member m : expireInAMonth) {
				notifyAboutFutureExpiration(vosMap.get(m.getVoId()), m, "{} will expire in a month in {}.");
			}

			// log message for all members which will expire in 14 days
			Calendar expireInA14Days = Calendar.getInstance();
			expireInA14Days.add(Calendar.DAY_OF_MONTH, 14);
			List<Member> expireIn14Days = perun.getSearcherBl().getMembersByExpiration(sess, "=", expireInA14Days);
			for (Member m : expireIn14Days) {
				notifyAboutFutureExpiration(vosMap.get(m.getVoId()), m, "{} will expire in 14 days in {}.");
			}

			// log message for all members which will expire in 7 days
			Calendar expireInA7Days = Calendar.getInstance();
			expireInA7Days.add(Calendar.DAY_OF_MONTH, 7);
			List<Member> expireIn7Days = perun.getSearcherBl().getMembersByExpiration(sess, "=", expireInA7Days);
			for (Member m : expireIn7Days) {
				notifyAboutFutureExpiration(vosMap.get(m.getVoId()), m, "{} will expire in 7 days in {}.");
			}

			// log message for all members which will expire tomorrow
			Calendar expireInADay = Calendar.getInstance();
			expireInADay.add(Calendar.DAY_OF_MONTH, 1);
			List<Member> expireIn1Days = perun.getSearcherBl().getMembersByExpiration(sess, "=", expireInADay);
			for (Member m : expireIn1Days) {
				notifyAboutFutureExpiration(vosMap.get(m.getVoId()), m, "{} will expire in 1 days in {}.");
			}

			// log message for all members which expired 7 days ago
			Calendar expiredWeekAgo = Calendar.getInstance();
			expiredWeekAgo.add(Calendar.DAY_OF_MONTH, -7);
			List<Member> expired7DaysAgo = perun.getSearcherBl().getMembersByExpiration(sess, "=", expiredWeekAgo);
			for (Member m : expired7DaysAgo) {
				if (Arrays.asList(Status.VALID, Status.SUSPENDED, Status.EXPIRED).contains(m.getStatus())) {
					if (didntSubmitExtensionApplication(m)) {
						// still didn't apply for extension
						getPerun().getAuditer().log(sess, "{} has expired {} days ago in {}.", m, 7, vosMap.get(m.getVoId()));
						sendExpirationNotification(vosMap.get(m.getVoId()), null, m, ApplicationMail.MailType.USER_EXPIRED);
					} else {
						log.debug("{} not notified about expiration, has submitted - pending application.", m);
					}
				} else {
					log.debug("{} not notified about expiration, is not in VALID, SUSPENDED or EXPIRED state.", m);
				}
			}

			// switch members, which expire today
			Calendar expireToday = Calendar.getInstance();
			List<Member> shouldBeExpired = perun.getSearcherBl().getMembersByExpiration(sess, "<=", expireToday);
			for (Member member : shouldBeExpired) {
				if (member.getStatus().equals(Status.VALID)) {
					try {
						perun.getMembersManagerBl().expireMember(sess, member);
						log.info("Switching {} to EXPIRE state, due to expiration {}.", member, (String) perun.getAttributesManagerBl().getAttribute(sess, member, "urn:perun:member:attribute-def:def:membershipExpiration").getValue());
					} catch (MemberNotValidYetException e) {
						log.error("Consistency error while trying to expire member {}, exception {}", member, e);
					}
				}
			}

			// switch members, which shouldn't be expired
			List<Member> shouldntBeExpired = perun.getSearcherBl().getMembersByExpiration(sess, ">", expireToday);
			for (Member member : shouldntBeExpired) {
				if (member.getStatus().equals(Status.EXPIRED)) {
					try {
						perun.getMembersManagerBl().validateMember(sess, member);
						log.info("Switching {} to VALID state, due to changed expiration {}.", member, (String) perun.getAttributesManagerBl().getAttribute(sess, member, "urn:perun:member:attribute-def:def:membershipExpiration").getValue());
					} catch (WrongAttributeValueException e) {
						log.error("Error during validating member {}, exception {}", member, e);
					} catch (WrongReferenceAttributeValueException e) {
						log.error("Error during validating member {}, exception {}", member, e);
					}
				}
			}

		} catch(InternalErrorException e){
			log.error("Synchronizer: checkMembersState, exception {}", e);
		} catch(AttributeNotExistsException e){
			log.warn("Synchronizer: checkMembersState, attribute definition for membershipExpiration doesn't exist, exception {}", e);
		} catch(WrongAttributeAssignmentException e){
			log.error("Synchronizer: checkMembersState, attribute name is from wrong namespace, exception {}", e);
		}
	}

	/**
	 * Check if member should be notified about future expiration and do it
	 *
	 * @param vo VO member is from
	 * @param member Member to notify
	 * @param auditerMessage Text of auditer message to use
	 * @throws InternalErrorException When implementation fails
	 */
	private void notifyAboutFutureExpiration(Vo vo, Member member, String auditerMessage) throws InternalErrorException {

		try {
			if (allowedStatuses.contains(member.getStatus())) {
				perun.getMembersManagerBl().canExtendMembershipWithReason(sess, member);
				if (didntSubmitExtensionApplication(member)) {
					// still didn't apply for extension
					getPerun().getAuditer().log(sess, auditerMessage, member, vo);
					sendExpirationNotification(vo, null, member, ApplicationMail.MailType.USER_WILL_EXPIRE);
				} else {
					log.debug("{} not notified about expiration, has submitted - pending application.", member);
				}
			} else {
				log.debug("{} not notified about expiration, is not in VALID or SUSPENDED state.", member);
			}
		} catch (ExtendMembershipException ex) {
			if (!Objects.equals(ex.getReason(), ExtendMembershipException.Reason.OUTSIDEEXTENSIONPERIOD)) {
				// we don't care about other reasons (LoA), user can update it later
				if (didntSubmitExtensionApplication(member)) {
					// still didn't apply for extension
					getPerun().getAuditer().log(sess, auditerMessage, member, vo);
					sendExpirationNotification(vo, null, member, ApplicationMail.MailType.USER_WILL_EXPIRE);
				} else {
					log.debug("{} not notified about expiration, has submitted - pending application.", member);
				}
			}
		}

	}

	/**
	 * Check if member didn't submit new extension application - in such case, do not send expiration notifications
	 *
	 * @param member Member to check applications for
	 * @return TRUE = didn't submit application / FALSE = otherwise
	 */
	private boolean didntSubmitExtensionApplication(Member member) {

		try {
			Application application = jdbc.queryForObject(RegistrarManagerImpl.APP_SELECT + " where a.id=(select max(id) from application where vo_id=? and apptype=? and user_id=? )", RegistrarManagerImpl.APP_MAPPER, member.getVoId(), String.valueOf(Application.AppType.EXTENSION), member.getUserId());
			return !Arrays.asList(Application.AppState.NEW, Application.AppState.VERIFIED).contains(application.getState());
		} catch (EmptyResultDataAccessException ex) {
			// has no application submitted
			return true;
		} catch (Exception ex) {
			log.error("Unable to check if {} has submitted pending application: {}.", member, ex);
			return true;
		}

	}

	/**
	 * Send expiration notifications using new way - defined per VO / group
	 *
	 * @param vo VO to relate notification to
	 * @param group Group to relate notification to
	 * @param member Member to notify to
	 * @param mailType Type of notification (will expire / expired)
	 */
	private void sendExpirationNotification(Vo vo, Group group, Member member, ApplicationMail.MailType mailType) {

		try {

			User user = perun.getUsersManagerBl().getUserByMember(sess, member);
			mailManager.sendExpirationInternal(vo, group, user, member, mailType);

		} catch (Exception ex) {
			log.error("Unable to send notification using new way! {}", ex);
		}

	}

}
