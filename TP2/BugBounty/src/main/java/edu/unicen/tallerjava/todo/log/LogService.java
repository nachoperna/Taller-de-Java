package edu.unicen.tallerjava.todo.log;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.unicen.tallerjava.todo.users.User;

/**
 * El servicio de logs.
 */
@Service
public class LogService {
	private final HashMap<User, List<Log>> logs = new HashMap<>();

	public List<Log> getLogs() {
		return logs.values().stream().flatMap(list -> list.stream()).collect(Collectors.toList());
	}

	/**
	 * Este método agrega un log a la lista de logs.
	 * 
	 * @param action La acción a logear
	 * @param user   El usuario que generó la acción
	 */
	public void addLog(String action, User user) {
		Log log = new Log(UUID.randomUUID(), action, user);
		List<Log> list = logs.entrySet().stream()
                  .filter(entry -> entry.getKey().getName().equals(user.getName()))
                  .map(Map.Entry::getValue)
                  .findFirst()
                  .orElse(null);
		if (list == null) {
			list = new ArrayList<>();
			logs.put(user, list);
		}
		list.add(log);
	}

	/**
	 * Limpia la lista de logs.
	 */
	public void clear() {
		this.logs.clear();
	}

	public List<Log> getUserLogs(User user) {
		return this.logs.entrySet().stream()
                  .filter(entry -> entry.getKey().getName().equals(user.getName()))
                  .map(Map.Entry::getValue)
                  .findFirst()
                  .orElse(null);
	}
}
