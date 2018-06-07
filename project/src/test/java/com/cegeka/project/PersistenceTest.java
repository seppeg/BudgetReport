package com.cegeka.project;

import com.cegeka.project.infrastructure.ZookeeperFacade;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {"spring.cloud.zookeeper.enabled=false"}
)
@ExtendWith({SpringExtension.class, PostgresqlContainerTestExtension.class})
@Transactional
@Tag("persistence")
@DirtiesContext
public abstract class PersistenceTest {

    @MockBean
    private ZookeeperFacade zookeeperFacade;

    @PersistenceContext(type= PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    protected void flush(){
        entityManager.flush();
    }
}
