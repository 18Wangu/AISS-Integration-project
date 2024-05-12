package aiss.videominer.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aiss.videominer.model.Caption;

@Repository
public interface CaptionRepository extends JpaRepository<Caption, String> {
	
}
    
