package be.thomasmore.myapp.repositories;

import be.thomasmore.myapp.model.Members;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository <Members,Integer> {

}
