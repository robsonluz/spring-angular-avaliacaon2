package edu.fae.avaliacaon2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.fae.avaliacaon2.model.Hotel;
import edu.fae.avaliacaon2.model.Reserva;
import edu.fae.avaliacaon2.repository.HotelRepository;
import edu.fae.avaliacaon2.repository.ReservaRepository;

@RestController
@RequestMapping("/api/hoteis")
public class HotelController {
	@Autowired private HotelRepository hotelRepository;
	@Autowired private ReservaRepository reservaRepository;

	//http://localhost:8081/api/hoteis GET
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Hotel show(@PathVariable Long id) {
		return hotelRepository.findOne(id);
	}	
	
	//http://localhost:8081/api/hoteis POST
	@RequestMapping(value="", method=RequestMethod.POST)
	public Hotel save(@RequestBody Hotel hotel) {
		hotelRepository.save(hotel);
		return hotel;
	}	
	
	
	@RequestMapping(value="/{idHotel}/reservar", method=RequestMethod.POST)
	public Hotel reservar(@PathVariable Long idHotel, @RequestBody Reserva reserva) {
		Hotel hotel = hotelRepository.findOne(idHotel);
		if(hotel.getVagasDisponiveis() > 0) {
			reserva.setHotel(hotel);
			hotel.getReservas().add(reserva);
			hotel.setVagasDisponiveis(hotel.getVagasDisponiveis() - 1);
			
			reservaRepository.save(reserva);
			hotelRepository.save(hotel);
		}
		return hotel;
	}
	
	@RequestMapping(value="/{idHotel}/cancelarReserva/{idReserva}", method=RequestMethod.POST)
	public Hotel cancelarReserva(@PathVariable Long idHotel, @PathVariable Long idReserva) {
		
		Hotel hotel = hotelRepository.findOne(idHotel);
		Reserva reserva = reservaRepository.findOne(idReserva);
		
		hotel.setVagasDisponiveis(hotel.getVagasDisponiveis() + 1);
		hotel.getReservas().remove(reserva);
		
		reservaRepository.delete(reserva);
		hotelRepository.save(hotel);
		
		return hotel;
	}
}
