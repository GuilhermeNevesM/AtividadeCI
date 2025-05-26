package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetControllerTest {

	private PetRepository petRepository;
	private PetController petController;

	@BeforeEach
	void setUp() {
		petRepository = mock(PetRepository.class);
		petController = new PetController(petRepository);
	}

	@Test
	void deveListarPets_comSucesso() {
		List<Pet> petsFakes = Arrays.asList(
				new Pet(1, "Rex", "Labrador", "Grande", "Cachorro"),
				new Pet(2, "Mimi", "Persa", "Pequeno", "Gato")
		);

		when(petRepository.findAll()).thenReturn(petsFakes);

		List<Pet> resultado = petController.listarPets();

		assertEquals(2, resultado.size());
		verify(petRepository, times(1)).findAll();
	}

	@Test
	void deveFalharAoListarPets_quandoRepositorioLancaExcecao() {
		when(petRepository.findAll()).thenThrow(new RuntimeException("Erro ao acessar o banco"));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			petController.listarPets();
		});

		assertEquals("Erro ao acessar o banco", exception.getMessage());
		verify(petRepository, times(1)).findAll();
	}


	@Test
	void deveCriarPet_comSucesso() {
		Pet novoPet = new Pet(null, "Rex", "Labrador", "Grande", "Cachorro");
		Pet petSalvo = new Pet(1, "Rex", "Labrador", "Grande", "Cachorro");

		when(petRepository.save(novoPet)).thenReturn(petSalvo);

		Pet resultado = petController.criarPet(novoPet);

		assertNotNull(resultado);
		assertEquals(1, resultado.getId());
		verify(petRepository, times(1)).save(novoPet);
	}

	@Test
	void deveFalharAoCriarPet_quandoRepositorioLancaExcecao() {
		Pet novoPet = new Pet(null, "Rex", "Labrador", "Grande", "Cachorro");

		when(petRepository.save(novoPet)).thenThrow(new RuntimeException("Erro ao salvar pet"));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			petController.criarPet(novoPet);
		});

		assertEquals("Erro ao salvar pet", exception.getMessage());
		verify(petRepository, times(1)).save(novoPet);
	}
}
