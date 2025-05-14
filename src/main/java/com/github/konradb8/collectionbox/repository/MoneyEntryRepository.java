package com.github.konradb8.collectionbox.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.github.konradb8.collectionbox.model.money.MoneyEntry;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyEntryRepository extends JpaRepository<MoneyEntry, Long> {
    MoneyEntry findByBoxUid(String uid);

}
