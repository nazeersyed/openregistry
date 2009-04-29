package org.openregistry.core.domain.jpa.sor;

import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;
import org.javalid.annotations.validation.DateAfter;
import org.openregistry.core.domain.sor.SorRole;
import org.openregistry.core.domain.sor.SorSponsor;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.jpa.JpaRoleInfoImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorLeaveImpl;
import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.Campus;
import org.openregistry.core.domain.EmailAddress;
import org.openregistry.core.domain.Leave;
import org.openregistry.core.domain.OrganizationalUnit;
import org.openregistry.core.domain.Phone;
import org.openregistry.core.domain.RoleInfo;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Url;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 11:15:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity(name="sorRole")
@Table(name="prs_role_records")
@ValidateDefinition
public class JpaSorRoleImpl extends org.openregistry.core.domain.internal.Entity implements SorRole {

    @Id
    @Column(name="record_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_role_records_seq")
    @SequenceGenerator(name="prs_role_records_seq",sequenceName="prs_role_records_seq",initialValue=1,allocationSize=50)
    private Long recordId;

    @Column(name="id")
    @NotEmpty
    private String sorId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorUrlImpl.class)
    private Set<Url> urls = new HashSet<Url>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorEmailAddressImpl.class)
    private Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorPhoneImpl.class)
    private Set<Phone> phones = new HashSet<Phone>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch = FetchType.EAGER, targetEntity = JpaSorAddressImpl.class)
    private Set<Address> addresses = new HashSet<Address>();

    @Column(name="source_sor_id", nullable = false)
    @NotEmpty
    private String sourceSorIdentifier;

    @ManyToOne(optional = false)
    @JoinColumn(name="sor_person_id", nullable=false)
    private JpaSorPersonImpl person;

    @Column(name="percent_time",nullable=false)
    private int percentage;

    @ManyToOne(optional = false)
    @JoinColumn(name="person_status_t")
    private JpaTypeImpl personStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sorRole",fetch=FetchType.EAGER, targetEntity = JpaSorLeaveImpl.class)
    private Set<Leave> leaves = new HashSet<Leave>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private JpaRoleInfoImpl roleInfo;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="sponsor_id")
    @NotNull (customCode="sponsorRequiredMsg")
    private JpaSorSponsorImpl sponsor;

    @Column(name="affiliation_date",nullable=false)
    @Temporal(TemporalType.DATE)
    @NotNull(customCode="startDateRequiredMsg")
    private Date start;

    @Column(name="termination_date")
    @Temporal(TemporalType.DATE)
    @DateAfter(after = "#{parent.start}", customCode = "startDateEndDateCompareMsg")
    private Date end;

    @ManyToOne()
    @JoinColumn(name="termination_t")
    private JpaTypeImpl terminationReason;

    @Column(name="prc_role_id",nullable = true)
    private Long roleId;

    public JpaSorRoleImpl() {
        // nothing to do
    }
    
    public JpaSorRoleImpl(JpaRoleInfoImpl roleInfo, JpaSorPersonImpl sorPerson) {
        this.roleInfo = roleInfo;
        this.person = sorPerson;
    }

    public Long getId() {
        return this.recordId;
    }

    public String getSorId() {
        return this.sorId;
    }

    public void setSorId(String sorId){
        this.sorId = sorId;
    }

    public String getSourceSorIdentifier() {
        return this.sourceSorIdentifier;
    }

    public void setSourceSorIdentifier(final String sorIdentifier) {
        this.sourceSorIdentifier = sorIdentifier;
    }
    
    public RoleInfo getRoleInfo() {
    	return this.roleInfo;
    }

    public String getTitle() {
        return this.roleInfo.getTitle();
    }

    public Type getAffiliationType() {
        return this.roleInfo.getAffiliationType();
    }

    public int getPercentage() {
        return this.percentage;
    }

    public void setPercentage(final int percentage) {
        this.percentage = percentage;
    }

    public Type getPersonStatus() {
        return this.personStatus;
    }

    public void setPersonStatus(final Type personStatus) {
        if (!(personStatus instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.personStatus = (JpaTypeImpl) personStatus;
    }

    public SorSponsor setSponsor() {
       	final JpaSorSponsorImpl sponsor = new JpaSorSponsorImpl(this);
        this.sponsor = sponsor;
        return sponsor;
    }

    public SorSponsor getSponsor() {
        return this.sponsor;
    }
    public Type getTerminationReason() {
        return this.terminationReason;
    }

    public void setTerminationReason(final Type reason) {
        if (!(reason instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }
        this.terminationReason = (JpaTypeImpl) reason;
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setStart(final Date date) {
        this.start = date;
    }

    public void setEnd(final Date date) {
        this.end = date;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

	public Address addAddress() {
	    final JpaSorAddressImpl jpaAddress = new JpaSorAddressImpl(this);
	    this.addresses.add(jpaAddress);
	    return jpaAddress;
	}

	public EmailAddress addEmailAddress() {
	    final JpaSorEmailAddressImpl jpaEmailAddress = new JpaSorEmailAddressImpl(this);
	    this.emailAddresses.add(jpaEmailAddress);
	    return jpaEmailAddress;
	}

	public Phone addPhone() {
	    final JpaSorPhoneImpl jpaPhone = new JpaSorPhoneImpl(this);
	    this.phones.add(jpaPhone);
	    return jpaPhone;
	}

	public Url addUrl() {
	    final JpaSorUrlImpl jpaUrl = new JpaSorUrlImpl(this);
	    this.urls.add(jpaUrl);
	    return jpaUrl;
	}

	public Set<Address> getAddresses() {
		return this.addresses;
	}

    //work around to problems with binding to a set versus a list.
    @Transient
    private List<Address> addressList;

    public List<Address> getAddressList() {
        addressList = new ArrayList();
        Iterator iterator = getAddresses().iterator();
        while (iterator.hasNext()){
            Address address = (Address)iterator.next();
            addressList.add(address);
        }
        return this.addressList;
    }

    public void setAddressList(List list){
        this.addressList=list;
    }

	public Set<EmailAddress> getEmailAddresses() {
		return this.emailAddresses;
	}

    //work around to problems with binding to a set versus a list.
    @Transient
    private List<EmailAddress> emailAddressList;

    public List<EmailAddress> getEmailAddressList() {
        emailAddressList = new ArrayList();
        Iterator iterator = getEmailAddresses().iterator();
        while (iterator.hasNext()){
            EmailAddress emailAddress = (EmailAddress)iterator.next();
            emailAddressList.add(emailAddress);
        }
        return this.emailAddressList;
    }

    public void setEmailAddressList(List list){
        this.emailAddressList=list;
    }

	public Set<Leave> getLeaves() {
		return this.leaves;
	}

	public Set<Phone> getPhones() {
		return this.phones;
	}

    //work around to problems with binding to a set versus a list.
    @Transient
    private List<Phone> phoneList;

    public List<Phone> getPhoneList() {
        phoneList = new ArrayList();
        Iterator iterator = getPhones().iterator();
        while (iterator.hasNext()){
            Phone phone = (Phone)iterator.next();
            phoneList.add(phone);
        }
        return this.phoneList;
    }

    public void setPhoneList(List list){
        this.phoneList=list;
    }


	public Set<Url> getUrls() {
		return this.urls;
	}

	public Campus getCampus() {
		return this.roleInfo.getCampus();
	}

	public String getLocalCode() {
		// TODO Auto-generated method stub
		return null;
	}

	public OrganizationalUnit getOrganizationalUnit() {
		return this.roleInfo.getOrganizationalUnit();
	}
}
