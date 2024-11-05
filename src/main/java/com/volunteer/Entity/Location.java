package com.volunteer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "Location")
public class Location {

    @Id
    @Column(name = "province_code", length = 7)
    private String provinceCode;

    @Column(name = "city_code", length = 7)
    private String cityCode;
//
//    @Column(name = "province_name", length = 50)
//    private String provinceName;
//
//    @Column(name = "city_name", length = 50)
//    private String cityName;
}
