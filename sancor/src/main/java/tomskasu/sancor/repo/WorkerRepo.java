package tomskasu.sancor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tomskasu.sancor.entity.Worker;

public interface WorkerRepo extends JpaRepository<Worker, Long> {
}
