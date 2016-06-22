package edu.fae.avaliacaon2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.fae.avaliacaon2.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{

}
