package com.tesis.demo;

import com.tesis.demo.model.Geodata;
import com.tesis.demo.repository.GeodataDAO;
import com.tesis.demo.repository.GeodataRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@ComponentScan(basePackages = {"com.tesis.demo"})
@ContextConfiguration
@RunWith(SpringRunner.class)
public class GeodataRepositoryTest {

    @Autowired
    private GeodataDAO geodataDAO;

    @Autowired
    private GeodataRepository geodataRepository;

    @Before()
    public void before() {
        //geodataRepository.deleteAll();
    }

    @Test
    public void testFindDistinctByFileNameAndField() {
        List<String> result = geodataDAO.findDistinctByFileNameAndField("basilicas.csv", "LOCALIDAD");
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testFindByFileNameAndFieldAndFieldValue() {
        List<Geodata> result = geodataRepository.findByFileNameAndFieldAndFieldValue("basilicas.csv",
                "LOCALIDAD", "BUENOS AIRES");
        Assert.assertEquals(15, result.size());
    }

}
