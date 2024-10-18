package com.example.gnosi.usergnosi;

import com.example.gnosi.usergnosi.UsergnosiApplication;
import com.example.gnosi.usergnosi.controller.CreateUserDto;
import com.example.gnosi.usergnosi.controller.UpdateUserDto;
import com.example.gnosi.usergnosi.entity.User;
import com.example.gnosi.usergnosi.repository.UserRepository;
import com.example.gnosi.usergnosi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = UsergnosiApplication.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateUser() {
		// Arrange
		CreateUserDto createUserDto = new CreateUserDto("John", "Doe", "john.doe@example.com", "password123", "STUDENT");
		User user = new User(createUserDto.firstName(), createUserDto.lastName(), createUserDto.email(), createUserDto.password(), createUserDto.userType());
		UUID expectedUserId = UUID.randomUUID();

		when(userRepository.save(any(User.class))).thenReturn(new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getUserType()));

		// Act
		UUID actualUserId = userService.createUser(createUserDto);

		// Assert
		assertNotNull(actualUserId);
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void testGetUserById_UserExists() {
		// Arrange
		UUID userId = UUID.randomUUID();
		User user = new User("John", "Doe", "john.doe@example.com", "password123", "STUDENT");
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// Act
		Optional<User> result = userService.getUserById(userId.toString());

		// Assert
		assertTrue(result.isPresent());
		assertEquals(user.getEmail(), result.get().getEmail());
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	void testGetUserById_UserDoesNotExist() {
		// Arrange
		UUID userId = UUID.randomUUID();
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Act
		Optional<User> result = userService.getUserById(userId.toString());

		// Assert
		assertFalse(result.isPresent());
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	void testListUsers() {
		// Arrange
		User user1 = new User("John", "Doe", "john.doe@example.com", "password123", "STUDENT");
		User user2 = new User("Jane", "Doe", "jane.doe@example.com", "password456", "STUDENT");
		when(userRepository.findAll()).thenReturn(List.of(user1, user2));

		// Act
		List<User> result = userService.listUsers();

		// Assert
		assertEquals(2, result.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void testUpdateUserById_UserExists() {
		// Arrange
		UUID userId = UUID.randomUUID();
		UpdateUserDto updateUserDto = new UpdateUserDto("Johnny", "Smith", null);
		User existingUser = new User("John", "Doe", "john.doe@example.com", "password123", "STUDENT");

		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

		// Act
		userService.updateUserById(userId.toString(), updateUserDto);

		// Assert
		assertEquals("Johnny", existingUser.getFirstName());
		assertEquals("Smith", existingUser.getLastName());
		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, times(1)).save(existingUser);
	}

	@Test
	void testUpdateUserById_UserDoesNotExist() {
		// Arrange
		UUID userId = UUID.randomUUID();
		UpdateUserDto updateUserDto = new UpdateUserDto("Johnny", "Smith", null);

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Act
		userService.updateUserById(userId.toString(), updateUserDto);

		// Assert
		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testDeleteById_UserExists() {
		// Arrange
		UUID userId = UUID.randomUUID();
		when(userRepository.existsById(userId)).thenReturn(true);

		// Act
		userService.deleteById(userId.toString());

		// Assert
		verify(userRepository, times(1)).existsById(userId);
		verify(userRepository, times(1)).deleteById(userId);
	}

	@Test
	void testDeleteById_UserDoesNotExist() {
		// Arrange
		UUID userId = UUID.randomUUID();
		when(userRepository.existsById(userId)).thenReturn(false);

		// Act
		userService.deleteById(userId.toString());

		// Assert
		verify(userRepository, times(1)).existsById(userId);
		verify(userRepository, never()).deleteById(any(UUID.class));
	}
}
