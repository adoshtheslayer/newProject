package com.example.springjparelationships.respository;

import com.example.springjparelationships.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
