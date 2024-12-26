package com.csis231.api.model;

import jakarta.persistence.*;

@Table(name = "document")
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "license_plate")
    private String licensePlate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cctv_nb", nullable = false)
    private CCTV cctv;

    public Document() {}

    public Document(String name, String path, String licensePlate, User user, CCTV cctv) {
        this.name = name;
        this.path = path;
        this.licensePlate = licensePlate;
        this.user = user;
        this.cctv = cctv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CCTV getCctv() {
        return cctv;
    }

    public void setCctv(CCTV cctv) {
        this.cctv = cctv;
    }
}
