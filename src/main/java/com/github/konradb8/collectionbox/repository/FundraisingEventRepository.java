package com.github.konradb8.collectionbox.repository;


import com.github.konradb8.collectionbox.model.event.FundraisingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FundraisingEventRepository extends JpaRepository<FundraisingEvent, Long> {
    FundraisingEvent findByName(String name);

}
