package cmpt276.demo.dao;

import cmpt276.demo.models.Files;
import cmpt276.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<Files, Integer> {

}