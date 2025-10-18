package br.com.fullcycle.hexagonal.infra.jpa.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private LocalDate date;

    private int totalSpots;

    @ManyToOne(fetch = FetchType.LAZY)
    private PartnerEntity partnerEntity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<TicketEntity> ticketEntities;

    public EventEntity() {
        this.ticketEntities = new HashSet<>();
    }

    public EventEntity(Long id, String name, LocalDate date, int totalSpots, Set<TicketEntity> ticketEntities) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.totalSpots = totalSpots;
        this.ticketEntities = ticketEntities != null ? ticketEntities : new HashSet<>();
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public PartnerEntity getPartner() {
        return partnerEntity;
    }

    public void setPartner(PartnerEntity partnerEntity) {
        this.partnerEntity = partnerEntity;
    }

    public Set<TicketEntity> getTickets() {
        return ticketEntities;
    }

    public void setTickets(Set<TicketEntity> ticketEntities) {
        this.ticketEntities = ticketEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity eventEntity = (EventEntity) o;
        return Objects.equals(id, eventEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
