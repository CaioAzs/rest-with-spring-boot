package com.restspringboot.azsrest.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restspringboot.azsrest.exceptions.RequiredObjectNullException;
import com.restspringboot.azsrest.models.User;
import com.restspringboot.azsrest.repositories.UserRepository;
import com.restspringboot.azsrest.services.UserService;
import com.restspringboot.azsrest.unittests.mocks.MockUser;
import com.restspringboot.azsrest.vo.v1.UserVO;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	MockUser mockedUser;

	@InjectMocks
	private UserService userService;

	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {

		mockedUser = new MockUser();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() throws Exception {
		User entity = mockedUser.mockEntity(1);

		when(userRepository.findById(1L)).thenReturn(Optional.of(entity));

		var result = userService.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/user/v1/1>;rel=\"self\"]"));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("Female", result.getGender());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());

		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void testFindAll() {

		List<User> entitylist = mockedUser.mockEntityList();

		when(userRepository.findAll()).thenReturn(entitylist);

		var results = userService.findAll();
		assertNotNull(results);
		assertEquals(results.size(), 14);

		var user_test0 = results.get(0);
		var user_test1 = results.get(0);
		var user_test2 = results.get(0);
		assertNotNull(user_test0);
		assertNotNull(user_test1);
		assertNotNull(user_test2);
		
		assertNotNull(user_test0.getKey());
		assertNotNull(user_test0.getLinks());
		assertTrue(user_test0.toString().contains("links: [</api/user/v1/0>;rel=\"self\"]"));
		assertEquals("Address Test0", user_test0.getAddress());
		assertEquals("Male", user_test0.getGender());
		assertEquals("First Name Test0", user_test0.getFirstName());
		assertEquals("Last Name Test0", user_test0.getLastName());

		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void testPostUser() throws Exception {
		User entity = mockedUser.mockEntity(1);

		User persisted = entity; // Esse é diferente do primeiro pois quando cria-se um mock, ele nao vem com ID,
									// mas quando
									// a entidade é persistida no banco, ela passa a ter ID, isso sera usado no WHEN
									// abaixo
		persisted.setId(1L);

		UserVO vo = mockedUser.mockVO(1);
		vo.setKey(1L);

		when(userRepository.save(entity)).thenReturn(persisted);

		var result = userService.postUser(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/user/v1/1>;rel=\"self\"]"));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("Female", result.getGender());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());

		verifyNoMoreInteractions(userRepository);

	}

	@Test
	void testPostWithNullUser() throws Exception {

		Exception e = assertThrows(RequiredObjectNullException.class, () -> {
			userService.postUser(null);
		});

		String expectedMessage = "You cannot persist a null user.";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

		verifyNoMoreInteractions(userRepository);

	}

	@Test
	void testPutUser() throws Exception {
		User entity = mockedUser.mockEntity(1);
		entity.setId(1L);
		User persisted = entity; // Esse é diferente do primeiro pois quando cria-se um mock, ele nao vem com ID,
									// mas quando
									// a entidade é persistida no banco, ela passa a ter ID, isso sera usado no WHEN
									// abaixo
		persisted.setId(1L);

		UserVO vo = mockedUser.mockVO(1);
		vo.setKey(1L);

		when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
		when(userRepository.save(entity)).thenReturn(persisted);

		var result = userService.putUser(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/user/v1/1>;rel=\"self\"]"));
		assertEquals("Address Test1", result.getAddress());
		assertEquals("Female", result.getGender());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());

		verifyNoMoreInteractions(userRepository);
	}

	@Test
	void testPutWithNullUser() throws Exception {

		Exception e = assertThrows(RequiredObjectNullException.class, () -> {
			userService.postUser(null);
		});

		String expectedMessage = "You cannot persist a null user.";
		String actualMessage = e.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

		verifyNoMoreInteractions(userRepository);

	}

	@Test
	void testDeleteUser() throws Exception {
		User entity = mockedUser.mockEntity(1);

		when(userRepository.findById(1L)).thenReturn(Optional.of(entity));

		var result = userService.findById(1L);
		userService.deleteUser(1L);

	}

}
