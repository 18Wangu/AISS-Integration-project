package aiss.videominer.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import aiss.videominer.model.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
	
}
    
