package com.debanik.warehouse.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "warehouse")
@NamedQueries(
        {
                @NamedQuery(name = "UserForWarehouseEntry", query = "select a from Warehouse a where a.uuid=:uuid")
        }
)
public class Warehouse implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uuid")
    @NotNull
    private String uuid;

    @Column(name="product_capacity")
    @NotNull
    private Integer product_capacity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.DELETE)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Cascade(CascadeType.DELETE)
    private Products products;

    @Column(name = "date")
    @NotNull
    private ZonedDateTime timeStamp;

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getProduct_capacity() {
        return product_capacity;
    }

    public void setProduct_capacity(Integer product_capacity) {
        this.product_capacity = product_capacity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }



}
