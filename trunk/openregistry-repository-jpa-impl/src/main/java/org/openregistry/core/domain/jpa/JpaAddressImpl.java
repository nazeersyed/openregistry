package org.openregistry.core.domain.jpa;

import org.openregistry.core.domain.Address;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.Region;
import org.openregistry.core.domain.Country;
import org.openregistry.core.domain.internal.Entity;

import javax.persistence.*;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 1.0.0
 *
 */
@javax.persistence.Entity(name="address")
@Table(name="prs_addresses")
public class JpaAddressImpl extends Entity implements Address {

    @Id
    @Column(name="address_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prd_address_seq")
    private Long id;

    private JpaTypeImpl type;

    @Column(name="line1", nullable = true,length=100)
    private String line1;

    @Column(name="line2", nullable = true,length=100)
    private String line2;

    @Column(name="line3", nullable = true,length=100)
    private String line3;

    private JpaRegionImpl jpaRegion;

    private JpaCountryImpl jpaCountry;

    @Column(name="city",length=100,nullable = false)
    private String city;

    @Column(name="postal_code",length=9, nullable = false)
    private String postalCode;

    @ManyToOne(optional=false)
    @JoinColumn(name="prs_sor_role_record_id")
    private JpaRoleImpl role;

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
        return this.jpaRegion;
    }

    public Country getCountry() {
        return this.jpaCountry;
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
        if (!(region instanceof JpaRegionImpl)) {
            throw new IllegalStateException("Region implementation must be of type JpaRegionImpl");
        }
        this.jpaRegion = (JpaRegionImpl) region;
    }

    public void setCountry(final Country country) {
        if (!(country instanceof JpaCountryImpl)) {
            throw new IllegalStateException("Country implementation must be of type JpaCountryImpl");
        }
        this.jpaCountry = (JpaCountryImpl) country;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }
}
