package ec.prueba.back.repository;

import ec.prueba.back.entity.CommerceQuarantineEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommerceQuarantineRepository
        extends PagingAndSortingRepository<CommerceQuarantineEntity, Long> {
}