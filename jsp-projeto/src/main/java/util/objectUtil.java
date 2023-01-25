package util;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class objectUtil {
	private objectUtil(){  }

	public static boolean objetoValidacao(Object object) {
		return nonNull(object) && !object.toString().isEmpty();
	}
	public static boolean objetosValidacao(Object... objects) {
		for (Object obj : objects) {
			if (isNull(obj) || obj.toString().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	public static boolean isObjectsNotValid(Object... objects) {
		return !objetosValidacao(objects);
	}
}