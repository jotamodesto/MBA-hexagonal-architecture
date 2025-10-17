package br.com.fullcycle.hexagonal.infra.dtos;

public record NewEventDTO(String name, String date, Integer totalSpots, String partnerId) {
}
