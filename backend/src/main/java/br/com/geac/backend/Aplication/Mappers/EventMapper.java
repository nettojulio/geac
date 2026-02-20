package br.com.geac.backend.Aplication.Mappers;

import br.com.geac.backend.Aplication.DTOs.Reponse.EventResponseDTO;
import br.com.geac.backend.Aplication.DTOs.Reponse.LocationResponseDTO;
import br.com.geac.backend.Domain.Entities.Event;
import br.com.geac.backend.Domain.Entities.EventRequirement;
import br.com.geac.backend.Domain.Entities.Tag;
import br.com.geac.backend.Repositories.EventRequirementRepository;
import br.com.geac.backend.Repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final TagRepository tagRepository;
    private final EventRequirementRepository reqRepository;

    public EventResponseDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }

        LocationResponseDTO locationDTO = null;
        if (event.getLocation() != null) {
            locationDTO = new LocationResponseDTO(
                    event.getLocation().getId(),
                    event.getLocation().getName(),
                    event.getLocation().getStreet(),
                    event.getLocation().getNumber(),
                    event.getLocation().getNeighborhood(),
                    event.getLocation().getCity(),
                    event.getLocation().getState(),
                    event.getLocation().getZipCode(),
                    event.getLocation().getReferencePoint(),
                    event.getLocation().getCapacity()
            );
        }

        List<Tag> tags = tagRepository.findAllById(event.getTags().stream().map(Tag::getId).collect(Collectors.toSet()));
        List<String> names = tags.stream().map(Tag::getName).toList();
        EventRequirement requirement = reqRepository.findById(event.getRequirement().getId()).orElse(null);
        return new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getOnlineLink(),
                event.getStartTime(),
                event.getEndTime(),
                event.getWorkloadHours(),
                event.getMaxCapacity(),
                event.getStatus(),
                event.getCreatedAt(),

                event.getCategory() != null ? event.getCategory().getId() : null,
                event.getCategory() != null ? event.getCategory().getName() : null,

                locationDTO,

                event.getOrganizer() != null ? event.getOrganizer().getName() : null,
                event.getOrganizer() != null ? event.getOrganizer().getEmail() : null,

                event.getRequirement() != null ? event.getRequirement().getId() : null,
                requirement != null ? List.of(requirement.getDescription())  : null,
                event.getTags() != null ? names : null,
                List.of("Palestrante 1", "Palestrante 2") // Exemplo fixo
        );
    }

    public List<EventResponseDTO> toDTOList(List<Event> events) {
        return events.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}