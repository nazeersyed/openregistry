package org.openregistry.core.domain.jpa.sor;

import org.openregistry.core.domain.internal.Entity;
import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.jpa.JpaTypeImpl;
import org.openregistry.core.domain.jpa.JpaRegionImpl;
import org.openregistry.core.domain.jpa.JpaCountryImpl;
import org.openregistry.core.domain.jpa.sor.JpaSorRoleImpl;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nancy Mond
 * Date: Apr 7, 2009
 * Time: 2:57:11 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity(name="sorAddress")
@Table(name="prs_addresses")
@Audited
public class JpaSorAddressImpl extends Entity implements Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prs_addresses_seq")
    @SequenceGenerator(name="prs_addresses_seq",sequenceName="prs_addresses_seq",initialValue=1,allocationSize=50)
    private Long id;

    @ManyToOne
    @JoinColumn(name="address_t")
    private JpaTypeImpl type;

    @Column(name="line1", nullable = true,length=100)
    private String line1;

    @Column(name="line2", nullable = true,length=100)
    private String line2;

    @Column(name="line3", nullable = true,length=100)
    private String line3;

    @ManyToOne
    @JoinColumn(name="region_id")
    private JpaRegionImpl region;

    @ManyToOne
    @JoinColumn(name="country_id")
    private JpaCountryImpl country;

    @Column(name="city",length=100,nullable = false)
    private String city;

    @Column(name="postal_code",length=9, nullable = false)
    private String postalCode;

    @ManyToOne(optional=false)
    @JoinColumn(name="role_record_id")
    private JpaSorRoleImpl sorRole;

    public JpaSorAddressImpl() {
        // nothing to do
    }

    public JpaSorAddressImpl(final JpaSorRoleImpl sorRole) {
        this.sorRole = sorRole;
    }

    protected Long getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public String getLine1() {
        return this.line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public String getLine3() {
        return this.line3;
    }

    public Region getRegion() {
        return this.region;
    }

    public Country getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setType(final Type type) {
        if (!(type instanceof JpaTypeImpl)) {
            throw new IllegalArgumentException("Requires type JpaTypeImpl");
        }

        this.type = (JpaTypeImpl) type;
    }

    public void setLine1(final String line1) {
        this.line1 = line1;
    }

    public void setLine2(final String line2) {
        this.line2 = line2;
    }

    public void setLine3(final String line3) {
        this.line3 = line3;
    }

    public void setRegion(final Region region) {
        if (region != null && !(region instanceof JpaRegionImpl)) {
            throw new IllegalStateException("Region implementation must be of type JpaRegionImpl");
        }
        this.region = (JpaRegionImpl) region;
    }

    public void setCountry(final Country country) {
        if (country != null && !(country instanceof JpaCountryImpl)) {
            throw new IllegalStateException("Country implementation must be of type JpaCountryImpl");
        }
        this.country = (JpaCountryImpl) country;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }
}
