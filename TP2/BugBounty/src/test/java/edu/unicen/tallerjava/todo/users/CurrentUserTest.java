package edu.unicen.tallerjava.todo.users;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CurrentUserTest {

	CurrentUserService currentUser;

	@BeforeEach
	public void setup(){
		this.currentUser = new CurrentUserService();
	}

	/**
	 * El servicio debería retornar un usuario por defecto.
	 */
	@Test
	public void testGetsDefaultUser() {
		User user = currentUser.getCurrent();
		assertNotNull(user);
	}

	/**
	 * Chequea que si 2 threads acceden de forma concurrente obtentan el mismo usuario.
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void testCurrentConcurrent() throws InterruptedException {
		for (int i = 0; i < 10000; i++) {
			// En este caso creamos una instancia separada para crear uno nuevo
			// en cada iteración
			CurrentUserService testSvc = new CurrentUserService();

			final User[] users = new User[2];

			Thread t1 = new Thread(() -> {
				users[0] = testSvc.getCurrent();
			});
			Thread t2 = new Thread(() -> {
				users[1] = testSvc.getCurrent();
			});

			t1.start();
			t2.start();

			t1.join();
			t2.join();
			assertNotNull(users[0]);
			assertNotNull(users[1]);
			assertTrue(users[0]==users[1]);
		}
	}
}
